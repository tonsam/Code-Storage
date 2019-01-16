//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;

public class SaveAsAction extends AbstractAction {
	private static SaveAsAction saveAsAction = new SaveAsAction();

	private SaveAsAction() {
		super("Save As...");
	}

	public static SaveAsAction getAction() {
		return saveAsAction;
	}

	public void actionPerformed(ActionEvent e) {
		SaveAsable target =
			(SaveAsable) ActionUtilities.getCommandTarget(e, SaveAsable.class);
		if (target != null && target.isSaveAsable(e)) {
			target.saveAs(e);
		}
	}

	public boolean isTargetEnabled(EventObject evt) {
		SaveAsable target =
			(SaveAsable) ActionUtilities.getCommandTarget(evt, SaveAsable.class);

		if (target != null) {
			return target.isSaveAsable(evt);
		} else {
			return false;
		}
	}
}