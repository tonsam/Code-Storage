import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Ball extends Item
{
    private int imagecount = 1;
    
    private Image[] images;
    
    public Ball(double x, double y, double gravity, double damp)
    {
        super(x, y, gravity, damp);
        
        images = new Image[6];
        
        images[0] = getImage("images\\ball-1.gif");
        images[1] = getImage("images\\ball-2.gif");
        images[2] = getImage("images\\ball-3.gif");
        images[3] = getImage("images\\ball-4.gif");
        images[4] = getImage("images\\ball-5.gif");
        images[5] = getImage("images\\ball-6.gif");
        
        setSize(images[0].getWidth(null), images[0].getHeight(null));
    }
        
    private Image getImage(String name) {
        Object o = getClass().getResource(name);
        return new ImageIcon(getClass().getResource(name)).getImage();
    }

    public void draw(Graphics bufferGraphics) {
        if (imagecount > 29)
            imagecount = 0;
        if (imagecount < 0)
            imagecount = 29;
        
        bufferGraphics.drawImage(images[imagecount / 5], (int) getX(),
                    (int) getY(), null);
        
        if (getVX() < 0.0D && getVX() >= -2D)
            imagecount++;
        if (getVX() < -2D && getVX() >= -4D)
            imagecount = imagecount + 2;
        if (getVX() < -4D)
            imagecount = imagecount + 3;
        if (getVX() > 0.0D && getVX() <= 2D)
            imagecount--;
        if (getVX() > 2D && getVX() <= 4D)
            imagecount = imagecount + 2;
        if (getVX() > 4D)
            imagecount = imagecount + 3;
    }
}