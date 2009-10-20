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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.lucidtechnics.blackboard.config.WorkspaceConfiguration;
import com.lucidtechnics.blackboard.config.EventConfiguration;

import com.lucidtechnics.blackboard.util.Guard;
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
	private int maxWorkspaceThread = 20;
	private int maxPersistenceThread = 1;
	private int maxWorkspace = 100000;
	private int activeWorkspaceCount;
	private ThreadPoolExecutor blackboardExecutor;
	private ScheduledThreadPoolExecutor scheduledBlackboardExecutor;
	private ThreadPoolExecutor workspaceExecutor;
	private ThreadPoolExecutor managerExecutor;
	private ThreadPoolExecutor persistenceExecutor;
	private Map<String, WorkspaceConfiguration> eventToWorkspaceMap;
	private Map<Object, TargetSpace> targetSpaceMap; //workspace identifier --> target space
	private Guard targetSpaceGuard;
	private boolean managingBlackboard;
	private Persister persister;
	private String host = "localhost";
	private int port = 28000;
	private String user = "blackboard";
	private String password = "blackboard";
	private boolean timePlans = true;

	private ReentrantReadWriteLock blackboardReadWriteLock;
	private Lock blackboardReadLock;
	private Lock blackboardWriteLock;
	private BlackboardFactory blackboardFactory;

	public ErrorManager getErrorManager() { return errorManager; }
	private BlackboardActor getBlackboardActor() { return blackboardActor; }
	private int getMaxBlackboardThread() { return maxBlackboardThread; }
	public int getMaxScheduledBlackboardThread() { return maxScheduledBlackboardThread; }
	private int getMaxWorkspaceThread() { return maxWorkspaceThread; }
	private int getMaxPersistenceThread() { return maxPersistenceThread; }
	private int getMaxWorkspace() { return maxWorkspace; }
	private int getActiveWorkspaceCount() { return activeWorkspaceCount; }
	private ThreadPoolExecutor getBlackboardExecutor() { return blackboardExecutor; }
	private ScheduledThreadPoolExecutor getScheduledBlackboardExecutor() { return scheduledBlackboardExecutor; }
	private ThreadPoolExecutor getWorkspaceExecutor() { return workspaceExecutor; }
	private ThreadPoolExecutor getManagerExecutor() { return managerExecutor; }
	private ThreadPoolExecutor getPersistenceExecutor() { return persistenceExecutor; }
	private Map<String, WorkspaceConfiguration> getEventToWorkspaceMap() { return eventToWorkspaceMap; }
	private ReentrantReadWriteLock getBlackboardReadWriteLock() { return blackboardReadWriteLock; }
	private Lock getBlackboardReadLock() { return blackboardReadLock; }
	private Lock getBlackboardWriteLock() { return blackboardWriteLock; }
	private BlackboardFactory getBlackboardFactory() { return blackboardFactory; }
	private Map<Object, TargetSpace> getTargetSpaceMap() { return targetSpaceMap; }
	private Guard getTargetSpaceGuard() { return targetSpaceGuard; }
	private boolean getManagingBlackboard() { return managingBlackboard; }
	private Persister getPersister() { return persister; }
	public String getHost() { return host; }
	public int getPort() { return port; }
	public String getUser() { return user; }
	public String getPassword() { return password; }
	public boolean getTimePlans() { return timePlans; }
	
	public void setErrorManager(ErrorManager _errorManager) { errorManager = _errorManager; }
	private void setBlackboardActor(BlackboardActor _blackboardActor) { blackboardActor = _blackboardActor; }
	public void setMaxBlackboardThread(int _maxBlackboardThread) { maxBlackboardThread = _maxBlackboardThread; }
	public void setMaxScheduledBlackboardThread(int _maxScheduledBlackboardThread) { maxScheduledBlackboardThread = _maxScheduledBlackboardThread; }
	public void setMaxWorkspaceThread(int _maxWorkspaceThread) { maxWorkspaceThread = _maxWorkspaceThread; }
	public void setMaxPersistenceThread(int _maxPersistenceThread) { maxPersistenceThread = _maxPersistenceThread; }
	public void setMaxWorkspace(int _maxWorkspace) { maxWorkspace = _maxWorkspace; }
	private void setActiveWorkspaceCount(int _activeWorkspaceCount) { activeWorkspaceCount = _activeWorkspaceCount; }
	private void setBlackboardExecutor(ThreadPoolExecutor _blackboardExecutor) { blackboardExecutor = _blackboardExecutor; }
	private void setScheduledBlackboardExecutor(ScheduledThreadPoolExecutor _scheduledBlackboardExecutor) { scheduledBlackboardExecutor = _scheduledBlackboardExecutor; }
	private void setWorkspaceExecutor(ThreadPoolExecutor _workspaceExecutor) { workspaceExecutor = _workspaceExecutor; }
	private void setManagerExecutor(ThreadPoolExecutor _managerExecutor) { managerExecutor = _managerExecutor; }
	private void setPersistenceExecutor(ThreadPoolExecutor _persistenceExecutor) { persistenceExecutor = _persistenceExecutor; }
	private void setEventToWorkspaceMap(Map<String, WorkspaceConfiguration> _eventToWorkspaceMap) { eventToWorkspaceMap = _eventToWorkspaceMap; }
	private void setBlackboardReadWriteLock(ReentrantReadWriteLock _blackboardReadWriteLock) { blackboardReadWriteLock = _blackboardReadWriteLock; }
	private void setBlackboardReadLock(Lock _blackboardReadLock) { blackboardReadLock = _blackboardReadLock; }
	private void setBlackboardWriteLock(Lock _blackboardWriteLock) { blackboardWriteLock = _blackboardWriteLock; }
	public void setBlackboardFactory(BlackboardFactory _blackboardFactory) { blackboardFactory = _blackboardFactory; }
	private void setTargetSpaceMap(Map<Object, TargetSpace> _targetSpaceMap) { targetSpaceMap = _targetSpaceMap;	 }
	private void setTargetSpaceGuard(Guard _targetSpaceGuard) { targetSpaceGuard = _targetSpaceGuard; }
	private void setManagingBlackboard(boolean _managingBlackboard) { managingBlackboard = _managingBlackboard; }
	private void setPersister(Persister _persister) { persister = _persister; } 
	public void setHost(String _host) { host = _host; }
	public void setPort(int _port) { port = _port; }
	public void setUser(String _user) { user = _user; }
	public void setPassword(String _password) { password = _password; }
	public void setTimePlans(boolean _timePlans) { timePlans = _timePlans; }
	
	public Blackboard()
	{
		boolean propertyExists = PropertyUtil.getInstance().loadProperties("/src/Blackboard.properties", "blackboard.cfg", true);

		if (propertyExists == true)
		{
			setTimePlans(Boolean.valueOf(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.time.plans")));
			setMaxBlackboardThread(Integer.valueOf(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.max.blackboard.thread")));
			setMaxScheduledBlackboardThread(Integer.valueOf(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.max.scheduled.blackboard.thread")));
			setMaxWorkspaceThread(Integer.valueOf(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.max.workspace.thread")));
			setMaxPersistenceThread(1);
			setMaxWorkspace(Integer.valueOf(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.max.workspace")));

			setHost(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.db.host"));
			setPort(Integer.valueOf(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.db.port")));
			setUser(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.db.user"));
			setPassword(PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.db.password"));
		}

		setBlackboardActor(new BlackboardActor("Blackboard"));
		setEventToWorkspaceMap(new HashMap());
		setTargetSpaceMap(new HashMap<Object, TargetSpace>());
		setBlackboardReadWriteLock(new ReentrantReadWriteLock());
		setBlackboardReadLock(getBlackboardReadWriteLock().readLock());
		setBlackboardWriteLock(getBlackboardReadWriteLock().writeLock());
		setBlackboardFactory(new BlackboardFactoryImpl());
		setTargetSpaceGuard(new Guard());
		setErrorManager(com.lucidtechnics.blackboard.util.error.ErrorManager.getInstance());
	}

	private void initializeTargetSpacePersistentStore(String _persistenceDir)
	{
		setPersister(PersisterFactory.make("inkwell", _persistenceDir, this));	
	}

	public void init()
	{
		String persistenceDir = "./blackboard/persistence";
		String appsHome = "./blackboard/apps";

		boolean successful = PropertyUtil.getInstance().loadProperties("/src/Blackboard.properties", "blackboard.cfg", true);

		if (successful == true)
		{
			appsHome = PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.apps.home", appsHome);
			persistenceDir = PropertyUtil.getInstance().getProperty("blackboard.cfg", "blackboard.persistence.home", persistenceDir);
		}

		java.io.File file = new java.io.File(appsHome);

		if (file.exists() == false)
		{
			file.mkdirs();
		}

		file = new java.io.File(persistenceDir);

		if (file.exists() == false)
		{
			file.mkdirs();
		}

		initializeTargetSpacePersistentStore(persistenceDir);

		if (appsHome == null ||
		   org.apache.commons.lang.StringUtils.isWhitespace(appsHome) == true)
		{
			throw new RuntimeException("blackboard.apps.home has not been set in the Blackboard.properties file");
		}

		java.io.File appsDirectory = new java.io.File(appsHome);

		if (appsDirectory.isDirectory() != true)
		{
			throw new RuntimeException("Directory: " + appsHome + " as set in blackboard.apps.home is not a directory");
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

		establishBlackboardPlans();

		if (logger.isInfoEnabled() == true)
		{
			logger.info("Loaded event configurations: " + getEventToWorkspaceMap());
		}
		
		setBlackboardExecutor(new ThreadPoolExecutor(getMaxBlackboardThread(), getMaxBlackboardThread(), 100, TimeUnit.SECONDS,
			new LinkedBlockingQueue()));

		setScheduledBlackboardExecutor(new ScheduledThreadPoolExecutor(getMaxScheduledBlackboardThread()));

		setWorkspaceExecutor(new ThreadPoolExecutor(getMaxWorkspaceThread(), getMaxWorkspaceThread(), 100, TimeUnit.SECONDS,
			new LinkedBlockingQueue()));

		setPersistenceExecutor(new ThreadPoolExecutor(getMaxPersistenceThread(), getMaxPersistenceThread(), 100, TimeUnit.SECONDS,
			new LinkedBlockingQueue(	)));

		setManagerExecutor(new ThreadPoolExecutor(1, 1, 100, TimeUnit.SECONDS,
			new LinkedBlockingQueue()));

		if (logger.isInfoEnabled() == true)
		{
			logger.info("Blackboard Workspace Server Initialization Inception.");
			logger.info("Apache 2.0 Open Source License.");
			logger.info("Copyright Owner - LucidTechnics, LLC.");
			logger.info("Authors - Bediako Ntodi George and David Yuctan Hodge.");
			logger.info("Initialization was successful.");
		}
	}

	private void restoreToBlackboard(TargetSpace _targetSpace)
	{
		try
		{
			acquireBlackboardWriteLock();

			TargetSpace targetSpace = (TargetSpace) getTargetSpaceMap().get(_targetSpace.getWorkspaceIdentifier());

			if (targetSpace != null)
			{
				logger.warn("For workspace: " +
							_targetSpace.getWorkspaceIdentifier() +
							" it was scheduled to be persisted but is still in memory.");
			}

			getTargetSpaceMap().put(_targetSpace.getWorkspaceIdentifier(), _targetSpace);

			setActiveWorkspaceCount(getActiveWorkspaceCount() + 1);
		}
		finally
		{
			releaseBlackboardWriteLock();
		}
	}

	protected void executePlans(final TargetSpace _targetSpace, final List _planList)
	{
		getWorkspaceExecutor().execute(new Runnable() {
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
					guardTargetSpace(_targetSpace.getWorkspaceIdentifier());

					if (logger.isDebugEnabled() == true)
					{
						logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " the guard was obtained.");
					}

					_targetSpace.setExecuting();

					if (_targetSpace.isPersisted() == true)
					{
						restoreToBlackboard(_targetSpace);
					}

					for (int i = 0; i < _planList.size(); i++)
					{
						plan = (Plan) _planList.get(i);

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
										workspaceContext = new WorkspaceContext(_targetSpace, plan);

										_targetSpace.getWorkspaceWriteLock().lock();

										long startTime = 0l;
										long endTime = 0l;
										long totalTime = 0l;
										
										if (getTimePlans() == true)
										{
											startTime = System.currentTimeMillis();
										}

										//Execute the plan
										_targetSpace.setPlanState(plan, plan.execute(workspaceContext));

										if (getTimePlans() == true)
										{
											endTime = System.currentTimeMillis();
											totalTime = endTime - startTime;

											if (logger.isDebugEnabled() == true)
											{
												logger.debug("Plan: " + plan.getName() + " executed in: " + totalTime);
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

										_targetSpace.getWorkspaceWriteLock().unlock();
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
					if (((_targetSpace.isCompleted() == true)  || _targetSpace.isTerminated() == true) &&
						  _targetSpace.isPersisted() == false)
					{
						try
						{
							acquireBlackboardWriteLock();
							removeFromBlackboard(_targetSpace, false);
						}
						finally
						{
							releaseBlackboardWriteLock();
						}

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

					if (logger.isDebugEnabled() == true)
					{
						logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " the guard was released.");
					}

					releaseTargetSpace(_targetSpace.getWorkspaceIdentifier());
				}

				if (getTimePlans() == true)
				{
					endWorkspaceRun = System.currentTimeMillis();

					if (logger.isDebugEnabled() == true)
					{
						logger.debug("Processing target space time: " + (endWorkspaceRun - startWorkspaceRun));
					}
				}
			}
		});
	}

	private void acquireBlackboardReadLock()
	{
		getBlackboardReadLock().lock();
	}

	private void releaseBlackboardReadLock()
	{
		getBlackboardReadLock().unlock();
	}

	protected void acquireBlackboardWriteLock()
	{
		getBlackboardWriteLock().lock();
	}

	protected void releaseBlackboardWriteLock()
	{
		getBlackboardWriteLock().unlock();
	}

	public void placeOnBlackboard(Target _target)
	{
		if (logger.isDebugEnabled() == true)
		{
			logger.debug("Placing on blackboard the target: " + _target);
		}

		placeOnBlackboard(_target.getBlackboardObject());
	}

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

					add(_event);
				}
				catch (Throwable t)
				{
					logger.error("Caught exception: " + t.toString() + " while trying to add event: " + _event +
								 " to the blackboard.", t);
				}
			}
		});
	}

	public void schedulePlaceOnBlackboard(final Target _target, long _delay)
	{
		schedulePlaceOnBlackboard(_target.getBlackboardObject(), _delay);
	}

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
		try
		{
			guardTargetSpace(_workspaceIdentifier);

			if (logger.isDebugEnabled() == true)
			{
				logger.debug("For workspace: " + _workspaceIdentifier + " the guard was obtained for event: " + _eventName);
			}

			acquireBlackboardReadLock();

			TargetSpace targetSpace = (TargetSpace) getTargetSpaceMap().get(_workspaceIdentifier);

			if (targetSpace == null)
			{
				if (getTargetSpaceMap().keySet().contains(_workspaceIdentifier) == true)
				{
					targetSpace = retrieveTargetSpaceFromStore(_workspaceIdentifier);
				}
				else if (getTargetSpaceMap().keySet().contains(_workspaceIdentifier) == false)
				{
					targetSpace = getBlackboardFactory().createTargetSpace(_workspaceConfiguration, _workspaceIdentifier);
					String workspaceIdentifierString = (_workspaceIdentifier == null) ? "null" : _workspaceIdentifier.toString();
					targetSpace.addPlans(_workspaceConfiguration.getPlanSet());
				}

				if (targetSpace != null)
				{
					targetSpace.initialize(this);

					try
					{
						releaseBlackboardReadLock();
						acquireBlackboardWriteLock();

						getTargetSpaceMap().put(_workspaceIdentifier, targetSpace);

						incrementActiveWorkspaceCount();
					}
					finally
					{
						acquireBlackboardReadLock();
						releaseBlackboardWriteLock();
					}
				}
				else
				{
					throw new RuntimeException("Unable to create or retrieve workspace for workspaceIdentifier: " + _workspaceIdentifier);
				}
			}
			
			targetSpace.setDoNotPersistSet(_workspaceConfiguration.getDoNotPersistSet());
			targetSpace.setPersistChangeInfoHistory(_workspaceConfiguration.getPersistChangeInfoHistory());
			long currentTimeMillis = System.currentTimeMillis();
			targetSpace.setLastActiveTime(currentTimeMillis);

			if (logger.isDebugEnabled() == true)
			{
				logger.debug("For workspace: " + _workspaceIdentifier + " putting event " + _eventName);
			}

			targetSpace.put(_eventName, _event, getBlackboardActor(), null);
		}
		finally
		{
			releaseBlackboardReadLock();

			if (logger.isDebugEnabled() == true)
			{
				logger.debug("For workspace: " + _workspaceIdentifier + " the target space is released for event " + _eventName);
			}

			releaseTargetSpace(_workspaceIdentifier);
		}
	}

	private void incrementActiveWorkspaceCount()
	{
		setActiveWorkspaceCount(getActiveWorkspaceCount() + 1);

		if (getActiveWorkspaceCount() > getMaxWorkspace() && getManagingBlackboard() == false)
		{
			synchronized(this)
			{
				if (getActiveWorkspaceCount() > getMaxWorkspace() && getManagingBlackboard() == false)
				{
					setManagingBlackboard(true);
					runManageBlackboard();
				}
			}
		}

		if (logger.isDebugEnabled() == true)
		{
			logger.debug(getActiveWorkspaceCount());
		}
	}

	private void decrementActiveWorkspaceCount()
	{
		setActiveWorkspaceCount(getActiveWorkspaceCount() - 1);

		if (getActiveWorkspaceCount() > getMaxWorkspace() && getManagingBlackboard() == false)
		{
			synchronized(this)
			{
				if (getActiveWorkspaceCount() > getMaxWorkspace() && getManagingBlackboard() == false)
				{
					setManagingBlackboard(true);
					runManageBlackboard();
				}
			}
		}

		if (logger.isDebugEnabled() == true)
		{
			logger.debug(getActiveWorkspaceCount());
		}
	}

	private void runManageBlackboard()
	{
		getManagerExecutor().execute(new Runnable()
		{
			public void run()
			{
				manageBlackboard();
			}
		});
	}

	private void manageBlackboard()
	{
		long timestamp = 0l;

		try
		{
			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Managing active workspaces.  Reducing workspace count from: " + getActiveWorkspaceCount());
			}

			//get a list of retired candidate blackboards.
			List candidateTargetSpaceList = getCandidateTargetSpaces();
			timestamp = System.currentTimeMillis();

			//remove them from the blackboard
			Iterator candidateTargetSpaces = candidateTargetSpaceList.iterator();

			while (candidateTargetSpaces.hasNext() == true)
			{
				TargetSpace targetSpace = (TargetSpace) candidateTargetSpaces.next();

				if (targetSpace.isPersisted() == false &&
					  targetSpace.isCompleted() == false &&
					  targetSpace.isTerminated() == false &&
					  targetSpace.isExecuting() == false)
				{
					boolean acquiredLock = guardTargetSpace(targetSpace.getWorkspaceIdentifier(), false);

					if (acquiredLock == true)
					{
						try
						{
							if (targetSpace.isPersisted() == false &&
								  targetSpace.isCompleted() == false &&
								  targetSpace.isTerminated() == false &&
								  targetSpace.getLastUsedTimestamp() < timestamp)
							{
								if (logger.isDebugEnabled() == true)
								{
									logger.debug("p");
								}

								targetSpace.setPersisted();
								persistTargetSpace(targetSpace);

								acquireBlackboardWriteLock();
								removeFromBlackboard(targetSpace, true);
							}
						}
						finally
						{
							releaseBlackboardWriteLock();
							releaseTargetSpace(targetSpace.getWorkspaceIdentifier());
						}
					}
				}
			}
		}
		catch (Throwable t)
		{
			getErrorManager().logException(t, logger);
		}
		finally
		{
			if (logger.isDebugEnabled() == true)
			{
				logger.debug("Active workspace count is now: " + getActiveWorkspaceCount());
			}

		    setManagingBlackboard(false);
		}
	}

	private List getCandidateTargetSpaces()
	{
		TreeMap lruTreeMap = new TreeMap();
		List candidateTargetSpaceList = new ArrayList();
		Set targetSpaceSet = null;

		try
		{
			acquireBlackboardReadLock();
			targetSpaceSet = new HashSet(getTargetSpaceMap().values());
		}
		finally
		{
			releaseBlackboardReadLock();
		}

		Iterator targetSpaces = targetSpaceSet.iterator();

		while (targetSpaces.hasNext() == true)
		{
			TargetSpace targetSpace = (TargetSpace) targetSpaces.next();

			if (targetSpace != null && targetSpace.getLastUsedTimestamp() != 0l)
			{
				lruTreeMap.put(new Long(targetSpace.getLastUsedTimestamp() * -1l), targetSpace);
			}
		}

		candidateTargetSpaceList = new ArrayList();
		candidateTargetSpaceList.addAll(lruTreeMap.values());

		int candidateListSize = (int) Math.floor(candidateTargetSpaceList.size() * 0.20);

		return candidateTargetSpaceList.subList(0, candidateListSize);
	}

	protected void retireTargetSpace(final TargetSpace _targetSpace)
	{
		getPersistenceExecutor().execute(new Runnable() {
			public void run()
			{
				if (logger.isDebugEnabled() == true)
				{
					logger.debug("x");
				}

				if (logger.isDebugEnabled() == true)
				{
					logger.debug("Target space about to be retired.");
				}

				guardTargetSpace(_targetSpace.getWorkspaceIdentifier());

				if (logger.isDebugEnabled() == true)
				{
					logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " for retiring the guard was obtained.");
				}

				_targetSpace.setRetired();
				_targetSpace.setPersisted();
				_targetSpace.setRetireDate(new Date());

				TargetSpace targetSpace = _targetSpace.prepareForRetirement();

				try
				{
					persistTargetSpace(targetSpace);
				}
				finally
				{
					if (logger.isDebugEnabled() == true)
					{
						logger.debug("For workspace: " + _targetSpace.getWorkspaceIdentifier() + " for retiring the guard was released.");
					}

					releaseTargetSpace(_targetSpace.getWorkspaceIdentifier());
				}
				
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

	private TargetSpace retrieveTargetSpaceFromStore(Object _workspaceIdentifier)
	{
		return getPersister().get(_workspaceIdentifier);
	}

	protected void removeFromBlackboard(TargetSpace _targetSpace, boolean _leaveEntry)
	{
		if (getTargetSpaceMap().keySet().contains(_targetSpace.getWorkspaceIdentifier()) == true)
		{
			if (_leaveEntry == true)
			{
				getTargetSpaceMap().put(_targetSpace.getWorkspaceIdentifier(), null);
			}
			else
			{
				getTargetSpaceMap().remove(_targetSpace.getWorkspaceIdentifier());
			}

			decrementActiveWorkspaceCount();
		}
	}

	protected boolean guardTargetSpace(Object _id, boolean _blockUntilAcquired)
	{
		return getTargetSpaceGuard().acquireLock(_id, _blockUntilAcquired);
	}

	private void guardTargetSpace(Object _id)
	{
		getTargetSpaceGuard().acquireLock(_id, true);
	}

	protected void releaseTargetSpace(Object _id)
	{
		getTargetSpaceGuard().releaseLock(_id);
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
			else if (planArray[i].isDirectory() == false && planArray[i].getName().endsWith(".rb") == true)
			{
				if (logger.isInfoEnabled() == true)
				{
					logger.info("Loading plan: " + planArray[i].getName());
				}
				_workspaceConfiguration.getPlanSet().add(new RubyPlan(planArray[i].getName(), planArray[i].getAbsolutePath()));
			}
		}

		String eventName = extractEventName(_eventPlanDirectory.getName());
		
		getEventToWorkspaceMap().put(createFullEventName(_appName, _workspaceName, eventName), _workspaceConfiguration);
	}

	protected WorkspaceConfiguration configureWorkspace(String _appName, String _workspaceName, java.io.File _eventPlanDirectory)
	{
		WorkspaceConfiguration workspaceConfiguration = new WorkspaceConfiguration();
		workspaceConfiguration.setPlanSet(new java.util.HashSet<Plan>());
		workspaceConfiguration.setDoNotPersistSet(new java.util.HashSet<String>());
		workspaceConfiguration.setAppName(_appName);
		workspaceConfiguration.setWorkspaceName(_workspaceName);

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
			else if (configurationFileArray[i].isDirectory() == false && configurationFileArray[i].getName().endsWith("workspaceConfiguration.rb") == true)
			{
				if (logger.isInfoEnabled() == true)
				{
					logger.info("Loading configuration: " + configurationFileArray[i].getName());
				}
				
				configurator = new RubyConfigurator();
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

	protected void establishBlackboardPlans()
	{
		TargetSpaceTimeoutPlan plan = new TargetSpaceTimeoutPlan();
		plan.setPlanName("blackboard.targetspace.timeout");

		WorkspaceConfiguration workspaceConfiguration = new WorkspaceConfiguration();
		workspaceConfiguration.setPlanSet(new java.util.HashSet<Plan>());
		workspaceConfiguration.setDoNotPersistSet(new java.util.HashSet<String>());

		workspaceConfiguration.getPlanSet().add(plan);

		getEventToWorkspaceMap().put(plan.getPlanName(), workspaceConfiguration);
	}

	protected String extractEventName(String _pathName)
	{
		String pathName = _pathName.replaceAll(java.io.File.pathSeparator + "$", "");
		String[] tokenArray = pathName.split(java.io.File.pathSeparator);

		return tokenArray[tokenArray.length - 1];
	}
}