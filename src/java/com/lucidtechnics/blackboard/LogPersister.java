package com.lucidtechnics.blackboard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogPersister
   implements Persister
{
	private static Log logger = LogFactory.getLog(LogPersister.class);
	
	public LogPersister() {}

	public TargetSpace get(Object _workspaceIdentifier)
	{
		throw new RuntimeException("Log Persister cannot retrieve target spaces");
	}

	public void put(TargetSpace _targetSpace)
	{
		if (logger.isInfoEnabled() == true)
		{
			logger.info(_targetSpace.toJson());
		}
	}
}