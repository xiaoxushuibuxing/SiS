// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSColor.java

package awt;

import java.awt.Color;
import java.io.*;
import java.util.Vector;
import magus.net.provide.OPDataProvider;
import magus.util.ColorParser;

// Referenced classes of package magus.awt:
//			DCSConditionColor

public class DCSColor
{

	Color defaultColor;
	DCSConditionColor conditionColor;

	public DCSColor()
	{
		defaultColor = Color.black;
		conditionColor = null;
	}

	public DCSColor(Color c)
	{
		defaultColor = c;
		conditionColor = null;
	}

	public DCSColor(Color c, DCSConditionColor color)
	{
		defaultColor = c;
		conditionColor = color;
	}

	public void parseDCSColor(String s)
	{
		s = s.trim();
		int len = s.length();
		int index = s.indexOf(' ');
		if (index < 0)
		{
			defaultColor = ColorParser.getColor(s);
			conditionColor = null;
		} else
		{
			String s1 = s.substring(0, index);
			s1 = s1.trim();
			defaultColor = ColorParser.getColor(s1);
			String s2 = s.substring(index + 1, len);
			conditionColor = new DCSConditionColor();
			conditionColor.parseConditionColor(s2);
		}
	}

	public String toString()
	{
		String s = new String("");
		s = s + ColorParser.toString(defaultColor);
		if (conditionColor != null)
			s = s + " " + conditionColor.toString();
		return s;
	}

	public void setDefaultColor(Color c)
	{
		defaultColor = c;
	}

	public void setConditionColor(DCSConditionColor c1)
	{
		conditionColor = c1;
	}

	public Color getDefaultColor()
	{
		return defaultColor;
	}

	public DCSConditionColor getConditionColor()
	{
		return conditionColor;
	}

	public Color getColor(OPDataProvider opDataProvider)
	{
		if (conditionColor == null)
			return defaultColor;
		Color c = conditionColor.getColor(opDataProvider);
		if (c == null)
			return defaultColor;
		else
			return c;
	}

	public Color[] getDynamicColor()
	{
		if (conditionColor != null)
			return conditionColor.getDynamicColor();
		else
			return null;
	}

	public int getColorIndex(OPDataProvider opDataProvider)
	{
		int i = 0;
		if (conditionColor != null)
			i = conditionColor.getColorIndex(opDataProvider) + 1;
		return i;
	}

	public Vector getProcessPoints()
	{
		if (conditionColor == null)
			return null;
		else
			return conditionColor.getProcessPoints();
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeInt(defaultColor.getRGB());
		if (conditionColor == null)
		{
			out.writeByte(0);
		} else
		{
			out.writeByte(1);
			conditionColor.write(out);
		}
	}

	public void read(DataInputStream in)
		throws IOException
	{
		defaultColor = new Color(in.readInt());
		byte dynFlag = in.readByte();
		if (dynFlag == 1)
		{
			conditionColor = new DCSConditionColor();
			conditionColor.read(in);
		}
	}

	public static void main(String args1[])
	{
	}
}
