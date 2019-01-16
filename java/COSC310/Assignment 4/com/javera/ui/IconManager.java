//Copyright 2004, (c) Javera Software, LLC. as an unpublished work.  All rights reserved world-wide.
//This is a proprietary trade secret of Javera Software LLC.  Use restricted to licensing terms.

package com.javera.ui;

import java.net.*;
import java.util.Hashtable;
import javax.swing.ImageIcon;

/**
 * IconManager provides a convenience class to load icons.
 * 
 * @author David T. Smith
 */
public class IconManager {
  static ClassLoader classLoader = IconManager.class.getClassLoader();
  static Hashtable   iconTable   = new Hashtable();

  public static ImageIcon getIcon(String iconName)
  {
      return getImageIcon("images/" + iconName);
  }

  static public ImageIcon getImageIcon(String iconName)
  {
      ImageIcon icon = (ImageIcon) iconTable.get(iconName);

      if (icon == null)
      {
          URL url = classLoader.getResource(iconName);

          if (url != null)
              icon = new ImageIcon(url);
          else
              icon = new ImageIcon("x.gif");

          iconTable.put(iconName, icon);
      }

      return icon;
  }
}
