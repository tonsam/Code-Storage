import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Obstacle {
    private Rectangle r;
    private Rectangle ne;
    private Rectangle se;
    private Rectangle nw;
    private Rectangle sw;
    private Rectangle e;
    private Rectangle w;
    private Rectangle n;
    private Rectangle s;

    public Obstacle(int x, int y, int width, int height) {
        r = new Rectangle(x, y, width, height);
        ne = new Rectangle(x + width - 2, y - 2, 4, 4);
        se = new Rectangle(x + width - 2, y + height - 2, 4, 4);
        nw = new Rectangle(x - 2, y - 2, 4, 4);
        sw = new Rectangle(x - 2, y + height - 2, 4, 4);
        e = new Rectangle(x + width - 2, y - 2, 4, height + 4);
        w = new Rectangle(x - 2, y - 2, 4, height + 4);
        n = new Rectangle(x - 2, y - 2, width + 4, 4);
        s = new Rectangle(x - 2, y + height - 2, width + 4, 4);
    }

    private void updateRectangles() {
        ne.setBounds(r.x + r.width - 2, r.y - 2, 4, 4);
        se.setBounds(r.x + r.width - 2, r.y + r.height - 2, 4, 4);
        nw.setBounds(r.x - 2, r.y - 2, 4, 4);
        sw.setBounds(r.x - 2, r.y + r.height - 2, 4, 4);
        e.setBounds(r.x + r.width - 2, r.y - 2, 4, r.height + 4);
        w.setBounds(r.x - 2, r.y - 2, 4, r.height + 4);
        n.setBounds(r.x - 2, r.y - 2, r.width + 4, 4);
        s.setBounds(r.x - 2, r.y + r.height - 2, r.width + 4, 4);
    }

    public void paint(Graphics gr) {
        gr.setColor(Color.lightGray);
        gr.draw3DRect(r.x, r.y, r.width, r.height, true);
    }

    public OverAction getOverAction(int x, int y) {
        if (ne.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.width += evt.getX() - lastEvt.getX();
                    r.height -= evt.getY() - lastEvt.getY();
                    r.y += evt.getY() - lastEvt.getY();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (se.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.width += evt.getX() - lastEvt.getX();
                    r.height += evt.getY() - lastEvt.getY();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (nw.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.x += evt.getX() - lastEvt.getX();
                    r.y += evt.getY() - lastEvt.getY();
                    r.width -= evt.getX() - lastEvt.getX();
                    r.height -= evt.getY() - lastEvt.getY();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (sw.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.width -= evt.getX() - lastEvt.getX();
                    r.height += evt.getY() - lastEvt.getY();
                    r.x += evt.getX() - lastEvt.getX();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (n.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.height += evt.getY() - lastEvt.getY();
                    r.y += evt.getY() - lastEvt.getY();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (s.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.height += evt.getY() - lastEvt.getY();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (e.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.width += evt.getX() - lastEvt.getX();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (w.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.width -= evt.getX() - lastEvt.getX();
                    r.x += evt.getX() - lastEvt.getX();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        if (r.contains(x, y)) {
            return new OverAction() {
                MouseEvent lastEvt;

                public Cursor getCursor() {
                    return Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
                }

                public void mousePressed(MouseEvent evt) {
                    lastEvt = evt;
                }

                public void mouseDragged(MouseEvent evt) {
                    r.x += evt.getX() - lastEvt.getX();
                    r.y += evt.getY() - lastEvt.getY();
                    lastEvt = evt;
                    updateRectangles();
                }
            };
        }

        return null;
    }

    public Rectangle getBounds() {
        return r;
    }
}