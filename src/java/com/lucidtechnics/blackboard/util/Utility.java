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

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public final class Utility
{
	public static Class[] getAllTypes(Class _class)
	{
		ArrayList allTypesList = new ArrayList();
		ArrayList searchDomainList = new ArrayList();
		searchDomainList.add(_class);
		allTypesList.add(_class);

		allTypesList = getAllTypes(searchDomainList, allTypesList);

		return (Class[]) allTypesList.toArray(new Class[allTypesList.size()]);
	}

	public static ArrayList getAllTypes(ArrayList _searchDomainList, ArrayList _allTypesList)
	{
		for (int i = 0; i < _searchDomainList.size(); i++)
		{
			Class searchClass = (Class) _searchDomainList.get(i);
			_searchDomainList.remove(searchClass);

			Class superClass = searchClass.getSuperclass();

			if (superClass != null && (_allTypesList.contains(superClass) == false))
			{
				_searchDomainList.add(superClass);
				_allTypesList.add(superClass);
			}

			Class[] interfaceArray = searchClass.getInterfaces();

			if (interfaceArray != null)
			{
				for (int j = 0; j < interfaceArray.length; j++)
				{
					if (interfaceArray[j] != null && (_allTypesList.contains(interfaceArray[j]) == false))
					{
						_searchDomainList.add(interfaceArray[j]);
						_allTypesList.add(interfaceArray[j]);
					}
				}
			}
		}

		if (_searchDomainList.isEmpty() == false)
		{
			_allTypesList = getAllTypes(_searchDomainList, _allTypesList);
		}

		return _allTypesList;
	}

	public static String toString(Object _object)
	{
		StringBuffer stringBuffer = new StringBuffer();
		
		try
		{

			Map propertyMap = BeanUtils.describe(_object);
			
			stringBuffer.append(_object.getClass().getName() + "\n");

			Iterator propertyNames = propertyMap.keySet().iterator();

			while (propertyNames.hasNext() == true)
			{
				String property = (String) propertyNames.next();

				if ("class".equalsIgnoreCase(property) == false)
				{
					String value = (String) propertyMap.get(property);
					stringBuffer.append("  -- " + property + " --> " + value + "\n");
				}
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}

		return stringBuffer.toString();
	}
}