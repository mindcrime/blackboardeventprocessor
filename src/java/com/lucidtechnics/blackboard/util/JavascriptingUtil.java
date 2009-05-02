/*
 Copyright 2007, Lucid Technics, LLC.

 Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 except in compliance with the License. You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in
 writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 specific language governing permissions and limitations under the License.
*/

package com.lucidtechnics.blackboard.util;

import java.io.Reader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucidtechnics.blackboard.util.PropertyUtil;

public class JavascriptingUtil
{
	private static Log log = LogFactory.getLog(JavascriptingUtil.class);

	private Set<String> scriptResourceSet;
	private Map<String, Object> bindingsMap;
	private Scriptable scope;

	private Set<String> getScriptResourceSet() { return scriptResourceSet; }
	private Scriptable getScope() { return scope; }
	private Map<String, Object> getBindingsMap() { return bindingsMap; }

	private void setScriptResourceSet(Set<String> _scriptResourceSet) {  scriptResourceSet = _scriptResourceSet; }
	public void setScope(Scriptable _scope) { scope = _scope; }
    public void setBindingsMap(Map<String, Object> _bindingsMap) { bindingsMap = _bindingsMap; }

    public JavascriptingUtil()
	{
		setScriptResourceSet(new HashSet<String>());
		setBindingsMap(new HashMap<String, Object>());
    }

    public void bind(String _name, Object _value)
    {
		getBindingsMap().put(_name, _value);
	}

	public void loadScript(String _scriptResource)
	{
		InputStream inputStream = null;
		
		try
		{
			if (Context.getCurrentContext() == null)
			{
				throw new RuntimeException("Cannot use loadScript outside of the scope of a call to executeScript.  A context must be present.");
			}

			inputStream = findScript(_scriptResource);
		
			if (getScriptResourceSet().contains(_scriptResource) == false)
			{
				Reader reader = new InputStreamReader(inputStream);

				try
				{
					Context.getCurrentContext().evaluateReader(getScope(), reader, _scriptResource, 1, null);
				}
				catch(Throwable t)
				{
					t.printStackTrace();
					throw new RuntimeException("Unable to execute script: " + _scriptResource + " for this reason: " + t.toString(), t);
				}

				getScriptResourceSet().add(_scriptResource);
			}
		}
		catch(Throwable t)
		{
			t.printStackTrace();
			log.error(t.toString());
			throw new RuntimeException(t);
		}
		finally
		{
			if (inputStream != null) { try { inputStream.close(); } catch (Throwable t) {} }
		}
	}

	private InputStream findScript(String _scriptResource)
	{
		String rapidDeploy = "no";
		InputStream inputStream = null;

		try
		{
			if (log.isDebugEnabled() == true)
			{
				log.debug("Attempting to load script: " + _scriptResource);
			}

			java.io.File file = new java.io.File(_scriptResource);

			if (file.exists() == true)
			{
				inputStream = new java.io.FileInputStream(file);
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}

		if (inputStream == null)
		{
			throw new RuntimeException("Unable to find script file. Make sure that this: " + _scriptResource + " exists.");
		}

		return inputStream;
	}

	private Object executeScript(String _scriptResource, Reader _reader)
	{
		Context context = (new ContextFactory()).enterContext();
		context.setLanguageVersion(170);
		
		Object result = null;

		try
		{
			setScope(new org.mozilla.javascript.ImporterTopLevel(context, false));

			for (String key: getBindingsMap().keySet())
			{
				Object object = Context.javaToJS(getBindingsMap().get(key), getScope());
				ScriptableObject.putProperty(getScope(), key, object);
			}

			result = context.evaluateReader(getScope(), _reader, _scriptResource, 1, null);
		}
		catch(Throwable t)
		{
			throw new RuntimeException("Unable to execute script: " + _scriptResource + " for this reason: " + t.toString(), t);
		}
		finally
		{
			context.exit();
		}

		return result;
	}

	private Object executeScript(String[] _scriptResources, Reader[] _readers)
	{
		Context context = (new ContextFactory()).enterContext();
		context.setLanguageVersion(170);
		
		Object result = null;
		int i = 0;
		
		try
		{
			setScope(new org.mozilla.javascript.ImporterTopLevel(context, false));

			for (String key: getBindingsMap().keySet())
			{
				Object object = Context.javaToJS(getBindingsMap().get(key), getScope());
				ScriptableObject.putProperty(getScope(), key, object);
			}
			
			for (i = 0; i < _scriptResources.length; i++)
			{
				String scriptResource = _scriptResources[i];
				Reader reader = _readers[i];
				
				result = context.evaluateReader(getScope(), reader, scriptResource, 1, null);
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException("Unable to execute script: " + _scriptResources[i] + " for this reason: " + t.toString(), t);
		}
		finally
		{
			context.exit();
		}

		return result;
	}

	public Object executeScriptResource(String[] _scriptResources)
	{
		if (_scriptResources == null)
		{
			throw new RuntimeException("Cannot execute a null script");
		}

		Reader[] readers = new Reader[_scriptResources.length];
		int i = 0;
		
		for (String scriptResource : _scriptResources)
		{
			InputStream inputStream = findScript(scriptResource);

			readers[i] = new InputStreamReader(inputStream);
			i++;
		}

		return executeScript(_scriptResources, readers);
	}
	
    public Object executeScriptResource(String _scriptResource)
	{
		if (_scriptResource == null)
		{
			throw new RuntimeException("Cannot execute a null script");
		}

		InputStream inputStream = findScript(_scriptResource);

		Reader reader = new InputStreamReader(inputStream);

		return executeScript(_scriptResource, reader);
    }

    public Object execute(String _script)
    {
		if (_script == null)
		{
			throw new RuntimeException("Unable to execute null script");
		}

		Object result = null;
		Reader reader = new StringReader(_script);

		return executeScript("<dynamic source>", reader);
    }
}