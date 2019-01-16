package edu.iup.cosc310.painter;

/**
 * An abstact data type to perform (redo) an edit action together
 * with a method effective undo the action.
 * 
 * @author dtsmith
 */
public interface EditAction {
	/**
	 * Perform some edit action
	 */
	public void redo();
	
	/**
	 * Undo the effects of the edit action
	 */
	public void undo();
}
