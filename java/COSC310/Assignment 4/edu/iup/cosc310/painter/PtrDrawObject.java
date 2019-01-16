package edu.iup.cosc310.painter; 

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JPopupMenu;

import com.javera.ui.actions.CopyAction;
import com.javera.ui.actions.CutAction;
import com.javera.ui.actions.DeleteAction;
import com.javera.ui.actions.GroupAction;
import com.javera.ui.actions.UngroupAction;

/**
 * Root class for all draw objects that will be rendered in the painter.
 * 
 * Defines the location and selected properties. Also defines the signaiture of
 * methods for general questions that the painter or other objects may use.
 * These methods are typically overridden in subclasses.
 * 
 * Note: while this class retains the location information, it is the subclass
 * that determines the meaning of the location (i.e. is the top-left coordinate,
 * is the center coodinate, etc.)
 * 
 * @author dtsmith
 */
public class PtrDrawObject implements Lineable, Colorable, Serializable {
	protected transient PtrEditor editor;
	private int x;
	private int y;
	private boolean selected;
	private Color lineColor;
	private Color color;
	private int lineWidth;

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		editor = PtrEditor.getEditor();
	}

	/**
	 * Base constructor - this will always get called.
	 * 
	 * @param editor
	 *            the editor that may hold the PtrDrawObject
	 */
	public PtrDrawObject(PtrEditor editor) {
		// Call init. Init is a hook that a subclass may override to perform
		// any pre construction initialization
		init(editor);
		this.editor = editor;
	}

	/**
	 * Constructor setting initial location
	 * 
	 * @param editor
	 *            the editor that may hold the PtrDrawObject
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 */
	public PtrDrawObject(PtrEditor edtitor, int x, int y) {
		this(edtitor);
		setX(x);
		setY(y);
	}

	/**
	 * Initialize - this method is a hook that a subclass may override. This enables
	 * a subclass to perform any pre construction initialization.
	 * 
	 */
	public void init(PtrEditor edtitor) {

	}

	/**
	 * Get the x coordinate
	 * 
	 * @return the x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * An edit action to redo/undo set x
	 * 
	 * @author dtsmith
	 *
	 */
	private class SetXEditAction implements EditAction {
		int originalX;
		int newX;

		public SetXEditAction(int originalX, int newX) {
			super();
			this.originalX = originalX;
			this.newX = newX;
		}

		public void redo() {
			PtrDrawObject.this.x = newX;
		}

		public void undo() {
			PtrDrawObject.this.x = originalX;
		}
	}

	/**
	 * Set the x coodinate
	 * 
	 * @param x
	 *            the new x coodinate
	 */
	public void setX(int x) {
		if (x != getX()) {
			editor.executeEditAction(new SetXEditAction(getX(), x));
			return;
		}
	}

	/**
	 * Get the y coordinate
	 * 
	 * @return the y coordinate
	 */
	public int getY() {
		return y;
	}

	/**
	 * An edit action to redo/undo set y
	 * 
	 * @author dtsmith
	 *
	 */
	private class SetYEditAction implements EditAction {
		int originalY;
		int newY;

		public SetYEditAction(int originalY, int newY) {
			super();
			this.originalY = originalY;
			this.newY = newY;
		}

		public void redo() {
			PtrDrawObject.this.y = newY;
		}

		public void undo() {
			PtrDrawObject.this.y = originalY;
		}
	}

	/**
	 * Set the y coodinate
	 * 
	 * @param y
	 *            the new y coodinate
	 */
	public void setY(final int y) {
		if (y != getY()) {
			editor.executeEditAction(new SetYEditAction(getY(), y));
		}
	}

	/**
	 * Set the location (i.e. both x and y coodinates)
	 * 
	 * @param x
	 *            the new x coodinate
	 * @param y
	 *            the new y coodinate
	 */
	public void setLocation(int x, int y) {
		setX(x);
		setY(y);
	}

	/**
	 * Indicated if the object is currently selected
	 * 
	 * @return true if the object is currently selection, otherwise false
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Set whether or not the object is selected
	 * 
	 * @param selected
	 *            use true if the object is to be selected, otherwise false
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Draw the rendering of this object using the supplied graphics object.
	 * 
	 * @param g
	 *            the graphics object on which to render the object
	 */
	public void draw(Graphics g) {
	}

	/**
	 * Indicate if the coordinate is within the bounds of the object.
	 * 
	 * @param x
	 *            the x coordinate to be tested
	 * @param y
	 *            the y coordinate to be tested
	 * @return true if the coordinate is within the bounds of the object.
	 */
	public boolean isOver(int x, int y) {
		return false;
	}

	/**
	 * Indicate the object lies within the bounds of a bounding box.
	 * 
	 * @param x
	 *            the x coordinate of the bounding box
	 * @param y
	 *            the y coordinate of the bounding box
	 * @param width
	 *            of the bounding box
	 * @param height
	 *            of the bounding box
	 * @return true if he object lies within the bounds of a bounding box.
	 */
	public boolean isInside(int x, int y, int width, int height) {
		return false;
	}

	/**
	 * Get the edit action handler for this object. The edit action handler is the
	 * object through which the painter will use to edit the object.
	 * 
	 * @param x
	 *            the x coordinate of the mouse event used to activate the action
	 *            handler
	 * @param y
	 *            the y coordinate of the mouse event used to activate the action
	 *            handler
	 * @return an edit action handler used to edit the object.
	 */
	public PtrEditActionHandler getEditActionHandler(int x, int y) {
		return null;
	}

	/**
	 * Get the pop up menu, if any, for this object. Initially defines cut, copy and
	 * delete.
	 * 
	 * @param x
	 *            the x coordinate of the mouse event used to activate the popup
	 *            menu
	 * @param y
	 *            the y coordinate of the mouse event used to activate the popup
	 *            menu
	 * @return a popup menu for this object. If no popup menu is available, returns
	 *         null.
	 */
	public JPopupMenu getPopupMenu(int x, int y) {
		JPopupMenu menu = new JPopupMenu();

		menu.add(CutAction.getAction());
		menu.add(CopyAction.getAction());
		menu.add(DeleteAction.getAction());
		// menu.add(GroupAction.getAction());
		// menu.add(UngroupAction.getAction());

		return menu;
	}

	// TODO
	private class SetColorEditAction implements EditAction {
		Color originalColor;
		Color newColor;

		public SetColorEditAction(Color original, Color newColor) {
			this.originalColor = original;
			this.newColor = newColor;
		}

		public void redo() {
			PtrDrawObject.this.color = newColor;
		}

		public void undo() {
			PtrDrawObject.this.color = originalColor;
		}

	}

	public void setColor(Color color) {
		if (color != getColor()) {
			editor.executeEditAction(new SetColorEditAction(getColor(), color));
		}
	}

	/**
	 * Get the color of a PtrDrawOject
	 * 
	 * @return the color of a PtrDrawOject
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * object class for a SetLineColor EditAction
	 * 
	 * @author David Kornish
	 *
	 */
	private class SetLineColorEditAction implements EditAction {
		Color originalLineColor;
		Color newLineColor;

		/**
		 * constructor to initialize the new and original colors
		 * 
		 * @param original
		 * @param newColor
		 */
		public SetLineColorEditAction(Color original, Color newColor) {
			this.originalLineColor = original;
			this.newLineColor = newColor;
		}

		public void redo() {
			PtrDrawObject.this.lineColor = newLineColor;
		}

		@Override
		public void undo() {
			PtrDrawObject.this.lineColor = originalLineColor;
		}

	}

	/**
	 * Set the line color of a PtrDrawOject
	 * 
	 * @param color
	 *            the new color
	 */
	public void setLineColor(Color lineColor) {
		if (lineColor != getLineColor()) {
			editor.executeEditAction(new SetLineColorEditAction(getLineColor(), lineColor));
		}
	}

	/**
	 * Get the line color of a PtrDrawOject
	 * 
	 * @return the line color of a PtrDrawOject
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Object class for a SetLineWidth EditAction
	 * 
	 * @author David Kornish
	 *
	 */
	private class SetLineWidthEditAction implements EditAction {
		int originalLineWidth;
		int newLineWidth;

		/**
		 * constructor to initialize the original and new widths
		 * 
		 * @param original
		 * @param newWidth
		 */
		public SetLineWidthEditAction(int original, int newWidth) {
			this.originalLineWidth = original;
			this.newLineWidth = newWidth;
		}

		/**
		 * execute a redo by returning to the original line width
		 */
		public void redo() {
			PtrDrawObject.this.lineWidth = newLineWidth;
		}

		/**
		 * execute an undo by changing to the new line width
		 */
		@Override
		public void undo() {
			PtrDrawObject.this.lineWidth = originalLineWidth;
		}

	}

	/**
	 * Set the line width of a PtrDrawOject
	 * 
	 * @param lineWidth
	 *            the new lineWidth
	 */
	public void setLineWidth(int newLineWidth) {
		if (newLineWidth != getLineWidth()) {
			editor.executeEditAction(new SetLineWidthEditAction(getLineWidth(), newLineWidth));
		}
	}

	/**
	 * Get the line width of a PtrDrawOject
	 * 
	 * @return the line width of a PtrDrawOject
	 */
	public int getLineWidth() {
		return lineWidth;
	}

}