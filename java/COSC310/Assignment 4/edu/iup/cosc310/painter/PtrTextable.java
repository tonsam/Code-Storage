package edu.iup.cosc310.painter;

/**
 * Interface identifying that a draw object has a text property.  This is used
 * by the paint panel editor.
 * 
 * @author David T. Smith
 */
public interface PtrTextable {
	public String getText();
	public void setText(String text);
}
