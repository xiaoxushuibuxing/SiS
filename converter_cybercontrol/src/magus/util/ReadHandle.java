// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReadHandle.java

package magus.util;

import java.io.*;

public class ReadHandle
{

	public ReadHandle()
	{
	}

	public static String[] sscan(String str, String delim, boolean flag)
	{
		int MAX_LEN = 100;
		String s[] = new String[MAX_LEN];
		int num = 0;
		for (int i = 0; i < MAX_LEN; i++)
		{
			int id = str.indexOf(delim);
			if (id < 0)
			{
				s[i] = str;
				break;
			}
			s[i] = str.substring(0, id);
			str = str.substring(id + delim.length());
			if (flag)
				str = str.trim();
			num++;
		}

		String ss[] = new String[num + 1];
		for (int i = 0; i < num + 1; i++)
			ss[i] = s[i];

		return ss;
	}

	public static String[] sscan(String str, int num)
	{
		int cp = -1;
		str = str.trim();
		String s[] = new String[num];
		try
		{
			for (int i = 0; i < num; i++)
			{
				cp = str.indexOf(' ');
				if (cp == -1)
				{
					s[i] = str;
					break;
				}
				s[i] = str.substring(0, cp);
				str = str.substring(cp + 1);
				str = str.trim();
			}

		}
		catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) { }
		return s;
	}

	public static String[] sscan(String str, int num, char c)
	{
		int cp = -1;
		String s[] = new String[num];
		try
		{
			for (int i = 0; i < num; i++)
			{
				cp = str.indexOf(c);
				if (cp == -1)
				{
					s[i] = str;
					break;
				}
				s[i] = str.substring(0, cp);
				str = str.substring(cp + 1);
			}

		}
		catch (StringIndexOutOfBoundsException stringindexoutofboundsexception) { }
		return s;
	}

	public static void writeCString(DataOutputStream out, String str)
	{
		try
		{
			byte sByte[] = str.getBytes();
			int len = sByte.length;
			out.writeShort((short)len);
			out.write(sByte, 0, len);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public static String readCString(DataInputStream in)
	{
		int i = 0;
		int sLen = 0;
		String tmp = "";
		try
		{
			int len = in.readShort();
			byte sByte[] = new byte[len];
			while (len > 0) 
			{
				int j = in.read(sByte, sLen, len);
				len -= j;
				sLen += j;
				if (++i > 30)
					break;
			}
			tmp = new String(sByte, 0, sLen);
		}
		catch (IOException e)
		{
			System.out.println(e.toString());
			return null;
		}
		return tmp;
	}

	public static String parseString(byte abyte0[], int offset, int len)
	{
		int l = 0;
		for (int i = 0; i < len; i++)
		{
			if (abyte0[offset + i] == 0)
				break;
			l++;
		}

		return new String(abyte0, offset, l);
	}

	public static int unSignedByte(byte abyte0[], int i)
	{
		int j = abyte0[i];
		j &= 0xff;
		return j;
	}

	public static int status16(byte abyte0[], int i)
	{
		return unSignedShort(abyte0, i);
	}

	public static int status32(byte abyte0[], int i)
	{
		return signedLong(abyte0, i);
	}

	public static int unSignedShort(byte buf[], int i)
	{
		return unSignedByte(buf, i + 1) + (unSignedByte(buf, i) << 8);
	}

	public static int signedShort(byte buf[], int i)
	{
		return unSignedByte(buf, i + 1) + (buf[i] << 8);
	}

	public static int signedLong(byte buf[], int i)
	{
		return unSignedByte(buf, i + 3) + (unSignedByte(buf, i + 2) << 8) + (unSignedByte(buf, i + 1) << 16) + (buf[i] << 24);
	}

	public static float signedFloat(byte abyte0[], int i)
	{
		return Float.intBitsToFloat(signedLong(abyte0, i));
	}

	public static int readBytes(DataInputStream in, byte buf[], int offset, int size)
	{
		try
		{
			in.readFully(buf, offset, size);
		}
		catch (IOException _e)
		{
			return -1;
		}
		return 0;
	}
}
