import java.awt.Cursor;
import java.awt.event.MouseEvent;

public interface OverAction {
    public Cursor getCursor();

    public void mousePressed(MouseEvent evt);

    public void mouseDragged(MouseEvent evt);
}

