package com.javera.ui.actions;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.KeyStroke;

import com.javera.ui.IconManager;


public class PasteAction extends ManagedStateAction {
    private static PasteAction pasteAction = new PasteAction();

    private PasteAction() {
        super( "Paste", IconManager.getIcon( "Paste.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'P' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_V, Event.CTRL_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Paste" );
    }

    public static PasteAction getAction() { return pasteAction; }

    public void actionPerformed( ActionEvent e ) {
        Pasteable pasteable = ( Pasteable )
            ActionUtilities.getCommandTarget( e, Pasteable.class );
            
		if ( pasteable != null ) {
			pasteable.paste( e );
		}

    }

    public boolean isTargetEnabled( EventObject evt ) {
        Pasteable pasteable = ( Pasteable )
            ActionUtilities.getCommandTarget( evt, Pasteable.class );

        if ( pasteable != null ) {
            return pasteable.isPasteable( evt );
        } else {
            return false;
        }
    }
}



