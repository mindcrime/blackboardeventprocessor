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

import com.lucidtechnics.blackboard.util.Utility;

public class TargetWrapper
   extends SampleTarget
   implements Target
{
	private Object object;
	private Intercepter intercepter;

	public Object getBlackboardObject() { return object; }
	public Intercepter getIntercepter() { return intercepter; }

	public void setBlackboardObject(Object _object) { object = _object; }
	public void setIntercepter(Intercepter _intercepter) { intercepter = _intercepter; }

	public void setAttribute2(Integer _valueInteger)
		throws Exception
	{
		if (getIntercepter() != null)
		{
			getIntercepter().monitor("superClassName", "subClassName", "methodName", "targetName", this, _valueInteger);
			
			((SampleTarget) getBlackboardObject()).setAttribute2(_valueInteger);
		}
	}

	public void setAttributeLong(long _attributeLong)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeLong);
		super.setAttributeLong(_attributeLong);
	}
	
	public Object myMethod(String _monkey, long _cab, String _me, String _jump)
	{
		SampleTarget sampleTarget = (SampleTarget) getBlackboardObject();
		return sampleTarget.myMethod(_monkey, _cab, _me, _jump);
	}
	
	public short getAttributeLong1()
	{
		SampleTarget sampleTarget = (SampleTarget) getBlackboardObject();
		return 1;
	}
	
	public void setAttributeShort(short _attributeShort)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeShort);
		super.setAttributeShort(_attributeShort);
	}
	
	public void setAttributeByte(byte _attributeByte)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeByte);
		super.setAttributeByte(_attributeByte);
	}
	
	public void setAttributeInt(int _attributeInt)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeInt);
		super.setAttributeInt(_attributeInt);
	}
	
	public void setAttributeFloat(float _attributeFloat)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeFloat);
		super.setAttributeFloat(_attributeFloat);
	}
	
	public void setAttributeDouble(double _attributeDouble)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeDouble);
		super.setAttributeDouble(_attributeDouble);
	}
	
	public void setAttributeChar(char _attributeChar)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeChar);
		super.setAttributeChar(_attributeChar);
	}
	
	public void setAttributeBoolean(boolean _attributeBoolean)
	{
		getIntercepter().monitor("SuperClassName", "subclassname", "methodname", "targetname", this, _attributeBoolean);
		super.setAttributeBoolean(_attributeBoolean);
	}
	
	public void testMethod(Integer[] _stringArray)
	{
		boolean test = true;
		Integer[] stringArray = { 0, 1, 2 };
		testMethod(stringArray);
		setAttributeBoolean(test);
		System.out.println();
	}

	public String[] method()
	{
		String[] stringArray = {"", "A"}; 
		return stringArray;
	}
	
	public TargetWrapper() { }
	
	public String toString()
	{
		return Utility.toString(getBlackboardObject());
	}
}