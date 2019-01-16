package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * PtrPanel in the panel on which the draw objects will be rendered.
 * 
 * @author David T. Smith
 */
public class PtrPanel extends JPanel {
	private PtrEditor editor;           // Reference to the editor
	private PtrTextable  lastTextable;  // Reference to the last textable object used by the text editor
	private JTextArea textArea;         // A JTextArea used to edit text

	/**
	 * Constructor
	 * 
	 * @param editor reference to the editor that contains this PtrPanel.
	 */
	public PtrPanel(PtrEditor editor) {
		this.editor = editor;
		this.setLayout(null);
		
		this.textArea = new JTextArea();
		this.textArea.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				if (!e.isTemporary()) {
					lastTextable.setText(textArea.getText());
					remove(textArea);					
				}				
			}	
		});
		
		this.textArea.getDocument().addDocumentListener(new DocumentListener() {
			private void recomputeBounds() {
			    textArea.setSize(textArea.getPreferredSize());
			}

			public void changedUpdate(DocumentEvent e) {
				recomputeBounds();				
			}

			public void insertUpdate(DocumentEvent e) {
				recomputeBounds();				
			}

			public void removeUpdate(DocumentEvent e) {
				recomputeBounds();				
			}			
		});
	}

	/**
	 * Activate a text area on the painter panel to enable a specific textable item to be edited.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param text
	 */
	public void activateTextEditor(int x, int y, int width, int height, PtrTextable textable) {
		lastTextable = textable;
		textArea.setText(textable.getText());
		textArea.setBounds(x, y, width, height);
		add(textArea);
		textArea.requestFocus();
	}

	/**
	 * Override of the panels paintComponent method.  This override will invoke the draw method of
	 * each of the draw objects defined in the editor.
	 */
	public void paintComponent(Graphics g) {
	    // Clear the entire background
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		// Now draw each object
		for (Iterator iter = editor.getDrawObjects().iterator(); iter.hasNext();) {
			PtrDrawObject object = (PtrDrawObject) iter.next();

			object.draw(g);
		}
	}
}