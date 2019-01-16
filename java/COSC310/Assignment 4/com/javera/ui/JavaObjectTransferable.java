// Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
// This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author dtsmith
 */
public class JavaObjectTransferable implements Transferable, Serializable {
	private transient Object javaObject;
	private ArrayList dataFlavors = new ArrayList();
	
	public JavaObjectTransferable(Object javaObject) {
		this.javaObject = javaObject;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return (DataFlavor[]) dataFlavors.toArray(new DataFlavor[dataFlavors.size()]);
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return dataFlavors.contains(flavor);
	}

	public boolean addDataFlavor(DataFlavor flavor) {
		return dataFlavors.add(flavor);
	}
	
	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
		if (isDataFlavorSupported(flavor)) {
			return javaObject;
		} else {
			return null;
		}
	}
}
