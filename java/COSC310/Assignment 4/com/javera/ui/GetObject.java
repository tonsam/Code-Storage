//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

/**
 * GetObject interface provides access to the getObject() method used by
 * the various renderers to obtain a contained object
 * 
 * @author David T. Smith
 */
public interface GetObject {
    /**
     * Get the contained object
     * @return the contained object
     */
    public Object getObject();
}