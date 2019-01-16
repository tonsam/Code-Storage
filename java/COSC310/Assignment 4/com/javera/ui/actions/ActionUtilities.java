package com.javera.ui.actions;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

public class ActionUtilities {
	public static JFrame getFrame(EventObject e) {
		if (!(e.getSource() instanceof Component)) {
			return null;
		}

		Component comp = (Component) e.getSource();

		JFrame frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, comp);

		while (frame == null) {
			JPopupMenu popupMenu =
				(JPopupMenu) SwingUtilities.getAncestorOfClass(JPopupMenu.class, comp);

			if (popupMenu == null) {
				return null;
			}

			comp = popupMenu.getInvoker();

			frame = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class, comp);
		}

		return frame;
	}

	public static JDialog getDialog(EventObject e) {
		if (!(e.getSource() instanceof Component)) {
			return null;
		}

		Component comp = (Component) e.getSource();

		JDialog dialog = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, comp);

		while (dialog == null) {
			JPopupMenu popupMenu =
				(JPopupMenu) SwingUtilities.getAncestorOfClass(JPopupMenu.class, comp);

			if (popupMenu == null) {
				return null;
			}

			comp = popupMenu.getInvoker();

			dialog = (JDialog) SwingUtilities.getAncestorOfClass(JDialog.class, comp);
		}

		return dialog;
	}

	public static Object getCommandTarget(EventObject e, Class commandClass) {
		Object target = getDialog(e);
		
		if (target == null) {
			target = getFrame(e);
		}

		for (;;) {
			if (target == null) {
				return null;
			}

			if (commandClass.isInstance(target)) {
				return target;
			}

			if (!(target instanceof Selectable))
				return null;

			target = ((Selectable) target).getSelectedItem();
		}
	}
}
