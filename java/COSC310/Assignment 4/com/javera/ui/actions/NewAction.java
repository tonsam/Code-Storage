package com.javera.ui.actions;

import com.javera.ui.IconManager;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.Action;
import javax.swing.AbstractAction;

public class NewAction extends AbstractAction {
    private static NewAction newAction = new NewAction();

    private NewAction() {
        super( "New...", IconManager.getIcon( "New.gif" ) );
        putValue( Action.SHORT_DESCRIPTION, "New" );
    }

    public static NewAction getAction() {
        return newAction;
    }

    public void actionPerformed( ActionEvent e ) {
        Newable newable = ( Newable )
            ActionUtilities.getCommandTarget( e, Newable.class );

        if ( newable != null ) {
            newable.makeNew( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Newable newable = ( Newable )
            ActionUtilities.getCommandTarget( evt, Newable.class );

        if ( newable != null ) {
            return newable.isNewable( evt );
        } else {
            return false;
        }

    }
}
