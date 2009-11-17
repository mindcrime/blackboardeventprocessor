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

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorkspaceExecutionContext
   implements java.io.Serializable
{
	private static transient Log logger = LogFactory.getLog(WorkspaceExecutionContext.class);

	private TargetSpace targetSpace;
	private int workspaceExecutorName;
	private Object workspaceIdentifier;

	protected TargetSpace getTargetSpace() { return targetSpace; }
	protected int getWorkspaceExecutorName() { return workspaceExecutorName; }
	protected Object getWorkspaceIdentifier() { return workspaceIdentifier; }

	protected void setTargetSpace(TargetSpace _targetSpace) { targetSpace = _targetSpace; }
	protected void setWorkspaceExecutorName(int _workspaceExecutorName) { workspaceExecutorName = _workspaceExecutorName; }
	protected void setWorkspaceIdentifier(Object _workspaceIdentifier) { workspaceIdentifier = _workspaceIdentifier; }

	public WorkspaceExecutionContext(Object _workspaceIdentifier, int _workspaceExecutorName)
	{
		setWorkspaceIdentifier(_workspaceIdentifier);
		setWorkspaceExecutorName(_workspaceExecutorName);
	}
}