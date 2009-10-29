package com.lucidtechnics.blackboard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NullPersister
   implements Persister
{
	private static Log logger = LogFactory.getLog(NullPersister.class);
	
	public NullPersister(Blackboard _blackboard, String _persistenceDir)
	{
	}

	public void init()
	{		
	}

	public TargetSpace get(Object _workspaceIdentifier)
	{
		throw new RuntimeException("Null persister is place.  Persistence not handled by Blackboard");
	}

	public void put(TargetSpace _targetSpace)
	{
		if (logger.isInfoEnabled() == true)
		{
			logger.info("Target space identified by: " + _targetSpace.getWorkspaceIdentifier() + " received by NullPersister");
		}
	}
}