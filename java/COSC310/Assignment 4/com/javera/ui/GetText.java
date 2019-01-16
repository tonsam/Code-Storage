//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

/**
 * GetText interface provides access to the getText() method used by
 * the default renderers for JvList and JvComboBox to get the text to be
 * displayed as an entry
 * 
 * @author David T. Smith
 */
public interface GetText {
	/**
	 * Get the text to be displayed by the renderer
	 * @ return the text to be displayed
	 */
	public String getText();
}