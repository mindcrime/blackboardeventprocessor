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

final class TargetSpaceState
{    
	protected final static int ACTIVE = 0;  //There are plans still waiting to be executed.
	protected final static int COMPLETED = 1;  //There are no active plans available to execute and no plans currently executing.
	protected final static int TERMINATED = 2; //Target space reached unrecoverable error condition
	protected final static int EXECUTING = 3; //Plans are currently scheduled to execute against this target
	protected final static int RETIRED = 4; //Target space is retired.  No more activity is allowed against this target space.

	protected final static int PERSISTED = 8; //Target space has been removed from memory and is now stored in a repository.
	
	private final static int PERSISTED_MASK = 7; 


	public static final boolean isRetired(int _targetSpaceState)
	{
		int planState = _targetSpaceState & PERSISTED_MASK;

		return (planState == RETIRED);
	}

	public static final boolean isActive(int _targetSpaceState)
	{
		int planState = _targetSpaceState & PERSISTED_MASK;

		return (planState == ACTIVE);
	}

	public static final boolean isExecuting(int _targetSpaceState)
	{
		int planState = _targetSpaceState & PERSISTED_MASK;

		return (planState == EXECUTING);
	}

	public static final boolean isTerminated(int _targetSpaceState)
	{
		int planState = _targetSpaceState & PERSISTED_MASK;

		return (planState == TERMINATED);
	}

	public static final boolean isCompleted(int _targetSpaceState)
	{
		int planState = _targetSpaceState & PERSISTED_MASK;

		return (planState == COMPLETED);
	}

	public static final boolean isPersisted(int _targetSpaceState)
	{
		return (_targetSpaceState >= PERSISTED);
	}

	public static final int setActive(int _targetSpaceState)
	{
		return PERSISTED & _targetSpaceState | ACTIVE;
	}

	public static final int setExecuting(int _targetSpaceState)
	{
		return PERSISTED & _targetSpaceState | EXECUTING;
	}

	public static final int setTerminated(int _targetSpaceState)
	{
		return PERSISTED & _targetSpaceState | TERMINATED;
	}

	public static final int setCompleted(int _targetSpaceState)
	{
		return PERSISTED & _targetSpaceState | COMPLETED;
	}

	public static final int setRetired(int _targetSpaceState)
	{
		return PERSISTED & _targetSpaceState | RETIRED;
	}

	public static final int setPersisted(int _targetSpaceState)
	{
		return PERSISTED | _targetSpaceState;
	}
}