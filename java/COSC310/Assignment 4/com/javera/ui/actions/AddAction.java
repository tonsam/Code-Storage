package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.Action;

import com.javera.ui.IconManager;

public class AddAction extends ManagedStateAction {
    private static AddAction addAction = new AddAction();

    private AddAction() {
        super( "New...", IconManager.getIcon( "Add.gif" ) );
        putValue( Action.SHORT_DESCRIPTION, "New" );
    }

    public static AddAction getAction() {
        return addAction;
    }

    public void actionPerformed( ActionEvent e ) {
        Addable target = ( Addable )ActionUtilities.getCommandTarget( e,
            Addable.class );
        if ( target != null && target.isAddable( e ) ) {
            target.add( e );
        }
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Addable target = ( Addable )ActionUtilities.getCommandTarget( evt,
            Addable.class );

        if ( target != null ) {
            return target.isAddable( evt );
        } else {
            return false;
        }
    }
}
