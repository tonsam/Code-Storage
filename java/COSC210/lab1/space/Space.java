
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
 
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Space extends JPanel implements Runnable, MouseListener, MouseMotionListener, KeyListener {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Space");
        Space space = new Space();
        frame.getContentPane().add(space);
        frame.setLocation(50, 50);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.show();
        space.start();
    }
    
    public Space() {
        gameState = 0;
        everStarted = true;
        running = false;
        loadedImages = 0;
        noImages = imageNames.length;
        setCursor(Cursor.getPredefinedCursor(1));
        baseFont = new Font("Monospaced", 0, 10);
        boldFont = new Font("Monospaced", 1, 20);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setPreferredSize(new Dimension(APPLET_WIDTH, APPLET_HEIGHT));
    }
    
     public void start() {
        (new Thread(this)).start();
        requestFocus();
    }

    private void pause(int t) {
        try {
            Thread.sleep(t);
        } catch (Exception exception) {
        }
    }

    private Image getImage(String name) {
        Object o = getClass().getResource(name);
        return new ImageIcon(getClass().getResource(name)).getImage();
    }

    public String[][] getParameterInfo() {
        return null;
    }

    public void update(Graphics g) {
        paint(bufferGraphics);
        g.drawImage(imageBuffer, 0, 0, this);
    }

    public void paintGame(Graphics g) {
        g.setFont(baseFont);
        int vx_ = displayX >> 1;
        int vx__ = displayX << 1;
        g.drawImage(images[BACKGROUND_IMAGE], 0, 0, APPLET_WIDTH, 320, vx_, 0,
                vx_ + APPLET_WIDTH, 320, null);
        g.drawImage(images[FOREGROUND_IMAGE_1], 0, 212, APPLET_WIDTH, 348,
                displayX, 0, displayX + APPLET_WIDTH, 136, null);
        g.drawImage(images[FOREGROUND_IMAGE_2], 0, 259, APPLET_WIDTH, 364,
                vx__, 0, vx__ + APPLET_WIDTH, 105, null);

        for (int a = 0; a < ufo.length; a++) {
            int x = ufo[a].x;
            int y = ufo[a].y;
            int index = FIGHTER_IMAGE_1;
            if (ufo[a].bomber)
                index = BOMBER_IMAGE_1;
            if (ufo[a].hit) {
                index++;
                g.drawImage(images[index], x - vx__, y, null);
                continue;
            }
            g.drawImage(images[index], x - vx__, y, null);

            if (!ufo[a].attacking)
                continue;
            if (ufo[a].phase)
                g.drawImage(images[ATTACK_IMAGE], (x - vx__) + 4, y + 33,
                        (x - vx__) + 96, y + 91, 0, 0, 92, 58, null);
            else
                g.drawImage(images[ATTACK_IMAGE], (x - vx__) + 4, y + 33,
                        (x - vx__) + 96, y + 91, 92, 0, 184, 58, null);
            ufo[a].phase = !ufo[a].phase;
        }

        if (shootingCount > 0) {
            shootingCount--;
            g.setColor(Color.blue);
            g.fillPolygon(blueShootShape);
            g.setColor(Color.cyan);
            g.fillPolygon(cyanShootShape);
        }
        if (newShotsCredit > 0)
            g.drawImage(images[POWER_UP_IMAGE], 4, 310, 36, 346, 0, 0, 32, 32,
                    null);
        if (newLifeCredit > 0)
            g.drawImage(images[POWER_UP_IMAGE], 40, 310, 72, 346, 32, 0, 64,
                    32, null);
        if (newPowerCredit > 0)
            g.drawImage(images[POWER_UP_IMAGE], 76, 310, 112, 346, 64, 0, 96,
                    32, null);
        g.drawImage(images[CONSOLE_IMAGE], 0, 343, null);
        if (numberShots < 25)
            g.setColor(Color.red);
        else if (numberShots < 50)
            g.setColor(Color.yellow);
        else
            g.setColor(Color.green);
        g.fillRect(4, 377, numberShots, 8);
        g.setColor(Color.green);
        g.drawString("" + score, 4, 402);
        int ce = (remainingLife * 100) / MAX_LIFE;
        if (ce < 25)
            g.setColor(Color.red);
        else if (ce < 50)
            g.setColor(Color.yellow);
        else
            g.setColor(Color.green);
        g.fillRect(310, 386, ce, 8);
        drawRadar(g);
    }

    public void paint(Graphics g) {
        if (running) {
            if (gameState == RUNNING)
                paintGame(g);
            else if (gameState == BEGIN)
                g.drawImage(images[TITLE_IMAGE], 0, 0, null);
            else if (gameState == GAME_OVER) {
                g.drawImage(images[DARK_BG_IMAGE], 0, 0, null);
                if (offsetY > 0) {
                    int w = 334 + offsetY;
                    int h = 50 + offsetY;
                    int x = (APPLET_WIDTH - w) / 2;
                    int y = (APPLET_HEIGHT - h) / 2;
                    g.drawImage(images[GAME_OVER_IMAGE], x, y, x + w, y + h, 0,
                            0, 334, 50, null);
                } else if (offsetY > -200)
                    g.drawImage(images[GAME_OVER_IMAGE], 43, 185, null);
                else if (offsetY <= -200 && offsetY > -375) {
                    g.drawImage(images[GAME_OVER_IMAGE], 43, 385 + offsetY,
                            null);
                } else {
                    g.setFont(boldFont);
                    g.drawImage(images[GAME_OVER_IMAGE], 43, 10, null);
                    int y = offsetY + 725;
                    if (y < 120) {
                        y = 120;
                        if (rank >= 0)
                            setGameState(3);
                        else
                            setGameState(4);
                    }
                    for (int a = 0; a < names.length; a++) {
                        g.setColor(Color.orange);
                        g.drawString(names[a], 80, y);
                        g.setColor(Color.white);
                        g.drawString("" + highestScores[a], 306, y);
                        y += 25;
                    }

                }
            } else if (gameState == ENTER_INITIALS) {
                g.setFont(boldFont);
                g.drawImage(images[DARK_BG_IMAGE], 0, 0, null);
                g.drawImage(images[GAME_OVER_IMAGE], 43, 10, null);
                for (int a = 0; a < names.length; a++) {
                    g.setColor(Color.orange);
                    g.drawString(names[a], 80, 120 + a * 25);
                    g.setColor(Color.white);
                    g.drawString("" + highestScores[a], 306, 120 + a * 25);
                }

                g.drawString(String.valueOf(initials1), 80, 122 + rank * 25);
            } else if (gameState == AT_END) {
                g.setFont(boldFont);
                g.drawImage(images[DARK_BG_IMAGE], 0, 0, null);
                g.drawImage(images[GAME_OVER_IMAGE], 43, 10, null);
                for (int a = 0; a < names.length; a++) {
                    g.setColor(Color.orange);
                    g.drawString(names[a], 80, 120 + a * 25);
                    g.setColor(Color.white);
                    g.drawString("" + highestScores[a], 306, 120 + a * 25);
                }

            }
        } else if (images != null && images[LOGO_IMAGE] != null) {
            g.setColor(Color.black);
            g.fillRect(0, 0, APPLET_WIDTH, APPLET_HEIGHT);
            g.drawImage(images[LOGO_IMAGE], 133, 50, null);
            g.setColor(Color.blue);
            g.drawRect(158, 300, 104, 10);
            int w = (100 * loadedImages) / noImages;
            g.setColor(Color.cyan);
            g.fillRect(160, 301, w, 8);
        }
    }

    public void drawRadar(Graphics g) {
        g.setColor(Color.green);
        g.drawRect(115 + displayX / 5, 378, 42, 20);
        for (int a = 0; a < ufo.length; a++) {
            if (ufo[a].y <= 0 || ufo[a].x <= 0 || ufo[a].x >= 1820)
                continue;
            if (ufo[a].attacking) {
                g.setColor(Color.red);
                g.fillRect(115 + ufo[a].x / 10, 377 + ufo[a].y / 20, 4, 4);
            } else {
                g.setColor(Color.orange);
                g.fillRect(115 + ufo[a].x / 10, 378 + ufo[a].y / 20, 2, 2);
            }
        }

    }

    private void setGameState(int s) {
        long t = System.currentTimeMillis();
        if (t - deltaTime < (long) 400)
            return;
        deltaTime = t;
        label0: switch (s) {
        default:
            break;

        case BEGIN:
            creditType = -1;
            newPowerCredit = 0;
            newLifeCredit = 0;
            newShotsCredit = 0;
            shootingCount = 0;
            ufoStepAmount = 5;
            score = 0;
            numberShots = 100;
            remainingLife = MAX_LIFE;
            setFont(baseFont);
            for (int a = 0; a < initials2.length; a++) {
                initials2[a] = ' ';
                initials1[a] = ' ';
            }

            initials1[0] = '_';
            initialIndex = 0;
            startOfGameTime = 0L;
            goLeft = goRight = goLeftMouse = goRightMouse = false;
            break;

        case RUNNING: // '\001'
            for (int a = 0; a < ufo.length; a++)
                ufo[a].respawn();

            offsetY = 300;
            break;

        case GAME_OVER: // '\002'
            rank = -1;
            int a = 0;
            do {
                if (a >= names.length)
                    break label0;
                if (score > highestScores[a]) {
                    for (int b = names.length - 1; b > a; b--) {
                        names[b] = names[b - 1];
                        highestScores[b] = highestScores[b - 1];
                    }

                    highestScores[a] = score;
                    names[a] = "";
                    rank = a;
                    System.out.println("rank:" + rank);
                    break label0;
                }
                a++;
            } while (true);
        }
        gameState = s;
    }

    public void mousePressed(MouseEvent e) {
        requestFocus();
        if (gameState == RUNNING) {
            if (numberShots >= MAX_SHOTS) {
                numberShots -= MAX_SHOTS;
                int x = e.getX();
                shootY = e.getY();
                blueShootShape.xpoints[0] = cyanShootShape.xpoints[0] = x;
                blueShootShape.ypoints[0] = cyanShootShape.ypoints[0] = shootY;
                shootingCount = 1;
                shootX = x + (displayX << 1);
                //                    audioShoot.play();
            } else {
                //                    audioNoShoot.play();
            }
        } else if (gameState == BEGIN)
            setGameState(RUNNING);
        else if (gameState == AT_END)
            setGameState(BEGIN);
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        goLeftMouse = false;
        goRightMouse = false;
        if (x < 20)
            goLeftMouse = true;
        else if (x > 400)
            goRightMouse = true;
    }

    public void keyPressed(KeyEvent e) {
        if (gameState == RUNNING) {
            boolean value = true;

            if (stopped) {
                stopped = false;
                repaint();
            } else {
                switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT: // '%'
                    goLeft = value;
                    break;

                case KeyEvent.VK_RIGHT: // '\''
                    goRight = value;
                    break;
                }
            }
        } else if (gameState == BEGIN) {
                setGameState(RUNNING);
        } else if (gameState == ENTER_INITIALS) {
                if (rank >= 0) {
                    int code = e.getKeyCode();
                    if (code == KeyEvent.VK_BACK_SPACE) {
                        initials1[initialIndex] = ' ';
                        initials2[initialIndex] = ' ';
                        if (initialIndex > 0)
                            initialIndex--;
                        initials1[initialIndex] = '_';
                        initials2[initialIndex] = ' ';
                        names[rank] = String.valueOf(initials2);
                    } else if (code == 10) {
                        rank = -1;
                        score = 0;
                        setGameState(4);
                    }
                }
        } else if (gameState == AT_END) {
                setGameState(BEGIN);
        } else if (gameState == 3 && rank >= 0
                && offsetY < 375) {
            char c = e.getKeyChar();
            long d = System.currentTimeMillis() - startOfGameTime;
            if (d > (long) 150
                    && (Character.isLetterOrDigit(c) || c == '_' || c == ' ')) {
                startOfGameTime = System.currentTimeMillis();
                initials2[initialIndex] = c;
                initials1[initialIndex] = '\n';
                if (initialIndex < initials2.length - 1)
                    initialIndex++;
                initials1[initialIndex] = '_';
                names[rank] = String.valueOf(initials2);
            }
        }
    }

    private void stepUFO(int a) {
        ufo[a].step(ufoStepAmount);
        if (ufo[a].attacking) {
            if (ufo[a].sound) {
                ufo[a].sound = false;
                //                audioWarning.play();
            }
            if (remainingLife > 0)
                remainingLife--;
        }
    }

    private void ufoHit(int a) {
        ufo[a].hit = false;
        ufo[a].shield--;
        if (ufo[a].shield < 0) {
            if (ufo[a].bomber) {
                score += 50;
                if (Math.random() > 0.0D) {
                    creditType = (int) (Math.random() * (double) 3);
                    switch (creditType) {
                    case 0: // '\0'
                        newShotsCredit = 100;
                        break;

                    case 1: // '\001'
                        newLifeCredit = 100;
                        break;

                    case 2: // '\002'
                        newPowerCredit = 75;
                        break;

                    case 3: // '\003'
                        newLifeCredit = 100;
                        break;
                    }
                } else {
                    creditType = -1;
                }
            } else {
                score += 25;
            }
            hitCount++;
            if (hitCount % 5 == 0)
                ufoStepAmount++;
            ufo[a].respawn();
        }
    }

    public void run() {
        
        if (everStarted) {
            everStarted = false;
            imageBuffer = createImage(APPLET_WIDTH, APPLET_HEIGHT);
            bufferGraphics = imageBuffer.getGraphics();
            blueShootX[0] = 210;
            blueShootY[0] = 210;
            blueShootX[1] = 230;
            blueShootY[1] = 352;
            blueShootX[2] = 190;
            blueShootY[2] = 352;
            cyanShootX[0] = 210;
            cyanShootY[0] = 210;
            cyanShootX[1] = 220;
            cyanShootY[1] = 352;
            cyanShootX[2] = 200;
            cyanShootY[2] = 352;
            blueShootShape = new Polygon(blueShootX, blueShootY, 3);
            cyanShootShape = new Polygon(cyanShootX, cyanShootY, 3);
            noImages = imageNames.length;
            images = new Image[imageNames.length];
            for (int a = 0; a < imageNames.length; a++) {
                images[a] = getImage(imageNames[a]);
                loadedImages++;
                repaint();
                Thread.yield();
            }

            //            audioShoot = getAudioClip(getDocumentBase(), "shoot.au");
            //            audioShoot.play();
            Thread.yield();
            //            audioShoot.stop();
            //            audioWarning = getAudioClip(getDocumentBase(), "warn.au");
            //            audioWarning.play();
            Thread.yield();
            //            audioWarning.stop();
            //            audioNoShoot = getAudioClip(getDocumentBase(), "noshoot.au");
            //            audioNoShoot.play();
            Thread.yield();
            //            audioNoShoot.stop();
            for (; loadedImages < noImages; loadedImages++)
                repaint();

            ufo = new UFO[5];
            for (int a = 0; a < ufo.length; a++)
                ufo[a] = new UFO(1820, APPLET_HEIGHT);

        }
        for (running = true; running;)
            if (gameState == RUNNING) {
                long t1 = System.currentTimeMillis();
                int delta = 10;

                if (newLifeCredit > 0) {
                    if (remainingLife < MAX_LIFE - 5)
                        remainingLife += 5;
                    else
                        remainingLife = MAX_LIFE;
                } else if (newPowerCredit > 0)
                    delta <<= 1;
                if (goLeft)
                    if (displayX > delta)
                        displayX -= delta;
                    else
                        displayX = 0;
                if (goLeftMouse)
                    if (displayX > delta)
                        displayX -= delta;
                    else
                        displayX = 0;
                if (goRight) {
                    int mx = 700;
                    if (displayX < mx - delta)
                        displayX += delta;
                    else
                        displayX = mx;
                }
                if (goRightMouse) {
                    int mx = 700;
                    if (displayX < mx - delta)
                        displayX += delta;
                    else
                        displayX = mx;
                }
                if (shootingCount > 0) {
                    int a = 0;
                    do {
                        if (a >= ufo.length)
                            break;
                        if (ufo[a].hit)
                            ufoHit(a);
                        else if (ufo[a].bomber) {
                            if (ufo[a].x + 6 < shootX && ufo[a].x + 56 > shootX
                                    && ufo[a].y + 8 < shootY
                                    && ufo[a].y + 28 > shootY) {
                                ufo[a].hit = true;
                                break;
                            }
                        } else if (ufo[a].x + 6 < shootX
                                && ufo[a].x + 96 > shootX
                                && ufo[a].y + 7 < shootY
                                && ufo[a].y + 26 > shootY) {
                            ufo[a].hit = true;
                            break;
                        }
                        stepUFO(a);
                        a++;
                    } while (true);
                } else {
                    for (int a = 0; a < ufo.length; a++) {
                        if (ufo[a].hit)
                            ufoHit(a);
                        stepUFO(a);
                    }

                }

                newLifeCredit--;
                newPowerCredit--;
                newShotsCredit--;

                if (remainingLife <= 0)
                    setGameState(2);

                repaint();

                if (numberShots < 100) {
                    numberShots += NEW_SHOTS;
                    if (newShotsCredit > 0)
                        numberShots += 2;
                    if (numberShots > 100)
                        numberShots = 100;
                }
                long t2 = System.currentTimeMillis();
                long t3 = t2 - t1;
                if (t3 > (long) 100)
                    Thread.yield();
                else
                    pause((int) ((long) 100 - t3));
            } else {
                if (gameState == 2)
                    offsetY -= 20;
                Thread.yield();
                repaint();
            }

    }

    private static final int APPLET_WIDTH = 420;

    private static final int APPLET_HEIGHT = 420;

    private static final int MAX_SHOTS = 15;

    private static final int NEW_SHOTS = 2;

    private static final int MAX_LIFE = 400;

    private static boolean everStarted = true;

    private static boolean stopped = false;

    private static boolean running = false;

    private static final int LOGO_IMAGE = 0;

    private static final int BACKGROUND_IMAGE = 1;

    private static final int FOREGROUND_IMAGE_1 = 2;

    private static final int FOREGROUND_IMAGE_2 = 3;

    private static final int FIGHTER_IMAGE_1 = 4;

    private static final int FIGHTER_IMAGE_2 = 5;

    private static final int CONSOLE_IMAGE = 6;

    private static final int ATTACK_IMAGE = 7;

    private static final int BOMBER_IMAGE_1 = 8;

    private static final int BOMBER_IMAGE_2 = 9;

    private static final int POWER_UP_IMAGE = 10;

    private static final int TITLE_IMAGE = 11;

    private static final int DARK_BG_IMAGE = 12;

    private static final int GAME_OVER_IMAGE = 13;

    private static final int BEGIN = 0;

    private static final int RUNNING = 1;

    private static final int GAME_OVER = 2;

    private static final int ENTER_INITIALS = 3;

    private static final int AT_END = 4;

    private static int offsetY;

    private static int gameState = 0;

    private static long deltaTime = 0L;

    private static String imageNames[] = { "images\\logo.gif", "images\\bgl1.jpg", "images\\fg.gif",
            "images\\fg2.gif", "images\\fighter.gif", "images\\fighter2.gif", "images\\console.gif",
            "images\\attack.gif", "images\\bomber.gif", "images\\bomber2.gif", "images\\powerup.gif",
            "images\\titel.jpg", "images\\darkbg.jpg", "images\\gameover.gif" };

    private static String names[] = { "www", "steinke", "net", "", "", "hope",
            "you", "had", "fun", ":-)" };

    private static int highestScores[] = { 500, 450, 400, 350, 300, 250, 200,
            150, 100, 50 };

    private static char initials1[] = { '_', '\n', ' ', ' ', ' ', ' ', ' ', ' ' };

    private static char initials2[] = { ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' };

    private static int initialIndex = 0;

    private static int creditType = -1;

    private static int newPowerCredit = 0;

    private static int newLifeCredit = 0;

    private static int newShotsCredit = 0;

    private static UFO ufo[];

    private static int shootingCount = 0;

    private static Image images[];

    private static Image imageBuffer;

    private static Graphics bufferGraphics;

    private static int blueShootX[] = new int[3];

    private static int blueShootY[] = new int[3];

    private static int cyanShootX[] = new int[3];

    private static int cyanShootY[] = new int[3];

    private static int shootX;

    private static int shootY;

    private static Polygon blueShootShape;

    private static Polygon cyanShootShape;

    //    private static AudioClip audioShoot;
    //    private static AudioClip audioNoShoot;
    //    private static AudioClip audioWarning;

    private static MediaTracker mt = null;

    private static int loadedImages = 0;

    private static int noImages;

    private static int displayX = 350;

    private static int ufoStepAmount = 5;

    private static int hitCount = 0;

    private static boolean goLeft;

    private static boolean goRight;

    private static boolean goLeftMouse;

    private static boolean goRightMouse;

    private static int score = 0;

    private static int rank = -1;

    private static int numberShots = 100;

    private static int remainingLife = 400;

    private static Font baseFont;

    private static Font boldFont;

    private static long startOfGameTime = 0L;

    static {
        noImages = imageNames.length;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }
}