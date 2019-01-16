//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

public class CloseAction extends ManagedStateAction {
	private static CloseAction saveAction = new CloseAction();

	private CloseAction() {
		super("Close");
		putValue(javax.swing.Action.SHORT_DESCRIPTION, "Close");
	}

	public static CloseAction getAction() {
		return saveAction;
	}

	public void actionPerformed(ActionEvent e) {
		Closeable target =
			(Closeable) ActionUtilities.getCommandTarget(e, Closeable.class);
		if (target != null && target.isCloseable(e)) {
			target.close(e);
		}
	}

	public boolean isTargetEnabled(EventObject evt) {
		Closeable target =
			(Closeable) ActionUtilities.getCommandTarget(evt, Closeable.class);

		if (target != null) {
			return target.isCloseable(evt);
		} else {
			return false;
		}
	}
}
