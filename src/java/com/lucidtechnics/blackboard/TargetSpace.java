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
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;
   
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class TargetSpace
{
	private final static Log logger = LogFactory.getLog(TargetSpace.class);
	
	private String name;
	private String appName;
	private transient Blackboard blackboard;
	private Object workspaceIdentifier;
	private Map targetMap;
	private Map planStateMap;
	private transient Set doNotPersistSet;
	private Set exceptionSet;
	private transient Set eventNameSet;
	private transient ReentrantReadWriteLock workspaceReadWriteLock;
	private transient Lock workspaceReadLock;
	private transient Lock workspaceWriteLock;
	private ChangeInfoHistory changeInfoHistory;
	private transient boolean terminateOnError;
	private int state;
	private transient long lastUsedTimestamp;
	private transient boolean persistChangeInfoHistory;
	private Date createDate;
	private Date retireDate;
	private transient long lastActiveTime;
	
	private transient ReentrantReadWriteLock workspaceExclusivityReadWriteLock;
	private transient Lock workspaceExclusivityReadLock;
	private transient Lock workspaceExclusivityWriteLock;

	public String getName() { return name; }
	public String getAppName() { return appName; }
	protected Blackboard getBlackboard() { return blackboard; }
	public  Object getWorkspaceIdentifier() { return workspaceIdentifier; }
	protected Map getTargetMap() { return targetMap; }
	protected Map getPlanStateMap() { return planStateMap; }
	protected Set getDoNotPersistSet() { return doNotPersistSet; }
	protected Set getExceptionSet() { return exceptionSet; }
	protected ReentrantReadWriteLock getWorkspaceReadWriteLock() { return workspaceReadWriteLock; }
	protected Lock getWorkspaceReadLock() { return workspaceReadLock; }
	protected Lock getWorkspaceWriteLock() { return workspaceWriteLock; }
	protected ReentrantReadWriteLock getWorkspaceExclusivityReadWriteLock() { return workspaceExclusivityReadWriteLock; }
	protected Lock getWorkspaceExclusivityReadLock() { return workspaceExclusivityReadLock; }
	protected Lock getWorkspaceExclusivityWriteLock() { return workspaceExclusivityWriteLock; }
	protected ChangeInfoHistory getChangeInfoHistory() { return changeInfoHistory; }
	protected int getState() { return state; }
	protected boolean getTerminateOnError() { return terminateOnError; }
	protected long getLastUsedTimestamp() { return lastUsedTimestamp; }
	protected boolean getPersistChangeInfoHistory() { return persistChangeInfoHistory; }
	protected Date getCreateDate() { return createDate; }
	protected Date getRetireDate() { return retireDate; }
	protected long getLastActiveTime() { return lastActiveTime; }
	
	protected void setName(String _name) { name = _name; }
	protected void setAppName(String _appName) { appName = _appName; }
	protected void setBlackboard(Blackboard _blackboard) { blackboard = _blackboard; }
	protected void setWorkspaceIdentifier(Object _workspaceIdentifier) { workspaceIdentifier = _workspaceIdentifier; }
	protected void setTargetMap(Map _targetMap) { targetMap = _targetMap; }
	protected void setPlanStateMap(Map _planStateMap) { planStateMap = _planStateMap; }
	protected void setDoNotPersistSet(Set _doNotPersistSet) { doNotPersistSet = _doNotPersistSet; }
	protected void setExceptionSet(Set _exceptionSet) { exceptionSet = _exceptionSet; }
	protected void setWorkspaceReadWriteLock(ReentrantReadWriteLock _workspaceReadWriteLock) { workspaceReadWriteLock = _workspaceReadWriteLock; }
	protected void setWorkspaceReadLock(Lock _workspaceReadLock) { workspaceReadLock = _workspaceReadLock; }
	protected void setWorkspaceWriteLock(Lock _workspaceWriteLock) { workspaceWriteLock = _workspaceWriteLock; }
	protected void setWorkspaceExclusivityReadWriteLock(ReentrantReadWriteLock _workspaceExclusivityReadWriteLock) { workspaceExclusivityReadWriteLock = _workspaceExclusivityReadWriteLock; }
	protected void setWorkspaceExclusivityReadLock(Lock _workspaceExclusivityReadLock) { workspaceExclusivityReadLock = _workspaceExclusivityReadLock; }
	protected void setWorkspaceExclusivityWriteLock(Lock _workspaceExclusivityWriteLock) { workspaceExclusivityWriteLock = _workspaceExclusivityWriteLock; }
	protected void setChangeInfoHistory(ChangeInfoHistory _changeInfoHistory) { changeInfoHistory = _changeInfoHistory; }
	protected void setState(int _state) { state = _state; }
	protected void setTerminateOnError(boolean _terminateOnError) { terminateOnError = _terminateOnError; }
	protected void setLastUsedTimestamp(long _lastUsedTimestamp) { lastUsedTimestamp = _lastUsedTimestamp; }
	protected void setPersistChangeInfoHistory(boolean _persistChangeInfoHistory) { persistChangeInfoHistory = _persistChangeInfoHistory; }
	protected void setCreateDate(Date _createDate) { createDate = _createDate; }
	protected void setRetireDate(Date _retireDate) { retireDate = _retireDate; }
	protected void setLastActiveTime(long _lastActiveTime) { lastActiveTime = _lastActiveTime; }
	
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, Object _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, int _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, int[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, short _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, short[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, long _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, long[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, boolean _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, boolean[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, byte _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, byte[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, char _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, char[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, double _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, double[] _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, float _value, Actor _actor);
	protected abstract void monitor(String _superClassName, String _subClassName, String _methodName, String _targetName, Object _target, float[] _value, Actor _actor);

	protected abstract void put(String _targetName, Object _object, Actor _actor, Intercepter _intercepter);
	protected abstract void put(String _targetName, Target _target, Actor _actor);
	protected abstract void put(String _targetName, String _target, Actor _actor);
	protected abstract void put(String _targetName, Integer _target, Actor _actor);
	protected abstract void put(String _targetName, Long _target, Actor _actor);
	protected abstract void put(String _targetName, Character _target, Actor _actor);
	protected abstract void put(String _targetName, Short _target, Actor _actor);
	protected abstract void put(String _targetName, Boolean _target, Actor _actor);
	protected abstract void put(String _targetName, Byte _target, Actor _actor);
	protected abstract void put(String _targetName, Double _target, Actor _actor);
	protected abstract void put(String _targetName, Float _target, Actor _actor);
	
	protected abstract void putNull(String _targetName, Actor _actor);

	protected abstract Object remove(String _targetName, Actor _actor);

	protected abstract boolean supportsTransactions();
	protected abstract void commit(Actor _actor);
	protected abstract void rollback(Actor _actor);

	protected void initialize(Blackboard _blackboard)
	{
		setWorkspaceReadWriteLock(new ReentrantReadWriteLock());
		setWorkspaceReadLock(getWorkspaceReadWriteLock().readLock());
		setWorkspaceWriteLock(getWorkspaceReadWriteLock().writeLock());
		setWorkspaceExclusivityReadWriteLock(new ReentrantReadWriteLock());
		setWorkspaceExclusivityReadLock(getWorkspaceExclusivityReadWriteLock().readLock());
		setWorkspaceExclusivityWriteLock(getWorkspaceExclusivityReadWriteLock().writeLock());

		setState(TargetSpaceState.ACTIVE);
		
		setBlackboard(_blackboard);
	}
	
	protected Object get(String _targetName)
	{
		Object target = null;

		try
		{
			getWorkspaceReadLock().lock();

			target = getTargetMap().get(_targetName);
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return target;
	}

	protected List getAllTargets(String _targetName)
	{
		List targetList  = new ArrayList();

		try
		{
			getWorkspaceReadLock().lock();

			List tempEventList = (List) getTargetMap().get(_targetName);

			if (tempEventList != null)
			{
				targetList.addAll(tempEventList);
			}
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return targetList;
	}

	protected boolean has(String _targetName)
	{
		boolean has = false;

		try
		{
			getWorkspaceReadLock().lock();
			has =  getTargetMap().containsKey(_targetName);
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return has;
	}

	protected boolean isEmpty()
	{
		boolean isEmpty = true;

		try
		{
			getWorkspaceReadLock().lock();
			isEmpty =  getTargetMap().isEmpty();
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return isEmpty;
	}

	protected boolean hasTarget(String _targetName)
	{
		boolean hasTarget = true;

		try
		{
			getWorkspaceReadLock().lock();
			hasTarget = getTargetMap().containsKey(_targetName);
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return hasTarget;
	}

	protected boolean hasTargetAction(String _targetName, int _action)
	{
		return (getChangeInfoHistory().hasTargetAction(_targetName, _action) == true);
	}

	protected boolean hasAttributeAction(String _targetName, String _attributeName)
	{
		return (getChangeInfoHistory().hasAttributeAction(_targetName, _attributeName) == true);
	}

	protected List getTargetHistory(String _targetName)
	{
		List targetHistory = new ArrayList();
		
		try
		{
			getWorkspaceReadLock().lock();
			targetHistory = getChangeInfoHistory().getTargetHistory(_targetName);
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return targetHistory;
	}

	protected List getAttributeHistory(String _targetName, String _attributeName)
	{
		List attributeHistory = new ArrayList();

		try
		{
			getWorkspaceReadLock().lock();
			attributeHistory = getChangeInfoHistory().getAttributeHistory(_targetName, _attributeName);
		}
		finally
		{
			getWorkspaceReadLock().unlock();
		}

		return attributeHistory;
	}
	
	protected void addPlan(Plan _plan)
	{
		getPlanStateMap().put(_plan, new Integer(PlanState.ACTIVE));
	}

	protected void setPlanState(Plan _plan, boolean _isFinished)
	{
		if (_isFinished == true)
		{
			setFinished(_plan);
		}
		else
		{
			setActive(_plan);
		}
	}

	protected void addPlans(Set _planSet)
	{
		if (_planSet != null)
		{
			Iterator plans = _planSet.iterator();

			while (plans.hasNext() == true)
			{
				Plan plan = (Plan) plans.next();
				addPlan(plan);
			}
		}
	}
	
	protected void clear(Actor _actor)
	{
		try
		{
			getWorkspaceWriteLock().lock();
			getTargetMap().clear();
		}
		finally
		{
			getWorkspaceWriteLock().unlock();
			notifyPlans(new ChangeInfo(ChangeInfo.WORKSPACE_CLEARED, _actor, getName(), getWorkspaceIdentifier()));
		}
	}

	protected void clearAllHistory()
	{
		try
		{
			getWorkspaceWriteLock().lock();
			setChangeInfoHistory(new ChangeInfoHistory());
		}
		finally
		{
			getWorkspaceWriteLock().unlock();
		}
	}

	protected void clearTargetHistory(String _targetName)
	{
		try
		{
			getWorkspaceWriteLock().lock();
			getChangeInfoHistory().eraseTargetHistory(_targetName);
		}
		finally
		{
			getWorkspaceWriteLock().unlock();
		}
	}

	protected void clearAttributeHistory(String _targetName, String _attributeName)
	{
		try
		{
			getWorkspaceWriteLock().lock();
			getChangeInfoHistory().eraseAttributeHistory(_targetName, _attributeName);
		}
		finally
		{
			getWorkspaceWriteLock().unlock();
		}
	}

	protected void schedulePlaceOnBlackboard(Object _object, long _delay)
	{
		getBlackboard().schedulePlaceOnBlackboard(_object, _delay);
	}
	
	protected void placeOnBlackboard(Object _object)
	{
		getBlackboard().placeOnBlackboard(_object);
	}

	protected void setActive(Plan _plan)
	{
		int planState = PlanState.setActive(getPlanState(_plan));
		getPlanStateMap().put(_plan, planState);
	}

	void setExecuting(Plan _plan)
	{		
		int planState = PlanState.setExecuting(getPlanState(_plan));
		getPlanStateMap().put(_plan, planState);
	}

	protected void setExecuted(Plan _plan)
	{
		int planState = PlanState.setExecuted(getPlanState(_plan));
		getPlanStateMap().put(_plan, planState);
	}

	protected void setFinished(Plan _plan)
	{
		int planState = PlanState.setFinished(getPlanState(_plan));
		getPlanStateMap().put(_plan, planState);
	}

	protected void setErrored(Plan _plan, Throwable _t)
	{
		getExceptionSet().add(_t.toString());
		int planState = PlanState.setErrored(getPlanState(_plan));
		getPlanStateMap().put(_plan, planState);
		if (getTerminateOnError() == true) { setTerminated(); }
	}

	protected void notifyPlans(ChangeInfo _changeInfo)
	{
		if (_changeInfo != null)
		{
			_changeInfo.setTimestamp(System.currentTimeMillis());
			setLastUsedTimestamp(_changeInfo.getTimestamp());
			getChangeInfoHistory().add(_changeInfo);
		}

		if (isExecuting() == false)
		{
			notifyPlans();
		}
	}

	protected void notifyPlans()
	{
		if (isTerminated() == false)
		{
			if (logger.isDebugEnabled() == true)
			{
				logger.debug(toString());
			}

			try
			{
				acquireWorkspaceWriteLock();
				setExecuting();
				getBlackboard().executePlans(this, new ArrayList(getPlanStateMap().keySet()));
			}
			catch(Throwable t)
			{
				setTerminated();
				throw new RuntimeException(t);
			}
			finally
			{
				releaseWorkspaceWriteLock();
			}
		}
	}

	protected ChangeInfo[] getChangeInfoHistoryArray()
	{
		return (ChangeInfo[]) getChangeInfoHistory().toArray(new ChangeInfo[getChangeInfoHistory().size()]);
	}

	protected int getChangeInfoCount()
	{
		return getChangeInfoHistory().getCount();
	}

	private String getPlanInfo(Set _planSet, String _planSentence)
	{
		StringBuffer stringBuffer = new StringBuffer();
		Set planSet = new HashSet(_planSet);

		Iterator plans = planSet.iterator();

		while (plans.hasNext() == true)
		{
			Plan plan = (Plan) plans.next();
			stringBuffer.append("Plan: " + plan.getName() + " " + _planSentence + "\n");
		}

		return stringBuffer.toString();
	}

	public String toString()
	{
		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("Workspace name: " + getName());
		stringBuffer.append(" identified by: " + getWorkspaceIdentifier() + "\n");
		stringBuffer.append("is in state: " + getState() + "\n");
		stringBuffer.append("Plan info:\n");

		Iterator plans = getPlanStateMap().keySet().iterator();

		while (plans.hasNext() == true)
		{
			Plan plan = (Plan) plans.next();
			int planState = getPlanState(plan);
			
			stringBuffer.append("Plan: " + plan.getName() + " has state: " + planState + ".\n");
		}
		
		stringBuffer.append("Target info:\n");
		
		Map targetMap = new HashMap(getTargetMap());

		Iterator targetNames = targetMap.keySet().iterator();

		while (targetNames.hasNext() == true)
		{
			String targetName = (String) targetNames.next();
			Object target = targetMap.get(targetName);

			stringBuffer.append("target: " + targetName + " is: " + target + ".\n");
		}

		return stringBuffer.toString();
	}
	
	Set getTargets()
	{
		Set targetSet = new HashSet();

		if (getTargetMap() != null)
		{
			targetSet.addAll(getTargetMap().values());
		}

		return targetSet;
	}

	Set getTargetNames()
	{
		Set targetNameSet = new HashSet();

		if (getTargetMap() != null)
		{
			targetNameSet.addAll(getTargetMap().keySet());
		}

		return targetNameSet;
	}	
	
	protected boolean isActive(Plan _plan)
	{
		return PlanState.isActive(getPlanState(_plan));
	}

	protected boolean isExecuting(Plan _plan)
	{
		return PlanState.isExecuting(getPlanState(_plan));
	}

	protected boolean isFinished(Plan _plan)
	{
		return PlanState.isFinished(getPlanState(_plan));
	}

	protected boolean isExecuted(Plan _plan)
	{
		return PlanState.isExecuted(getPlanState(_plan));
	}

	protected boolean isErrored(Plan _plan)
	{
		return PlanState.isErrored(getPlanState(_plan));
	}

	private int getPlanState(Plan _plan)
	{
		return ((Integer) getPlanStateMap().get(_plan)).intValue();
	}

	protected boolean isActive()
	{
		return TargetSpaceState.isActive(getState());
	}

	protected boolean isRetired()
	{
		return TargetSpaceState.isRetired(getState());
	}

	protected boolean isExecuting()
	{
		return TargetSpaceState.isExecuting(getState());
	}

	protected boolean isCompleted()
	{
		return TargetSpaceState.isCompleted(getState());
	}

	protected boolean isTerminated()
	{
		return TargetSpaceState.isTerminated(getState());
	}

	protected boolean isPersisted()
	{
		return TargetSpaceState.isPersisted(getState());
	}

	protected void setActive()
	{
		setState(TargetSpaceState.setActive(getState()));
	}

	protected void setCompleted()
	{
		setState(TargetSpaceState.setCompleted(getState()));
	}

	protected void setExecuting()
	{
		setState(TargetSpaceState.setExecuting(getState()));
	}

	protected void setTerminated()
	{
		setState(TargetSpaceState.setTerminated(getState()));
	}

	protected void setPersisted()
	{
		setState(TargetSpaceState.setPersisted(getState()));
	}	

	protected void setRetired()
	{
		setState(TargetSpaceState.setRetired(getState()));
	}	

	protected void acquireWorkspaceReadLock()
	{
		getWorkspaceExclusivityReadLock().lock();
	}

	protected void releaseWorkspaceReadLock()
	{
		getWorkspaceExclusivityReadLock().unlock();
	}

	protected void acquireWorkspaceWriteLock()
	{
		getWorkspaceExclusivityWriteLock().lock();
	}

	protected void releaseWorkspaceWriteLock()
	{
		getWorkspaceExclusivityWriteLock().unlock();
	}

	protected TargetSpace prepareForPersistence()
	{
		setPersisted();
		
		if (getPersistChangeInfoHistory() == false)
		{
			getChangeInfoHistory().eraseHistory();
		}

		return this;
	}

	protected TargetSpace prepareForRetirement()
	{
		if (getDoNotPersistSet().isEmpty() == false)
		{
			Iterator targetNames = getDoNotPersistSet().iterator();

			while (targetNames.hasNext() == true)
			{
				getTargetMap().remove(targetNames.next());
			}
		}

		return this;
	}
}