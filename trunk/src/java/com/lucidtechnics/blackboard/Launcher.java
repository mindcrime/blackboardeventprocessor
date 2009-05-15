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

public class Launcher
{
	private static Log logger = LogFactory.getLog(Launcher.class);

	public static final void main(String[] _args)
	{
		final Blackboard blackboard = new Blackboard();
		blackboard.init();

		java.io.File currentDirectory = new java.io.File(".");
		TargetSpace targetSpace = new TargetSpaceImpl();
		targetSpace.setBlackboard(blackboard);

		java.io.File[] generatorArray = currentDirectory.listFiles();

		for (int i = 0; i < generatorArray.length; i++)
		{
			com.lucidtechnics.blackboard.Plan plan = null;
			
			if (generatorArray[i].isDirectory() == false && generatorArray[i].getName().endsWith(".js") == true)
			{
				logger.debug("Executing generator: " + generatorArray[i].getName());

				plan = new com.lucidtechnics.blackboard.JavaScriptPlan(
					generatorArray[i].getName(), generatorArray[i].getAbsolutePath());
			}
			else if (generatorArray[i].isDirectory() == false && generatorArray[i].getName().endsWith(".rb") == true)
			{
				logger.debug("Executing generator: " + generatorArray[i].getName());

				plan = new com.lucidtechnics.blackboard.RubyPlan(
					generatorArray[i].getName(), generatorArray[i].getAbsolutePath());
			}

			if (plan != null)
			{
				plan.execute(new com.lucidtechnics.blackboard.WorkspaceContext(targetSpace, plan));
			}
		}

		logger.info("Plan execution is completed");
		
		Object object = new Object();
		
		synchronized(object)
		{
			try { object.wait(); } catch (InterruptedException e) {}
		}
	}
}