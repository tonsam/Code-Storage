package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import com.javera.ui.IconManager;

public class SaveAction extends ManagedStateAction {
	private static SaveAction saveAction = new SaveAction();

	private SaveAction() {
		super("Save", IconManager.getIcon("Save.gif"));
		putValue(javax.swing.Action.SHORT_DESCRIPTION, "Save");
	}

	public static SaveAction getAction() {
		return saveAction;
	}

	public void actionPerformed(ActionEvent e) {
		Saveable target =
			(Saveable) ActionUtilities.getCommandTarget(e, Saveable.class);
		if (target != null && target.isSaveable(e)) {
			target.save(e);
		}
	}

	public boolean isTargetEnabled(EventObject evt) {
		Saveable target =
			(Saveable) ActionUtilities.getCommandTarget(evt, Saveable.class);

		if (target != null) {
			return target.isSaveable(evt);
		} else {
			return false;
		}
	}
}
