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

package com.lucidtechnics.blackboard.config;

import java.util.HashSet;
import java.util.Set;

public class WorkspaceConfiguration
{
	private String appName;
	private String workspaceName;
	private Set planSet = new java.util.HashSet<com.lucidtechnics.blackboard.Plan>();
	private Set doNotPersistSet = new java.util.HashSet<String>();
	private boolean persistChangeInfoHistory = false;
	private Long workspaceTimeoutInSeconds = Long.MAX_VALUE;

	public String getAppName() { return appName; }
	public String getWorkspaceName() { return workspaceName; }
	public Set getPlanSet() { return planSet; }
	public Set getDoNotPersistSet() { return doNotPersistSet; }
	public boolean getPersistChangeInfoHistory() { return persistChangeInfoHistory; }
	public Long getWorkspaceTimeoutInSeconds() { return workspaceTimeoutInSeconds; }

	protected void setAppName(String _appName) { appName = _appName; }
	protected void setWorkspaceName(String _workspaceName) { workspaceName = _workspaceName; }
	protected void setPlanSet(Set _planSet) { planSet = _planSet; }

	/**
	 * Identify the set of targets that should not be automatically persisted when
	 * the workspace is completed.  Targets identified in this set will
	 * be removed from the workspace just before persistence.
	 *  
	 * @param _doNotPersistSet the collection of targets that should
	 * not be automatically persisted upon workspace completion.
	 *
	 * @see Workspace
	 * 
	 */

	public void setDoNotPersistSet(Set _doNotPersistSet) { doNotPersistSet = _doNotPersistSet; }

	/**
	 * Identify how long in seconds a workspace should remain idle in memory
	 * before it is persisted.  Workspaces persisted in this manner are not
	 * labled as complete, and as such will be resurrected from persistent
	 * store should the {@link Blackboard} consider it necessary to do
	 * so.  Note that targets identified in the do not persist set are
	 * not removed when this temporary persistence happens.  As such
	 * they will be available to the workspace when the workspace is
	 * resurrected. Also all plans are restored to the same state they
	 * were in when the workspace was persisted.
	 *
	 * The default setting is for the workspace to be allowed to idle for
	 * Long.MAX_VALUE seconds.
	 * 
	 * @param _workspaceTimeoutInSeconds the collection of targets that should
	 * not be automatically persisted upon workspace completion.
	 *
	 * @see Workspace
	 * 
	 */

	public void setWorkspaceTimeoutInSeconds(Long _workspaceTimeoutInSeconds) { workspaceTimeoutInSeconds = _workspaceTimeoutInSeconds; }
	
	/**
	 * Identify whether or not Blackboard should automatically persist
	 * change info history.  Developers will find this useful during
	 * debugging as it gives a total history of all actions applied to
	 * workspace events and targets.  In production this feature could
	 * be turned off.
	 * 
	 * The default setting is for the workspace is set to automatically
	 * persist change info history.
	 * 
	 * @param _persistChangeInfoHistory set to false if you do not want
	 * change info history to be automatically persisted with the rest
	 * of the workspace.
	 *
	 * @see Workspace
	 * 
	 */

	public void setPersistChangeInfoHistory(boolean _persistChangeInfoHistory) { persistChangeInfoHistory = _persistChangeInfoHistory; }
	
	public WorkspaceConfiguration() {}

	public WorkspaceConfiguration(String _appName, String _workspaceName)
	{
		setAppName(_appName);
		setWorkspaceName(_workspaceName);
	}
}