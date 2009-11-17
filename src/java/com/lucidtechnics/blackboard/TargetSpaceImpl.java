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

import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TargetSpaceImpl
   extends TargetSpace
   implements java.io.Serializable
{
	private final static Log logger = LogFactory.getLog(TargetSpaceImpl.class);
	
	public TargetSpaceImpl()
	{
		setCreateDate(new Date());
		setTargetMap(new HashMap());
		setPlanStateMap(new HashMap());
		setDoNotPersistSet(new HashSet());
		setExceptionSet(new HashSet());
		setChangeInfoHistory(new ChangeInfoHistory());
	}
	
	public TargetSpaceImpl(Object _workspaceIdentifier)
	{
		this();
		setWorkspaceIdentifier(_workspaceIdentifier);
	}
	
	protected final void put(String _targetName, Object _object, Actor _actor, Intercepter _intercepter)
	{
		if (_object != null && TargetConstructor.canConstructTarget(_object.getClass()) == true)
		{
			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Constructing target for target: " + _targetName);
			}

			Target target = TargetConstructor.constructTarget(_targetName, _object.getClass());
			target.setBlackboardObject(_object);
			target.setIntercepter(_intercepter);

			put(_targetName, target, _actor);
		}
		else if (_object == null)
		{
			putNull(_targetName, _actor);
		}
		else
		{
			//TODO: Either a class that is final, and array, or an enum
			//I think you simply add this object to the targetpace
			//directly.  There might be some virtue to marking these
			//classes somehow.  Or maybe we shouldn't even handle them?

			//Need to handle the case after I merge with the
			//other code base.

			throw new RuntimeException("Cannot add targets of type array, enum, or targets that are final classes: " + _object.getClass());
		}
	}

	protected final void put(String _targetName, Target _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, String _target, Actor _actor)
	{
		boolean updatedTarget = getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Integer _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Long _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Character _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Short _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Boolean _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Byte _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Double _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void put(String _targetName, Float _target, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, _target);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target));
	}

	protected final void putNull(String _targetName, Actor _actor)
	{
		boolean updatedTarget =  getTargetMap().containsKey(_targetName);
		getTargetMap().put(_targetName, null);
		int action = (updatedTarget == true) ? ChangeInfo.TARGET_UPDATED : ChangeInfo.TARGET_ADDED;
		notifyPlans(new ChangeInfo(action, _actor, getName(), getWorkspaceIdentifier(), _targetName, null));
	}
	
	protected Target remove(String _targetName, Actor _actor)
	{
		Target target = (Target) getTargetMap().get(_targetName);
		getTargetMap().remove(_targetName);
		notifyPlans(new ChangeInfo(ChangeInfo.TARGET_REMOVED, _actor, getName(), getWorkspaceIdentifier(), _targetName, target));

		return target;
	}
	
	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, Object _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
					_superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, int _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, int[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, short _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
					_superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, short[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, byte _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, byte[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, long _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
					_superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, long[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, boolean _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, boolean[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, char _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, char[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, float _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
					_superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, float[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, double _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						_superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	protected void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, double[] _value, Actor _actor)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Workspace target: " + getName() + ":" + _targetName + " which is an object of class: " +
						 _superClassName + " has had attribute: " + _methodName.substring(3) + " set to value: " +
						 _value + " by actor: " + _actor.getName());
		}

		notifyPlans(new ChangeInfo(ChangeInfo.ATTRIBUTE_SET, _actor, getName(), getWorkspaceIdentifier(), _targetName, _target, _methodName.substring(3), _value));
	}

	public String toString()
	{
		return super.toString();
	}
	
	protected boolean supportsTransactions()
	{
		return false;
	}

	protected void commit(Actor _actor) { throw new RuntimeException("Transactions not supported by this workspace"); }
	protected void rollback(Actor _actor) { throw new RuntimeException("Transactions not supported by this workspace"); }
}