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

package com.lucidtechnics.blackboard;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.HashSet;
import java.util.Date;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.LinkedBlockingQueue;

import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;
import com.lucidtechnics.blackboard.config.EventConfiguration;

import com.lucidtechnics.blackboard.util.error.ErrorManager;
import com.lucidtechnics.blackboard.util.PropertyUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Blackboard
{
	private static Log logger = LogFactory.getLog(Blackboard.class);

	private ErrorManager errorManager;
	private BlackboardActor blackboardActor;
	private int maxBlackboardThread = 1;
	private int maxScheduledBlackboardThread = 1;
	private int maxWorkspaceThread;
	private int maxPersistenceThread;
	private int maxWorkspace;
	private int activeWorkspaceCount;
	private ThreadPoolExecutor blackboardExecutor;
	private ScheduledThreadPoolExecutor scheduledBlackboardExecutor;
	private ThreadPoolExecutor workspaceExecutor;
	private ThreadPoolExecutor persistenceExecutor;
	private Map<String, WorkspaceConfiguration> eventToWorkspaceMap;
	private Persister persister;
	private boolean timePlans;
	private String appsHome;
	private Map<Integer, ThreadPoolExecutor> workspaceExecutorMap;
	private int currentWorkspaceExecutor = 0;

	private ErrorManager getErrorManager() { return errorManager; }
	private BlackboardActor getBlackboardActor() { return blackboardActor; }
	private int getMaxBlackboardThread() { return maxBlackboardThread; }
	private int getMaxScheduledBlackboardThread() { return maxScheduledBlackboardThread; }
	private int getMaxWorkspaceThread() { return maxWorkspaceThread; }
	private int getMaxPersistenceThread() { return maxPersistenceThread; }
	private int getMaxWorkspace() { return maxWorkspace; }
	private int getActiveWorkspaceCount() { return activeWorkspaceCount; }
	private ThreadPoolExecutor getBlackboardExecutor() { return blackboardExecutor; }
	private ScheduledThreadPoolExecutor getScheduledBlackboardExecutor() { return scheduledBlackboardExecutor; }
	private ThreadPoolExecutor getPersistenceExecutor() { return persistenceExecutor; }
	private Map<String, WorkspaceConfiguration> getEventToWorkspaceMap() { return eventToWorkspaceMap; }
	private Persister getPersister() { return persister; }
	private boolean getTimePlans() { return timePlans; }
	private String getAppsHome() { return appsHome; }
	private Map<Integer, ThreadPoolExecutor> getWorkspaceExecutorMap() { return workspaceExecutorMap; }
	
	private void setErrorManager(ErrorManager _errorManager) { errorManager = _errorManager; }
	private void setBlackboardActor(BlackboardActor _blackboardActor) { blackboardActor = _blackboardActor; }
	private void setMaxBlackboardThread(int _maxBlackboardThread) { maxBlackboardThread = _maxBlackboardThread; }
	private void setMaxScheduledBlackboardThread(int _maxScheduledBlackboardThread) { maxScheduledBlackboardThread = _maxScheduledBlackboardThread; }
	private void setMaxWorkspaceThread(int _maxWorkspaceThread) { maxWorkspaceThread = _maxWorkspaceThread; }
	private void setMaxPersistenceThread(int _maxPersistenceThread) { maxPersistenceThread = _maxPersistenceThread; }
	private void setMaxWorkspace(int _maxWorkspace) { maxWorkspace = _maxWorkspace; }
	private void setActiveWorkspaceCount(int _activeWorkspaceCount) { activeWorkspaceCount = _activeWorkspaceCount; }
	private void setBlackboardExecutor(ThreadPoolExecutor _blackboardExecutor) { blackboardExecutor = _blackboardExecutor; }
	private void setScheduledBlackboardExecutor(ScheduledThreadPoolExecutor _scheduledBlackboardExecutor) { scheduledBlackboardExecutor = _scheduledBlackboardExecutor; }
	private void setPersistenceExecutor(ThreadPoolExecutor _persistenceExecutor) { persistenceExecutor = _persistenceExecutor; }
	private void setEventToWorkspaceMap(Map<String, WorkspaceConfiguration> _eventToWorkspaceMap) { eventToWorkspaceMap = _eventToWorkspaceMap; }
	private void setPersister(Persister _persister) { persister = _persister; } 
	private void setTimePlans(boolean _timePlans) { timePlans = _timePlans; }
	private void setAppsHome(String _appsHome) { appsHome = _appsHome; }
	private void setWorkspaceExecutorMap(Map<Integer, ThreadPoolExecutor> _workspaceExecutorMap) { workspaceExecutorMap = _workspaceExecutorMap; }

	public Blackboard(BlackboardConfiguration _blackboardConfiguration)
	{
		configureBlackboard(_blackboardConfiguration);

		setBlackboardActor(new BlackboardActor("Blackboard"));
		setEventToWorkspaceMap(new HashMap());
		setWorkspaceExecutorMap(new HashMap<Integer, ThreadPoolExecutor>());
		
		init();
	}

	public Blackboard()
	{
		this(new BlackboardConfiguration());
	}

	private void configureBlackboard(BlackboardConfiguration _blackboardConfiguration)
	{		
		java.io.File blackboardConfigurationFile = new java.io.File("." + java.io.File.separator + "blackboard.configuration.js");

		if (blackboardConfigurationFile.exists() == true)
		{
			JavaScriptConfigurator configurator = new JavaScriptConfigurator();
			String configuratorPath = blackboardConfigurationFile.getAbsolutePath();
			configurator.execute(_blackboardConfiguration, configuratorPath);
		}
		
		this.setErrorManager(_blackboardConfiguration.getErrorManager());
		this.setMaxBlackboardThread(_blackboardConfiguration.getMaxBlackboardThread());
		this.setMaxScheduledBlackboardThread(_blackboardConfiguration.getMaxScheduledBlackboardThread());
		this.setMaxWorkspaceThread(_blackboardConfiguration.getMaxWorkspaceThread());
		this.setMaxPersistenceThread(_blackboardConfiguration.getMaxPersistenceThread());
		this.setMaxWorkspace(_blackboardConfiguration.getMaxWorkspace());
		this.setPersister(_blackboardConfiguration.getPersister());
		this.setTimePlans(_blackboardConfiguration.getTimePlans());
		this.setAppsHome(_blackboardConfiguration.getAppsHome());
	}
	
	private void init()
	{		
		java.io.File file = new java.io.File(getAppsHome());

		if (file.exists() == false)
		{
			file.mkdirs();
		}

		java.io.File appsDirectory = new java.io.File(getAppsHome());

		if (appsDirectory.isDirectory() != true)
		{
			throw new RuntimeException("Directory: " + getAppsHome() + " as set in blackboard.apps.home is not a directory");
		}

		java.io.File[] directoryFiles = appsDirectory.listFiles();

		for (int i = 0; i < directoryFiles.length; i++)
		{
			if (directoryFiles[i].isDirectory() == true)
			{
				String appName = directoryFiles[i].getName();

				if (logger.isInfoEnabled() == true)
				{
					logger.info("Configuring app: " + appName);
				}

				java.io.File[] workspaceDirectoryFiles = directoryFiles[i].listFiles();

				for (int j = 0; j < workspaceDirectoryFiles.length; j++)
				{
					if (workspaceDirectoryFiles[j].isDirectory() == true)
					{
						String workspaceName = workspaceDirectoryFiles[j].getName();

						if (logger.isInfoEnabled() == true)
						{
							logger.info("Processing workspace: " + workspaceName);
						}

						java.io.File[] eventDirectoryFiles = workspaceDirectoryFiles[j].listFiles();

						WorkspaceConfiguration workspaceConfiguration = configureWorkspace(appName, workspaceName, workspaceDirectoryFiles[j]);
						
						for (int k = 0; k < eventDirectoryFiles.length; k++)
						{
							if (eventDirectoryFiles[k].isDirectory() == true)
							{ 
								processEventPlans(appName, workspaceName, workspaceConfiguration, eventDirectoryFiles[k]);
							}
						}
					}
				}
			}
		}

		if (logger.isInfoEnabled() == true)
		{
			logger.info("Loaded event configurations: " + getEventToWorkspaceMap());
		}
		
		setBlackboardExecutor(new ThreadPoolExecutor(1, 1, 100, TimeUnit.SECONDS,
			new LinkedBlockingQueue()));

		setScheduledBlackboardExecutor(new ScheduledThreadPoolExecutor(getMaxScheduledBlackboardThread()));
		
		for (int i = 0; i <= getMaxWorkspaceThread(); i++)
		{
			getWorkspaceExecutorMap().put(i, new ThreadPoolExecutor(1, 1, 100, TimeUnit.SECONDS,
				new LinkedBlockingQueue()));
		}

		setPersistenceExecutor(new ThreadPoolExecutor(getMaxPersistenceThread(), getMaxPersistenceThread(), 100, TimeUnit.SECONDS,
			new LinkedBlockingQueue(	)));

		if (logger.isInfoEnabled() == true)
		{
			logger.info("Blackboard Workspace Server Initialization Inception.");
			logger.info("Apache 2.0 Open Source License.");
			logger.info("Copyright Owner - LucidTechnics, LLC.");
			logger.info("Authors - Bediako Ntodi George and David Yuctan Hodge.");
			logger.info("Initialization was successful.");
		}

		org.apache.jcs.JCS.setConfigFilename("/blackboard.ccf");
	}

	protected void executePlans(final TargetSpace _targetSpace, final java.util.Collection<Plan> _planList)
	{
		WorkspaceExecutionContext workspaceExecutionContext = get(_targetSpace.getWorkspaceIdentifier());
		ThreadPoolExecutor workspaceExecutor = getWorkspaceExecutorMap().get(workspaceExecutionContext.getWorkspaceExecutorName());
		
		workspaceExecutor.execute(new Runnable()
		{
			public void run()
			{				
				long startWorkspaceRun = 0l;
				long endWorkspaceRun = 0l;

				if (getTimePlans() == true)
				{
					startWorkspaceRun = System.currentTimeMillis();
				}

				WorkspaceContext workspaceContext = null;
				Plan plan = null;
				Map planToChangeInfoCountMap = new HashMap();
				boolean notifyPlans = false;
				Set activePlanSet = new HashSet();

				try
				{
					_targetSpace.setExecuting();

					for (Plan executingPlan: _planList)
					{
						plan = executingPlan;

						if (_targetSpace.isTerminated() == false &&
							  _targetSpace.isActive(plan) == true)
						{
							try
							{
								activePlanSet.add(plan);

								if (_targetSpace.isFinished(plan) == false)
								{
									if (logger.isDebugEnabled() == true)
									{
										logger.debug("For workspace: " +
											_targetSpace.getWorkspaceIdentifier() + " plan: " +
											plan.getName() + " is about to be executed.");
									}

									_targetSpace.setExecuting(plan);

									try
									{
										if (getTimePlans() == true)
										{
											endWorkspaceRun = System.currentTimeMillis();

											if (logger.isInfoEnabled() == true)
											{
												logger.info("Processing target space time: " + (endWorkspaceRun - startWorkspaceRun));
											}
										}

										workspaceContext = new WorkspaceContext(_targetSpace, plan);										

										long startTime = 0l;
										long endTime = 0l;
										long totalTime = 0l;

										if (getTimePlans() == true)
										{
											startTime = System.currentTimeMillis();
										}

										boolean exceptionThrown = false;
										//Execute the plan
										try
										{
											_targetSpace.setPlanState(plan, plan.execute(workspaceContext));
										}
										catch(Throwable t)
										{
											//If a plan self destructs
											//the whole target space is
											//considered compromised.
											//The target space is
											//retired and the target
											//clean up.  This is to
											//prevent inadvertant
											//memory leaks. Plan
											//operators should strive
											//to not have there plans
											//throw errant exceptions.
											exceptionThrown = true;

											if (_targetSpace.getTerminateOnError() == true)
											{
												logger.error(t.toString());
												_targetSpace.setTerminated();
											}
										}

										if (getTimePlans() == true)
										{
											endTime = System.currentTimeMillis();
											totalTime = endTime - startTime;

											if (logger.isInfoEnabled() == true)
											{
												logger.info("Plan: " + plan.getName() + " executed in: " + totalTime);
											}
										}

										if (_targetSpace.isFinished(plan) == true)
										{
											if (logger.isDebugEnabled() == true)
											{
												logger.debug("For workspace: " +
													_targetSpace.getWorkspaceIdentifier() + " plan: " +
													plan.getName() + " ran and is now finished.");
											}
										}

										_targetSpace.setLastActiveTime(System.currentTimeMillis());
									}
									finally
									{
										workspaceContext.expire();

										//Keep track of the state of
										//the workspace when this plan
										//was finished. Later on we
										//will check to see if the
										//workspace had been changed by
										//other plans since this plan
										//was last run.
									}

									planToChangeInfoCountMap.put(plan, new Integer(_targetSpace.getChangeInfoCount()));
								}
								else
								{
									if (logger.isDebugEnabled() == true)
									{
										logger.debug("For workspace: " +
											_targetSpace.getWorkspaceIdentifier() + " plan: " +
											plan.getName() + " is finished.");
									}

									activePlanSet.remove(plan);
								}

								if (logger.isDebugEnabled() == true)
								{
									logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " plan: " + plan.getName() + " executed successfully.");
								}
							}
							catch (Throwable t)
							{
								_targetSpace.setErrored(plan, t);
								activePlanSet.remove(plan);
								logger.error("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " encountered exception while trying to execute plan: " + plan);
								getErrorManager().logException(t, logger);
							}
							finally
							{
								_targetSpace.setExecuted(plan);

								if (_targetSpace.isFinished(plan) == true && activePlanSet.contains(plan) == true)
								{
									activePlanSet.remove(plan);
								}
							}
						}
					}

					for (Iterator activePlans = activePlanSet.iterator(); activePlans.hasNext() == true;)
					{
						plan = (Plan) activePlans.next();

						if (logger.isDebugEnabled() == true)
						{
							logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() +
										 " plan: " + plan.getName() + " is an active plan.");
						}

						if (_targetSpace.isFinished(plan) == true)
						{
							if (logger.isDebugEnabled() == true)
							{
								logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() +
									" plan: " + plan.getName() + " is now a finished plan.");
							}

							activePlans.remove();
						}
						else
						{
							if (logger.isDebugEnabled() == true)
							{
								logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() +
									" plan: " + plan.getName() + " is still an active plan.");
							}

							int planChangeCount = 0;
							Integer planChangeCountInteger = (Integer) planToChangeInfoCountMap.get(plan);
							planChangeCount = planChangeCountInteger.intValue();

							if (_targetSpace.getChangeInfoCount() > planChangeCount)
							{
								//Plan is interested in the
								//workspace and there have been
								//changes since it was last
								//run.

								if (logger.isDebugEnabled() == true)
								{
									logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() +
										" notifying plans for plan: " + plan.getName());
								}

								notifyPlans = true;
							}
							else
							{
								if (logger.isDebugEnabled() == true)
								{
									logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() +
										" NOT notifying plans for plan: " + plan.getName());
								}
							}
						}

						if (logger.isDebugEnabled() == true)
						{
							logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() +
										 " setting workspace as executed.");
						}
					}

					if (activePlanSet.isEmpty() == true)
					{
						_targetSpace.setCompleted();
					}
				}
				finally
				{
					if (getTimePlans() == true)
					{
						endWorkspaceRun = System.currentTimeMillis();

						if (logger.isInfoEnabled() == true)
						{
							logger.info("Processing target space time at beginning of finally: " + (endWorkspaceRun - startWorkspaceRun));
						}
					}

					if (((_targetSpace.isCompleted() == true)  || _targetSpace.isTerminated() == true) &&
						  _targetSpace.isPersisted() == false)
					{
						remove(_targetSpace.getWorkspaceIdentifier());
						retireTargetSpace(_targetSpace);
					}
					else if (notifyPlans == true)
					{
						if (logger.isDebugEnabled() == true)
						{
							logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " execute plan will notify plans.");
						}

						_targetSpace.setActive();
						_targetSpace.notifyPlans();
					}
					else
					{
						_targetSpace.setActive();

						if (logger.isDebugEnabled() == true)
						{
							logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " execute plan will NOT notify plans");
						}
					}

					if (getTimePlans() == true)
					{
						endWorkspaceRun = System.currentTimeMillis();

						if (logger.isInfoEnabled() == true)
						{
							logger.info("Processing target space time: " + (endWorkspaceRun - startWorkspaceRun));
						}
					}
				}
			}
		});
	}

	/**
	 * Places a target directly on the Blackboard.  The target must
	 * have a {@link Event} annotation or the Blackboard will
	 * eventually throw an Exception.
	 *
	 * Developer's can use this method to effectively create a whole
	 * new workspace.  It the appName, workspaceName, name and the
	 * value of the workspace identifier are the same as that of the
	 * event that caused this workspace creation, if this workspace is
	 * still active at the time of _object's arrival, the _target will
	 * appear on this workspace.  If any of those values are different
	 * the _target will appear on another workspace.
	 *
	 * @param _target the name of the target to be placed on the workspace.
	 * @see Event
	 * 
	 */

	public void placeOnBlackboard(Target _target)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Placing on blackboard the target: " + _target);
		}

		placeOnBlackboard(_target.getBlackboardObject());
	}

	/**
	 * Places an object directly on the Blackboard.  The object must
	 * have a {@link Event} annotation or the Blackboard will
	 * eventually throw an Exception.
	 *
	 * Developer's can use this method to effectively create a whole
	 * new workspace.  It the appName, workspaceName, name and the
	 * value of the workspace identifier are the same as that of the
	 * event that caused this workspace creation, if this workspace is
	 * still active at the time of _object's arrival, the _object will
	 * appear on this workspace.  If any of those values are different
	 * the _object will appear on another workspace.
	 *
	 * @param _object the name of the target to be placed on the workspace.
	 * @see Event
	 * 
	 */

	public void placeOnBlackboard(final Object _event)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Placing on blackboard the event: " + _event);
		}

		getBlackboardExecutor().execute(new Runnable() {
			public void run()
			{
				try
				{
					if (logger.isDebugEnabled() == true)
					{
						logger.debug("o");
					}

					if (_event instanceof WorkspaceExecutionContext)
					{
						Object workspaceIdentifier = ((WorkspaceExecutionContext) _event).getWorkspaceIdentifier();
						
						if (logger.isTraceEnabled() == true)
						{
							logger.trace("Cleaning up context for workspace identified by: " + workspaceIdentifier);
						}

						remove(workspaceIdentifier);
					}
					else
					{
						add(_event);

					}
				}
				catch (Throwable t)
				{
					logger.error("Caught exception: " + t.toString() + " while trying to add event: " + _event +
								 " to the blackboard.", t);
				}
			}
		});
	}

	/**
	 * Places a target directly on the Blackboard in the same
	 * manner as placeOnBlackboard(Target _target) except that it
	 * delays the arrival of that _target until _delay milliseconds has
	 * passed.  The target must have a {@link Event} annotation or the Blackboard
	 * will eventually throw an Exception.
	 * 
	 * @param _target the name of the target to be placed on the workspace.
	 * @param _delay the time in milliseconds that must pass before
	 * event appears on workspace.
	 */

	public void schedulePlaceOnBlackboard(final Target _target, long _delay)
	{
		schedulePlaceOnBlackboard(_target.getBlackboardObject(), _delay);
	}

	/**
	 * Places an object on directly on the Blackboard in the same
	 * manner as placeOnBlackboard(Object _object) except that it
	 * delays the arrival of that _object until _delay milliseconds has
	 * passed.  The object must have a {@link Event} annotation or the Blackboard
	 * will eventually throw an Exception.
	 * 
	 * @param _object the name of the target to be placed on the workspace.
	 * @param _delay the time in milliseconds that must pass before
	 * event appears on workspace.
	 */

	public void schedulePlaceOnBlackboard(final Object _event, long _delay)
	{
		getScheduledBlackboardExecutor().schedule(new Runnable() {
			public void run()
			{
				try
				{
					placeOnBlackboard(_event);
				}
				catch (Throwable t)
				{
					logger.error("Caught exception: " + t.toString() + " while trying to add event: " + _event +
								 " to the blackboard.", t);
				}
			}
		},
		_delay,
		TimeUnit.MILLISECONDS
		);
	}

	private List<String> determineEventNames(Object _event)
	{
		List<String> eventNameList = new ArrayList<String>();
		
		Class eventClass = _event.getClass();

		if (eventClass.isAnnotationPresent(Event.class) == true)
		{
			Event event = (Event) eventClass.getAnnotation(Event.class);

			if (event.name() != null)
			{
				String[] nameArray = event.name().split(",");

				for (int i = 0; i < nameArray.length; i++)
				{
					eventNameList.add(createFullEventName(event.appName(), event.workspaceName(), nameArray[i]));
				}
			}
		}

		return eventNameList;
	}

	private String determineWorkspaceIdentifierName(Object _event)
	{
		Class eventClass = _event.getClass();
		String propertyName = null;

		if (eventClass.isAnnotationPresent(Event.class) == true)
		{
			Event event = (Event) eventClass.getAnnotation(Event.class);

			if (event.workspaceIdentifier() != null	)
			{
				propertyName = event.workspaceIdentifier();
			}
		}

		return propertyName;
	}

	private void add(Object _event)
	{
		try
		{
			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Object identified by class: " +
							 _event.getClass() + " was added on the blackboard.");
			}

			boolean foundEventConfiguration = false;

			List<String> eventNameList = determineEventNames(_event);

			if (eventNameList.isEmpty() == true)
			{
				throw new RuntimeException("Unknown event: " + _event);
			}

			for (String eventName: eventNameList)
			{
				if (logger.isDebugEnabled() == true)
				{
					logger.debug("Object of class: " + _event.getClass() +
								 " is event " + eventName + " .");
				}

				WorkspaceConfiguration workspaceConfiguration = getEventToWorkspaceMap().get(eventName);

				if (workspaceConfiguration == null)
				{
					throw new RuntimeException("Unable to find the workspace configuration for eventName: " + eventName +
											   ". Please check your com.lucidtechnics.blackboard.Event annotation " +
											   " as it is defined in the class: " + _event.getClass() + 
											   " and make sure the Event annotation attributes appName, workspaceName, and name " +
											   " maps to the path under blackboard's \"apps\" directory <appName>/<workspaceName>/<name>");
				}

				Object workspaceIdentifier = PropertyUtils.getProperty(_event, determineWorkspaceIdentifierName(_event));
				addToTargetSpace(workspaceConfiguration, workspaceIdentifier, eventName, _event);
				foundEventConfiguration = true;
			}

			if (foundEventConfiguration == false)
			{
				logger.error("Object identified by class: " + _event.getClass() + " was not processed as it is not defined as an event.");
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
	}

	protected void addToTargetSpace(WorkspaceConfiguration _workspaceConfiguration,
									Object _workspaceIdentifier, String _eventName, Object _event)
			throws Exception
	{
		TargetSpace targetSpace = retrieveTargetSpace(_workspaceIdentifier, _workspaceConfiguration);

		if (logger.isDebugEnabled() == true)
		{
			logger.debug("For workspace: " + _workspaceIdentifier + " putting event " + _eventName);
		}

		targetSpace.put(_eventName, _event, getBlackboardActor(), null);
	}

	private TargetSpace retrieveTargetSpace(Object _workspaceIdentifier, WorkspaceConfiguration _workspaceConfiguration)
	{
		WorkspaceExecutionContext workspaceExecutionContext = get(_workspaceIdentifier);
		TargetSpace targetSpace = workspaceExecutionContext.getTargetSpace();

		if (targetSpace == null)
		{
			targetSpace = new TargetSpaceImpl(_workspaceIdentifier);
			targetSpace.addPlans(_workspaceConfiguration.getPlanSet());
			workspaceExecutionContext.setTargetSpace(targetSpace);
		}

		if (targetSpace.getBlackboard() == null)
		{
			targetSpace.initialize(this);

			targetSpace.setAppName(_workspaceConfiguration.getAppName());
			targetSpace.setName(_workspaceConfiguration.getWorkspaceName());
			targetSpace.setDoNotPersistSet(_workspaceConfiguration.getDoNotPersistSet());
			targetSpace.setPersistChangeInfoHistory(_workspaceConfiguration.getPersistChangeInfoHistory());
			targetSpace.setTerminateOnError(_workspaceConfiguration.getTerminateOnError());
			targetSpace.setWorkspaceConfiguration(_workspaceConfiguration);

			long currentTimeMillis = System.currentTimeMillis();
			targetSpace.setLastActiveTime(currentTimeMillis);
		}

		return targetSpace;
	}

	private void incrementActiveWorkspaceCount()
	{
		setActiveWorkspaceCount(getActiveWorkspaceCount() + 1);

		if (logger.isDebugEnabled() == true)
		{
			logger.debug(getActiveWorkspaceCount());
		}
	}

	private void decrementActiveWorkspaceCount()
	{
		setActiveWorkspaceCount(getActiveWorkspaceCount() - 1);

		if (logger.isDebugEnabled() == true)
		{
			logger.debug(getActiveWorkspaceCount());
		}
	}

	protected void retireTargetSpace(final TargetSpace _targetSpace)
	{
		getPersistenceExecutor().execute(new Runnable() {
			public void run()
			{
				_targetSpace.setRetired();
				_targetSpace.setPersisted();
				_targetSpace.setRetireDate(new Date());

				TargetSpace targetSpace = _targetSpace.prepareForRetirement();

				persistTargetSpace(targetSpace);

				//Placing the workspaceExcutionContext on the
				//blackboard, signals the blackboard execution
				//thread that this workspace execution is finished
				//and has been retired. The workspace is then removed
				//from the cache.  This avoids the need to establish a
				//lock for the critical cache region. 
				WorkspaceExecutionContext workSpaceExecutionContext = get(_targetSpace.getWorkspaceIdentifier());
				placeOnBlackboard(workSpaceExecutionContext);
				
				if (logger.isDebugEnabled() == true)
				{
					logger.debug("Target space identified by name: " + _targetSpace.getName() + " and id: " +
								 _targetSpace.getWorkspaceIdentifier() + " is retired.");
				}
			}
		});
	}

	private void persistTargetSpace(TargetSpace _targetSpace)
	{
		TargetSpace targetSpace = _targetSpace.prepareForPersistence();
		getPersister().put(_targetSpace);		
	}

	protected void processEventPlans(String _appName, String _workspaceName, WorkspaceConfiguration _workspaceConfiguration, java.io.File _eventPlanDirectory)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Getting plans from event directory: " + _eventPlanDirectory.getName());
		}

		//execute workspace configuration returns workspace
		//configuration for this workspace.

		java.io.File[] planArray = _eventPlanDirectory.listFiles();

		for (int i = 0; i < planArray.length; i++)
		{
			if (planArray[i].isDirectory() == false && planArray[i].getName().endsWith(".js") == true)
			{
				if (logger.isInfoEnabled() == true)
				{
					logger.info("Loading plan: " + planArray[i].getName());
				}

				_workspaceConfiguration.getPlanSet().add(new JavaScriptPlan(planArray[i].getName(), planArray[i].getAbsolutePath()));
			}
			else if (planArray[i].isDirectory() == false)
			{
				String[] tokenArray = planArray[i].getName().split("\\.");
				String extension = tokenArray[tokenArray.length - 1];

				if (Jsr223Plan.hasScriptingEngine(extension) == true)
				{
					if (logger.isInfoEnabled() == true)
					{
						logger.info("Loading plan: " + planArray[i].getName());
					}
				
					_workspaceConfiguration.getPlanSet().add(new Jsr223Plan(planArray[i].getName(), planArray[i].getAbsolutePath(), extension));
				}
				else
				{
					if (logger.isWarnEnabled() == true)
					{
						logger.warn("Not scriptable: " + planArray[i].getName());
					}
				}					
			}
		}

		String eventName = extractEventName(_eventPlanDirectory.getName());
		
		getEventToWorkspaceMap().put(createFullEventName(_appName, _workspaceName, eventName), _workspaceConfiguration);
	}

	protected WorkspaceConfiguration configureWorkspace(String _appName, String _workspaceName, java.io.File _eventPlanDirectory)
	{
		WorkspaceConfiguration workspaceConfiguration = new WorkspaceConfiguration(_appName, _workspaceName);

		java.io.File[] configurationFileArray = _eventPlanDirectory.listFiles();
		Configurator configurator = null;
		String configuratorPath = null;
		
		for (int i = 0; i < configurationFileArray.length; i++)
		{
			if (configurationFileArray[i].isDirectory() == false && configurationFileArray[i].getName().endsWith("workspaceConfiguration.js") == true)
			{
				if (logger.isInfoEnabled() == true)
				{
					logger.info("Loading configuration: " + configurationFileArray[i].getName());
				}
				
				configurator = new JavaScriptConfigurator();
				configuratorPath = configurationFileArray[i].getAbsolutePath();
			}
			else if (configurationFileArray[i].isDirectory() == false && configurationFileArray[i].getName().contains("workspaceConfiguration") == true)
			{
				if (logger.isInfoEnabled() == true)
				{
					logger.info("Loading configuration: " + configurationFileArray[i].getName());
				}

				String[] tokenArray = configurationFileArray[i].getName().split("\\.");
				String extension = tokenArray[tokenArray.length - 1];

				configurator = new Jsr223Configurator(extension);
				configuratorPath = configurationFileArray[i].getAbsolutePath();
			}
		}

		if (configuratorPath != null)
		{
			if (logger.isInfoEnabled() == true)
			{
				logger.info("Executing this configuration: " + configuratorPath);
			}
			
			configurator.execute(workspaceConfiguration, configuratorPath);
		}
		else
		{
			if (logger.isInfoEnabled() == true)
			{
				logger.info("No workspaceConfiguration.js/rb file found for directory: " + _eventPlanDirectory + ".");
			}
		}

		return workspaceConfiguration;
	}
	
	protected String createFullEventName(String _appName, String _workspaceName, String _eventName)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Creating event name from app name: " + _appName +
						 " and workspace name: " + _workspaceName +
						 " and event name: " + _eventName);
		}
		
		return _appName + "." + _workspaceName + "." + _eventName;
	}

	protected String extractEventName(String _pathName)
	{
		String pathName = _pathName.replaceAll(java.io.File.pathSeparator + "$", "");
		String[] tokenArray = pathName.split(java.io.File.pathSeparator);

		return tokenArray[tokenArray.length - 1];
	}

	private void put(Object _workspaceIdentifier, WorkspaceExecutionContext _workspaceExecutionContext)
	{
		try
		{
			org.apache.jcs.JCS jcs = org.apache.jcs.JCS.getInstance("blackboard");
			jcs.put(_workspaceIdentifier, _workspaceExecutionContext);
			incrementActiveWorkspaceCount();
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
	}

	private WorkspaceExecutionContext get(Object _workspaceIdentifier)
	{
		WorkspaceExecutionContext workspaceExecutionContext = null;
		
		try
		{
			org.apache.jcs.JCS jcs = org.apache.jcs.JCS.getInstance("blackboard");
			workspaceExecutionContext = (WorkspaceExecutionContext) jcs.get(_workspaceIdentifier);

			if (workspaceExecutionContext == null)
			{
				if (currentWorkspaceExecutor >= getMaxWorkspaceThread())
				{
					currentWorkspaceExecutor = 0;
				}

				workspaceExecutionContext = new WorkspaceExecutionContext(_workspaceIdentifier, currentWorkspaceExecutor);
				
				currentWorkspaceExecutor++;

				put(_workspaceIdentifier, workspaceExecutionContext);
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}

		return workspaceExecutionContext;
	}

	private boolean contains(Object _workspaceIdentifier)
	{
		try
		{
			org.apache.jcs.JCS jcs = org.apache.jcs.JCS.getInstance("blackboard");
			return (jcs.get(_workspaceIdentifier) != null);
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
	}

	protected void remove(Object _workspaceIdentifier)
	{
		try
		{
			org.apache.jcs.JCS jcs = org.apache.jcs.JCS.getInstance("blackboard");
			jcs.remove(_workspaceIdentifier);
			decrementActiveWorkspaceCount();

			if (logger.isTraceEnabled() == true)
			{
				logger.trace("Active workspace count is now: " + getActiveWorkspaceCount());
			}
		}
		catch(Throwable t)
		{
			throw new RuntimeException(t);
		}
	}
}