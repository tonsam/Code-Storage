package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class PageSetupAction extends AbstractAction
{
	private static PageSetupAction pageSetupAction = new PageSetupAction();

	private PageSetupAction() {
		super("Page Setup...");
		putValue(Action.SHORT_DESCRIPTION, "Page Setup");
	}

	public static PageSetupAction getAction() {
		return pageSetupAction;
	}

	public void actionPerformed(ActionEvent e) {
		PageSetupable pageSetupable =
			(PageSetupable) ActionUtilities.getCommandTarget(e, PageSetupable.class);

		if (pageSetupable != null)
		pageSetupable.pageSetup(e);
	}

	public boolean isTargetEnabled(EventObject evt) {
		Object target = ActionUtilities.getCommandTarget(evt, PageSetupable.class);
		if (target != null) {
			if (((PageSetupable) target).isPageSetupable(evt)) {
				return true;
			}
		}
		return false;
	}
}
