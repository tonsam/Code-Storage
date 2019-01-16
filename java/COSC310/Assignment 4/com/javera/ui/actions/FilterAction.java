package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.Action;


import com.javera.ui.IconManager;

public class FilterAction extends ManagedStateAction {
    private static FilterAction filterAction = new FilterAction();

    private FilterAction() {
        super( "Filter...", IconManager.getIcon( "filter.gif" ) );
        putValue( Action.MNEMONIC_KEY, new Integer( 'F' ) );
        putValue( Action.SHORT_DESCRIPTION, "Filter" );
    }

    public static FilterAction getAction() { return filterAction; }

    public void actionPerformed( ActionEvent e ) {
        Filterable filterable = ( Filterable )
            ActionUtilities.getCommandTarget( e, Filterable.class );

        if ( filterable != null ) {
            filterable.filter( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Filterable filterable = ( Filterable )
            ActionUtilities.getCommandTarget( evt, Filterable.class );

        if ( filterable != null ) {
            return filterable.isFilterable( evt );
        } else {
            return false;
        }
    }
}

