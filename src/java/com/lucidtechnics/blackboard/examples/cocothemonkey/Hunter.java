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

package com.lucidtechnics.blackboard.examples.cocothemonkey;

@com.lucidtechnics.blackboard.Event(appName="example", workspaceName="CocoTheMonkey", name="hunter", workspaceIdentifier="inForestOf")

public class Hunter
{
    private String inForestOf;

	public String getInForestOf() { return inForestOf; }
	public void setInForestOf(String _inForestOf) { inForestOf = _inForestOf; }
	
    public Hunter() {}
    
    public Hunter(String _inForestOf)
    {
		setInForestOf(_inForestOf);
    }
}