if $WORKSPACE.has("mango") == true and $WORKSPACE.has("monkey") == true
	fruit = $WORKSPACE.get("mango");
	monkey = $WORKSPACE.get("monkey");

	fruit.setEaten(true);
	monkey.setSleeping(false);
	monkey.setPlaying(false);
	monkey.setEating(true);

	$WORKSPACE.putOnWorkspace("eaten");

	$PLAN_CONTEXT.setIsFinished(true);
end


			