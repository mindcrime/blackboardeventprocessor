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

import xeus.jcl.JarClassLoader;
import xeus.jcl.JclObjectFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Launcher
{
	private static Log logger = LogFactory.getLog(Launcher.class);

	public void launch()
	{
		final Blackboard blackboard = new Blackboard();

		java.io.File currentDirectory = new java.io.File(".");
		TargetSpace targetSpace = new TargetSpaceImpl();
		targetSpace.setBlackboard(blackboard);

		logger.info("the current directory is: " + currentDirectory.getName());
		java.io.File[] generatorArray = currentDirectory.listFiles();

		for (int i = 0; i < generatorArray.length; i++)
		{
			com.lucidtechnics.blackboard.Plan plan = null;

			if (generatorArray[i].isDirectory() == false && generatorArray[i].getName().endsWith("blackboard.configuration.js") == false)
			{
				if (generatorArray[i].isDirectory() == false && generatorArray[i].getName().endsWith(".js") == true)
				{
					logger.info("Executing generator: " + generatorArray[i].getName());

					plan = new JavaScriptPlan();
				}
				else if (generatorArray[i].isDirectory() == false && generatorArray[i].getName().endsWith(".rb") == true)
				{
					logger.info("Executing generator: " + generatorArray[i].getName());
					plan = new RubyPlan();
				}

				if (plan != null)
				{
					plan.setName(generatorArray[i].getName());
					plan.setPath(generatorArray[i].getAbsolutePath());
					plan.execute(new com.lucidtechnics.blackboard.WorkspaceContext(targetSpace, plan));
				}
			}
		}

		logger.info("Driver execution is completed. Plans may still be processing.");

		Object object = new Object();

		synchronized(object)
		{
			try { object.wait(); } catch (InterruptedException e) {}
		}
	}	
}