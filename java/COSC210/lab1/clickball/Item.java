import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Item
{
    private double x;
    private double y;
    
    // Velocity settings
    private double vx;
    private double vy;

    // forces
    private double gravity;
    private double damp;
    
    private int imagecount = 1;
    
    private Image[] images;
    private int width;
    private int height;
    
    public Item(double x, double y, double gravity, double damp)
    {
        this.x = x;
        this.y = y;
        this.gravity = gravity;
        this.damp = damp;        
    }
    
    
    public void move()
    {
        setXY(getX() + vx, getY() + vy);
        vy = vy + gravity;
    }
    
    public void setVelocity(double vx, double vy)
    {
        this.vx = vx;
        this.vy = vy;
    }

    public double getVY()
    {
        return vy;
    }

    public double getVX()
    {
        return vx;
    }

    public void setXY(double x, double y)
    {
        this.y = y;
        this.x = x;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
    
    public double getDamp() {
        return damp;
    }

    public double getGravity() {
        return gravity;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    private Image getImage(String name) {
        Object o = getClass().getResource(name);
        return new ImageIcon(getClass().getResource(name)).getImage();
    }

    public void draw(Graphics bufferGraphics) {
    }

    public boolean checkBounds(Dimension size) {
        boolean bounced = false;
        
        if (getY() > size.getHeight() && getVY() > 0.0D) {
            setVelocity(getVX(), -getVY()
                    * damp);
            bounced = true;
        }
        if (getY() <= 0.0D && getVY() < 0.0D)
            setVelocity(getVX(), -getVY());
        
        if (getX() <= 0.0D
                || getX() >= (double) (size.width - this.width))
            setVelocity(-getVX(), getVY());
        
        return bounced;
    }


    public boolean checkClick(int mouseX, int mouseY) {
        if (mouseX < getX() || mouseX >= getX() + width || mouseY < getY() || mouseY >= getY() + height) {
            return false;
        }
        
        int vx = 0;
        int vy = 0;
        
        
        if (mouseX >= (int) getX()
                && mouseX < (int) getX() + width / 7) {
            vx = 8;
        } else if (mouseX >= (int) getX() + width / 7
                && mouseX < (int) getX() + (2 * width) / 7) {
            vx = 4;
        } else if (mouseX >= (int) getX() + (width * 2) / 7
                && mouseX < (int) getX() + (width * 3) / 7) {
            vx = 2;
        } else if (mouseX >= (int) getX() + (width * 3) / 7
                && mouseX < (int) getX() + (width * 4) / 7) {
            vx = 0;
        } else if (mouseX >= (int) getX() + (width * 4) / 7
                && mouseX < (int) getX() + (width * 5) / 7) {
            vx = -2;
        } else if (mouseX >= (int) getX() + (width * 5) / 7
                && mouseX < (int) getX() + (width * 6) / 7) {
            vx = -4;
        } else if (mouseX >= (int) getX() + (width * 6) / 7
                && mouseX <= (int) getX() + width) {
            vx = -8;
        }
        
        if (mouseY >= (int) getY()
                && mouseY < (int) getY() + height / 3) {
            vy = -17;
        } else if (mouseY >= (int) getY() + height / 3
                && mouseY < (int) getY() + (height * 2) / 3) {
            vy = -10;
        } else if (mouseY >= (int) getY() + (width * 2) / 3
                && mouseY <= (int) getY() + width) {
            vy = -6;
        }
        
        setVelocity(vx, vy);
        return true;
    }
}