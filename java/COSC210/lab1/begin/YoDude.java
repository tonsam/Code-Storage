import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

public class YoDude extends JFrame {
    
    public YoDude(String message) {
        super("Yo Dude");
        getContentPane().add(new JLabel(message));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
    }
    
    public static void main(String[] args) {
        YoDude yoDude = new YoDude(" What's up? ");
        yoDude.setLocation(300, 300);
        yoDude.show();
    }
}
