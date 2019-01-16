package edu.iup.cosc310.painter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.StringTokenizer;

public class PtrDrawText extends PtrDrawRect implements PtrTextable, Fontable {	
	private String text = "";
	private int textWidth;
	private int textHeight;
	private Font font;

	/**
	 * Default constructor
	 * @param editor the editor that may hold the PtrDrawObject
	 */
	public PtrDrawText(PtrEditor editor) {
		super(editor);
	}
	
    /**
     * Constructor
     * 
	 * @param editor the editor that may hold the PtrDrawObject
     * @param x initial x coordinate for the text
     * @param y initial y coordinate for the text
     */
	public PtrDrawText(PtrEditor editor, int x, int y) {
		super(editor, x, y);
		
		// Set the size to -1's, size is thus determined by the
		// size of the text.
		setSize(-1,-1);
	}

	/**
	 * Set the text to be renderend
	 * 
	 * @param text the text to be rendered
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Get the text that is renderend
	 * 
	 * @return text that is rendered
	 */
	public String getText() {
		return text;
	}

	/**
	 * Get the width
	 * 
	 * @return the width
	 */
	public int getWidth() {
	    if (super.getWidth() == -1) {
	        return getTextWidth();
	    } else {
	        return super.getWidth();
	    }
	}
	
	/**
	 * Get the height
	 * 
	 * @return the height
	 */
	public int getHeight() {
	    if (getTextHeight() > super.getHeight()) {
	        return getTextHeight();
	    } else {
	        return super.getHeight();
	    }
	}
	
    /**
     * Get the width based on the current text.
     * 
     * @return the width based on the text.
     */
    public int getTextWidth() {
        return textWidth;
    }
	
    /**
     * Get the height based on the current text.
     * 
     * @return the height based on the text.
     */
    public int getTextHeight() {
        return textHeight;
    }
    
    /**
     * Set the width of the text
     * 
     * @param textWidth the width of the text.
     */
    private void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }
    
    /**
     * Set the height of the text
     * 
     * @param textHeight the height of the text.
     */
    private void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }
 
    
	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	/**
	 * Draw (render) a text onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.setFont(font);

		// break the text into lines
		StringTokenizer st = new StringTokenizer(getText(), "\n");
		
		// initialize a y coordinate to the base line of the text.
		int y = getY() + g.getFontMetrics().getAscent();
		int w = 10;
		
		while (st.hasMoreTokens()) {	
		    String line = st.nextToken();
		    
			int sw = g.getFontMetrics().stringWidth(line);
		    
		    // Must handle word wrapping if the width coordinate has been set
		    if (super.getWidth() != -1 && super.getWidth() < sw) {
		        
		    } else {
			    // Just render a line
				g.drawString(line, getX(), y);
				
				if (sw > w) {
				    w = sw;
				}
		    }
			
			// update the y coordinate
			y += g.getFontMetrics().getHeight();
						
		}

		// Update the text sizes
		setTextHeight(y - (getY() + g.getFontMetrics().getAscent()));
		setTextWidth(w);
		
		drawGrabHandles(g);
	}
}