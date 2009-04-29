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

public class ChangeInfoHelper 
{
	private ChangeInfo lastChangeInfo;
	private ChangeInfo[] changeInfoHistory;
	
	public ChangeInfoHelper(ChangeInfo[] _changeInfoHistoryArray)
	{
		if(_changeInfoHistoryArray!=null && _changeInfoHistoryArray.length>0)
		{
			lastChangeInfo = _changeInfoHistoryArray[_changeInfoHistoryArray.length-1];
		}
	
		changeInfoHistory = _changeInfoHistoryArray;
	}
	
	/**
	 * Performs a comparison of the lastChangeAction to the argument.  Returns true if both match.
	 * 
	 * @param targetAction the parameter to compare the target action with
	 * @return
	 */
	public boolean lastChangeActionEquals(int targetAction)
	{
		return lastChangeInfo!=null && lastChangeInfo.getAction()==targetAction; 
	}
	
	/**
	 * Performs a comparison of the lastChangeTargetName to the argument.  Returns true if both match.
	 * 
	 * @param targetName the parameter to compare the target name with
	 * @return
	 */
	public boolean lastChangeTargetNameEqualsIgnoreCase(String targetName)
	{
		return lastChangeInfo!=null && lastChangeInfo.getTargetName()!=null &&
			lastChangeInfo.getTargetName().equalsIgnoreCase(targetName); 
	}
	
	/**
	 * Returns the lastChangeInfo object.  This method may return a null.
	 * @return
	 */
	public ChangeInfo getLastChangeInfo()
	{
		return lastChangeInfo;
	}
	
	public Object getLastChangeTarget()
	{
		if(lastChangeInfo==null)
			return null;
		
		return lastChangeInfo.getTarget();
	}
}
