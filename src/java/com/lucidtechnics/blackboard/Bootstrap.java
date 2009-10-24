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

public class Bootstrap
{
	static
	{
		try
		{
			JarClassLoader jarClassLoader = new JarClassLoader();
			
			jarClassLoader.add("lib" + java.io.File.separator + "core"); //Recursively load all jar files in the folder/sub-folder(s)
			jarClassLoader.add("lib" + java.io.File.separator + "ext"); //Recursively load all jar files in the folder/sub-folder(s)
			
			classLoader = jarClassLoader;
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
	}

	private static JarClassLoader classLoader;

	public static JarClassLoader getClassLoader() { return classLoader; }

	public static final void boot()
	{
		try
		{
			JclObjectFactory factory = JclObjectFactory.getInstance();
			final Object launcher = factory.create	(getClassLoader(), "com.lucidtechnics.blackboard.Launcher");

			Class launcherClass = getClassLoader().loadClass("com.lucidtechnics.blackboard.Launcher");
			java.lang.reflect.Method method = launcherClass.getMethod("launch");			
			method.invoke(launcher);
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
	}
	
	public static final void main(String[] _args)
	{
		boot();
	}
}