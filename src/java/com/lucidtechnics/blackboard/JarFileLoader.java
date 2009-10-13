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

public class JarFileLoader
   extends java.net.URLClassLoader
{
	private static Log log = LogFactory.getLog(JarFileLoader.class);

	public JarFileLoader(java.net.URL[] urls)
	{
		super (urls);
	}

	public void addFile(String _path)
	{
		try
		{
			boolean alreadyLoadedJar = false;
			String urlPath = "jar:file://" + _path + "!/";
			java.net.URL url = new java.net.URL(urlPath);

			java.net.URLClassLoader systemClassLoader = (java.net.URLClassLoader) ClassLoader.getSystemClassLoader();
			java.net.URL[] urlArray = systemClassLoader.getURLs();
			
			for (int i = 0; i < urlArray.length; i++)
			{
				if (org.apache.commons.lang.StringUtils.equalsIgnoreCase(urlArray[i].toString(), url.toString()) == true)
				{
					alreadyLoadedJar = true;
				}
			}

			if (alreadyLoadedJar == false)
			{
				Class urlClassLoaderClass = java.net.URLClassLoader.class;
				java.lang.reflect.Method method = urlClassLoaderClass.getDeclaredMethod("addURL", java.net.URL.class);
				method.setAccessible(true);
				method.invoke(systemClassLoader, url);
			}
			else
			{
				log.info("Already loaded: " + url.toString());
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			throw new RuntimeException(t);
		}
	}
}