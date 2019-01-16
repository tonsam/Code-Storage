
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

class RollDice extends JPanel {
    Dice dice1;
    Dice dice2;
    Random rand;

    RollDice() {
        dice1 = new Dice();
        dice2 = new Dice();
        rand = new Random();

        JButton rollButton = new JButton("Roll");
        rollButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                rollDice();
             }
        });

        add(dice1);
        add(dice2);
        add(rollButton);
    }

    public void rollDice() {
        new Thread() {
            public void run() {
                for (int i = 100; i < 1000; i *= 1.3) {
                    dice1.setValue(rand.nextInt(6));
                    dice2.setValue(rand.nextInt(6));
                    repaint();
                    try {
                        Thread.sleep(i);
                    } catch (InterruptedException e) {
                    }
                }

                
                
            }
        }.start();
    }


    public static void main(String[] a) {
        JFrame app = new JFrame();
        app.setLocation(300, 200);
        app.getContentPane().add(new RollDice());
        app.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        app.pack();
        app.show();
    }
}