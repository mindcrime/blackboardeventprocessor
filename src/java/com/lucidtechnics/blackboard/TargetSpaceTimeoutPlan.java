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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucidtechnics.blackboard.Plan;
import com.lucidtechnics.blackboard.PlanPredicate;
import com.lucidtechnics.blackboard.Workspace;
import com.lucidtechnics.blackboard.Target;
import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;

import java.util.List;

public class TargetSpaceTimeoutPlan
   implements Plan
{
	private static Log logger = LogFactory.getLog(TargetSpaceTimeoutPlan.class);

	private String planName;

	public String getPlanName() { return planName; }
	public void setPlanName(String _planName) { planName = _planName; }

	public TargetSpaceTimeoutPlan() {}

	public String getName() { return getPlanName(); }

	public void execute(Workspace _workspace)
	{
		List targetSpaceTimeoutEventList = _workspace.getTargetHistory("Blackboard.TargetSpaceTimeoutEvent");

		for (int i = 0; i < targetSpaceTimeoutEventList.size(); i++)
		{
			TargetSpaceTimeoutEvent targetSpaceTimeoutEvent = (TargetSpaceTimeoutEvent) targetSpaceTimeoutEventList.get(i);
			TargetSpace targetSpace = targetSpaceTimeoutEvent.getTargetSpace();
			WorkspaceConfiguration workspaceConfiguration = targetSpaceTimeoutEvent.getWorkspaceConfiguration();
			Blackboard blackboard = targetSpaceTimeoutEvent.getBlackboard();
			
			long currentTimeMillis = System.currentTimeMillis();
			long timeoutTimeMillis = workspaceConfiguration.getWorkspaceTimeoutInSeconds();
			long newTimeoutTimeMillis = currentTimeMillis + timeoutTimeMillis;

			boolean targetSpaceGuarded = false;

			try
			{
				targetSpaceGuarded = blackboard.guardTargetSpace(targetSpace.getWorkspaceIdentifier(), false);

				if (targetSpaceGuarded == true)
				{
					if (targetSpace.isRetired() == true ||
						  targetSpace.isCompleted() == true ||
						  targetSpace.isTerminated() == true)
					{
						//do nothing
					}
					else
					{
						long lastActiveTimeMillis = targetSpace.getLastActiveTime();
						long inactivityTimeMillis = currentTimeMillis - lastActiveTimeMillis;

						if (timeoutTimeMillis <= inactivityTimeMillis)
						{
							targetSpace.setTerminated();

							try
							{
								blackboard.acquireBlackboardWriteLock();
								blackboard.removeFromBlackboard(targetSpace, false);
							}
							finally
							{
								blackboard.releaseBlackboardWriteLock();
							}

							blackboard.retireTargetSpace(targetSpace);
						}
						else
						{
							//reset timeout period
							long remainingTimeMillis = timeoutTimeMillis - inactivityTimeMillis;
							_workspace.schedulePlaceOnBlackboard(targetSpaceTimeoutEvent, remainingTimeMillis);
						}
					}
				}
				else
				{
					//this means that it is currenty active so
					//reset timeout period
					_workspace.schedulePlaceOnBlackboard(targetSpaceTimeoutEvent, newTimeoutTimeMillis);
				}
			}
			finally
			{
				if (targetSpaceGuarded == true) { blackboard.releaseTargetSpace(targetSpace.getWorkspaceIdentifier()); }
			}
		}

		_workspace.clearTargetHistory("Blackboard.targetSpaceTimeoutEvent");
	}

	public PlanPredicate getPlanPredicate()
	{
		return new PlanPredicate()
		{			
			public boolean isInterested(Workspace _workspace)
			{
				return 	(_workspace.has("Blackboard.TargetSpaceTimeoutEvent") == true);
			}

			public boolean isFinished(Workspace _workspace)
			{
				return false;
			}
		};
	}	
}