/*
* Copyright 2002-2006 Bediako George.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.lucidtechnics.blackboard;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Date;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;
import com.lucidtechnics.blackboard.config.EventConfiguration;

import com.lucidtechnics.blackboard.util.Guard;
import com.lucidtechnics.blackboard.util.error.ErrorManager;
import com.lucidtechnics.blackboard.util.PropertyUtil;

public class BlackboardConfiguration
{
	private ErrorManager errorManager = com.lucidtechnics.blackboard.util.error.ErrorManager.getInstance();
	private int maxBlackboardThread = 1;
	private int maxScheduledBlackboardThread = 1;
	private int maxWorkspaceThread = 2;
	private int maxPersistenceThread = 2;
	private int maxWorkspace = 100000;
	private Persister persister = new InkwellPersister("." + java.io.File.separator + "blackboard" + java.io.File.separator + "persistence");
	private boolean timePlans = true;
	private String appsHome = "." + java.io.File.separator + "blackboard" + java.io.File.separator + "apps";

	public ErrorManager getErrorManager() { return errorManager; }
	public int getMaxBlackboardThread() { return maxBlackboardThread; }
	public int getMaxScheduledBlackboardThread() { return maxScheduledBlackboardThread; }
	public int getMaxWorkspaceThread() { return maxWorkspaceThread; }
	public int getMaxPersistenceThread() { return maxPersistenceThread; }
	public int getMaxWorkspace() { return maxWorkspace; }
	public Persister getPersister() { return persister; }
	public boolean getTimePlans() { return timePlans; }
	public String getAppsHome() { return appsHome; }


	/**
	 * Set the {@link ErrorManager} the {@link Blackboard} should use to manage
	 * and report errors.
	 * 
	 * @param _errorManager a object that extends {@link ErrorManager}
	 *
	 * @see ErrorManager
	 * @see Blackboard
	 * 
	 */

	public void setErrorManager(ErrorManager _errorManager) { errorManager = _errorManager; }

	/**
	 * Set the maximum number of threads that should be available to
	 * recieve new events placed on the Blackboard. As such these thread
	 * represents the entry point for new activity for the {@link
	 * Blackboard}.
	 *
	 * The default value of 1 should be sufficient for most applications. 
	 * 
	 * @param _maxBlackboardThread the maximum number of threads to be
	 * made available for event reception. 
	 *
	 * @see Blackboard
	 * 
	 */

	public void setMaxBlackboardThread(int _maxBlackboardThread) { maxBlackboardThread = _maxBlackboardThread; }

	/**
	 * Set the maximum number of threads that should be available to
	 * recieve new events to be scheduled for arrival on the Blackboard.
	 * As such these thread represents the entry point for new activity
	 * for the {@link Blackboard}.
	 *
	 * The default value of 1 should be sufficient for most applications. 
	 * 
	 * @param _maxScheduledBlackboardThread the maximum number of threads to be
	 * made available for scheduled event reception.
	 *
	 * @see Blackboard
	 * 
	 */

	public void setMaxScheduledBlackboardThread(int _maxScheduledBlackboardThread) { maxScheduledBlackboardThread = _maxScheduledBlackboardThread; }

	/**
	 * Set the maximum number of threads that should be available to
	 * process {@link Workspace}s.  Depending on how intensive your
	 * application is adjusting this value may have an effect on how
	 * your application performs.
	 * 
	 * @param _maxWorkspaceThread the maximum number of threads to be
	 * made available for event reception. 
	 *
	 * @see Blackboard
	 * 
	 */

	public void setMaxWorkspaceThread(int _maxWorkspaceThread) { maxWorkspaceThread = _maxWorkspaceThread; }

	/**
	 * Set the maximum number of threads that should be available to
	 * automatically persist {@link Workspace}s upon completion.
	 * Depending on how intensive your application is adjusting this
	 * value may have an effect on how your application performs.
	 * 
	 * @param _maxPersistenceThread the maximum number of threads to be
	 * made available for workspace persistence. 
	 *
	 * @see Persister
	 * 
	 */
	
	public void setMaxPersistenceThread(int _maxPersistenceThread) { maxPersistenceThread = _maxPersistenceThread; }

	/**
	 * Set the maximum number of {@link Workspace}s that should be available in
	 * memory.  Depending on how intensive your application is adjusting this
	 * value may have an effect on how your application performs.
	 * Whenever the Blackboard exceeds this many workspaces, idle
	 * workspaces may be persisted via the assigned persister.
	 *
	 * Only use this option if there is a memory concern.  If the
	 * processing threads counts are adjusted accordingly, the default
	 * value of 100000 should prove to be sufficient.
	 * 
	 * @param _maxWorkspaceThread the maximum number of workspaces to be
	 * made available in memory. 
	 *
	 * @see Workspace
	 * 
	 */

	public void setMaxWorkspace(int _maxWorkspace) { maxWorkspace = _maxWorkspace; }

	/**
	 * Set the {@link Persister} that should be available to
	 * persist {@link Workspace}es.
	 *
	 * The Inkwell persister is already set as the default.
	 * 
	 * @param _persister the maximum number of threads to be
	 * made available for event reception. 
	 *
	 * @see Blackboard
	 * 
	 */

	public void setPersister(Persister _persister) { persister = _persister; }

	/**
	 * Turn on plan processing monitoring.  The times for plan
	 * processing will be posted to a log4j file.  This should be set
	 * to false for production support.
	 * 
	 * @param _timePlans set to true iff timing plans is required.
	 *
	 * @see Blackboard
	 * 
	 */

	public void setTimePlans(boolean _timePlans) { timePlans = _timePlans; }

	/**
	 * The root directory for all the apps to be run by this blackboard.
	 *  
	 * @param _appsHome the root directory for all the applications to
	 * be run by this Blackboard.
	 *
	 * @see Blackboard
	 * 
	 */

	public void setAppsHome(String _appsHome) { appsHome = _appsHome; }
}