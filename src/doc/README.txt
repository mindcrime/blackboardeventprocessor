. "/home/bediako/app/epsilon/epsilon13.06/epsilon.sh"; set +o emacs
bediako@Loki:~/work/dayzero-web$ bediako@Loki:~/work/dayzero-web$ cd ..
bediako@Loki:~/work$ cd blackboard
bediako@Loki:~/work/blackboard$ ant project
Buildfile: build.xml

clean-classes:
   [delete] Deleting directory /home/bediako/work/blackboard/classes
   [delete] Deleting directory /home/bediako/work/blackboard/dist

clean-target:
   [delete] Deleting directory /home/bediako/work/blackboard/deploy/bin
   [delete] Deleting directory /home/bediako/work/blackboard/deploy/lib
   [delete] Deleting directory /home/bediako/work/blackboard/deploy/config
   [delete] Deleting directory /home/bediako/work/blackboard/deploy/log
   [delete] Deleting directory /home/bediako/work/blackboard/workspace/blackboard
   [delete] Deleting: /home/bediako/work/blackboard/workspace/CocoTheMonkey.sh
   [delete] Deleting directory /home/bediako/work/blackboard/stage

clean:

init:
    [mkdir] Created dir: /home/bediako/work/blackboard/stage
    [mkdir] Created dir: /home/bediako/work/blackboard/stage/shl
    [mkdir] Created dir: /home/bediako/work/blackboard/stage/cfg/blackboard
    [mkdir] Created dir: /home/bediako/work/blackboard/classes
    [mkdir] Created dir: /home/bediako/work/blackboard/dist
    [mkdir] Created dir: /home/bediako/work/blackboard/dist/lib
    [mkdir] Created dir: /home/bediako/work/blackboard/deploy/config/blackboard
    [mkdir] Created dir: /home/bediako/work/blackboard/deploy/lib
    [mkdir] Created dir: /home/bediako/work/blackboard/deploy/bin
    [mkdir] Created dir: /home/bediako/work/blackboard/deploy/log

prepareSource:
     [copy] Copying 6 files to /home/bediako/work/blackboard/stage

compile:
     [echo] /home/bediako/work/blackboard/lib/asm-2.2.2/asm-all-2.2.2.jar:/home/bediako/work/blackboard/lib/beanshell/bsh-2.0b4.jar:/home/bediako/work/blackboard/lib/build-only/junit3.8.1/junit.jar:/home/bediako/work/blackboard/lib/cglib-2.2._beta1/cglib-2.2_beta1.jar:/home/bediako/work/blackboard/lib/commons-beanutil/commons-beanutils-core.jar:/home/bediako/work/blackboard/lib/commons-beanutil/commons-beanutils.jar:/home/bediako/work/blackboard/lib/commons-lang/commons-lang-2.4.jar:/home/bediako/work/blackboard/lib/commons-logging/commons-logging.jar:/home/bediako/work/blackboard/lib/db4o/bloat-1.0.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-bench.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-db4ounit.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-instrumentation.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-java1.1.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-java1.2.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-java5.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-nqopt.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-osgi-test.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-osgi.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-taj.jar:/home/bediako/work/blackboard/lib/db4o/db4o-7.4.88.12908-tools.jar:/home/bediako/work/blackboard/lib/jaf-1.0.2/activation.jar:/home/bediako/work/blackboard/lib/jruby/jruby-engine.jar:/home/bediako/work/blackboard/lib/jruby/jruby.jar:/home/bediako/work/blackboard/lib/log4j/log4j-1.2.9.jar:/home/bediako/work/blackboard/lib/rhino/js.jar
    [javac] Compiling 49 source files to /home/bediako/work/blackboard/classes
    [javac] Note: /home/bediako/work/blackboard/src/java/com/lucidtechnics/blackboard/Blackboard.java uses or overrides a deprecated API.
    [javac] Note: Recompile with -Xlint:deprecation for details.
    [javac] Note: Some input files use unchecked or unsafe operations.
    [javac] Note: Recompile with -Xlint:unchecked for details.

jar:
      [jar] Building jar: /home/bediako/work/blackboard/dist/lib/blackboard-alpha-0.10.jar
      [jar] Updating jar: /home/bediako/work/blackboard/dist/lib/blackboard-alpha-0.10.jar
      [jar] Updating jar: /home/bediako/work/blackboard/dist/lib/blackboard-alpha-0.10.jar
      [jar] Building jar: /home/bediako/work/blackboard/dist/lib/blackboardTest-alpha-0.10.jar

deploy:
     [copy] Copying 24 files to /home/bediako/work/blackboard/deploy/lib
     [copy] Copying 3 files to /home/bediako/work/blackboard/deploy/bin
     [copy] Copying 2 files to /home/bediako/work/blackboard/deploy/lib
     [copy] Copying 3 files to /home/bediako/work/blackboard/deploy/cfg
     [copy] Copying 4 files to /home/bediako/work/blackboard/workspace/blackboard/workspaces
     [copy] Copied 5 empty directories to 3 empty directories under /home/bediako/work/blackboard/workspace/blackboard/workspaces
     [copy] Copying 1 file to /home/bediako/work/blackboard/workspace

project:
     [copy] Copying 24 files to /home/bediako/work/blackboard/dist/blackboard/lib
     [copy] Copying 1 file to /home/bediako/work/blackboard/dist/blackboard
      [zip] Building zip: /home/bediako/work/blackboard/dist/blackboard-standalone.zip

BUILD SUCCESSFUL
Total time: 6 seconds
bediako@Loki:~/work/blackboard$ ls 
bin        build.xml  deploy         dist  result  stage             workspace
build.bat  classes    desktop.build  lib   src     token.properties
bediako@Loki:~/work/blackboard$ cd src
bediako@Loki:~/work/blackboard/src$ ls
apps  cfg  doc  gen-java  java  jsp  shl  sql  static  test  workspaces
bediako@Loki:~/work/blackboard/src$ cd doc
bediako@Loki:~/work/blackboard/src/doc$ ls
developer  instructions.txt  javadoc  licenseSnippet.txt  user
bediako@Loki:~/work/blackboard/src/doc$ cat instructions.txt 
Hello,

Here is the link to the software project I was granted on sourceforge:
http://sourceforge.net/projects/blkbrd/

You can view the source code here:
http://blkbrd.svn.sourceforge.net/viewvc/blkbrd/branches/release0.1/

If you would like to download and run the Blackboard Workspace Server, 
please make sure you have a subversion client on you desktop, and at the
command line execute the following:

svn co https://blkbrd.svn.sourceforge.net/svnroot/blkbrd/branches/release0.1 blackboard

Once you have checked out the source, go to the blackboard directory that was just 
created, and execute "ant redeploy".  You may also import your project into Eclipse
and run it from there as well.

After deployment is completed, the bat or shell scripts for running the
"Coco The Monkey" example will be created in ../deploy/bin.  For Windows 
users execute cocothemonkey.bat.

Once the application is completed its run you will want to view the results. 
The results are stored in a DB4O object oriented database. You can view the 
results using their ObjectManager application.  You can get it from 
http://developer.db4o.com/files/folders/objectmanager_18/entry24827.aspx.
There may be a registration requirement, but the software is free.

Finally, once the ObjectManager is installed, you can use it to open the file
"targetSpaces.dat" in the ../deploy/bin directory. Explore the object tree to
see the data that is stored.  Feel free to play with the configurations in blackboard.xml
and cocothemonkey.xml.

Performance
Early results are promising. Right now on my laptop I am processing and persisting events AND
changes to event information at a rate of about 166 events per second.  This is without
any tuning whatsover.

I would be interested to hear your comments. Please drop me a line at
bediakogeorge@yahoo.com.  If you run into any problems please drop me a line.

Regards,

Bediakobediako@Loki:~/work/blackboard/src/doc$ 