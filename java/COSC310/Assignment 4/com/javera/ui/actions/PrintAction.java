package com.javera.ui.actions;

import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.Action;

import com.javera.ui.IconManager;

public class PrintAction extends ManagedStateAction {
	private static PrintAction printAction = new PrintAction();

	private PrintAction() {
		super("Print...", IconManager.getIcon("Print.gif"));
		putValue(Action.MNEMONIC_KEY, new Integer('i'));
		putValue(Action.SHORT_DESCRIPTION, "Print");
	}

	public static PrintAction getAction() {
		return printAction;
	}

	public void actionPerformed(ActionEvent e) {
		Printable printable =
			(Printable) ActionUtilities.getCommandTarget(e, Printable.class);

		if (printable != null)
			printable.print(e);
	}

	public boolean isTargetEnabled(EventObject evt) {
		Object target = ActionUtilities.getCommandTarget(evt, Printable.class);
		if (target != null) {
			if (((Printable) target).isPrintable(evt)) {
				return true;
			}
		}
		return false;
	}
}
