package edu.iup.cosc310.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

/**
 * A draw object that renders a image.
 * 
 * @author David T. Smith
 */
public class PtrDrawImage extends PtrDrawRect {
	private Image image;
	
    /**
     * Constructor
     * 
	 * @param editor the editor that may hold the PtrDrawObject
     * @param x initial x coordinate for the image
     * @param y initial y coordinate for the image
     */
	public PtrDrawImage(PtrEditor editor, int x, int y, Image image) {
		super(editor, x, y);
		this.image = image;
		setWidth(image.getWidth(null));
		setHeight(image.getHeight(null));
	}

	/**
	 * Draw (render) a image onto the supplied graphics object.
	 * 
	 * @param g the graphics object
	 */
	public void draw(Graphics g) {
	    g.drawImage(image, getX(), getY(), getWidth(), getHeight(), null);

	    g.setColor(getLineColor());
		
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setStroke(new BasicStroke(getLineWidth()));
		
		g2.drawLine(getX(), getY(), getX() + getWidth() - 1, getY());
		g2.drawLine(getX(), getY() + getHeight() - 1, getX() + getWidth() - 1,
				getY() + getHeight() - 1);
		g2.drawLine(getX(), getY(), getX(), getY() + getHeight() - 1);
		g2.drawLine(getX() + getWidth() - 1, getY(), getX() + getWidth() - 1,
				getY() + getHeight() - 1);
		g2.dispose();
		
		if (isSelected()) {
		    drawGrabHandles(g);
		}
	}

}