// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSXImageLib.java

package awt;

import java.io.*;
import java.net.URL;
import java.util.Hashtable;

// Referenced classes of package magus.awt:
//			DCSXImageData

public class DCSXImageLib
{

	public static final int MAX_PIXEL_WIDTH = 100;
	public static final int MAX_PIXEL_HEIGHT = 100;
	private Hashtable bmpLIB;
	private static DCSXImageLib mySelf = null;

	public DCSXImageLib()
	{
		bmpLIB = null;
		bmpLIB = new Hashtable();
	}

	public static DCSXImageLib getInstance()
	{
		if (mySelf == null)
			mySelf = new DCSXImageLib();
		return mySelf;
	}

	public DCSXImageData loadBitmapFile(String name)
	{
		DCSXImageData bmp = (DCSXImageData)bmpLIB.get(name);
		if (bmp != null)
			return bmp;
		String ximgname = name;
		URL imageURL = null;
		try
		{
			imageURL = new URL(ximgname);
		}
		catch (IOException e)
		{
			System.out.println("DCSBitmapLib: can't open " + ximgname);
			return null;
		}
		try
		{
			InputStream inStream = imageURL.openStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inStream));
			bmp = read(in);
			in.close();
		}
		catch (IOException e)
		{
			System.out.println("DCSXImageLib: IOException read " + ximgname);
		}
		if (bmp != null)
			bmpLIB.put(name, bmp);
		return bmp;
	}

	public DCSXImageData read(BufferedReader in)
		throws IOException, NumberFormatException
	{
		String s1 = in.readLine();
		String s2 = in.readLine();
		String s3 = in.readLine();
		String s4 = in.readLine();
		String s5 = in.readLine();
		if (s5 == null)
			return null;
		int i1 = s1.indexOf("_width");
		int i2 = s2.indexOf("_height");
		int i5 = s5.indexOf("{");
		if (i1 < 0 || i2 < 0 || i5 < 0)
			return null;
		int pw = Integer.parseInt(s1.substring(i1 + 6).trim());
		int ph = Integer.parseInt(s2.substring(i2 + 7).trim());
		int numpix = pw * ph;
		if (pw < 1 || pw > 100 || ph < 1 || ph > 100 || numpix % 8 != 0)
			return null;
		DCSXImageData bmp = new DCSXImageData(pw, ph);
		String head = "," + s5.substring(i5 + 1);
		boolean isEnd = false;
		int i = 0;
		do
		{
			String buffer = in.readLine();
			if (buffer == null)
				return null;
			buffer = head + buffer;
			buffer = buffer.trim();
			int len = buffer.length();
			if (len == 0 || buffer.charAt(0) != ',')
				return null;
			if (buffer.charAt(len - 1) == ',')
			{
				head = ",";
				buffer = buffer.substring(1, len - 1);
			} else
			if (buffer.endsWith("};"))
			{
				buffer = buffer.substring(1, len - 2);
				isEnd = true;
			} else
			{
				head = "";
				buffer = buffer.substring(1, len);
			}
			String s[] = buffer.split(",");
			if (i + s.length * 8 > numpix)
				return null;
			for (int j = 0; j < s.length; j++)
			{
				s[j] = s[j].trim();
				if (!s[j].startsWith("0x"))
					return null;
				byte c8 = (byte)Integer.parseInt(s[j].substring(2), 16);
				for (int k = 0; k < 8; k++)
					bmp.pixel[i++] = (byte)(c8 >> k & 1);

			}

		} while (!isEnd);
		if (i != numpix)
			bmp = null;
		return bmp;
	}

}
