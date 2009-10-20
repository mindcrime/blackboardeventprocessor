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

var eagle = WORKSPACE.get("example.CocoTheMonkey.eagle");

if ((WORKSPACE.has("example.CocoTheMonkey.hunter") === true) &&
	(eagle !== undefined && eagle !== null) &&
	  (eagle.perched === true))
{	
	eagle.shot = true;
	eagle.perched = false;
	WORKSPACE.remove("example.CocoTheMonkey.eagle");

	PLAN_CONTEXT.isFinished = (WORKSPACE.hasTargetAction("example.CocoTheMonkey.eagle", TARGET_REMOVED) === true);
}