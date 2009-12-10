Simply unzip this zip file and go to the root directory.

To execute the blackboard CocoTheMonkey demo, copy the driver file CocoTheMonkey.js from blackboard/apps/examples/CocoTheMonkey
 to the same directory as the Bootstrap.jar file.  Simply type "java -jar Bootstrap.jar" 
at the command line.  This will execute any ruby or javascript driver files in the blackboard 
directory, and so CocoTheMonkey will begin to run.

Driver files generate blackboard events and place them on the blackboard. The "CocoTheMonkey.js" 
JavaScript file is an example of a driver file.  The blackboard will process each one of the 
events using the plans found in the blackboard/workspaces directory.

If you want to run your own plans, simply define your own events, and place your plans 
in the directories (create these directories yourself) named for your events under 
blackboard/workspaces.  

See the wiki for more information.

Regards,

Bediako George
Lucid Technics