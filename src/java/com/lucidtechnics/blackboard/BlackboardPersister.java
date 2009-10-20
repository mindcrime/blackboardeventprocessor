package com.lucidtechnics.blackboard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BlackboardPersister
   implements Persister
{
	private static Log logger = LogFactory.getLog(BlackboardPersister.class);
	
	private Blackboard blackboard;
	private String persistenceDir;

	public Blackboard getBlackboard() {  return blackboard; }
	private String getPersistenceDir() { return persistenceDir; }
	
	public void setBlackboard(Blackboard _blackboard) { blackboard = _blackboard; }
	private void setPersistenceDir(String _persistenceDir) { persistenceDir = persistenceDir; }
	
	public BlackboardPersister(Blackboard _blackboard, String _persistenceDir)
	{
		setBlackboard(_blackboard);
		setPersistenceDir(_persistenceDir);
	}

	public void init()
	{		
	}

	public TargetSpace get(Object _workspaceIdentifier)
	{
		return new TargetSpaceImpl();
	}

	public void put(TargetSpace _targetSpace)
	{
		String json = new com.google.gson.Gson().toJson(_targetSpace);

		System.out.println(json);
	}
}