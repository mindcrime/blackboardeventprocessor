package com.lucidtechnics.blackboard;

import com.db4o.Db4o;
import com.db4o.ObjectServer;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Db4oPersister
   implements Persister
{
	private static Log logger = LogFactory.getLog(Db4oPersister.class);
	
	private ObjectServer server;
	private Blackboard blackboard;

	public ObjectServer getServer() { return server; }
	public Blackboard getBlackboard() {  return blackboard; }
	
	public void setServer(ObjectServer _server) { server = _server; }
	public void setBlackboard(Blackboard _blackboard) { blackboard = _blackboard; }
	
	public Db4oPersister(Blackboard _blackboard, String _persistenceDir)
	{
		Db4o.configure().allowVersionUpdates(true);
		Db4o.configure().callConstructors(true);
		Db4o.configure().exceptionsOnNotStorable(true);
		Db4o.configure().objectClass(TargetSpace.class).objectField("workspaceIdentifier").indexed(true);

		setServer(Db4o.openServer(_persistenceDir.replaceAll("/$", "") + "/blackboard.db4o", _blackboard.getPort()));
		getServer().grantAccess(_blackboard.getUser(), _blackboard.getPassword());

		setBlackboard(_blackboard);
	}

	public void init()
	{		
	}

	public TargetSpace get(Object _workspaceIdentifier)
	{
		ObjectContainer targetSpaceDatabase = null;
		TargetSpace targetSpace = new TargetSpaceImpl(_workspaceIdentifier);

		try
		{
			targetSpaceDatabase = Db4o.openClient(getBlackboard().getHost(), getBlackboard().getPort(),
				getBlackboard().getUser(), getBlackboard().getPassword());
			
			ObjectSet objectSet = targetSpaceDatabase.get(targetSpace);

			if (objectSet.size() > 1)
			{
				logger.warn("Retrieved more than one workspace with identifier: " + _workspaceIdentifier);
			}
			else if (objectSet.size() == 0)
			{
				throw new RuntimeException("Unable to retrieve workspace with identifier: " + _workspaceIdentifier);
			}
			else
			{
				if (logger.isDebugEnabled() == true)
				{
					logger.debug("Found exactly one workspace for retrieval identified by: " + _workspaceIdentifier);
				}
			}

			targetSpace = (TargetSpace) objectSet.next();

			if (targetSpace == null)
			{
				throw new RuntimeException("Null found while retrieving workspace with identifier: " + _workspaceIdentifier);
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
		finally
		{
			targetSpaceDatabase.close();
		}

		return targetSpace;
	}

	public void put(TargetSpace _targetSpace)
	{
		ObjectContainer targetSpaceDatabase = null;

		try
		{
			targetSpaceDatabase = Db4o.openClient(getBlackboard().getHost(), getBlackboard().getPort(),
				getBlackboard().getUser(), getBlackboard().getPassword());
			
			targetSpaceDatabase.set(_targetSpace);
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
		finally
		{
			if (targetSpaceDatabase != null)  { targetSpaceDatabase.close(); }
		}
	}
}