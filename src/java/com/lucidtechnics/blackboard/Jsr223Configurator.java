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

import com.lucidtechnics.blackboard.util.ScriptingUtil;
import com.lucidtechnics.blackboard.util.Jsr223ScriptingUtil;
import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;

public class Jsr223Configurator
   implements Configurator
{
	private static Log log = LogFactory.getLog(Jsr223Configurator.class);

	private String extension;

	private String getExtension() { return extension; }
	private void setExtension(String _extension) { extension = _extension; }
	
	public Jsr223Configurator(String _extension)
	{
		setExtension(_extension);
	}

	public void execute(WorkspaceConfiguration _workspaceConfiguration, String _path)
	{
		ScriptingUtil scriptingUtil = new Jsr223ScriptingUtil(getExtension());

		scriptingUtil.bind("CONFIGURATION", _workspaceConfiguration);
		scriptingUtil.bind("LOGGER", log);

		String[] scriptResources = new String[1];

		scriptResources[0] = _path;

		if (log.isDebugEnabled() == true)
		{
			log.debug("Execution for workspace configuration: " + _path);
		}
		
		scriptingUtil.executeScript(scriptResources);

		if (log.isDebugEnabled() == true)
		{
			log.debug("Completed execution for workspace configuration: " + _path);
		}
	}

	public void execute(BlackboardConfiguration _blackboardConfiguration, String _path)
	{
		ScriptingUtil scriptingUtil = new Jsr223ScriptingUtil(getExtension());

		scriptingUtil.bind("CONFIGURATION", _blackboardConfiguration);
		scriptingUtil.bind("LOGGER", log);

		String[] scriptResources = new String[1];

		scriptResources[0] = _path;

		if (log.isDebugEnabled() == true)
		{
			log.debug("Execution for blackboard configuration: " + _path);
		}

		scriptingUtil.executeScript(scriptResources);

		if (log.isDebugEnabled() == true)
		{
			log.debug("Completed execution for blackboard configuration: " + _path);
		}
	}
}