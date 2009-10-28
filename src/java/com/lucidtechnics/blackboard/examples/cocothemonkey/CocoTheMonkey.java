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

package com.lucidtechnics.blackboard.examples.cocothemonkey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lucidtechnics.blackboard.Blackboard;
import com.lucidtechnics.blackboard.examples.cocothemonkey.Monkey;
import com.lucidtechnics.blackboard.examples.cocothemonkey.Fruit;
import com.lucidtechnics.blackboard.examples.cocothemonkey.Mango;
import com.lucidtechnics.blackboard.examples.cocothemonkey.Eagle;

public class CocoTheMonkey
{
	private static Log logger = LogFactory.getLog(CocoTheMonkey.class);

	private boolean shutdown;
	
	public CocoTheMonkey() { shutdown = false; }

	public void shutdown()
	{
		shutdown = true;
	}

	public static void main(String[] _args)
		throws Exception
	{
		try
		{
			final Blackboard blackboard = new Blackboard();

			logger.info("Starting Coco The Monkey");
			System.out.println("Starting Coco The Monkey");

			Thread thread1 = new Thread(new Runnable()
			{
				public void run()
				{
					synchronized(blackboard)
					{
						try { blackboard.wait(); } catch (InterruptedException e) {}
					}

					for (int i = 0; i < 100; i++)
					{						
						Monkey monkey = new Monkey("Coco-1-" + i);
						Mango mango = new Mango("Coco-2-" + i);
						Eagle eagle = new Eagle("Coco-3-" + i);
						Hunter hunter = new Hunter("Coco-5-" + i);

						blackboard.placeOnBlackboard(eagle);
						blackboard.placeOnBlackboard(mango);
						blackboard.placeOnBlackboard(monkey);
						blackboard.placeOnBlackboard(hunter);
					}
				}
			});

			Thread thread2 = new Thread(new Runnable()
			{
				public void run()
				{
					synchronized(blackboard)
					{
						try { blackboard.wait(); } catch (InterruptedException e) {}
					}

					for (int i = 0; i < 100; i++)
					{
						Monkey monkey = new Monkey("Coco-4-" + i);
						Mango mango = new Mango("Coco-1-" + i);
						Eagle eagle = new Eagle("Coco-5-" + i);
						Hunter hunter = new Hunter("Coco-3-" + i);

						blackboard.placeOnBlackboard(eagle);
						blackboard.placeOnBlackboard(mango);
						blackboard.placeOnBlackboard(monkey);
						blackboard.placeOnBlackboard(hunter);
					}
				}
			});

			Thread thread3 = new Thread(new Runnable()
			{
				public void run()
				{
					synchronized(blackboard)
					{
						try { blackboard.wait(); } catch (InterruptedException e) {}
					}

					for (int i = 0; i < 100; i++)
					{
						Monkey monkey = new Monkey("Coco-3-" + i);
						Mango mango = new Mango("Coco-5-" + i);
						Eagle eagle = new Eagle("Coco-4-" + i);
						Hunter hunter = new Hunter("Coco-2-" + i);

						blackboard.placeOnBlackboard(eagle);
						blackboard.placeOnBlackboard(mango);
						blackboard.placeOnBlackboard(monkey);
						blackboard.placeOnBlackboard(hunter);
					}
				}
			});


			Thread thread4 = new Thread(new Runnable()
			{
				public void run()
				{
					synchronized(blackboard)
					{
						try { blackboard.wait(); } catch (InterruptedException e) {}
					}

					for (int i = 0; i < 100; i++)
					{
						Monkey monkey = new Monkey("Coco-2-" + i);
						Mango mango = new Mango("Coco-3-" + i);
						Eagle eagle = new Eagle("Coco-1-" + i);
						Hunter hunter = new Hunter("Coco-4-" + i);

						blackboard.placeOnBlackboard(eagle);
						blackboard.placeOnBlackboard(mango);
						blackboard.placeOnBlackboard(monkey);
						blackboard.placeOnBlackboard(hunter);
					}
				}
			});

			Thread thread5 = new Thread(new Runnable()
			{
				public void run()
				{
					synchronized(blackboard)
					{
						try { blackboard.wait(); } catch (InterruptedException e) {}
					}

					for (int i = 0; i < 100; i++)
					{
						Monkey monkey = new Monkey("Coco-5-" + i);
						Mango mango = new Mango("Coco-4-" + i);
						Eagle eagle = new Eagle("Coco-2-" + i);
						Hunter hunter = new Hunter("Coco-1-" + i);

						blackboard.placeOnBlackboard(eagle);
						blackboard.placeOnBlackboard(mango);
						blackboard.placeOnBlackboard(monkey);
						blackboard.placeOnBlackboard(hunter);
					}
				}
			});

			thread1.start();
			thread2.start();
			
			thread3.start();
			thread4.start();
			thread5.start();

			try
			{
				Thread.currentThread().sleep(1000);
			}
			catch(Throwable t)
			{
			}

			synchronized(blackboard)
			{
				blackboard.notifyAll();
			}
			
			Object object = new Object();

			synchronized(object)
			{
				try { object.wait(); } catch (InterruptedException e) {}
			}
		}
		finally
		{
		}
	}	
}