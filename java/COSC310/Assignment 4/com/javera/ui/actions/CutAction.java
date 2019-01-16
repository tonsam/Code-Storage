package com.javera.ui.actions;

import java.awt.Event;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.Action;
import javax.swing.KeyStroke;


import com.javera.ui.IconManager;

public class CutAction extends ManagedStateAction implements ClipboardOwner {
    private static CutAction cutAction = new CutAction();

    private CutAction() {
        super( "Cut", IconManager.getIcon( "Cut.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 't' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_X, Event.CTRL_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Cut" );
    }

    public static CutAction getAction() { return cutAction; }

    public void actionPerformed( ActionEvent e ) {
        Cutable cutable = ( Cutable )
            ActionUtilities.getCommandTarget( e, Cutable.class );

        if ( cutable != null ) {
            cutable.cut( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Cutable cutable = ( Cutable )
            ActionUtilities.getCommandTarget( evt, Cutable.class );

        if ( cutable != null ) {
            return cutable.isCutable( evt );
        } else {
            return false;
        }
    }

    public void lostOwnership( Clipboard par1, Transferable par2 ) {
        // following a paste, cut should probably be disabled at least until
        //  a new event re-enables it
        this.setEnabled( false );
    }
}



