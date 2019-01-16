import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Iterator;
import java.util.List;

class CollideBall {
    private int diameter = 20;

    //coordinates and value of increment
    private double x, y, xinc, yinc, coll_x, coll_y;

    private boolean collide;

    private Color color;

    //the constructor
    public CollideBall(int x, int y, double xinc, double yinc, Color c, int diameter) {
        this.x = x;
        this.y = y;
        this.xinc = xinc;
        this.yinc = yinc;
        this.color = c;
        this.diameter = diameter;
    }

    public double getCenterX() {
        return x + diameter / 2;
    }

    public double getCenterY() {
        return y + diameter / 2;
    }

    public void move(Rectangle bounds, List obstacles) {
        if (collide) {
            double xvect = coll_x - getCenterX();
            double yvect = coll_y - getCenterY();

            if ((xinc > 0 && xvect > 0) || (xinc < 0 && xvect < 0))
                xinc = -xinc;

            if ((yinc > 0 && yvect > 0) || (yinc < 0 && yvect < 0))
                yinc = -yinc;

            collide = false;
        }

        x += xinc;
        y += yinc;

        //when the ball bumps against a boundary, it bounces off
        if (x < 0 || x > bounds.width - diameter) {
            xinc = -xinc;
            x += xinc;
        }

        if (y < 0 || y > bounds.height - diameter) {
            yinc = -yinc;
            y += yinc;
        }

        //cast ball coordinates to integers
        int x = (int) this.x;
        int y = (int) this.y;

        //bounce off the obstacle
        //left border
        for (Iterator iter = obstacles.iterator(); iter.hasNext();) {
            Obstacle obstacle = (Obstacle) iter.next();
            Rectangle r = obstacle.getBounds();
            if (x > r.x - diameter && x < r.x - diameter + 7 && xinc > 0
                    && y > r.y - diameter && y < r.y + r.height) {
                xinc = -xinc;
                x += xinc;
            }
            //right border
            if (x < r.x + r.width && x > r.x + r.width - 7 && xinc < 0
                    && y > r.y - diameter && y < r.y + r.height) {
                xinc = -xinc;
                x += xinc;
            }
            //upper border
            if (y > r.y - diameter && y < r.y - diameter + 7 && yinc > 0
                    && x > r.x - diameter && x < r.x + r.width) {
                yinc = -yinc;
                y += yinc;
            }
            //bottom border
            if (y < r.y + r.height && y > r.y + r.height - 7 && yinc < 0
                    && x > r.x - diameter && x < r.x + r.width) {
                yinc = -yinc;
                y += yinc;
            }
        }
    }

    public void hit(CollideBall b) {
        if (!collide) {
            coll_x = b.getCenterX();
            coll_y = b.getCenterY();
            collide = true;
        }
    }

    public void paint(Graphics g) {
        g.setColor(color);

        g.fillOval((int) x, (int) y, diameter, diameter);

        g.setColor(Color.white);
        g.drawArc((int) x, (int) y, diameter, diameter, 45, 180);

        g.setColor(Color.darkGray);
        g.drawArc((int) x, (int) y, diameter, diameter, 225, 180);
    }

    public double getDiameter() {
        return diameter;
    }
}
