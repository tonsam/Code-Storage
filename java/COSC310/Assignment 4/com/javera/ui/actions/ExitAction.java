package com.javera.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;


public class ExitAction extends AbstractAction {
    private static ExitAction exitAction = new ExitAction();

    private ExitAction() {
        super( "Exit" );
        putValue( Action.MNEMONIC_KEY, new Integer( 'x' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_F4, Event.ALT_MASK ) );

    }

    public static ExitAction getAction() { return exitAction; }

	public void actionPerformed( ActionEvent e ) {
		Exitable exitable = ( Exitable )
			ActionUtilities.getCommandTarget( e, Exitable.class );

		if ( exitable != null ) {
			exitable.exit( e );
		}
	}

	public boolean isTargetEnabled( EventObject evt ) {
		Exitable exitable = ( Exitable )
			ActionUtilities.getCommandTarget( evt, Exitable.class );

		if ( exitable != null ) {
			return exitable.isExitable( evt );
		} else {
			return false;
		}

	}
}

