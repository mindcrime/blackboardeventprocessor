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

var fruit = WORKSPACE.get("fruit");

if (WORKSPACE.has("fruit") == true) && (fruit.eaten == true)
{				
	if (LOGGER.isDebugEnabled() == true)
	{
		LOGGER.debug("Monkey sleeping now");
	}

	var monkey = WORKSPACE.get("monkey");

	monkey.sleeping = true;
	monkey.playing = false;
	monkey.eating = false;

	WORKSPACE.putOnWorkspace("slept");

	PLAN_CONTEXT.isFinished = true;
}