# Introduction #

> The Blackboard Event Processor, or Blackboard for short, is a Java based implementation of the blackboard design paradigm. The blackboard design paradigm facilitates the organization of chunks of information for processing by loosely coupled software processes. These chunks of information are stored in the blackboard's central data repository. This central data repository is accessible by all participating software processes.

Software processes can add, update, and remove chunks of information in the repository. The blackboard design paradigm provides for data change notification services to the participating software processes, and provides for the scheduling of those software processes that wish to effect changes on the chunks of information in the repository. The diagram below illustrates the relationship among the data, software processes, and the blackboard in a hypothetical patient management application.

![http://blackboardeventprocessor.googlecode.com/svn/trunk/src/doc/user/Blackboard_Model.jpg](http://blackboardeventprocessor.googlecode.com/svn/trunk/src/doc/user/Blackboard_Model.jpg)

The advantage of this design pattern is that it encourages the development of a loosely coupled programming structure. In addition, the blackboard design pattern also promotes contractual software process design. That is to say, software processes have the choice to subscribe to only the data changes they find interesting, or they may choose to proclaim disinterest in the current state of the blackboard, at which point their execution ends.

Consider the example patient application. Some of the activities supported by this application include “creating a new patient record”, “updating a patients diagnosis”, and “updating a patient's health status”. These activities will result in changes to the patient's data that is stored in the blackboard's data repository.

Other processes like “notify nurse” for instance, can subscribe to the patient's data changes committed by the other software processes. As an example, if the prescription of the patient changes, both the “notify patient” and the “notify nurse” processes would be interested in that, whereas if the patient slips into a coma, processes “notify nurse” and “notify next of kin” would be interested instead.

Using the blackboard model, programmers developing software focus on creating the following logic for each of the software processes they develop:

  * Logic that dictates the conditions for which the software process is interested in executing.
  * Logic that dictates the software process activity.
  * Logic that dictates the conditions for which the software process is no longer interested in executing.

Blackboard implements many of the ideas from the blackboard programming model. In Blackboard, chunks of information are called “targets”, and the software processes interested in these targets are called “plans”. There are also special types of targets called “events”. Events differ from ordinary targets in that they are typically created outside the blackboard environment. Finally, targets, plans, and events are all organized on the blackboard in “workspaces”.

A workspace is a logical division of the blackboard. The relationship amongst an event, its workspace, its associated plans, and the blackboard is similar to the relationship between a JMS message, a J2EE container, its associated message driven beans, and a J2EE application server, in the sense that many different types of workspaces can run on a single blackboard, and that the arrival of an event starts the execution plans on that workspace. The difference lies in the fact that workspaces are specifically geared to handle event based processing.

## High Level Blackboard Workspace Server Design ##
The following diagram illustrates the logical relationship of targets, events, plans, and the blackboard.

![http://blackboardeventprocessor.googlecode.com/svn/trunk/src/doc/user/Blackboard_High_Level.jpg](http://blackboardeventprocessor.googlecode.com/svn/trunk/src/doc/user/Blackboard_High_Level.jpg)

In this diagram the large white oval represents the single blackboard. This blackboard supports three types of workspaces. Each type of workspace can support multiple instances, but for the purpose of this illustration only one instance of each workspace is shown. Targets are represented by circles, events by cylinders, and plans by hexagons. Targets, events, and plans are all named, and can be addressed by their names on the workspace2. For instance, in the “Music Order Workspace”, there are named targets, “T0” and “T1”, and named events “E0” and “E1”, and named plans “P0” and “P1”.

Whenever a target is retrieved by its name, the last instance of the target that was added to the workspace is retrieved, and the previous instance if lost to the workspace3. This differs from event retrieval where all versions of the named event added to the workspace are stored. This is why events are represented as a cylinder the diagram above.

### Blackboard ###
Blackboard consists of a blackboard, workspaces, and plans. The blackboard is
responsible for managing the resources used by the server, and it serves as the gateway through which all events must pass through in order to arrive in workspace. In the general blackboard design paradigm, events are placed directly on the blackboard, however, Blackboard's implementation of the blackboard promotes the separation of the blackboard into smaller parts called workspaces. This allows for performance improvements, and it lends well to programmer organization of the software processes by attaching plans to workspaces of a particular name.

After instantiating a blackboard, placing an event on the blackboard is as easy as invoking the placeOnBlackboard(Object object) method. The blackboard consults workspace configuration information to figure out what workspaces should be created for the event type of the object placed. So conceivably, one could define workspaces to respond to the any of the event types an object may have. For instance, if Customer object has a both a "customer" and a "person" event type, one could create workspaces in response to the Customer type and the Person type. This means the blackboard will create two workspaces every time it receives a Customer object.  Objects are assigned different event types via a Blackboard event annotation.

Additionally, the blackboard is responsible for persisting workspaces whenever they are retired, or whenever resource constraints called for temporary removal from memory.

### Events ###
In general, an event can be thought of as meta data describing an unique occurrence of an action of some kind. With Blackboard, events are represented as Java objects that can be placed on the blackboard at anytime. It is the action of placing an event on a blackboard that causes the creation of a new workspace. Every event is expected to have an attribute that will be used to identify a particular workspace instance. As an example, in the “Music Order Processing” workspace, if there exists a “New Order Submitted” event, the uniquely identifying attribute may be the “orderId”.

Whenever an event of the type “New Order Submitted” is placed on the blackboard, the blackboard first checks to see if an instance of the “Music Order Processing” workspace with a workspace identifier with the same value of the “orderId” attribute exists. If such a workspace instance exists, the event is placed in the workspace. If not, a new workspace is created, its workspace identifier attribute is set to the same value as the event's “orderid” attribute, and the event is placed there. Consequently, all events arriving after the first event arrived, will be ferried to that workspace, so long as the value of that order's orderId attribute is the same as the previous event. All interested plans attached to that workspace are automatically notified of the specific event arrivals. In the diagram below note that even though the “Payment Received” event arrives a full minute after the “New Music Order” Event it still gets routed to the correct workspace.

![http://blackboardeventprocessor.googlecode.com/svn/trunk/src/doc/user/Place_Event_On_Blackboard.jpg](http://blackboardeventprocessor.googlecode.com/svn/trunk/src/doc/user/Place_Event_On_Blackboard.jpg)

It important to note that there is no requirement for the “New Music Order” event to be the first to arrive. Indeed, it is perfectly acceptable for the “Payment Received” event to arrive first. Blackboard will still create the appropriate workspace, and the appropriate plans will be executed as they express interest.

This is a very powerful concept, and it forms the basis for much of the functionality of the Blackboard application. It also has implications regarding the performance and design of the software infrastructure that is creating the events in the first place. This flexibility in the arrival of events makes it easier to develop complicated applications.

Consider a stock market order processing application. The unique workspace identifier can be a stock's ticker symbol, and buy and sell orders can arrive in any order and at any time. Using Blackboard, this means that all buy and sell orders for a particular ticker symbol can be automatically ferried to the same workspace instance. Within that instance, plans can operate on the buy and sell orders, performing order validation, execution, and fulfillment, without having to bother with grouping orders together. It also means that order enhancements, corrections, and cancellations will automatically get pushed to the right workspace as well, as they are all organized by the stock's ticker symbol. And since all instances of a particular event are kept on the workspace, version
information for all of the events is provided for free.

### Workspaces ###
The workspace is the central organizing entity on the blackboard. It forms the basis for grouping events, and targets, and it defines the types of plans that are allowed to act upon that grouping.

Workspaces are also responsible for notifying plans of changes to events and targets on the name and attribute level.5 They check on plans for interest of execution, and schedule plans for execution on the blackboard. Finally, they are responsible for determining when a workspace can be retired, with the persistence of results produced by the plans a natural consequence.

Instances of workspaces are created whenever an event with a workspace identifier is placed on the blackboard. These workspaces are managed on the blackboard. The blackboard will automatically. A workspace keeps track of changes to adds, updates, and removals of targets and events at the name level. It will also keep track of changes on those targets and events attributes as well. manage the number of active workspaces that can be held in memory at any time, and will temporarily persist workspaces to a workspace repository whenever that maximum workspace limit is exceeded.

Workspaces can have several states. They are described below.

  1. Active: Workspaces in this state have plans that are still interested in execution.
  1. Completed: Workspaces in this state no longer have plans interested in execution.
  1. Terminated: Workspaces in this state have reached an unrecoverable error condition.
  1. Executing: Workspaces in this condition have plans that are currently executing on the blackboard.
  1. Persisted: Workspaces in this state are no longer in memory, but are persisted in the workspace repository.

### Plans ###
Plans are dynamic language scripts that are expected to execute its operating logic when provided with an interesting picture of the workspace. After execution, the plan must simplt indicate whether or not it wants to run again.  It does so by setting a "I am finished flag" to true sometime during its execution.

The workspace is not allowed to change once the plan begins execution. As such any new event arrivals will be blocked from entering the workspace until the plan finishes its scheduled execution run.

During an execution run, plans may alter the state of the workspace. They may add, remove, or update targets and events, and change target and events attributes. All such activities by the plan are recorded by the workspace, and stored in a change history object.

### Targets ###
Targets are Java beans that are placed on a workspace by Plans. They are mainly used to store new state generated by an executing plan, or to relabel Java beans as necessary. Consequently, two different targets may point to the same underlying Java bean. For instance, a newly required event, called “New Music Order” may be operated on by the plan “Validate New Music Order”. Upon completion of the validation of the “New Music Order”, that plan may choose to add to the workspace a target called “Validated Music Order”. The underlying object may still be the original “New Music Order” event, but with the new label “Validated Music Order” the plan “Fulfill Music Order” may suddenly express
interest. Using this technique, a workspace's work flow may be dictated by active plan's expression of interest to execute. This represents the basis of the loose coupling, and it allows otherwise complicated programming logic to be simplified.