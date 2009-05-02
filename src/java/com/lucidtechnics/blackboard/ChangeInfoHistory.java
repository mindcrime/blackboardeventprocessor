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

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class ChangeInfoHistory
{
	private final static Log logger = LogFactory.getLog(ChangeInfoHistory.class);

	private int count;
	private List changeInfoList;
	private Map targetNameToTargetListMap;
	private Map attributeNameToAttributeListMap;

	public int getCount() { return count; }
	public List getChangeInfoList() { return changeInfoList; }
	public Map getTargetNameToTargetListMap() { return targetNameToTargetListMap; }
	public Map getAttributeNameToAttributeListMap() { return attributeNameToAttributeListMap; }

	public void setCount(int _count) { count = _count; }
	public void setChangeInfoList(List _changeInfoList) { changeInfoList = _changeInfoList; }
	public void setTargetNameToTargetListMap(Map _targetNameToTargetListMap) { targetNameToTargetListMap = _targetNameToTargetListMap; }
	public void setAttributeNameToAttributeListMap(Map _attributeNameToAttributeListMap) { attributeNameToAttributeListMap = _attributeNameToAttributeListMap; }

	public ChangeInfoHistory()
	{
		init();
	}

	private void init()
	{
		setCount(0);
		setChangeInfoList(new ArrayList());
		setTargetNameToTargetListMap(new HashMap());
		setAttributeNameToAttributeListMap(new HashMap());
	}

	public void reset()
	{
		init();
	}
	
	public void eraseHistory()
	{
		setChangeInfoList(new ArrayList());
		setTargetNameToTargetListMap(new HashMap());
		setAttributeNameToAttributeListMap(new HashMap());
	}

	public boolean hasTargetAction(String _targetName, int _action)
	{
		boolean hasTargetAction = false;

		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Change info list: " + getChangeInfoList());
		}
		
		if (getChangeInfoList() != null)
		{
			Iterator changeInfos = getChangeInfoList().iterator();

			while (changeInfos.hasNext() == true && hasTargetAction == false)
			{
				if (logger.isDebugEnabled() == true)
				{
					logger.debug("Checking change info history for target: " + _targetName + " with action: " + _action);
				}
				
				ChangeInfo changeInfo = (ChangeInfo) changeInfos.next();

				if ((changeInfo.getTargetName().equals(_targetName) == true) && (changeInfo.getAction() == _action))
				{
					hasTargetAction = true;
				}
			}
		}

		if (hasTargetAction == true)
		{
			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Change info history has target: " + _targetName + " with action: " + _action);
			}
		}
		
		return hasTargetAction;
	}

	public boolean hasAttributeAction(String _targetName, String _attributeName)
	{
		boolean hasAttributeAction = false;

		TargetAttributeKey key = new TargetAttributeKey(_targetName, _attributeName);
		
		List changeInfoList = (List) getChangeInfoList();

		if (getChangeInfoList() != null)
		{
			Iterator changeInfos = getChangeInfoList().iterator();

			while (changeInfos.hasNext() == true && hasAttributeAction == false)
			{
				ChangeInfo changeInfo = (ChangeInfo) changeInfos.next();

				if ((changeInfo.getTargetName().equals(_targetName) == true) &&
					  (changeInfo.getAttributeName().equals(_attributeName) == true))
				{
					hasAttributeAction = true;
				}
			}
		}

		return hasAttributeAction;
	}
	
	public synchronized void add(ChangeInfo _changeInfo)
	{
		//Add to change info list
		setCount(getCount() + 1);
		getChangeInfoList().add(_changeInfo);

		//add to target specific change info history
		String targetName = _changeInfo.getTargetName();

		List targetList = (List) getTargetNameToTargetListMap().get(targetName);

		if (targetList == null)
		{
			targetList = new ArrayList();
			getTargetNameToTargetListMap().put(targetName, targetList);
		}

		targetList.add(_changeInfo.getTarget());

		//add to attribute specific change info history
		String attributeName = _changeInfo.getAttributeName();
		
		if (attributeName != null)
		{
			TargetAttributeKey targetAttributeKey = new TargetAttributeKey(targetName, attributeName);

			List attributeList = (List) getAttributeNameToAttributeListMap().get(targetAttributeKey);

			if (attributeList == null)
			{
				attributeList = new ArrayList();
				getAttributeNameToAttributeListMap().put(targetAttributeKey, attributeList);
			}

			attributeList.add(_changeInfo.getAttribute());
		}
	}

	public synchronized Object[] toArray(Object[] _objectArray)
	{
		return getChangeInfoList().toArray(_objectArray);
	}

	public synchronized List getTargetHistory(String _targetName)
	{
		ArrayList targetHistoryListCopy = new ArrayList();

		List targetHistoryList = (List) getTargetNameToTargetListMap().get(_targetName);

		if (targetHistoryList != null)
		{
			targetHistoryListCopy.addAll(targetHistoryList);
		}

		return targetHistoryListCopy;
	}

	public synchronized void eraseTargetHistory(String _targetName)
	{
		getTargetNameToTargetListMap().remove(_targetName);
	}

	public synchronized List getAttributeHistory(String _targetName, String _attributeName)
	{
		ArrayList attributeHistoryListCopy = new ArrayList();

		TargetAttributeKey targetAttributeKey = new TargetAttributeKey(_targetName, _attributeName);
		
		List attributeHistoryList = (List) getAttributeNameToAttributeListMap().get(targetAttributeKey);

		if (attributeHistoryList != null)
		{
			attributeHistoryListCopy.addAll(attributeHistoryList);
		}

		return attributeHistoryListCopy;
	}

	public synchronized void eraseAttributeHistory(String _targetName, String _attributeName)
	{
		getAttributeNameToAttributeListMap().remove(new TargetAttributeKey(_targetName, _attributeName));
	}
	
	public class TargetAttributeKey
	{
		private String targetName;
		private String attributeName;

		public String getTargetName() { return targetName; }
		public String getAttributeName() { return attributeName; }

		public void setTargetName(String _targetName) { targetName = _targetName; }
		public void setAttributeName(String _attributeName) { attributeName = _attributeName; }
		
		protected TargetAttributeKey(String _targetName, String _attributeName)
		{
			setTargetName(_targetName);
			setAttributeName(_attributeName);
		}

		public int hashCode()
		{
			return getTargetName().hashCode() + getAttributeName().hashCode();
		}
		
		public boolean equals(Object _object)
		{
			boolean equals = false;

			if ((_object != null) && (_object instanceof TargetAttributeKey) == true)
			{
				TargetAttributeKey targetAttributeKey = (TargetAttributeKey) _object;
				
				equals = this.getTargetName().equals(targetAttributeKey.getTargetName()) &&
						 this.getAttributeName().equals(targetAttributeKey.getAttributeName());
			}

			return equals;
		}
	}

	public int size()
	{
		return getChangeInfoList().size();
	}
}