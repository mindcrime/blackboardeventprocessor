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

class PlanContext
	implements Plan
{
	private String name;
	private Plan plan;
	private PlanPredicate planPredicate;

	public String getName() { return name; }
	protected Plan getPlan() { return plan; }
	public PlanPredicate getPlanPredicate() { return planPredicate; }
	
	private void setName(String _name) { name = _name; }
	private void setPlan(Plan _plan) { plan = _plan; }
	private void setPlanPredicate(PlanPredicate _planPredicate) { planPredicate = _planPredicate; }
	
	public PlanContext() {}
	
	public PlanContext(Plan _plan)
	{
		setPlan(_plan);
		
		setName(_plan.getName());
		setPlanPredicate(_plan.getPlanPredicate());
	}

	public void execute(Workspace _workspace)
	{
		getPlan().execute(_workspace);
	}

	public String toString()
	{
		return getName();
	}
}