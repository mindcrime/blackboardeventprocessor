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

public interface Workspace
   extends Intercepter
{
	public String getName();
	public String getAppName();
	public Object getWorkspaceIdentifier();
	public Object get(String _targetName);
	public boolean has(String _targetName);
	
	public void putOnWorkspace(String _targetName, Object _object);
	public void putOnWorkspace(String _targetName, Target _target);
	public void putOnWorkspace(String _targetName, String _object);
	public void putOnWorkspace(String _targetName, Float _object);
	public void putOnWorkspace(String _targetName, Double _object);
	public void putOnWorkspace(String _targetName, Long _object);
	public void putOnWorkspace(String _targetName, Integer _object);
	public void putOnWorkspace(String _targetName, Byte _object);
	public void putOnWorkspace(String _targetName, Boolean _object);
	public void putOnWorkspace(String _targetName, Short _object);
	public void putOnWorkspace(String _targetName, Character _object);

	public void putOnWorkspace(String _targetName);
	
	public void placeOnBlackboard(Object _object);
	public void schedulePlaceOnBlackboard(Object _object, long _delay);
	public Object remove(String _targetName);
	public void clear();
	public void clearAllHistory();
	public void clearTargetHistory(String _targetName);
	public void clearAttributeHistory(String _targetName, String _attributeName);
	public boolean isEmpty();
	public boolean hasTarget(String _targetName);
	public boolean hasTargetAction(String _targetName, int _action);
	public boolean hasAttributeAction(String _targetName, String _attributeName);
	public List getTargetHistory(String _targetName);
	public List getAttributeHistory(String _targetName, String _attributeName);
	public ChangeInfo[] getChangeInfoHistory();
	public String toString();
	public boolean isCompleted();
	public boolean supportsTransactions();
	public void commit();
	public void rollback();	
}