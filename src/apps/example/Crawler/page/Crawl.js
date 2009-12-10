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

if ((WORKSPACE.has("example.Crawler.page") === true)
	  && (WORKSPACE.get("example.Crawler.page").processed === false))
{
	var page = WORKSPACE.get("example.Crawler.page");

	page.setProcessed(true);

	if (page.getDepth() > 0)
	{		
		var browser = new Packages.hannibal.util.Browser();
		var content = browser.get(page.getUri());

		var tidy = new Packages.org.w3c.tidy.Tidy();
		tidy.setXmlOut(true);
		var inputStream = new Packages.java.io.ByteArrayInputStream(content.getBytes());
		var dom = tidy.parseDOM(inputStream, null);

		var anchorList = dom.getElementsByTagName("a");

		for (var i = 0; i < anchorList.getLength(); i++)
		{
			var anchor = anchorList.item(i);
			var href = anchor.getAttributes().getNamedItem("href");

			if (href)
			{
				var hrefValue = href.getNodeValue();

				if (hrefValue.startsWith("http:") === true)
				{
					var newUri = new Packages.java.net.URL(hrefValue);

					Packages.java.lang.System.out.println("New uri is: " + newUri);

					var path = newUri.getPath().replaceAll("\\/", "XXXXXXXXXXXXXXXXXXXXXXX");
					var path = browser.encode(path, "UTF-8");
					path = path.replaceAll("XXXXXXXXXXXXXXXXXXXXXXX", "\\/");

					newUri = "http://" + newUri.getHost().replaceAll("\\/$", "") + path;

					Packages.java.lang.System.out.println("And now new uri is: " + newUri);

					var newDepth = page.getDepth() - 1;
					var newPage = new Packages.com.lucidtechnics.blackboard.examples.crawler.Page(newUri, page.getUri(), newDepth);

					WORKSPACE.placeOnBlackboard(newPage);
				}
			}
		}
	}

	PLAN_CONTEXT.isFinished = true;
}