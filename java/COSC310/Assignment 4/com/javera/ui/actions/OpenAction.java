package com.javera.ui.actions;

import com.javera.ui.IconManager;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.Action;

public class OpenAction extends ManagedStateAction {
    private static OpenAction openAction = new OpenAction();

    private OpenAction() {
        super( "Open...", IconManager.getIcon( "Open.gif" ) );
        putValue( Action.SHORT_DESCRIPTION, "Open" );
    }

    public static OpenAction getAction() {
        return openAction;
    }

    public void actionPerformed( ActionEvent e ) {
        Openable openable = ( Openable )
            ActionUtilities.getCommandTarget( e, Openable.class );

        if ( openable != null ) {
            openable.open( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Openable openable = ( Openable )
            ActionUtilities.getCommandTarget( evt, Openable.class );

        if ( openable != null ) {
            return openable.isOpenable( evt );
        } else {
            return false;
        }

    }
}
