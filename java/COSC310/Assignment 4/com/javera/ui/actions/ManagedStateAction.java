package com.javera.ui.actions;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.Icon;

public abstract class ManagedStateAction extends AbstractAction {
	private static ArrayList managedActions = new ArrayList();

	public ManagedStateAction() {
		super();
		managedActions.add(this);
		this.setEnabled(true);
	}

	public ManagedStateAction(String label) {
		super(label);
		managedActions.add(this);
		this.setEnabled(true);
	}

	public ManagedStateAction(String label, Icon icon) {
		super(label, icon);
		managedActions.add(this);
		this.setEnabled(true);
	}

	public abstract boolean isTargetEnabled(EventObject evt);

	public static void setStateFor(EventObject evt) {
		for (Iterator iter = managedActions.iterator(); iter.hasNext();) {
			ManagedStateAction action = (ManagedStateAction) iter.next();
			action.setEnabled(action.isTargetEnabled(evt));
		}
	}

}