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

public interface Intercepter
{
	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, Object _value);
			
	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, int _int);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, short _short);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, char _char);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, long _long);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, boolean _boolean);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, byte _byte);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, double _double);

	public void monitor(String _superClassName, String _subClassName,
						String _methodName, String _targetName,
						Object _target, float _float);

}