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

package com.lucidtechnics.blackboard.util.error;

import com.lucidtechnics.blackboard.BlackboardException;
import org.apache.commons.logging.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class ErrorManager
{
	private static ErrorManager errorManager;

	private ErrorManager() {}

	public synchronized static ErrorManager getInstance()
	{
		if (errorManager == null)
		{
			errorManager = new ErrorManager();
		}

		return errorManager;
	}
			
	public void logException(Throwable _throwable, Log _logger)
	{
		PrintWriter printWriter = null;
		StringWriter stringWriter = null;
		
		try
		{
			_logger.error("Encountered exception with message: " + _throwable.getMessage());
			_logger.error("WITH JAVA STACK TRACE:\n");

			stringWriter = new StringWriter(); 
			printWriter = new PrintWriter(stringWriter);

			_throwable.printStackTrace(printWriter);

			_logger.error(stringWriter.toString());
		}
		catch(Exception e)
		{

		}
		finally
		{
			if (stringWriter != null) { try { stringWriter.close(); } catch(Exception e) {} }
			if (printWriter != null) { try { printWriter.close(); } catch(Exception e) {} }
		}
	}

	public void throwException(Throwable _t, Log _logger)
	{
		logException(_t, _logger);

		if (_logger.isDebugEnabled() == true)
		{
			_logger.debug("ErrorManager Found this exception: " + _t.getClass());
		}
		
		if ((_t instanceof BlackboardException) == true)
		{
			throw (BlackboardException) _t;
		}
		else
		{
			throw new BlackboardException(_t);
		}
	}

	public void throwExceptionNoLogging(Throwable _t)
	{
		if ((_t instanceof BlackboardException) == true)
		{
			throw (BlackboardException) _t;
		}
		else
		{
			throw new BlackboardException(_t);
		}
	}

	public void throwUnwrappedRuntimeException(RuntimeException _r, Log _logger)
	{
		logException(_r, _logger);
		throw _r;
	}

	public void warnException(Throwable _throwable, Log _logger)
	{
		PrintWriter printWriter = null;
		StringWriter stringWriter = null;

		try
		{
			_logger.warn("Encountered exception with message: " + _throwable.getMessage());
			_logger.warn("WITH JAVA STACK TRACE:\n");

			stringWriter = new StringWriter(); 
			printWriter = new PrintWriter(stringWriter);

			_throwable.printStackTrace(printWriter);

			_logger.error(stringWriter.toString());
		}
		catch(Exception e)
		{

		}
		finally
		{
			if (stringWriter != null) { try { stringWriter.close(); } catch(Exception e) {} }
			if (printWriter != null) { try { printWriter.close(); } catch(Exception e) {} }
		}
	}

	public void warn(String _message, Log _logger)
	{
		_logger.warn(_message);
	}
}