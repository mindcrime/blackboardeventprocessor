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

public final class Utility
{
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