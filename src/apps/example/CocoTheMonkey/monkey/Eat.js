if (WORKSPACE.has("example.CocoTheMonkey.mango") === true && WORKSPACE.has("example.CocoTheMonkey.monkey") === true)
{	
	var fruit = WORKSPACE.get("example.CocoTheMonkey.mango");
	var monkey = WORKSPACE.get("example.CocoTheMonkey.monkey");

	fruit.eaten = true;
	monkey.sleeping = false;
	monkey.playing = false;
	monkey.eating = true;

	WORKSPACE.put("example.CocoTheMonkey.eaten");

	PLAN_CONTEXT.isFinished = true;
}