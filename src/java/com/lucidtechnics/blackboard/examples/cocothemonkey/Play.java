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

public class Play
   implements Plan
{
	private static Log logger = LogFactory.getLog(Play.class);

	private String planName;

	public String getPlanName() { return planName; }
	public void setPlanName(String _planName) { planName = _planName; }

	public Play() {}

	public String getName() { return getPlanName(); }

	public void execute(Workspace _workspace)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Monkey playing now");
		}

		Monkey monkey = (Monkey) _workspace.get("monkey");
		monkey.setPlaying(true);
		monkey.setEating(false);
		monkey.setSleeping(false);

		_workspace.putOnWorkspace("played");
	}

	public PlanPredicate getPlanPredicate()
	{
		return new PlanPredicate()
		{			
			public boolean isInterested(Workspace _workspace)
			{				
				return 	(_workspace.has("monkey") == true) &&
						(_workspace.has("eagle") == false);
			}

			public boolean isFinished(Workspace _workspace)
			{
				return (_workspace.has("played") == true);
			}
		};
	}
}