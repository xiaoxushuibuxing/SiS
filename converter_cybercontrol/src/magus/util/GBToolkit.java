// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GBToolkit.java

package magus.util;

import java.awt.*;

public class GBToolkit
{

	public GBToolkit()
	{
	}

	public static void setToScreenCenter(Component com)
	{
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		com.setLocation((screen.width - com.getWidth()) / 2, (screen.height - com.getHeight()) / 2);
	}

	public static void setSizeByScale(Component com, float factor)
	{
		if (factor < 0.0F || factor > 1.0F)
			factor = 0.75F;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		com.setSize((int)((float)screen.width * factor), (int)((float)screen.height * factor));
	}

	public static void setBoundsByScale(Component com, float factor)
	{
		if (factor < 0.0F || factor > 1.0F)
			factor = 0.75F;
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		com.setBounds((int)(((1.0F - factor) / 2.0F) * (float)screen.width), (int)(((1.0F - factor) / 2.0F) * (float)screen.height), (int)((float)screen.width * factor), (int)((float)screen.height * factor));
	}
}
