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
import com.lucidtechnics.blackboard.util.ScriptingUtil;
import com.lucidtechnics.blackboard.util.Jsr223ScriptingUtil;

public class Jsr223Plan
   implements Plan
{
	private static Log log = LogFactory.getLog(Jsr223Plan.class);

	private String name;
	private String path;
	private String engineName;

	protected Jsr223Plan() {}

	protected Jsr223Plan(String _engineName)
	{
		setEngineName(_engineName);
	}

	protected Jsr223Plan(String _name, String _path, String _engineName)
	{
		setName(_name);
		setPath(_path);
		setEngineName(_engineName);
	}
	
	public String getName() { return name; }
	public void setName(String _name) { name = _name; }

	public String getPath() { return path; }
	public void setPath(String _path) { path = _path; }

	public String getEngineName() { return engineName; }
	public void setEngineName(String _engineName) { engineName = _engineName; }

	public static boolean hasScriptingEngine(String _extension)
	{
		return Jsr223ScriptingUtil.hasScriptingEngine(_extension);
	}
	
	public boolean execute(Workspace _workspace)
	{
		PlanContext planContext = new PlanContext(getName());

		ScriptingUtil scriptingUtil = new Jsr223ScriptingUtil(getEngineName());

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
			log.debug("Execution for plan: " + getPath());
		}
		
		scriptingUtil.executeScript(scriptResources);

		if (log.isDebugEnabled() == true)
		{
			log.debug("Completed execution for plan: " + getName());
		}

		return planContext.getIsFinished();
	}
}