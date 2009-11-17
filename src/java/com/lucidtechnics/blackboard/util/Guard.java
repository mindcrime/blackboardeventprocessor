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

package com.lucidtechnics.blackboard.util;

import java.util.Map;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class Guard
{
	private static Log logger = LogFactory.getLog(Guard.class);

	private Map idMap;
	
	public Guard()
	{
		setIdMap(new HashMap());
	}
	
	private Map getIdMap() { return idMap; }
	private void setIdMap(Map _idMap) { idMap = _idMap; }
	
	public boolean acquireLock(Object _id, boolean _blockUntilAcquired)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Guard for id: " + _id +
					 " with block until acquired value: " +
						 _blockUntilAcquired);
		}
		
		boolean acquiredLock = false;
		
		do
		{
			synchronized(this)
			{
				if (logger.isInfoEnabled() == true)
				{
					logger.info("synced on object: " + _id);
				}

				if (getIdMap().containsKey(_id) == false)
				{
					getIdMap().put(_id, new GuardState(_id));
				}

				GuardState guardState = (GuardState) getIdMap().get(_id);

				acquiredLock = guardState.acquireLock();
				
				if (acquiredLock == false && _blockUntilAcquired == true)
				{
					if (logger.isInfoEnabled() == true)
					{
						logger.info("Waiting to acquire lock on id: " + _id);
					}

					try { wait(); } catch (InterruptedException e) {}
				}
			}
		}
		while (acquiredLock == false && _blockUntilAcquired == true);

		if (logger.isInfoEnabled() == true)
		{
			logger.info("For id: " + _id + ". Acquired lock is: " + acquiredLock);
		}
		
		return acquiredLock;
	}

	public boolean releaseLock(Object _id)
	{
		boolean releasedLock = false;
		
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("releasing guard for id: " + _id);
		}

		GuardState guardState = (GuardState) getIdMap().get(_id);

		if (guardState != null)
		{
			synchronized(this)
			{
				releasedLock = guardState.releaseLock();
				
				if (releasedLock == true )
				{
					getIdMap().remove(_id);

					notifyAll();

					if (logger.isDebugEnabled() == true)
					{
						logger.debug("Released guard for id: " + _id);
					}
				}
				else
				{
					if (logger.isDebugEnabled() == true)
					{
						logger.warn("Tried to release lock but it is not owned by this thread for id: " + _id);
					}
				}
			}
		}
		else
		{
			if (logger.isWarnEnabled() == true)
			{
				logger.warn("Guard is still in place for id: " + _id);
			}
		}

		return releasedLock;
	}

	private final class GuardState
	{
		private Object id;
		private Thread ownerThread;
		private int lockCount;

		protected Object getId() { return id; }
		protected Thread getOwnerThread() { return ownerThread; }
		protected int getLockCount() { return lockCount; }

		protected void setId(Object _id) { id = _id; }
		protected void setOwnerThread(Thread _ownerThread) { ownerThread = _ownerThread; }
		protected void setLockCount(int _lockCount) { lockCount = _lockCount; }

		private GuardState() {}
		
		protected GuardState(Object _id)
		{
			setId(_id);
			setOwnerThread(Thread.currentThread());
			setLockCount(0);
		}

		protected boolean acquireLock()
		{
			boolean acquiredLock = false;

			if (isGuardOwnedByCurrentThread() == true)
			{
				setLockCount(getLockCount() + 1);
				acquiredLock = true;
			}

			return acquiredLock;
		}

		protected boolean releaseLock()
		{
			boolean releasedLock = false;
			
			if (isGuardOwnedByCurrentThread() == true)
			{
				setLockCount(getLockCount() - 1);

				if (getLockCount() == 0)
				{
					releasedLock = true;
				}
				
				if (getLockCount() < 0)
				{
					throw new RuntimeException("Invalid lock state for guard on id: " + getId());
				}
			}
			else
			{
				logger.warn("Thread other than guard owner thread tried to release guard for id:  " + getId());
			}

			return releasedLock;
		}
		
		protected boolean isGuardOwnedByCurrentThread()
		{
			return (Thread.currentThread().equals(getOwnerThread()) == true);
		}
	}
}