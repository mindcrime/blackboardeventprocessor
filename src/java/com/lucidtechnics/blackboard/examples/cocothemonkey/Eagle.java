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

public class Eagle
{
	private String lookingFor;
	private boolean perched;
	private boolean shot;

	public boolean getPerched() { return perched; }
	public boolean getShot() { return shot; }
	public String getLookingFor() { return lookingFor; }

	public void setPerched(boolean _perched) { perched = _perched; }
	public void setShot(boolean _shot) { shot = _shot; }
    public void setLookingFor(String _lookingFor) { lookingFor = _lookingFor; }

    public Eagle() {}

    public Eagle(String _lookingFor)
    {
		setLookingFor(_lookingFor);
		setShot(false);
		setPerched(true);
	}
}