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
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WorkspaceContext
   implements Workspace
{
	private final static Log logger = LogFactory.getLog(WorkspaceContext.class);

	private TargetSpace targetSpace;
	private Plan plan;
	private boolean isExpired = false;
	
	private Set interceptedTargetSet;

	protected TargetSpace getTargetSpace() { return targetSpace; }
	private Plan getPlan() { return plan; }
	protected boolean getIsExpired() { return isExpired; }
	public Set getInterceptedTargetSet() { return interceptedTargetSet; }
	
	protected void setTargetSpace(TargetSpace _targetSpace) { targetSpace = _targetSpace; }
	private void setPlan(Plan _plan) { plan = _plan; }
	protected void setIsExpired(boolean _isExpired) { isExpired = _isExpired; }
	public void setInterceptedTargetSet(Set _interceptedTargetSet) { interceptedTargetSet = _interceptedTargetSet; }
	
	protected WorkspaceContext(TargetSpace _targetSpace, Plan _plan)
	{
		setTargetSpace(_targetSpace);
		setPlan(_plan);
		setInterceptedTargetSet(new HashSet());
	}

	public String getName()
	{
		return getTargetSpace().getName();
	}

	public String getAppName()
	{
		return getTargetSpace().getAppName();
	}

	public Object getWorkspaceIdentifier()
	{
		return getTargetSpace().getWorkspaceIdentifier();
	}

	protected void expire()
	{
		Iterator interceptedTargets = getInterceptedTargetSet().iterator();

		while (interceptedTargets.hasNext() == true)
		{
			Target target = (Target) interceptedTargets.next();
			target.setIntercepter(null);
		}

		setIsExpired(true);
	}

	private void checkIfExpired()
	{
		if (getIsExpired() == true)
		{
			throw new BlackboardException("Workspace context: " + getName() + " cannot be used after plan complete execution.  You cannot keep the workspace context after plan has executed.");
		}
	}

	public void placeOnBlackboard(Object _object)
	{
		getTargetSpace().placeOnBlackboard(_object);
	}

	public void schedulePlaceOnBlackboard(Object _object, long _delay)
	{
		getTargetSpace().schedulePlaceOnBlackboard(_object, _delay);
	}

	public Object get(String _targetName)
	{
		checkIfExpired();

		Object object = getTargetSpace().get(_targetName);

		if (object != null && (object instanceof Target) == true)
		{
			Target target = (Target) object;
			
			if (target != null)
			{
				getInterceptedTargetSet().add(target);
				target.setIntercepter(this);
			}

			object = target;
		}

		return object;
	}

	public boolean has(String _targetName)
	{
		checkIfExpired();
		return getTargetSpace().has(_targetName);
	}

	public void putOnWorkspace(String _targetName, Object _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }
		
		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan(), this);
	}

	public void putOnWorkspace(String _targetName, Target _target)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }
		
		checkIfExpired();

		if (_target != null)
		{
			getInterceptedTargetSet().add(_target);
			_target.setIntercepter(this);
		}
		
		getTargetSpace().put(_targetName, _target, getPlan());
	}

	public void putOnWorkspace(String _targetName, String _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName, Float _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName, Double _object)
	{
		{
			if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

			checkIfExpired();
			getTargetSpace().put(_targetName, _object, getPlan());
		}
	}

	public void putOnWorkspace(String _targetName, Long _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName, Integer _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}


	public void putOnWorkspace(String _targetName, Byte _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName, Boolean _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName, Short _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName, Character _object)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().put(_targetName, _object, getPlan());
	}

	public void putOnWorkspace(String _targetName)
	{
		if (_targetName == null) { throw new RuntimeException("Null target names are not allowed"); }

		checkIfExpired();
		getTargetSpace().putNull(_targetName, getPlan());
	}

	public Object remove(String _targetName)
	{
		checkIfExpired();
		
		Target target = (Target) getTargetSpace().remove(_targetName, getPlan());

		if (target != null)
		{
			target.setIntercepter(null);
		}

		return target;
	}

	public void clear()
	{
		checkIfExpired();
		getTargetSpace().clear(getPlan());
	}

	public void clearAllHistory()
	{
		checkIfExpired();
		getTargetSpace().clearAllHistory();
	}

	public void clearTargetHistory(String _targetName)
	{
		getTargetSpace().clearTargetHistory(_targetName);
	}

	public void clearAttributeHistory(String _targetName, String _attributeName)
	{
		getTargetSpace().clearAttributeHistory(_targetName, _attributeName);
	}

	public boolean isEmpty()
	{
		checkIfExpired();
		return getTargetSpace().isEmpty();
	}

	public boolean hasTarget(String _targetName)
	{
		checkIfExpired();
		return getTargetSpace().hasTarget(_targetName);
	}

	public List getTargetHistory(String _targetName)
	{
		return getTargetSpace().getTargetHistory(_targetName);
	}

	public List getAttributeHistory(String _targetName, String _attributeName)
	{
		return getTargetSpace().getAttributeHistory(_targetName, _attributeName);
	}
	
	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						Object _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						int _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						byte _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						short _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						long _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						char _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						float _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						double _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}


	public void monitor(String _superClassName,
						String _subClassName,
						String _methodName,
						String _targetName,
						Object _target,
						boolean _value)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monitor called for target: " + _targetName + " and value: " + _value + " and method: " + _methodName + " and plan: " + getPlan().getName());
		}
		checkIfExpired();
		getTargetSpace().monitor(_superClassName, _superClassName, _methodName, _targetName, _target, _value, getPlan());
	}

	public ChangeInfo[] getChangeInfoHistory()
	{
		checkIfExpired();
		return getTargetSpace().getChangeInfoHistoryArray();
	}

	public boolean isCompleted()
	{
		return getTargetSpace().isCompleted();
	}

	public boolean supportsTransactions()
	{
		return getTargetSpace().supportsTransactions();
	}

	public void commit()
	{
		checkIfExpired();
		getTargetSpace().commit(getPlan());
	}

	public void rollback()
	{
		checkIfExpired();
		getTargetSpace().rollback(getPlan());
	}

	public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append(getTargetSpace().toString());
		stringBuffer.append("Current executing plan is: " + getPlan().getName() + ".\n");

		return stringBuffer.toString();
	}

	public String toJson()
	{
		return getTargetSpace().toJson();
	}

	public boolean hasTargetAction(String _targetName, int _action)
	{
		return (getTargetSpace().hasTargetAction(_targetName, _action) == true);
	}

	public boolean hasAttributeAction(String _targetName, String _attributeName)
	{
		return (getTargetSpace().hasAttributeAction(_targetName, _attributeName) == true);
	}
}