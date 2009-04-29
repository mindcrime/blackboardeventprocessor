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
	private String name;
	private Set planSet;
	private Set doNotPersistSet;
	private boolean isTransactional = false;
	private boolean persistChangeInfoHistory = false;
	private Long workspaceTimeoutInSeconds = Long.MAX_VALUE;

	public String getName() { return name; }
	public Set getPlanSet() { return planSet; }
	public Set getDoNotPersistSet() { return doNotPersistSet; }
	public boolean getIsTransactional() { return isTransactional; }
	public boolean getPersistChangeInfoHistory() { return persistChangeInfoHistory; }
	public Long getWorkspaceTimeoutInSeconds() { return workspaceTimeoutInSeconds; }
			
	public void setName(String _name) { name = _name; }
	public void setPlanSet(Set _planSet) { planSet = _planSet; }
	public void setDoNotPersistSet(Set _doNotPersistSet) { doNotPersistSet = _doNotPersistSet; }
	public void setIsTransactional(boolean _isTransactional) { isTransactional = _isTransactional; }
	public void setPersistChangeInfoHistory(boolean _persistChangeInfoHistory) { persistChangeInfoHistory = _persistChangeInfoHistory; }
	public void setWorkspaceTimeoutInSeconds(Long _workspaceTimeoutInSeconds) { workspaceTimeoutInSeconds = _workspaceTimeoutInSeconds; }
	
	public WorkspaceConfiguration()
	{
	}
}