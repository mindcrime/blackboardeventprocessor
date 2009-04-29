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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ReadOnlyWorkspaceContext
   extends WorkspaceContext
{
	private final static Log logger = LogFactory.getLog(ReadOnlyWorkspaceContext.class);

	protected ReadOnlyWorkspaceContext(TargetSpace _targetSpace)
	{
		super(_targetSpace, null);
	}

	protected ReadOnlyWorkspaceContext(TargetSpace _targetSpace, Plan _plan)
	{
		super(_targetSpace, _plan);
	}
	
	public void putOnWorkspace(String _targetName, Object _target)
	{
		throw new RuntimeException("Cannot put target while expressing interest: " + _targetName + ". This workspace is read only");
	}

	public void putOnWorkspace(String _targetName, Target _target)
	{
		throw new RuntimeException("Cannot put target while expressing interest: " + _targetName + ". This workspace is read only");
	}

	public Object remove(String _targetName)
	{
		throw new RuntimeException("Cannot put target while expressing interest: " + _targetName + ". This workspace is read only");
	}

	public void clear()
	{
		throw new RuntimeException("Cannot clear while expressing interest. This workspace is read only");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, Object _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, long _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, int _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, short _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, boolean _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, char _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, byte _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, double _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, float _value)
	{
		throw new RuntimeException("Cannot change target while expressing interest: " + _targetName + ". This workspace is read only.");
	}

	public boolean supportsTransactions()
	{
		return false;
	}

	public void commit()
	{
		throw new RuntimeException("Cannot commit while expressing interest. This workspace is read only");
	}

	public void rollback()
	{
		throw new RuntimeException("Cannot rollback while expressing interest. This workspace is read only");
	}
}