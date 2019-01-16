package edu.iup.cosc310.painter;

import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.javera.ui.IconManager;

/**
 * Tool to initially construct a PtrDrawImage
 * 
 * @author David T. Smith
 */
public class PtrDrawImageTool extends PtrDrawTool {
    /**
     * Constructor
     */
	public PtrDrawImageTool() {
		super(IconManager.getIcon("image.gif"));
	}

	/**
	 * Process event that activates this tool
	 * 
	 * @param editor reference to the painter
	 * @param e the mouse event that initiated the activateTool.
	 */
	public void activateTool(PtrEditor editor, MouseEvent e) {
	    editor.clearSelection();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            public boolean accept(File f) {
                return f.isDirectory() || f.getName().toLowerCase().endsWith(".jpg") 
                         || f.getName().toLowerCase().endsWith(".gif");
            }

            public String getDescription() {
                return "JPG & GIF Images";
            }
        });

        if (fileChooser.showOpenDialog(editor) ==
                          JFileChooser.APPROVE_OPTION) {
            ImageIcon imageIcon = new
                          ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());

 
			PtrDrawImage image = new PtrDrawImage(editor, e.getX(), e.getY(), imageIcon.getImage());
			image.setLineColor(editor.getLineColor());
			image.setLineWidth(editor.getLineWidth());
	
			editor.addDrawObject(image);		
        }

		editor.selectDefaultTool();
		editor.repaint();
	}
}
