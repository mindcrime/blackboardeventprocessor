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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucidtechnics.blackboard.Plan;
import com.lucidtechnics.blackboard.PlanPredicate;
import com.lucidtechnics.blackboard.Workspace;
import com.lucidtechnics.blackboard.Target;

public class Eat
{
	private static Log logger = LogFactory.getLog(Eat.class);

	private String planName;

	public String getPlanName() { return planName; }
	public void setPlanName(String _planName) { planName = _planName; }

	public Eat() {}

	public String getName() { return getPlanName(); }

	public void execute(Workspace _workspace)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monkey eating now");
		}

		Fruit fruit = (Fruit) _workspace.get("fruit");
		Monkey monkey = (Monkey) _workspace.get("monkey");

		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monkey is of class: " + monkey.getClass());
		}
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Fruit is of class: " + fruit.getClass());
		}

		if (logger.isDebugEnabled() == true)
		{
			logger.debug("fruit.getEaten: " + fruit.getEaten());
		}
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("monkey.getSleeping: " + monkey.getSleeping());
		}
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("monkey.getPlaying: " + monkey.getPlaying());
		}
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("monkey.getEating: " + monkey.getEating());
		}
		
		fruit.setEaten(true);
		monkey.setSleeping(false);
		monkey.setPlaying(false);
		monkey.setEating(true);
		
		_workspace.put("eaten");

		if (logger.isDebugEnabled() == true)
		{
			logger.debug("fruit.getEaten: " + fruit.getEaten());
			logger.debug("monkey.getSleeping: " + monkey.getSleeping());
			logger.debug("monkey.getPlaying: " + monkey.getPlaying());
			logger.debug("monkey.getEating: " + monkey.getEating());

			logger.debug("Monkey intercepter is: " + ((Target) monkey).getIntercepter());
			logger.debug("Monkey intercepter class is: " + ((Target) monkey).getIntercepter().getClass());
			logger.debug("Fruit intercepter is: " + ((Target) fruit).getIntercepter());
			logger.debug("Fruit intercepter class is: " + ((Target) fruit).getIntercepter().getClass());
		}
	}

	public PlanPredicate getPlanPredicate()
	{
		return new PlanPredicate()
		{			
			public boolean isInterested(Workspace _workspace)
			{
				Fruit fruit = (Fruit) _workspace.get("fruit");
				
				return 	(_workspace.has("fruit") == true) &&
						(_workspace.has("monkey") == true) &&
						((fruit instanceof Mango) == true);
			}

			public boolean isFinished(Workspace _workspace)
			{
				Fruit fruit = (Fruit) _workspace.get("fruit");

				return 	(_workspace.has("eaten") == true);
			}
		};
	}	
}