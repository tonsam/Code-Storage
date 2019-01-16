package edu.iup.cosc310.painter;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.javera.ui.actions.ExitAction;
import com.javera.ui.actions.NewAction;
import com.javera.ui.actions.OpenAction;
import com.javera.ui.actions.PageSetupAction;
import com.javera.ui.actions.PrintAction;
import com.javera.ui.actions.SaveAction;
import com.javera.ui.actions.SaveAsAction;

/**
 * The file menu for the painter
 * 
 * @author David T. Smith
  */
public class PtrFileMenu extends JMenu {
    /**
     * default constructor
     */
	public PtrFileMenu() {
		super("File");

		setMnemonic('F');

		JMenuItem m;

		m = add(NewAction.getAction());
		m.setMnemonic('N');
		m.setAccelerator(KeyStroke.getKeyStroke("control N"));

		m = add(OpenAction.getAction());
		m.setMnemonic('O');
		m.setAccelerator(KeyStroke.getKeyStroke("control O"));

		m = add(SaveAction.getAction());
		m.setMnemonic('S');
		m.setAccelerator(KeyStroke.getKeyStroke("control S"));

		m = add(SaveAsAction.getAction());
		m.setMnemonic('A');

		addSeparator();

		m = add(PageSetupAction.getAction());
		m.setMnemonic('u');

		m = add(PrintAction.getAction());
		m.setMnemonic('p');
		m.setAccelerator(KeyStroke.getKeyStroke("control P"));

		addSeparator();

		m = add(ExitAction.getAction());
		m.setMnemonic('x');
	}

}