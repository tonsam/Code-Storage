package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import com.javera.ui.IconManager;

public class OpenSpecialAction extends ManagedStateAction {
    private static OpenSpecialAction openSpecialAction = new OpenSpecialAction();

    private OpenSpecialAction()
    {
	super("Open Special...", IconManager.getIcon("Open.gif"));
    }

    public static OpenSpecialAction getAction() {
	return openSpecialAction;
    }

    public void actionPerformed(ActionEvent e)
    {
	OpenableSpecial openable = (OpenableSpecial) ActionUtilities.getCommandTarget(e, OpenableSpecial.class);

	if (openable != null) {
	    openable.openSpecial();
	}
    }

    public boolean isTargetEnabled(EventObject evt) {
	OpenableSpecial openable = (OpenableSpecial) ActionUtilities.getCommandTarget(evt, OpenableSpecial.class);

	if (openable != null) {
	    return openable.isOpenableSpecial(evt);
	} else {
	    return false;
	}
    }
}