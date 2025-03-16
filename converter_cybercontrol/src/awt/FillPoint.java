// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FillPoint.java

package awt;

import java.awt.Color;
import magus.net.provide.OPDataProvider;

public class FillPoint
{

	String pointname;
	String FD;
	Color fillColor;

	public FillPoint()
	{
		fillColor = Color.blue;
	}

	public FillPoint(String name, String fd)
	{
		fillColor = Color.blue;
		pointname = name;
		FD = fd;
	}

	public FillPoint(String name, String fd, Color c)
	{
		fillColor = Color.blue;
		pointname = name;
		FD = fd;
		fillColor = c;
	}

	public void setName(String s)
	{
		pointname = s;
	}

	public void setFD(String fd)
	{
		FD = fd;
	}

	public void setColor(Color c)
	{
		fillColor = c;
	}

	public String getName()
	{
		return pointname;
	}

	public String getFD()
	{
		return FD;
	}

	public Color getColor()
	{
		return fillColor;
	}

	public double getValue(OPDataProvider opDataProvider)
	{
		return opDataProvider.getValueFloat(pointname, FD);
	}
}
