package com.lucidtechnics.blackboard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InkwellPersister
   implements Persister
{
	private static Log logger = LogFactory.getLog(InkwellPersister.class);

	private String persistenceDir;
	private com.lucidlabs.inkwell.Inkwell database;
	
	private String getPersistenceDir() { return persistenceDir; }
	private void setPersistenceDir(String _persistenceDir) { persistenceDir = _persistenceDir; }

	private com.lucidlabs.inkwell.Inkwell getDatabase() { return database; }
	private void setDatabase(com.lucidlabs.inkwell.Inkwell _database) { database = _database; }
	
	public InkwellPersister(Blackboard _blackboard, String _persistenceDir)
	{
		setDatabase(new com.lucidlabs.inkwell.Inkwell(_persistenceDir, "blackboard"));
		setPersistenceDir(_persistenceDir);
	}

	public void init() {}

	public TargetSpace get(Object _workspaceIdentifier)
	{
		throw new RuntimeException("Not implemented as yet");
	}

	public void put(TargetSpace _targetSpace)
	{
		String json = new com.google.gson.Gson().toJson(_targetSpace);
		getDatabase().insert(_targetSpace.getWorkspaceIdentifier().toString(), json);
	}
}