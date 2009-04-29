package com.lucidtechnics.blackboard;

import org.springframework.beans.factory.xml.XmlBeanFactory;

import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;

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

public class SpringBlackboardFactory
   implements BlackboardFactory
{
	private XmlBeanFactory xmlBeanFactory;

	private XmlBeanFactory getXmlBeanFactory() { return xmlBeanFactory; }
	public void setXmlBeanFactory(XmlBeanFactory _xmlBeanFactory) { xmlBeanFactory = _xmlBeanFactory; }
	
	public SpringBlackboardFactory() {}
	
	public void init(XmlBeanFactory _xmlbeanFactory)
	{
	    setXmlBeanFactory(_xmlbeanFactory);
	}

	public Plan createPlan(String _planName)
	{
		return (Plan) getXmlBeanFactory().getBean(_planName);
	}

	public TargetSpace createTargetSpace(WorkspaceConfiguration _workspaceConfiguration, Object _workspaceIdentifier)
	{
		TargetSpace targetSpace = null;
		
		String workspaceName = _workspaceConfiguration.getName();

		if (_workspaceConfiguration.getIsTransactional() == false)
		{
			targetSpace = new TargetSpaceImpl(workspaceName, _workspaceIdentifier);
		}
		else
		{
			throw new RuntimeException("Transactional workspaces not supported at this time"); 
			//TODO create a transactional supporting workspace
		}

		return targetSpace;
	}			
}