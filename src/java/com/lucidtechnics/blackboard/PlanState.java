package com.lucidtechnics.blackboard;

final class PlanState
{    
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
	protected final static int ACTIVE = 0;
	
	protected final static int EXECUTING = 1;
	protected final static int FINISHED = 2;
	protected final static int ERRORED = 3;
	private final static int EXECUTED = 4;
	
	private final static int EXECUTED_MASK = 3; 
	
	public static final boolean isErrored(int _planState)
	{
		int planState = _planState & EXECUTED_MASK;

		return (planState == ERRORED);
	}

	public static final boolean isActive(int _planState)
	{
		int planState = _planState & EXECUTED_MASK;

		return (planState == ACTIVE);
	}

	public static final boolean isExecuting(int _planState)
	{
		int planState = _planState & EXECUTED_MASK;

		return (planState == EXECUTING);
	}

	public static final boolean isFinished(int _planState)
	{
		int planState = _planState & EXECUTED_MASK;

		return (planState == FINISHED);
	}

	public static final boolean isExecuted(int _planState)
	{
		return (_planState >= EXECUTED);
	}

	public static final int setExecuted(int _planState)
	{
		return _planState | EXECUTED;
	}

	public static final int setActive(int _planState)
	{
		return EXECUTED & _planState | ACTIVE;
	}

	public static final int setExecuting(int _planState)
	{
		return EXECUTED & _planState | EXECUTING;
	}

	public static final int setFinished(int _planState)
	{
		return EXECUTED & _planState | FINISHED;
	}

	public static final int setErrored(int _planState)
	{
		return EXECUTED & _planState | ERRORED;
	}
}