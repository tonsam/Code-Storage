/* Generated by Together */

package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;
import javax.swing.Action;

public class ExportAction extends ManagedStateAction {
    private static ExportAction exportAction = new ExportAction();

    private ExportAction() {
        super( "Export..." );
        putValue( Action.MNEMONIC_KEY, new Integer( 't' ) );
        putValue( Action.SHORT_DESCRIPTION, "Export" );
    }

    public static ExportAction getAction() {
        return exportAction;
    }

    public void actionPerformed( ActionEvent e ) {
        Exportable exportable = ( Exportable )
            ActionUtilities.getCommandTarget( e, Exportable.class );

        if ( exportable != null )
            exportable.export( e );
    }

    public boolean isTargetEnabled( EventObject evt ) {
        Object target = ActionUtilities.getCommandTarget( evt,
            Exportable.class );
        if ( target != null ) {
            if ( ( ( Exportable )target ).isExportable( evt ) ) {
                return true;
            }
        }
        return false;
    }
}
