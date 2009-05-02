package com.lucidtechnics.blackboard;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucidtechnics.blackboard.ChangeInfo;
import com.lucidtechnics.blackboard.util.JavascriptingUtil;

public class JavaScriptPlan
   implements Plan
{
	private static Log log = LogFactory.getLog(JavaScriptPlan.class);

	private String name;
	private String path;

	protected JavaScriptPlan(String _name, String _path)
	{
		setName(_name);
		setPath(_path);
	}
	
	public String getName() { return name; }
	public void setName(String _name) { name = _name; }

	public String getPath() { return path; }
	public void setPath(String _path) { path = _path; }
	
	public boolean execute(Workspace _workspace)
	{
		PlanContext planContext = new PlanContext();

		JavascriptingUtil scriptingUtil = new JavascriptingUtil();

		scriptingUtil.bind("PLAN_CONTEXT", planContext);
		scriptingUtil.bind("WORKSPACE", _workspace);
		scriptingUtil.bind("LOGGER", log);

		scriptingUtil.bind("WORKSPACE_CLEARED", ChangeInfo.WORKSPACE_CLEARED);
		scriptingUtil.bind("TARGET_ADDED", ChangeInfo.TARGET_ADDED);
		scriptingUtil.bind("TARGET_UPDATED", ChangeInfo.TARGET_UPDATED);
		scriptingUtil.bind("TARGET_REMOVED", ChangeInfo.TARGET_REMOVED);
		scriptingUtil.bind("ATTRIBUTE_SET", ChangeInfo.ATTRIBUTE_SET);
		scriptingUtil.bind("NO_CHANGE", ChangeInfo.NO_CHANGE);

		String[] scriptResources = new String[1];

		scriptResources[0] = getPath();

		if (log.isDebugEnabled() == true)
		{
			log.debug("JavaScript execution for plan: " + getPath());
		}
		
		scriptingUtil.executeScriptResource(scriptResources);

		if (log.isDebugEnabled() == true)
		{
			log.debug("Completed JavaScript execution for plan: " + getName());
		}

		return planContext.getIsFinished();
	}
}