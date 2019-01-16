
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

class Dice extends JLabel {
    private int value;

    Dice() {
        super();
        setValue(0);
        
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                setValue(getValue() + 1);
            }
         });
    }

    public int getValue() {
        return value;
    }
    
    void setValue(int value) {
        this.value = value;
        switch (value) {
        case 0:
            this.setIcon(getImage("images\\one.GIF"));
            break;
        case 1:
            this.setIcon(getImage("images\\two.GIF"));
            break;
        case 2:
            this.setIcon(getImage("images\\three.GIF"));
            break;
        case 3:
            this.setIcon(getImage("images\\four.GIF"));
            break;
        case 4:
            this.setIcon(getImage("images\\five.GIF"));
            break;
        case 5:
            this.setIcon(getImage("images\\six.GIF"));
            break;
        }
    }
    
    
    ImageIcon getImage(String name) {
        Object o = getClass().getResource(name);
        return new ImageIcon(getClass().getResource(name));
    }
    
    public static void main(String[] a) {
        JPanel panel = new JPanel();
        Dice dice1 = new Dice();
        
        dice1.setValue(2);
        
        panel.add(dice1);
        
        JFrame app = new JFrame();
        app.getContentPane().add(panel);
        app.setLocation(300, 200);
        app.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        app.pack();
        app.show();
    }

}

