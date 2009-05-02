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

if ((WORKSPACE.has("fruit") === true) &&
	  (WORKSPACE.has("monkey") === true))
{
	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("Monkey eating now");
	}

	var monkey = WORKSPACE.get("monkey");

	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("Monkey is of class: " + monkey.getClass());
	}

	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("Fruit is of class: " + fruit.getClass());
	}

	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("fruit.getEaten: " + fruit.eaten);
	}
	
	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("monkey.getSleeping: " + monkey.sleeping);
	}
	
	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("monkey.getPlaying: " + monkey.playing);
	}
	
	if (LOGGER.isDebugEnabled() === true)
	{
		LOGGER.debug("monkey.getEating: " + monkey.eating);
	}
		
	fruit.eaten = true;
	monkey.sleeping = false;
	monkey.playing = false;
	monkey.eating = true;
		
	WORKSPACE.putOnWorkspace("eaten");

	if (LOGGER.isDebugEnabled() == true)
	{
		LOGGER.debug("fruit.getEaten: " + fruit.eaten);
		LOGGER.debug("monkey.getSleeping: " + monkey.sleeping);
		LOGGER.debug("monkey.getPlaying: " + monkey.playing);
		LOGGER.debug("monkey.getEating: " + monkey.eating);

		LOGGER.debug("Monkey intercepter is: " + monkey.interceptor);
		LOGGER.debug("Monkey intercepter class is: " + monkey.interceptor.getClass());
		LOGGER.debug("Fruit intercepter is: " + fruit.intercepter);
		LOGGER.debug("Fruit intercepter class is: " + fruit.intercepter().getClass());
	}

	PLAN_CONTEXT.isFinished = true;
}