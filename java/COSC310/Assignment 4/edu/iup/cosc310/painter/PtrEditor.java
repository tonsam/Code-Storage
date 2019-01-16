package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileFilter;

import com.javera.ui.IconManager;
import com.javera.ui.actions.CopyAction;
import com.javera.ui.actions.Copyable;
import com.javera.ui.actions.CutAction;
import com.javera.ui.actions.Cutable;
import com.javera.ui.actions.DeleteAction;
import com.javera.ui.actions.Deleteable;
import com.javera.ui.actions.Exitable;
import com.javera.ui.actions.Groupable;
import com.javera.ui.actions.NewAction;
import com.javera.ui.actions.Newable;
import com.javera.ui.actions.OpenAction;
import com.javera.ui.actions.Openable;
import com.javera.ui.actions.PasteAction;
import com.javera.ui.actions.RedoAction;
import com.javera.ui.actions.Redoable;
import com.javera.ui.actions.SaveAction;
import com.javera.ui.actions.SaveAsable;
import com.javera.ui.actions.Saveable;
import com.javera.ui.actions.UndoAction;
import com.javera.ui.actions.Undoable;
import com.javera.ui.actions.Ungroupable;
import com.javera.ui.layout.JvBoxLayout;

import edu.iup.cosc310.painter.PtrDrawObject.*;
import edu.iup.cosc310.util.LinkedStack;

/**
 * PtrEditor is the main frame for the painter application.
 * 
 * PtrEditor will processes all mouse and keyboard events and forward them to
 * the appropriate paint object depending on the context.
 * 
 * PtrEditor also provides a number of service methods the the paint objects can
 * use access and manipulate other objects.
 * 
 * @author David T. Smith, David Kornish
 * 
 */
public class PtrEditor extends JFrame implements MouseListener, MouseMotionListener, KeyListener, Undoable, Redoable,
		Groupable, Ungroupable, Deleteable, Cutable, Copyable, Openable, Saveable, SaveAsable, Newable, Exitable {
	private PtrPanel painterPanel; // Painter panel where paint objects will be
									// rendered
	private PtrEditActionHandler editActionHandler; // Edit action handler to
													// which events will be
													// forwarded
	private PtrDrawTool defaultTool; // The default toolbar tool
	private PtrToolGroup toolGroup; // Group to provide exclusive selection of
									// toolbar tools

	private ArrayList<PtrDrawObject> drawObjects; // The paint objects themselves.
	private String fileName;
	private Color color = Color.white;
	private Color lineColor = Color.black;
	private int lineWidth = 1;
	private Font font = getFont();

	private static PtrEditor editor;

	private LinkedStack<EditAction> undoStack;
	private LinkedStack<EditAction> redoStack;

	public static PtrEditor getEditor() {
		return editor;
	}

	/**
	 * Main line - just creates and displays a PtrEditor
	 * 
	 * @param args
	 *            - not used
	 */
	static public void main(String[] args) {
		new PtrEditor().setVisible(true);
	}

	/**
	 * Default constructor
	 * 
	 * Builds the painter frame
	 * 
	 */
	public PtrEditor() {
		editor = this;

		this.undoStack = new LinkedStack<EditAction>();
		this.redoStack = new LinkedStack<EditAction>();

		// create an empty list of paint objects
		drawObjects = new ArrayList();

		// create the menu bar
		JMenuBar menuBar = new JMenuBar();

		menuBar.add(new PtrFileMenu());
		menuBar.add(new PtrEditMenu());
		menuBar.add(new PtrOptionsMenu());
		menuBar.add(new PtrHelpMenu());

		// create the tool bar
		JToolBar toolBar = new JToolBar();
		toolBar.add(NewAction.getAction());
		toolBar.add(OpenAction.getAction());
		toolBar.add(SaveAction.getAction());

		toolBar.addSeparator();

		toolBar.add(CutAction.getAction());
		toolBar.add(CopyAction.getAction());
		toolBar.add(PasteAction.getAction());

		toolBar.addSeparator();

		toolBar.add(UndoAction.getAction());
		toolBar.add(RedoAction.getAction());

		toolBar.addSeparator();

		toolGroup = new PtrToolGroup();

		toolBar.add(toolGroup.add(defaultTool = new PtrDrawSelectionTool()));
		toolBar.add(toolGroup.add(new PtrDrawTextTool()));
		toolBar.add(toolGroup.add(new PtrDrawLineTool()));
		toolBar.add(toolGroup.add(new PtrDrawBoxTool()));
		toolBar.add(toolGroup.add(new PtrDrawOvalTool()));
		toolBar.add(toolGroup.add(new PtrDrawImageTool()));

		toolBar.addSeparator();

		toolBar.add(DeleteAction.getAction());

		selectDefaultTool();

		JPanel attrBar = new JPanel(new JvBoxLayout(JvBoxLayout.X_AXIS, 0, 0, 0, 0, 0));
		attrBar.add(new JLabel("Line color "));
		final JButton lineColorButton = new JButton();
		lineColorButton.setBackground(lineColor);
		lineColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyLineColor();
			}
		});
		attrBar.add(lineColorButton);
		JButton chooseLineColor = new JButton(IconManager.getIcon("DownArrow.gif"));
		chooseLineColor.setPreferredSize(new Dimension(20, chooseLineColor.getHeight()));
		chooseLineColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(PtrEditor.this, "Select Line Color", lineColor);
				if (newColor != null) {
					lineColor = newColor;
					lineColorButton.setBackground(lineColor);
					applyLineColor();
				}
			}
		});
		attrBar.add(chooseLineColor);
		attrBar.add(new JLabel("  Line size "));
		final JComboBox lineWidthCombo = new JComboBox();
		lineWidthCombo.addItem(0);
		lineWidthCombo.addItem(1);
		lineWidthCombo.addItem(2);
		lineWidthCombo.addItem(4);
		lineWidthCombo.addItem(8);
		lineWidthCombo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				lineWidth = (Integer) lineWidthCombo.getSelectedItem();
				applyLineWidth();
			}
		});
		lineWidthCombo.setSelectedItem(lineWidth);
		JPanel t = new JPanel(new JvBoxLayout(JvBoxLayout.Y_AXIS, 0, 0, 0, 0, 0));
		t.add(lineWidthCombo);
		attrBar.add(lineWidthCombo);
		attrBar.add(new JLabel("  Background "));
		final JButton colorButton = new JButton();
		colorButton.setBackground(color);
		colorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				applyColor();
			}
		});
		attrBar.add(colorButton);
		JButton chooseColor = new JButton(IconManager.getIcon("DownArrow.gif"));
		chooseColor.setPreferredSize(new Dimension(20, colorButton.getHeight()));
		chooseColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color newColor = JColorChooser.showDialog(PtrEditor.this, "Select Background Color", color);
				if (newColor != null) {
					color = newColor;
					colorButton.setBackground(color);
					applyColor();
				}
			}
		});
		attrBar.add(chooseColor);

		// create the paint panel where the paint objects will be rendered
		painterPanel = new PtrPanel(this);

		painterPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		// set all paint panel events to be processed by the editor
		painterPanel.setFocusable(true);
		painterPanel.addMouseListener(this);
		painterPanel.addMouseMotionListener(this);
		painterPanel.addKeyListener(this);

		// Add the menubar, toolbar and painter panel to the frame
		setJMenuBar(menuBar);

		getContentPane().setLayout(new JvBoxLayout(JvBoxLayout.Y_AXIS));
		getContentPane().add(toolBar);
		getContentPane().add(attrBar);
		getContentPane().add(painterPanel, new Double(1.0));

		// Finishing touches, title, size and location
		setSize(500, 400);
		setLocation(200, 50);
		setTitle("Painter");

		// Make sure the process stops when use closes the frame via the title
		// bar close button
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exit(null);
			}
		});
	}

	/**
	 * Activate a text area on the painter panel to enable a specific textable item
	 * to be edited.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param text
	 */
	public void activateTextEditor(int x, int y, int width, int height, PtrTextable text) {
		painterPanel.activateTextEditor(x, y, width, height, text);
	}

	/**
	 * Get the current list of draw objects.
	 * 
	 * @return the current list of draw objects.
	 */
	public List getDrawObjects() {
		return drawObjects;
	}

	/**
	 * An edit action to redo/undo add draw object
	 * 
	 * @author dtsmith
	 *
	 */
	private class AddDrawObjectEditAction implements EditAction {
		PtrDrawObject object;

		public AddDrawObjectEditAction(PtrDrawObject object) {
			this.object = object;
		}

		public void redo() {
			drawObjects.add(object);
		}

		@Override
		public void undo() {
			drawObjects.remove(object);
		}
	}

	/**
	 * Add a draw object to the list of draw objects. All draw objects will be
	 * rendered on the painter panel
	 * 
	 * @param object
	 *            the draw object to be added
	 */
	public void addDrawObject(PtrDrawObject object) {
		executeEditAction(new AddDrawObjectEditAction(object));

		repaint();
	}

	/**
	 * Add a draw object to the list of draw objects W/O support for Undo. All draw
	 * objects will be rendered on the painter panel
	 * 
	 * This method should only be used in special cases where undo not supporte
	 * 
	 * @param object
	 *            the draw object to be added
	 */
	public void _addDrawObject(PtrDrawObject object) {
		drawObjects.add(object);
		repaint();
	}

	public class RemoveDrawObjectEditAction implements EditAction {
		PtrDrawObject object;

		public RemoveDrawObjectEditAction(PtrDrawObject original) {
			this.object = original;
		}

		public void redo() {
			drawObjects.remove(object);
		}

		@Override
		public void undo() {
			drawObjects.add(object);
		}

	}

	/**
	 * Remove a draw object to the list of draw objects.
	 * 
	 * @param object
	 *            the draw object to be removed
	 */
	public void removeDrawObject(PtrDrawObject object) {
		// TODO need to create an EditAction, be careful to have the undo
		// reinsert the removed object at the correct location

		editor.executeEditAction(new RemoveDrawObjectEditAction(object));

		repaint();
	}

	/**
	 * Revmove draw object from the list of draw objects W/O support for Undo. All
	 * draw objects will be rendered on the painter panel
	 * 
	 * This method should only be used in special cases where undo not supported
	 * 
	 * @param object
	 *            the draw object to be removed
	 */
	public void _removeDrawObject(PtrDrawObject object) {
		drawObjects.remove(object);
		repaint();
	}

	/**
	 * Get the object at a given coordinate
	 * 
	 * @param x
	 *            the x coordinate
	 * @param y
	 *            the y coordinate
	 * @return the object at a given coordinate. If the is no object at the
	 *         coordinate, returns null.
	 */
	public PtrDrawObject getOver(int x, int y) {
		PtrDrawObject over = null;

		for (PtrDrawObject drawObject : drawObjects) {

			if (drawObject.isOver(x, y)) {
				over = drawObject;
			}
		}

		return over;
	}

	/**
	 * Selects all objects in a bounding box
	 * 
	 * @param x
	 *            the x coordinate of the bounding box
	 * @param y
	 *            the y coordinate of the bounding box
	 * @param width
	 *            of the bounding box
	 * @param height
	 *            of the bounding box
	 */
	public void selectObjects(int x, int y, int width, int height) {
		for (PtrDrawObject drawObject : drawObjects) {
			if (drawObject.isInside(x, y, width, height)) {
				drawObject.setSelected(true);
			}
		}

		repaint();
	}

	public void selectDefaultTool() {
		defaultTool.setSelected(true);
	}

	/**
	 * Clear the current selection. Any selected objects will be unselected.
	 */
	public void clearSelection() {
		for (PtrDrawObject drawObject : drawObjects) {
			if (drawObject.isSelected()) {
				drawObject.setSelected(false);
			}
		}
	}

	/**
	 * Get the current editor action handler that will process mouse and keyboard
	 * events.
	 * 
	 * @return the current editor action handler that will process mouse and
	 *         keyboard events.
	 */
	public PtrEditActionHandler getEditorAction() {
		return editActionHandler;
	}

	/**
	 * Set the current editor action handler that will process mouse and keyboard
	 * events.
	 * 
	 * When set to null, the selected tool will process mouse pressed events
	 * 
	 * @param editActionHandler
	 *            the new editor action handler that will process mouse and keyboard
	 *            events.
	 */
	public void setEditActionHandler(PtrEditActionHandler editActionHandler) {
		if (editActionHandler == null) {
			addUndoMarker();
		}

		this.editActionHandler = editActionHandler;
	}

	/**
	 * Show a popup menu at a given x, y coordinate on the painter panel
	 * 
	 * @param popupMenu
	 *            the popup menu to be displayed
	 * @param x
	 *            the x coordinate relative to the painter panel
	 * @param y
	 *            the y coordinate relative to the painter panel
	 */
	public void showPopupMenu(JPopupMenu popupMenu, int x, int y) {
		popupMenu.show(painterPanel, x, y);
	}

	/**
	 * Invoked when the mouse button has been clicked (pressed and released) on the
	 * paint panel.
	 * 
	 * currently not used
	 */
	public void mouseClicked(MouseEvent e) {
	}

	/**
	 * Invoked when a mouse button has been pressed on the paint panel.
	 */
	public void mousePressed(MouseEvent e) {
		if (editActionHandler != null) {
			editActionHandler.mousePressed(this, e);
		} else {
			painterPanel.requestFocus();
			toolGroup.getSelection().activateTool(this, e);
		}
	}

	/**
	 * Invoked when a mouse button has been released on the paint panel.
	 */
	public void mouseReleased(MouseEvent e) {
		if (editActionHandler != null) {
			editActionHandler.mouseReleased(this, e);
			repaint();
		}
	}

	/**
	 * Invoked when the mouse enters the paint panel.
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/**
	 * Invoked when the mouse exits the paint panel.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/**
	 * Invoked when the mouse is dragged on the paint panel.
	 */
	public void mouseDragged(MouseEvent e) {
		if (editActionHandler != null) {
			editActionHandler.mouseDrag(this, e);
			repaint();
		}
	}

	/**
	 * Invoked when the mouse cursor has been moved over the paint panel but no
	 * buttons have been pushed.
	 */
	public void mouseMoved(MouseEvent e) {
	}

	/**
	 * Invoked when a key on the keyboard has been pressed
	 */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Invoked when a key on the keyboard has been released
	 */
	public void keyReleased(KeyEvent e) {
	}

	/**
	 * Invoked when a key on the keyboard has been typed
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
	 * Delete the selected draw objects
	 */
	public void delete(ActionEvent evt) {
		List<PtrDrawObject> removeList = new ArrayList<PtrDrawObject>();

		for (PtrDrawObject drawObject : drawObjects) {
			if (drawObject.isSelected()) {
				removeList.add(drawObject);
			}
		}

		for (PtrDrawObject drawobject : removeList) {
			removeDrawObject(drawobject);
		}

		addUndoMarker();

		repaint();
	}

	/**
	 * Indicate if the delete button should be enabled for the current selection
	 * state.
	 * 
	 * @return true if the delete button should be enabled for the current selection
	 *         state, otherwise false.
	 */
	public boolean isDeleteable(EventObject evt) {
		for (PtrDrawObject drawObject : drawObjects) {

			if (drawObject.isSelected()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Cut the selected draw objects. The cut objects are placed on the system
	 * clipboard.
	 */
	public void cut(ActionEvent evt) {
		copy(evt);
		delete(null);
	}

	/**
	 * Indicate if the cut button should be enabled for the current selection state.
	 * 
	 * @return true if the cut button should be enabled for the current selection
	 *         state, otherwise false.
	 */
	public boolean isCutable(EventObject evt) {
		return isDeleteable(evt);
	}

	/**
	 * Copy the selected draw objects to the system clipboard.
	 */
	public void copy(ActionEvent evt) {
	}

	/**
	 * Indicate if the copy button should be enabled for the current selection
	 * state.
	 * 
	 * @return true if the copy button should be enabled for the current selection
	 *         state, otherwise false.
	 */
	public boolean isCopyable(EventObject evt) {
		return isDeleteable(evt);
	}

	/**
	 * Paste any draw objects on the system clipboard into the list of draw objects.
	 */
	public void paste() {
	}

	/**
	 * Open
	 * 
	 * @see com.javera.ui.actions.Openable#open(java.awt.event.ActionEvent)
	 */
	public void open(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".ptr");
			}

			public String getDescription() {
				return "Painter Files";
			}
		});

		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
				drawObjects = (ArrayList) in.readObject();
				fileName = fileChooser.getSelectedFile().getAbsolutePath();
				in.close();

				// TODO clear the undo and redo stacks
				undoStack.clear();
				redoStack.clear();

				repaint();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.javera.ui.actions.Openable#isOpenable(java.util.EventObject)
	 */
	public boolean isOpenable(EventObject evt) {
		return true;
	}

	public void save(ActionEvent evt) {
		if (fileName == null) {
			saveAs(evt);
			return;
		}

		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(drawObjects);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean isSaveable(EventObject evt) {
		return true;
	}

	public void saveAs(ActionEvent evt) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".ptr");
			}

			public String getDescription() {
				return "Painter Files";
			}
		});

		if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				String path = fileChooser.getSelectedFile().getAbsolutePath();
				if (!path.endsWith(".ptr")) {
					path += ".ptr";
				}
				new FileOutputStream(path).close();
				fileName = path;
				save(evt);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isSaveAsable(EventObject evt) {
		return true;
	}

	public JInternalFrame makeNew(ActionEvent evt) {
		drawObjects.clear();
		repaint();
		return null;
	}

	public boolean isNewable(EventObject evt) {
		return true;
	}

	public void exit(ActionEvent evt) {
		System.exit(0);
	}

	public boolean isExitable(EventObject evt) {
		return true;
	}

	@Override
	public void ungroup(ActionEvent evt) {
		List<PtrDrawGroup> groups = new ArrayList<PtrDrawGroup>();

		for (Iterator iter = getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();
			if (object.isSelected()) {
				if (object instanceof PtrDrawGroup) {
					groups.add((PtrDrawGroup) object);
					iter.remove();
				}
			}
		}

		for (PtrDrawGroup group : groups) {
			for (PtrDrawObject object : group.getGroupedObjects()) {
				object.setX((int) (object.getX() * group.getXScale() + group.getX()));
				object.setY((int) (object.getY() * group.getYScale() + group.getY()));

				if (object instanceof PtrDrawRect) {
					PtrDrawRect rect = (PtrDrawRect) object;
					rect.setWidth((int) (rect.getWidth() * group.getXScale()));
					rect.setHeight((int) (rect.getHeight() * group.getYScale()));
				}

				addDrawObject(object);
				object.setSelected(true);
			}
		}

		repaint();
	}

	@Override
	public boolean isUngroupable(EventObject evt) {
		return true;
	}

	private void applyLineColor() {
		for (Iterator iter = getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();
			if (object.isSelected() && object instanceof Lineable) {
				((Lineable) object).setLineColor(lineColor);
			}
		}

		addUndoMarker();

		repaint();
	}

	private void applyLineWidth() {
		for (Iterator iter = getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();
			if (object.isSelected() && object instanceof Lineable) {
				((Lineable) object).setLineWidth(lineWidth);
			}
		}

		addUndoMarker();

		repaint();
	}

	private void applyColor() {
		for (Iterator iter = getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();
			if (object.isSelected() && object instanceof Lineable) {
				((Colorable) object).setColor(color);
			}
		}
		addUndoMarker();

		repaint();
	}

	public void group(ActionEvent evt) {
		boolean firstObject = true;
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		List<PtrDrawObject> groupedObjects = new ArrayList<PtrDrawObject>();

		for (Iterator iter = getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();
			if (object.isSelected()) {
				int objectX;
				int objectY;
				int objectWidth;
				int objectHeight;
				if (object instanceof PtrDrawRect) {
					PtrDrawRect rect = (PtrDrawRect) object;
					if (rect.getWidth() < 0) {
						rect.setX(rect.getX() + rect.getWidth());
						rect.setWidth(-rect.getWidth());
					}
					if (rect.getHeight() < 0) {
						rect.setY(rect.getY() + rect.getHeight());
						rect.setHeight(-rect.getHeight());
					}
					objectX = rect.getX();
					objectY = rect.getY();
					objectWidth = rect.getWidth();
					objectHeight = rect.getHeight();
				} else {
					objectX = object.getX();
					objectY = object.getY();
					objectWidth = 0;
					objectHeight = 0;
				}
				if (firstObject) {
					x1 = objectX;
					y1 = objectY;
					x2 = objectX + objectWidth;
					y2 = objectY + objectHeight;
					firstObject = false;
				} else {
					if (objectX < x1) {
						x1 = objectX;
					}
					if (objectY < y1) {
						y1 = objectY;
					}
					if (objectX + objectWidth > x2) {
						x2 = objectX + objectWidth;
					}
					if (objectY + objectHeight > y2) {
						y2 = objectY + objectHeight;
					}
				}
			}
		}

		if (firstObject) {
			return;
		}

		for (Iterator iter = getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();
			if (object.isSelected()) {
				object.setX(object.getX() - x1);
				object.setY(object.getY() - y1);
				object.setSelected(false);
				iter.remove();
				groupedObjects.add(object);
			}
		}

		PtrDrawGroup group = new PtrDrawGroup(this, x1, y1, x2 - x1, y2 - y1, groupedObjects);
		this.addDrawObject(group);
		group.setSelected(true);
		repaint();

	}

	@Override
	public boolean isGroupable(EventObject evt) {
		return false;
	}

	public Color getColor() {
		return color;
	}

	public Color getLineColor() {
		return lineColor;
	}

	public int getLineWidth() {
		return lineWidth;
	}

	public Font getFont() {
		return font;
	}

	/**
	 * execute an edit action
	 * 
	 * @param editAction
	 *            action to execute
	 */
	void executeEditAction(EditAction editAction) {
		// TODO in addition to executing the action (calling redo), that action
		// should be pushed to the undo stack and the redo stack should be cleared.

		editAction.redo();
		undoStack.pushItem(editAction);
		redoStack.clear();
	}

	/**
	 * add an undo marker
	 */
	private void addUndoMarker() {
		// TODO add code to push a null delimiter to the undo stack, only do not add
		// a null delimited if the stack is empty or a null delimited is currently
		// on top of the stack

		if (!undoStack.isEmpty() && undoStack.peekItem() != null) {
			undoStack.pushItem(null);
			System.out.println("null undo marker added");
		}
	}

	/**
	 * add a redo marker
	 */
	private void addRedoMarker() {
		// TODO add code to push a null delimiter to the redo stack, only do not add
		// a null delimited if the stack is empty or a null delimited is currently
		// on top of the stack

		if (!redoStack.isEmpty() && redoStack.peekItem() != null) {
			redoStack.pushItem(null);
			System.out.println("null redo marker added");
		}
	}

	/**
	 * method to redo an action
	 */
	public void redo(ActionEvent evt) {
		// TODO add code to perform a redo for the editor, that is
		// pops EditActions from a redo stack, calling redo on each
		//
		// If the top of the stack is a null delimiter then first pop it.
		// Then, while the top of the stack is not a null delimiter, pop
		// the EditAction, execute its redo method, and add it to the
		// undo stack. After popping the EditActions, call addUndoMarker.

		if (redoStack.isEmpty()) {
			System.out.println("The redo stack is empty, you cannot undo now...");
		} else if (redoStack.peekItem() == null) {

			EditAction action = redoStack.popItem();

			while (!redoStack.isEmpty() && redoStack.peekItem() != null) {
				action = redoStack.popItem();
				action.redo();
				undoStack.pushItem(action);
			}
			addUndoMarker();

			repaint();
		}

	}

	public boolean isRedoable(EventObject evt) {
		return true;
	}

	/**
	 * method to undo an action
	 */
	public void undo(ActionEvent evt) {
		// TODO add code to perform an undo, that is
		// pops EditActions from a undo stack, calling undo on each.
		//
		// If the top of the stack is a null delimiter then first pop it.
		// Then, while the top of the stack is not a null delimiter, pop
		// the EditAction, execute its undo method, and add it to the
		// redo stack. After poping the EditActions, call addRedoMarker.

		if (undoStack.isEmpty()) {
			System.out.println("The undo stack is empty, you cannot undo now...");
		} else if (undoStack.peekItem() == null) {

			EditAction action = undoStack.popItem();

			while (!undoStack.isEmpty() && undoStack.peekItem() != null) {
				action = undoStack.popItem();
				action.undo();
				redoStack.pushItem(action);
			}
			addRedoMarker();

			repaint();
		}
	}

	@Override
	public boolean isUndoable(EventObject evt) {
		return true;
	}

}