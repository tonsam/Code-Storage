package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;

public class CloseAllAction extends AbstractAction {
	private static CloseAllAction saveAllAction = new CloseAllAction();

	private CloseAllAction() {
		super("Close All...");
		putValue(javax.swing.Action.SHORT_DESCRIPTION, "Close All");
	}

	public static CloseAllAction getAction() {
		return saveAllAction;
	}

	public void actionPerformed(ActionEvent e) {
		CloseAllable target =
			(CloseAllable) ActionUtilities.getCommandTarget(e, CloseAllable.class);
		if (target != null && target.isCloseAllable(e)) {
			target.closeAll(e);
		}
	}

	public boolean isTargetEnabled(EventObject evt) {
		CloseAllable target =
			(CloseAllable) ActionUtilities.getCommandTarget(evt, CloseAllable.class);

		if (target != null) {
			return target.isCloseAllable(evt);
		} else {
			return false;
		}
	}
}