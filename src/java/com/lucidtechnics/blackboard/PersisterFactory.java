package com.lucidtechnics.blackboard;

public class PersisterFactory
{
	public static Persister make(String _type, String _persistenceDir, Blackboard _blackboard)
	{
		Persister persister = null;
				
		if ("inkwell".equalsIgnoreCase(_type) == true)
		{
			persister = new InkwellPersister(_blackboard, _persistenceDir);
			persister.init();
		}			
		else
		{
			persister = new BlackboardPersister(_blackboard, _persistenceDir);
			persister.init();
		}			
		
		return persister; 
	}
}