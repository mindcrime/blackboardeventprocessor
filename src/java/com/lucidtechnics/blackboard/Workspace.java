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

public interface Workspace
   extends Intercepter
{
	/**
	 * Returns the name of this workspace.  This was derived from the
	 * event whose arrival caused the creation of this workspace and is
	 * taken from the value of the workspaceName attribute of the
	 * event's {@link Event} annotation.
	 * 
	 * @return the name of this workspace.
	 * @see Event
	 */

	public String getName();

	/**
	 * Returns the name of the application this workspace belongs to.  This was
	 * derived from the event whose arrival caused the creation of this
	 * workspace and is taken from the value of the appName attribute of the
	 * event's {@link Event} annotation.
	 * 
	 * @return the name of application this workspace belongs to.
	 * @see Event
	 */

	public String getAppName();

	/**
	 * Returns the value of the identifier that uniquely identifies this workspace.
	 * This was derived from the event whose arrival caused the creation of this
	 * workspace and is taken from the value of the workspaceIdentifier attribute
	 * of the event's {@link Event} annotation.
	 * 
	 * @return the value of the identifier that uniquely identifies
	 * this workspace.
	 * @see Event
	 */

	public Object getWorkspaceIdentifier();


	/**
	 * Returns the target or event with the given name. In the case of
	 * an event, it retrieves the most recent value of that event.
	 *
	 * @param _targetName the name of the target or event to be retrieved.
	 * @return the target or event identified by _targetName on this workspace.
	 */

	public Object get(String _targetName);


	/**
	 * Returns whether or not this workspace has this target or event.
	 *
	 * @param _targetName the name of the target or event.
	 * @return true iff this workspace has a target with name
	 * _targetName. returns false otherwise.
	 */

	public boolean has(String _targetName);


	/**
	 * Puts an object on the workspace named by _targetName.  Objects
	 * that are not already target's are wrapped by an {@link
	 * Intercepter} object that records changes to the object via its
	 * mutator methods.  The wrapper will be a subtype of this object.
	 * Object's that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the object or event to be put on the workspace.
	 * @param _object the Object to be put on the workspace.
	 */

	public void put(String _targetName, Object _object);


	/**
	 * Puts target on the workspace named by _targetName.
	 * Targets that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the target to be put on the workspace.
	 * @param _target the Target to be put on the workspace.
	 */

	public void put(String _targetName, Target _target);


	/**
	 * Puts a String on the workspace named by _targetName.
	 * Strings that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the String to be put on the workspace.
	 * @param _string the String to be put on the workspace.
	 */

	public void put(String _targetName, String _object);

	/**
	 * Puts a Float on the workspace named by _targetName.
	 * Floats that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Float to be put on the workspace.
	 * @param _float the Float to be put on the workspace.
	 */

	public void put(String _targetName, Float _object);

	/**
	 * Puts a Double on the workspace named by _targetName.
	 * Doubles that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Double to be put on the workspace.
	 * @param _double the Double to be put on the workspace.
	 */

	public void put(String _targetName, Double _object);


	/**
	 * Puts a Long on the workspace named by _targetName.
	 * Longs that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Long to be put on the workspace.
	 * @param _long the Long to be put on the workspace.
	 */

	public void put(String _targetName, Long _object);

	/**
	 * Puts an Integer on the workspace named by _targetName.
	 * Integers that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Integer to be put on the workspace.
	 * @param _integer the Integer to be put on the workspace.
	 */

	public void put(String _targetName, Integer _object);

	/**
	 * Puts a Byte on the workspace named by _targetName.
	 * Bytes that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Byte to be put on the workspace.
	 * @param _byte the Byte to be put on the workspace.
	 */

	public void put(String _targetName, Byte _object);

	/**
	 * Puts a Boolean on the workspace named by _targetName.
	 * Booleans that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Boolean to be put on the workspace.
	 * @param _boolean the Boolean to be put on the workspace.
	 */

	public void put(String _targetName, Boolean _object);

	/**
	 * Puts a Short on the workspace named by _targetName.
	 * Shortss that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Short to be put on the workspace.
	 * @param _short the Short to be put on the workspace.
	 */

	public void put(String _targetName, Short _object);

	/**
	 * Puts a Character on the workspace named by _targetName.
	 * Characters that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the Character to be put on the workspace.
	 * @param _character the Character to be put on the workspace.
	 */

	public void put(String _targetName, Character _object);

	/**
	 * Creates a target with no value on the workspace.  This method
	 * can be used as a simple message passer for plan's.  For instance
	 * if Plan 9 wanted to let other plan's know that it is completed
	 * the target "plan9isCompleted" could be placed on the workspace
	 * using this method.
	 *
	 * Puts a null value on the workspace named by  _targetName.
	 * null values that are placed on the workspace are visible to all
	 * the other plans that are active on in this workspace.
	 *
	 * @param _targetName the name of the null value to be put on the workspace.
	 */

	public void put(String _targetName);

	/**
	 * Convenience method used by plans to access the same method on {@link
	 * Blackboard}
	 * 
	 * @param _object the name of the target to be placed on the workspace.
	 * 
	 * @see Blackboard
	 * @see Event
	 * 
	 */

	public void placeOnBlackboard(Object _object);


	/**
	 * Convenience method used by plans to access the same method on {@link
	 * Blackboard}
	 * 
	 * @param _object the name of the target to be placed on the workspace.
	 * @param _delay the time in milliseconds that must pass before
	 * event appears on workspace.
	 *
	 * @see Blackboard
	 * @see Event
	 * 
	 */

	public void schedulePlaceOnBlackboard(Object _object, long _delay);


	/**
	 * Removes a target from the workspace.
	 *
	 * Puts a null value on the workspace named by  _targetName.
	 * Targets that are removed from the workspace are no longer
	 * visible on the workspace to all the other plans that are
	 * active on in this workspace.  Also the workspace will cease to
	 * record changes to this target via its mutator methods.
	 *
	 * @param _targetName the name of the target to be removed on the workspace.
	 * @return the target removed from the workspace.
	 */

	public Object remove(String _targetName);

	/**
	 * Clears this workspace of all targets and events.
	 */

	public void clear();


	/**
	 * Clears this workspace of the history of changes to all targets
	 * and events.
	 */

	public void clearAllHistory();


	/**
	 * Clears this workspace of the history of changes to all targets
	 * and events of the name _targetName
	 *
	 * @param _targetName the name of the target who's history is to be cleared
	 * from this workspace.
	 */

	public void clearTargetHistory(String _targetName);


	/**
	 * Clears this workspace of the history of changes to all attribute
	 * with the name _attributeName belonging to targets
	 * and events of the name _targetName.
	 * 
	 * @param _targetName the name of the target or event attribute who's history is to be cleared
	 * from this workspace.
	 * 
	 * @param _attributeName the name of the target or event that has aforementioned
	 * attribute.
	 */

	public void clearAttributeHistory(String _targetName, String _attributeName);

	/**
	 * Checks to see if this workspace has any targets.
	 * 
	 * @return true iff this workspace is empty. 
	 */

	public boolean isEmpty();

	/**
	 * Returns whether or not this workspace has this target.
	 *
	 * @param _targetName the name of the target.
	 * @return true iff this workspace has a target with name
	 * _targetName. returns false otherwise.
	 */

	public boolean hasTarget(String _targetName);

	/**
	 * Checks to see if a type of change was applied to a workspace for
	 * a target.  Workspace actions include clearing a workspace, the
	 * addition, update, or removal of a target, the setting of an
	 * attribute, or no change. 
	 *
	 * @param _targetName the name of the target
	 * @param _action the type of change that was applied. See {@link ChangeInfo} 
	 * @return true iff and only if the action was applied to the target
	 */

	public boolean hasTargetAction(String _targetName, int _action);

	/**
	 * Checks to see if a change was applied to a target's
	 * attribute.
	 *
	 * @param _targetName the name of the target
	 * @param _attributeName the name of the target's attribute.
	 * @return true iff and only if the target's attribute was changed.
	 */

	public boolean hasAttributeAction(String _targetName, String _attributeName);


	/**
	 * Returns a list detailing the history of changes to this target.
	 *
	 * @param _targetName the name of the target.
	 * @return a list of ChangeInfo for this target.
	 */
	
	public List getTargetHistory(String _targetName);


	/**
	 * Returns a list detailing the history of changes to this target's
	 * attribute.
	 *
	 * @param _targetName the name of the attribute.
	 * @param _attributeName the name of the target.
	 * 
	 * @return a list of ChangeInfo for this target.
	 */

	public List getAttributeHistory(String _targetName, String _attributeName);


	/**
	 * Returns a list detailing the history of changes to every target
	 * ever placed on this workspace.
	 *
	 * 
	 * @return an array of all ChangeInfo ever recorded for this workspace.
	 */

	public ChangeInfo[] getChangeInfoHistory();

	/**
	 * Returns a string representation of this workspace
	 * 
	 * @return a string representation of this workspace
	 */

	public String toString();

	/**
	 * Returns a Json string representation of this workspace
	 * 
	 * @return a Json string representation of this workspace
	 */

	public String toJson();
}