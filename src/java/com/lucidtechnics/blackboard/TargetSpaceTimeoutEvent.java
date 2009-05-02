package com.lucidtechnics.blackboard;

import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;

@com.lucidtechnics.blackboard.Event(name="blackboard.targetspace.timeout", workspaceIdentifier="blackboardWorkspaceName")

public class TargetSpaceTimeoutEvent
{
	private String blackboardWorkspaceName;
	private TargetSpace targetSpace;
	private WorkspaceConfiguration workspaceConfiguration;
	private Blackboard blackboard;

	public String getBlackboardWorkspaceName() { return blackboardWorkspaceName; }
	protected TargetSpace getTargetSpace() { return targetSpace; }
	protected WorkspaceConfiguration getWorkspaceConfiguration() { return workspaceConfiguration; }
	protected Blackboard getBlackboard() { return blackboard; }

	public void setBlackboardWorkspaceName(String _blackboardWorkspaceName) { blackboardWorkspaceName = _blackboardWorkspaceName; }
	protected void setTargetSpace(TargetSpace _targetSpace) { targetSpace = _targetSpace; }
	protected void setWorkspaceConfiguration(WorkspaceConfiguration _workspaceConfiguration) { workspaceConfiguration = _workspaceConfiguration; }
	protected void setBlackboard(Blackboard _blackboard) { blackboard = _blackboard; }

	public TargetSpaceTimeoutEvent() {}
}