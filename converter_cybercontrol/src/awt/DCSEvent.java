// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSEvent.java

package awt;

import java.io.*;

public class DCSEvent
{

	public static final byte NOEVENT = 0;
	public static final byte OPEN_WIN = 1;
	public static final byte CLOSE_WIN = 2;
	public static final byte CHANGE_PAGE = 3;
	public static final byte LAST_PAGE = 4;
	public static final byte NEXT_PAGE = 5;
	public static final byte PRINT = 6;
	public static final byte TARGET_STRING = 7;
	public static final byte OPEN_DEVICE = 8;
	public static final byte CLOSE_DEVICE = 9;
	public static final byte INCREASE = 10;
	public static final byte DECREASE = 11;
	public static final byte MANU = 12;
	public static final byte AUTO = 13;
	public static final byte SET_VALUE = 8;
	public static final byte OPEN_SUBWIN_1 = 21;
	public static final byte OPEN_SUBWIN_2 = 22;
	byte type;
	String targetString;
	short pokePrgms;
	short ox;
	short oy;
	short pts_num;
	short ef_num;
	String points[];
	String entrys[];

	public DCSEvent()
	{
		ox = 40;
		oy = 80;
		type = 0;
		targetString = null;
	}

	public DCSEvent(byte t)
	{
		ox = 40;
		oy = 80;
		type = t;
		targetString = null;
	}

	public DCSEvent(byte t, String s)
	{
		ox = 40;
		oy = 80;
		type = t;
		targetString = s;
	}

	public byte getType()
	{
		return type;
	}

	public String getString()
	{
		return targetString;
	}

	public String toString()
	{
		String s = "";
		s = type + " " + targetString;
		return s;
	}

	public void action()
	{
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(type);
		out.writeUTF(targetString);
	}

	public void read(DataInputStream in)
		throws IOException
	{
		type = in.readByte();
		targetString = in.readUTF();
	}
}
