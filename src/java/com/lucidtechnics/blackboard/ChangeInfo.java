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

public class ChangeInfo
{
	public static final int WORKSPACE_CLEARED = 0;
	public static final int TARGET_ADDED = 1;
	public static final int TARGET_UPDATED = 2;
	public static final int TARGET_REMOVED = 3;
	public static final int ATTRIBUTE_SET = 4;
	public static final int NO_CHANGE = 5;	

	private String workspaceName;
	private Object workspaceIdentifier;
	private String targetName;
	private Object target;
	private String attributeName;
	private Object attribute;
	private int action;
	private Actor actor;
	private long timestamp;

	public void setWorkspaceName(String _workspaceName) { workspaceName = _workspaceName; }
	public Object getWorkspaceIdentifier() { return workspaceIdentifier; }
	public String getTargetName() { return targetName; }
	public Object getTarget() { return target; }
	public String getAttributeName() { return attributeName; }
	public Object getAttribute() { return attribute; }
	public int getAction() { return action; }
	private Actor getActor() { return actor; }
	public long getTimestamp() { return timestamp; }
	
	private String getWorkspaceName() { return workspaceName; }
	private void setWorkspaceIdentifier(Object _workspaceIdentifier) { workspaceIdentifier = _workspaceIdentifier; }
	private void setTargetName(String _targetName) { targetName = _targetName; }
	private void setTarget(Object _target) { target = _target; }
	private void setAttributeName(String _attributeName) { attributeName = _attributeName; }
	private void setAttribute(Object _attribute) { attribute = _attribute; }
	private void setAction(int _action) { action = _action; }
	private void setActor(Actor _actor) { actor = _actor; }
	protected void setTimestamp(long _timestamp) { timestamp = _timestamp; }
	
	public ChangeInfo(int _action, Actor _actor, String _workspaceName, Object _workspaceIdentifier, String _targetName,
					  Object _target, String _attributeName, Object _attribute)
	{
		setAction(_action);
		setActor(_actor);
		setWorkspaceName(_workspaceName);
		setWorkspaceIdentifier(_workspaceIdentifier);
		setTargetName(_targetName);
		setTarget(_target);
		setAttributeName(_attributeName);
		setAttribute(_attribute);
	}

	public ChangeInfo(int _action, Actor _actor, String _workspaceName, Object _workspaceIdentifier, String _targetName, Object _target)
	{
		setAction(_action);
		setActor(_actor);
		setWorkspaceName(_workspaceName);
		setWorkspaceIdentifier(_workspaceIdentifier);
		setTargetName(_targetName);
		setTarget(_target);		
	}

	public ChangeInfo(int _action, Actor _actor, String _workspaceName, Object _workspaceIdentifier)
	{
		setAction(_action);
		setActor(_actor);
		setWorkspaceName(_workspaceName);
		setWorkspaceIdentifier(_workspaceIdentifier);
	}

	public String toString()
	{
	    StringBuffer stringBuffer = new StringBuffer();

	    stringBuffer.append("\nChange Information:\n");
	    stringBuffer.append("workspace name: " + getWorkspaceName() + "\n");
	    stringBuffer.append("workspace identifier: " + getWorkspaceIdentifier() + "\n");
	    stringBuffer.append("target name: " + getTargetName() + "\n");
	    stringBuffer.append("target: " + getTarget() + "\n");
	    stringBuffer.append("attribute name: " + getAttributeName() + "\n");
	    stringBuffer.append("attribute: " + getAttribute() + "\n");
		stringBuffer.append("action: " + getAction() + "\n");
		stringBuffer.append("actor: " + getActor() + "\n");

	    return stringBuffer.toString();
	}
}