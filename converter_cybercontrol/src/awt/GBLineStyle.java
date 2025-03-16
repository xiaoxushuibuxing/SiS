// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GBLineStyle.java

package awt;

import java.awt.*;

public class GBLineStyle
{

	public static final byte SOLID = 0;
	public static final byte DASHED = 1;
	public static final byte SMALL_DASH = 2;
	public static final byte BIG_DOTS = 3;
	public static final byte DOTTED = 4;
	public static final byte DASH_DOT = 5;
	public static final byte NEAR_SOLID = 6;
	public static final byte SPARSE = 7;
	public static final byte DOT_DASH = 8;
	public static final byte KATHLINE = 9;
	static final byte MIT_DASH1 = 10;
	static final byte MIT_DASH2 = 11;
	static final byte MIT_DASH3 = 12;
	static final byte MIT_DASH4 = 13;
	static final byte MIT_DASH5 = 14;
	static final String styleName[] = {
		"SOLID", "DASHED", "SMALL_DASH", "BIG_DOTS", "DOTTED", "DASH_DOT", "NEAR_SOLID", "SPARSE", "DOT_DASH", "KATHLINE", 
		"MIT_DASH1", "MIT_DASH2", "MIT_DASH3", "MIT_DASH4", "MIT_DASH5"
	};
	static final byte TOTAL_PATTERN = 15;
	static final float dash[][] = {
		{
			8F
		}, {
			4F
		}, {
			2.0F
		}, {
			1.0F
		}, {
			6F, 1.0F, 1.0F, 6F
		}, {
			10F, 2.0F
		}, {
			1.0F, 4F, 1.0F, 8F
		}, {
			1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 8F, 4F
		}, {
			7F, 7F, 2.0F
		}, {
			3F, 6F
		}, {
			5F, 5F
		}, {
			7F, 5F
		}, {
			9F, 5F, 3F, 5F
		}, {
			20F, 5F, 6F, 5F
		}
	};

	public GBLineStyle()
	{
	}

	public static void setLine(Graphics g, int lineStyle, int lineWidth)
	{
		Graphics2D g2 = (Graphics2D)g;
		BasicStroke b;
		switch (lineStyle)
		{
		case 0: // '\0'
			b = new BasicStroke(lineWidth, 0, 0);
			break;

		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
		case 8: // '\b'
		case 9: // '\t'
		case 10: // '\n'
		case 11: // '\013'
		case 12: // '\f'
		case 13: // '\r'
		case 14: // '\016'
			b = new BasicStroke(lineWidth, 0, 0, 10F, dash[lineStyle - 1], 0.0F);
			break;

		default:
			b = new BasicStroke();
			break;
		}
		g2.setStroke(b);
	}

	public static byte getStyle(String sname)
	{
		for (byte i = 0; i < 15; i++)
			if (sname.compareTo(styleName[i]) == 0)
				return i;

		return 0;
	}

	public static String styleToString(byte i)
	{
		String name = "NONAME";
		if (i >= 0 && i < 15)
			name = styleName[i];
		return name;
	}

	public static String widthToString(byte i)
	{
		String ws = "0 pt";
		if (i >= 0 && i < 15)
			ws = i + " " + "pt";
		return ws;
	}

}
