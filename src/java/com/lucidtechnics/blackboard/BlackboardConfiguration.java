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
	private int maxWorkspaceThread = 40;
	private int maxPersistenceThread = 60;
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
			
	public void setErrorManager(ErrorManager _errorManager) { errorManager = _errorManager; }
	public void setMaxBlackboardThread(int _maxBlackboardThread) { maxBlackboardThread = _maxBlackboardThread; }
	public void setMaxScheduledBlackboardThread(int _maxScheduledBlackboardThread) { maxScheduledBlackboardThread = _maxScheduledBlackboardThread; }
	public void setMaxWorkspaceThread(int _maxWorkspaceThread) { maxWorkspaceThread = _maxWorkspaceThread; }
	public void setMaxPersistenceThread(int _maxPersistenceThread) { maxPersistenceThread = _maxPersistenceThread; }
	public void setMaxWorkspace(int _maxWorkspace) { maxWorkspace = _maxWorkspace; }
	public void setPersister(Persister _persister) { persister = _persister; }
	public void setTimePlans(boolean _timePlans) { timePlans = _timePlans; }
	public void setAppsHome(String _appsHome) { appsHome = _appsHome; }
}