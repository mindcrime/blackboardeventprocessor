package com.lucidtechnics.blackboard;

public interface Persister
{
	/**
	 * Retrieves a {@link TargetSpace} from persistent store.
	 * @param _workspaceIdentifier the workspace identifier that
	 * uniquely identifies this workspace.
	 *
	 * @see Workspace
	 * @see TargetSpace
	 * @see Event
	 * 
	 */

	public TargetSpace get(Object _workspaceIdentifier);

	/**
	 * Places a {@link TargetSpace} into a persistent store.
	 * @param _targetSpace the target space that is to be persisted.
	 *
	 * @see Workspace
	 * @see TargetSpace
	 * @see Event
	 * 
	 */

	public void put(TargetSpace _targetSpace);
}