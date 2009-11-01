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

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucidtechnics.blackboard.util.PropertyUtil;

public class Jsr223ScriptingUtil
   implements ScriptingUtil
{
	private static Log log = LogFactory.getLog(Jsr223ScriptingUtil.class);

	private ScriptEngine scriptEngine;
	private Set<String> scriptResourceSet;
	private Map<String, Object> bindingsMap;

	private ScriptEngine getScriptEngine() { return scriptEngine; } 
	private Set<String> getScriptResourceSet() { return scriptResourceSet; }
	private Map<String, Object> getBindingsMap() { return bindingsMap; }

	private void setScriptEngine(ScriptEngine _scriptEngine) { scriptEngine = _scriptEngine; }
	private void setScriptResourceSet(Set<String> _scriptResourceSet) {  scriptResourceSet = _scriptResourceSet; }
    public void setBindingsMap(Map<String, Object> _bindingsMap) { bindingsMap = _bindingsMap; }

    public Jsr223ScriptingUtil(String _engineName)
	{
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine scriptEngine = scriptEngineManager.getEngineByExtension(_engineName);

		if (scriptEngine == null) { throw new RuntimeException("Unable to create script engine for: " + _engineName + ".  Is the right jar in the classpath?"); }

		setScriptEngine(scriptEngine);
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
			inputStream = findScript(_scriptResource);
		
			if (getScriptResourceSet().contains(_scriptResource) == false)
			{
				Reader reader = new InputStreamReader(inputStream);

				try
				{
					getScriptEngine().eval(reader, getScriptEngine().getContext());
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

	public Object executeScript(String _scriptResource)
	{
		ScriptContext scriptContext = getScriptEngine().getContext();
		
		Object result = null;

		try
		{
			for (String key: getBindingsMap().keySet())
			{
				scriptContext.setAttribute(key, getBindingsMap().get(key), ScriptContext.ENGINE_SCOPE);
			}

			result = getScriptEngine().eval(new InputStreamReader(findScript(_scriptResource)), scriptContext);
		}
		catch(Throwable t)
		{
			throw new RuntimeException("Unable to execute script: " + _scriptResource + " for this reason: " + t.toString(), t);
		}

		return result;
	}

	public Object executeScript(String[] _scriptResources)
	{
		ScriptContext scriptContext = getScriptEngine().getContext();

		Object result = null;

		int i = 0;
		
		try
		{
			for (String key: getBindingsMap().keySet())
			{
				scriptContext.setAttribute(key, getBindingsMap().get(key), ScriptContext.ENGINE_SCOPE);
			}
			
			for (i = 0; i < _scriptResources.length; i++)
			{
				result = getScriptEngine().eval(new InputStreamReader(findScript(_scriptResources[i])), scriptContext);
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException("Unable to execute script: " + _scriptResources[i] + " for this reason: " + t.toString(), t);
		}

		return result;
	}

    public Object execute(String _script)
    {
		Object result = null;

		if (_script == null)
		{
			throw new RuntimeException("Unable to execute null script");
		}

		ScriptContext scriptContext = getScriptEngine().getContext();

		try
		{
			result = getScriptEngine().eval(_script, scriptContext);
		}
		catch (Throwable t)
		{
			throw new RuntimeException("Unable to execute script: " + _script + " for this reason: " + t.toString(), t);
		}

		return result;
    }
}