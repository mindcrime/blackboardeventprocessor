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

package com.lucidtechnics.blackboard.examples.crawler;

@com.lucidtechnics.blackboard.Event(appName="example", workspaceName="Crawler", name="page", workspaceIdentifier="uri")

public class Page
{
	private String uri;
	private String parentUri;
	private int depth;
	private boolean processed;
	
	public String getUri() { return uri; }
	public String getParentUri() { return parentUri; }
	public int getDepth() { return depth; }
	public boolean getProcessed() { return processed; }

	public void setUri(String _uri) { uri = _uri; }
	public void setParentUri(String _parentUri) { parentUri = _parentUri; }
	public void setDepth(int _depth) { depth = _depth; }
	public void setProcessed(boolean _processed) { processed = _processed; }

    public Page() {}
    
    public Page(String _uri, String _parentUri, int _depth)
    {
		setUri(_uri);
		setDepth(_depth);
		setParentUri(_parentUri);
		setProcessed(false);
    }
}	