package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;

public class SaveAllAction extends AbstractAction {
	private static SaveAllAction saveAllAction = new SaveAllAction();

	private SaveAllAction() {
		super("Save All...");
		putValue(javax.swing.Action.SHORT_DESCRIPTION, "Save All");
	}

	public static SaveAllAction getAction() {
		return saveAllAction;
	}

	public void actionPerformed(ActionEvent e) {
		SaveAllable target =
			(SaveAllable) ActionUtilities.getCommandTarget(e, SaveAllable.class);
		if (target != null && target.isSaveAllable(e)) {
			target.saveAll(e);
		}
	}

	public boolean isTargetEnabled(EventObject evt) {
		SaveAllable target =
			(SaveAllable) ActionUtilities.getCommandTarget(evt, SaveAllable.class);

		if (target != null) {
			return target.isSaveAllable(evt);
		} else {
			return false;
		}
	}
}