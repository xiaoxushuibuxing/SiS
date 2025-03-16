// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DLHelper.java

package magus.util;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class DLHelper
{

	private static DLHelper dataLoadHelper;

	private DLHelper()
	{
	}

	public static DLHelper getInstance()
	{
		if (dataLoadHelper == null)
			dataLoadHelper = new DLHelper();
		return dataLoadHelper;
	}

	public URL getURL(String classPathLocation)
	{
		return getClass().getResource(classPathLocation);
	}

	public ImageIcon getImageIcon(String classPathLocation)
	{
		return new ImageIcon(getURL("/icons/" + classPathLocation));
	}

	public Image getImage(String classPathLocation)
	{
		return getImageIcon(classPathLocation).getImage();
	}
}
