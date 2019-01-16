
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class BouncingBalls extends JPanel implements Runnable,
        MouseMotionListener, MouseListener {
    Thread runner;

    Image buffer;
    Graphics bufferGraphics;

    ArrayList collideBalls = new ArrayList();

    ArrayList obstacles = new ArrayList();

    boolean intro = true;

    OverAction foundAction = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Balls");
        BouncingBalls clickBall = new BouncingBalls();
        frame.getContentPane().add(clickBall);
        frame.setSize(500, 500);
        frame.setLocation(50, 50);
        frame.show();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public BouncingBalls() {       
        //our balls have different start coordinates, increment values
        //(speed, direction) and colors
        collideBalls.add(new CollideBall(50, 20, 1.5, 2.0, Color.orange, 12));
        collideBalls.add(new CollideBall(60, 210, 2.0, -3.0, Color.red, 12));
        collideBalls.add(new CollideBall(15, 70, -2.0, -2.5, Color.pink, 12));
        collideBalls.add(new CollideBall(150, 30, -2.7, -2.0, Color.cyan, 12));
        collideBalls.add(new CollideBall(210, 30, 2.2, -3.5, Color.magenta, 12));
        collideBalls.add(new CollideBall(360, 170, 2.2, -1.5, Color.yellow, 12));
        collideBalls.add(new CollideBall(210, 180, -1.2, -2.5, Color.blue, 12));
        collideBalls.add(new CollideBall(330, 30, -2.2, -1.8, Color.green, 12));
        collideBalls.add(new CollideBall(180, 220, -2.2, -1.8, Color.black, 12));
        collideBalls.add(new CollideBall(330, 130, -2.2, -1.8, Color.gray, 12));

        // Add any obstacles
        
        obstacles.add(new Obstacle(150, 80, 130, 90));

        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                createDoubleBuffer();
            }
        });
        
        new Thread(this).start();
     }

    private void createDoubleBuffer() {
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
        }
        
        buffer = createImage(getSize().width, getSize().height);
        bufferGraphics = buffer.getGraphics();

    }
    
    public void run() {
        while (true) {
            Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

            try {
                Thread.sleep(15);
            } catch (Exception e) {
            }

            //move our balls around
            for (Iterator iter = collideBalls.iterator(); iter.hasNext();) {
                CollideBall collideBall = (CollideBall) iter.next();
                collideBall.move(getBounds(), obstacles);
            }

            handleCollision();

            repaint();
        }
    }
    private void handleCollision() {
        //we iterate through all the balls, checking for collision
        for (Iterator iter1 = collideBalls.iterator(); iter1.hasNext();) {
            CollideBall ball1 = (CollideBall) iter1.next();
            for (Iterator iter2 = collideBalls.iterator(); iter2.hasNext();) {
                CollideBall ball2 = (CollideBall) iter2.next();

                if (ball1 != ball2) {
                    if (collide(ball1, ball2)) {
                        ball1.hit(ball2);
                        ball2.hit(ball1);
                    }
                }
            }
        }
    }

    private boolean collide(CollideBall b1, CollideBall b2) {
        double wx = b1.getCenterX() - b2.getCenterX();
        double wy = b1.getCenterY() - b2.getCenterY();

        //we calculate the distance between the centers two
        //colliding balls (theorem of Pythagoras)
        double distance = Math.sqrt(wx * wx + wy * wy);

        if (distance < b1.getDiameter())
            return true;

        return false;
    }

    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        if (bufferGraphics == null) {
            createDoubleBuffer();
        }

        bufferGraphics.setColor(Color.lightGray);
        bufferGraphics.fillRect(0, 0, getSize().width, getSize().height);

        bufferGraphics.draw3DRect(5, 5, getSize().width - 10, getSize().height - 10,
                false);

        //paint the obstacles rectangle
        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            Obstacle obstacle = (Obstacle) iter.next();
            obstacle.paint(bufferGraphics);
        }

        //paint our balls
        for (Iterator iter = collideBalls.iterator(); iter.hasNext();) {
            CollideBall ball = (CollideBall) iter.next();
            ball.paint(bufferGraphics);
        }

        if (intro) {
            bufferGraphics.setColor(Color.red);

            bufferGraphics.setFont(new Font("Helvetica", Font.PLAIN, 12));
            bufferGraphics.drawString("You can move and resize the rectangles!", 20,
                    30);
        }

        g.drawImage(buffer, 0, 0, this);
    }

    public void mouseDragged(MouseEvent e) {
        if (foundAction != null) {
            foundAction.mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            Obstacle obsticle = (Obstacle) iter.next();

            OverAction overAction = obsticle.getOverAction(x, y);

            if (overAction != null) {
                setCursor(overAction.getCursor());
                return;
            }
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            Obstacle obsticle = (Obstacle) iter.next();

            OverAction overAction = obsticle.getOverAction(x, y);

            if (overAction != null) {
                overAction.mousePressed(e);
                foundAction = overAction;
                return;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        foundAction = null;
    }
}

