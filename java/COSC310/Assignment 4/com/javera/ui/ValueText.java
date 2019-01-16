package com.javera.ui;

// Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
// This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

/**
 * @author dtsmith
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ValueText implements GetText, GetValue {

	private String text;

	private String value;

	/**
	 * 
	 */
	public ValueText(String value, String text) {
		this.value = value;
		this.text = text;
	}

	/* (non-Javadoc)
	 * @see com.javera.ui.field.GetText#getText()
	 */
	public String getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see com.javera.ui.field.GetValue#getValue()
	 */
	public Object getValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
