package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.Action;


import com.javera.ui.IconManager;

/** A managed state action for Find operations. */
public class FindAction extends ManagedStateAction {
    private static FindAction findAction = new FindAction();

    private FindAction() {
        super( "Find...", IconManager.getIcon( "search.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'F' ) );
        putValue( Action.SHORT_DESCRIPTION, "Find" );
        putValue( Action.LONG_DESCRIPTION, "Finds text values" );
    }

    public static FindAction getAction() { return findAction; }

    public void actionPerformed( ActionEvent e ) {
        Findable findable = ( Findable )
            ActionUtilities.getCommandTarget( e, Findable.class );

        if ( findable != null ) {
            findable.find( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Findable findable = ( Findable )
            ActionUtilities.getCommandTarget( evt, Findable.class );

        if ( findable != null ) {
            return findable.isFindable( evt );
        } else {
            return false;
        }
    }
}

