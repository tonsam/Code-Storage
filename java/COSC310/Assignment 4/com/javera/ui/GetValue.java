//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

/**
 * GetValue interface provides access to the getValue() method used 
 * to obtain a value associated with an object
 * 
 * @author David T. Smith
 */
public interface GetValue {
    /**
     * Get the contained object
     * @return the contained object
     */
    public Object getValue();
}