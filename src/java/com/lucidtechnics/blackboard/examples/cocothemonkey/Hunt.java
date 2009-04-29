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
import com.lucidtechnics.blackboard.ChangeInfo;

public class Hunt
   implements Plan
{
	private static Log logger = LogFactory.getLog(Hunt.class);

	private String planName;

	public String getPlanName() { return planName; }
	public void setPlanName(String _planName) { planName = _planName; }

	public Hunt() {}

	public String getName() { return getPlanName(); }

	public void execute(Workspace _workspace)
	{
		Eagle eagle = (Eagle) _workspace.get("eagle");

		eagle.setShot(true);
		eagle.setPerched(false);
		_workspace.remove("eagle");
	}

	public PlanPredicate getPlanPredicate()
	{
		return new PlanPredicate()
		{			
			public boolean isInterested(Workspace _workspace)
			{
				Eagle eagle = (Eagle) _workspace.get("eagle");
				
				boolean isInterested = 	(_workspace.has("hunter") == true) &&
						(eagle != null) &&
										(eagle.getPerched() == true);

				return isInterested;
			}

			public boolean isFinished(Workspace _workspace)
			{
				Eagle eagle = (Eagle) _workspace.get("eagle");

				return (_workspace.hasTargetAction("eagle", ChangeInfo.TARGET_REMOVED) == true);
			}
		};
	}
}