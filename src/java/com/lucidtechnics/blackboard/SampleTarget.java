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

public class SampleTarget
{
	private Integer attribute2;
	private long attributeLong;
	private short attributeShort;
	private byte attributeByte;
	private int attributeInt;
	private float attributeFloat;
	private double attributeDouble;
	private char attributeChar;
	private boolean attributeBoolean;
	
	protected void setAttribute2(Integer _valueInteger)
		throws Exception
	{
		attribute2 = _valueInteger;
	}

	public void setAttributeLong(long _attributeLong) { attributeLong = _attributeLong; }
	public void setAttributeShort(short _attributeShort) { attributeShort = _attributeShort; }
	public void setAttributeByte(byte _attributeByte) { attributeByte = _attributeByte; }
	public void setAttributeInt(int _attributeInt) { attributeInt = _attributeInt; }
	public void setAttributeFloat(float _attributeFloat) { attributeFloat = _attributeFloat; }
	public void setAttributeDouble(double _attributeDouble) { attributeDouble = _attributeDouble; }
	public void setAttributeChar(char _attributeChar) { attributeChar = _attributeChar; }
	public void setAttributeBoolean(boolean _attributeBoolean) { attributeBoolean = _attributeBoolean; }
	
	protected Object myMethod(String _animal, long _vehicle, String _person, String _jump)
	{
		return new Object();
	}
	
	protected Object myMethod()
	{
		throw new BlackboardException("Unable to access protected methods on Blackboard object");
	}
	
	public long getAttributeLong()
	{
		return attributeLong;
	}

	public SampleTarget() { }

	public SampleTarget(Integer _attribute2, long _attributeLong)
			throws Exception
	{
		setAttribute2(_attribute2);
		setAttributeLong(_attributeLong);
	}
}