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

public class CopyAction extends ManagedStateAction implements ClipboardOwner {
    private static CopyAction copyAction = new CopyAction();

    private CopyAction() {
        super( "Copy", IconManager.getIcon( "Copy.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'C' ) );
        putValue( Action.ACCELERATOR_KEY,
            KeyStroke.getKeyStroke( KeyEvent.VK_C, Event.CTRL_MASK ) );
        putValue( Action.SHORT_DESCRIPTION, "Copy" );
    }

    public static CopyAction getAction() { return copyAction; }

    public void actionPerformed( ActionEvent e ) {
        Copyable copyable = ( Copyable )
            ActionUtilities.getCommandTarget( e, Copyable.class );

        if ( copyable != null ) {
            copyable.copy( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Copyable copyable = ( Copyable )
            ActionUtilities.getCommandTarget( evt, Copyable.class );

        if ( copyable != null ) {
            return copyable.isCopyable( evt );
        } else {
            return false;
        }
    }

    public void lostOwnership( Clipboard clipboard, Transferable t ) {
        // no need to do anything here, but required to put data on Clipboard
    }
}



