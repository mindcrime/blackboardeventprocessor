LOGGER.info("Starting Coco The Monkey");

thread1 = new Packages.java.lang.Thread(function run()
{
	for (var i = 0; i < 200; i++)
	{						
		monkey = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Monkey("Coco-1-" + i);
		mango = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Mango("Coco-2-" + i);
		eagle = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Eagle("Coco-3-" + i);
		hunter = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Hunter("Coco-4-" + i);

		WORKSPACE.placeOnBlackboard(eagle);
		WORKSPACE.placeOnBlackboard(mango);
		WORKSPACE.placeOnBlackboard(monkey);
		WORKSPACE.placeOnBlackboard(hunter);
	}
}
);

thread2 = new Packages.java.lang.Thread(function run()
{
	for (var i = 0; i < 200; i++)
	{						
		monkey = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Monkey("Coco-4-" + i);
		mango = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Mango("Coco-1-" + i);
		eagle = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Eagle("Coco-2-" + i);
		hunter = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Hunter("Coco-3-" + i);

		WORKSPACE.placeOnBlackboard(eagle);
		WORKSPACE.placeOnBlackboard(mango);
		WORKSPACE.placeOnBlackboard(monkey);
		WORKSPACE.placeOnBlackboard(hunter);
	}
}
);

thread3 = new Packages.java.lang.Thread(function run()
{
	for (var i = 0; i < 200; i++)
	{						
		monkey = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Monkey("Coco-3-" + i);
		mango = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Mango("Coco-4-" + i);
		eagle = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Eagle("Coco-1-" + i);
		hunter = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Hunter("Coco-2-" + i);

		WORKSPACE.placeOnBlackboard(eagle);
		WORKSPACE.placeOnBlackboard(mango);
		WORKSPACE.placeOnBlackboard(monkey);
		WORKSPACE.placeOnBlackboard(hunter);
	}
}
);

thread4 = new Packages.java.lang.Thread(function run()
{
	for (var i = 0; i < 200; i++)
	{						
		monkey = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Monkey("Coco-2-" + i);
		mango = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Mango("Coco-3-" + i);
		eagle = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Eagle("Coco-4-" + i);
		hunter = new Packages.com.lucidtechnics.blackboard.examples.cocothemonkey.Hunter("Coco-1-" + i);

		WORKSPACE.placeOnBlackboard(eagle);
		WORKSPACE.placeOnBlackboard(mango);
		WORKSPACE.placeOnBlackboard(monkey);
		WORKSPACE.placeOnBlackboard(hunter);
	}
}
);

thread1.start();
thread2.start();
thread3.start();
thread4.start();