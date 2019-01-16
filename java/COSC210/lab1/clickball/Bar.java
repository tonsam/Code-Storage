import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bar extends Item
{
    private Color color;
    
    public Bar(double x, double y, int width, int height, Color color)
    {
        super(x, y, 0, 1);
        
        setSize(width, height);
        this.color = color;
        
        setVelocity(4, 5);
    }
        
    private Image getImage(String name) {
        Object o = getClass().getResource(name);
        return new ImageIcon(getClass().getResource(name)).getImage();
    }

    public void draw(Graphics bufferGraphics) {
        Color prevColor = bufferGraphics.getColor();
        bufferGraphics.setColor(color);
        bufferGraphics.fillRect((int) getX(),(int) getY(), getWidth(), getHeight());
        bufferGraphics.setColor(prevColor);
    }
    
    public boolean checkClick(int mouseX, int mouseY) {
        if (mouseX < getX() || mouseX >= getX() + getWidth() || mouseY < getY() || mouseY >= getY() + getHeight()) {
            return false;
        }
        
        int vx = 0;
        int vy = 0;
        
        
        if (mouseX >= (int) getX()
                && mouseX < (int) getX() + getWidth() / 7) {
            vx = 8;
        } else if (mouseX >= (int) getX() + getWidth() / 7
                && mouseX < (int) getX() + (2 * getWidth()) / 7) {
            vx = 4;
        } else if (mouseX >= (int) getX() + (getWidth() * 2) / 7
                && mouseX < (int) getX() + (getWidth() * 3) / 7) {
            vx = 2;
        } else if (mouseX >= (int) getX() + (getWidth() * 3) / 7
                && mouseX < (int) getX() + (getWidth() * 4) / 7) {
            vx = 0;
        } else if (mouseX >= (int) getX() + (getWidth() * 4) / 7
                && mouseX < (int) getX() + (getWidth() * 5) / 7) {
            vx = -2;
        } else if (mouseX >= (int) getX() + (getWidth() * 5) / 7
                && mouseX < (int) getX() + (getWidth() * 6) / 7) {
            vx = -4;
        } else if (mouseX >= (int) getX() + (getWidth() * 6) / 7
                && mouseX <= (int) getX() + getWidth()) {
            vx = -8;
        }
        
        if (mouseY >= (int) getY()
                && mouseY < (int) getY() + getHeight() / 3) {
            vy = -10;
        } else if (mouseY >= (int) getY() + getHeight() / 3
                && mouseY < (int) getY() + (getHeight() * 2) / 3) {
            vy = -7;
        } else if (mouseY >= (int) getY() + (getHeight() * 2) / 3
                && mouseY <= (int) getY() + getHeight()) {
            vy = -5;
        }
        
        setVelocity(vx, vy);
        return true;
    }
}