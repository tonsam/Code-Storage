package com.javera.ui.actions;

import com.javera.ui.IconManager;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.Action;

public class SortAction extends ManagedStateAction {
    private static SortAction filterAction = new SortAction();

    private SortAction() {
        super( "Sort...", IconManager.getIcon( "sort.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'F' ) );
        putValue( Action.SHORT_DESCRIPTION, "Sort" );
    }

    public static SortAction getAction() {
        return filterAction;
    }

    public void actionPerformed( ActionEvent e ) {
        Sortable sortable = ( Sortable )
            ActionUtilities.getCommandTarget( e, Sortable.class );

        if ( sortable != null )
            sortable.sort( e );
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Sortable sortable = ( Sortable )
            ActionUtilities.getCommandTarget( evt, Sortable.class );

        if ( sortable != null ) {
            return sortable.isSortable( evt );
        } else {
            return false;
        }
    }
}
