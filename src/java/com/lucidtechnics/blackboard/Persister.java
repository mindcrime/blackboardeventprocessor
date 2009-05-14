package com.lucidtechnics.blackboard;

public interface Persister
{
	public void init();
	public TargetSpace get(Object _object);
	public void put(TargetSpace _targetSpace);
}