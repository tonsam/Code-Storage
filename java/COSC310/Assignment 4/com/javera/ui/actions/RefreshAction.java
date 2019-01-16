package com.javera.ui.actions;

import com.javera.ui.IconManager;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.Action;

/** A managed state action for Refresh operations. */
public class RefreshAction extends ManagedStateAction {
    private static RefreshAction refreshAction = new RefreshAction();

    private RefreshAction() {
        super( "Refresh...", IconManager.getIcon( "Refresh.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'F' ) );
        putValue( Action.SHORT_DESCRIPTION, "Refresh" );
        putValue( Action.LONG_DESCRIPTION, "Refresh" );
    }

    public static RefreshAction getAction() {
        return refreshAction;
    }

    public void actionPerformed( ActionEvent e ) {
        Refreshable refreshable = ( Refreshable )
            ActionUtilities.getCommandTarget( e, Refreshable.class );

        if ( refreshable != null )
            refreshable.refresh( e );
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Refreshable refreshable = ( Refreshable )
            ActionUtilities.getCommandTarget( evt, Refreshable.class );

        if ( refreshable != null ) {
            return refreshable.isRefreshable( evt );
        } else {
            return false;
        }
    }
}
