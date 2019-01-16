
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class ClickBall extends JPanel implements Runnable {
    private int refresh = 15;

    private int count;
    private int high;

    private Graphics bufferGraphics;
    private Image offscreen;

    private ArrayList items = new ArrayList();
    private boolean start = true;

    public ClickBall() {
        items.add(new Ball(250, 10, 0.29999999999999999D, 0.90000000000000002D));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (!start) {
                    for (Iterator iter = items.iterator(); iter.hasNext();) {
                        if (((Item) iter.next()).checkClick(me.getX(), me
                                .getY())) {
                            count++;
                            if (count > high)
                                high = count;
                        }
                    }
                } else {
                    start = false;
                }
            }
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                createDoubleBuffer();
            }
        });

        requestFocus();

        Color bg = new Color(22, 167, 26);
        setBackground(bg);
        setForeground(Color.black);

        Thread et = new Thread(this);
        et.setPriority(10);
        et.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Click Ball");
        ClickBall clickBall = new ClickBall();
        frame.getContentPane().add(clickBall);
        frame.setSize(500, 500);
        frame.setLocation(50, 50);
        frame.show();
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void createDoubleBuffer() {
        if (bufferGraphics != null) {
            bufferGraphics.dispose();
        }

        Dimension dim = getSize();
        offscreen = createImage(dim.width, dim.height);
        bufferGraphics = offscreen.getGraphics();
    }

    public void run() {
        while (offscreen == null) {
            pause(refresh);
        }
        
        do {
            Dimension dim = getSize();
            bufferGraphics.clearRect(0, 0, dim.width, dim.height);

            if (start) {
                for (Iterator iter = items.iterator(); iter.hasNext();) {
                    ((Item) iter.next()).draw(bufferGraphics);
                }

                bufferGraphics.drawString("CLICK to start", 10,
                        getSize().height / 2);

                bufferGraphics.drawString("Score: " + count + " High Score: "
                        + high, 10, getSize().height - 20);
                repaint();
                pause(refresh);
            } else {
                for (Iterator iter = items.iterator(); iter.hasNext();) {
                    Item item = (Item) iter.next();
                    item.move();
                    item.draw(bufferGraphics);
                }
                bufferGraphics.drawString("Score: " + count + " High Score: "
                        + high, 10, getSize().height - 20);

                repaint();

                pause(refresh);

                for (Iterator iter = items.iterator(); iter.hasNext();) {
                    if (((Item) iter.next()).checkBounds(getSize())) {
                        count = 0;
                    }
                }
            }
        } while (true);
    }

    private void pause(int pause) {
        Thread t = Thread.currentThread();
        try {
            Thread.sleep(pause);
        } catch (InterruptedException interruptedexception) {
        }
    }

    public void paint(Graphics g) {
        if (offscreen == null) {
            createDoubleBuffer();
        }
        
        g.drawImage(offscreen, 0, 0, this);
    }

    public void update(Graphics g) {
        paint(g);
    }
}