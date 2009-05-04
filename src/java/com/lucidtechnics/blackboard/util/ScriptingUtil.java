/*
 Copyright 2007, Lucid Technics, LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 except in compliance with the License. You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in
 writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 specific language governing permissions and limitations under the License.
*/

package com.lucidtechnics.blackboard.util;

public interface ScriptingUtil
{
    public void bind(String _name, Object _value);
	public void loadScript(String _scriptResource);
	public Object executeScriptResource(String[] _scriptResources);
    public Object executeScriptResource(String _scriptResource);
    public Object execute(String _script);
}