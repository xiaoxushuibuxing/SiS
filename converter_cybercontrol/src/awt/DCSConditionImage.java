// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSConditionImage.java

package awt;

import java.io.*;
import java.util.Vector;
import magus.net.provide.OPDataProvider;
import magus.util.ReadHandle;

// Referenced classes of package magus.awt:
//			DCSCondition

public class DCSConditionImage
{

	static int MAX_CONDITION = 30;
	String dynImage[];
	DCSCondition cond[];
	int condNumber;

	public DCSConditionImage()
	{
		condNumber = 0;
	}

	public DCSConditionImage(String s)
	{
		parseConditionImage(s);
	}

	public String toString()
	{
		String s = new String("");
		if (condNumber > 1)
			s = s + "{ ";
		for (int i = 0; i < condNumber; i++)
		{
			s = s + cond[i].toString() + " ";
			s = s + " " + dynImage[i] + " ";
		}

		if (condNumber > 1)
			s = s + "}";
		return s;
	}

	public void parseConditionImage(String cs)
	{
		cs = cs.replace('\n', ' ');
		cs = cs.trim();
		int len = cs.length();
		if (cs.indexOf('{') == 0 && cs.lastIndexOf('}') == len - 1)
		{
			cs = cs.substring(1, len - 1);
			cs = cs.trim();
		}
		String condString[] = new String[MAX_CONDITION];
		String imgName[] = new String[MAX_CONDITION];
		condNumber = 0;
		int status = 0;
		int fromIndex = 0;
		int lastIndex = 0;
		do
		{
			int i1 = cs.indexOf('(', fromIndex);
			int i2 = cs.indexOf(')', fromIndex);
			if (i2 == -1)
				break;
			if (i1 < i2 && i1 != -1)
			{
				status++;
				fromIndex = i1 + 1;
			} else
			{
				status--;
				fromIndex = i2 + 1;
			}
			if (status != 0)
				continue;
			condNumber++;
			condString[condNumber - 1] = cs.substring(lastIndex, i2 + 1);
			String s1 = cs.substring(i2 + 1);
			s1 = s1.trim();
			String ss[] = ReadHandle.sscan(s1, 1);
			if (ss[0] == null)
			{
				errorHandle();
				return;
			}
			ss[0] = ss[0].trim();
			len = ss[0].length();
			if (len == 0)
			{
				errorHandle();
				return;
			}
			imgName[condNumber - 1] = ss[0];
			lastIndex = cs.indexOf('(', fromIndex);
			if (lastIndex == -1)
				break;
			fromIndex = lastIndex;
		} while (true);
		cond = new DCSCondition[condNumber];
		dynImage = new String[condNumber];
		for (int i = 0; i < condNumber; i++)
		{
			cond[i] = new DCSCondition(condString[i]);
			if (cond[i].getConditionNumber() <= 0)
			{
				errorHandle();
				return;
			}
			dynImage[i] = imgName[i];
		}

	}

	public void errorHandle()
	{
		condNumber = 0;
		System.out.println("Please check the Condition Color string!");
	}

	public String getImage(OPDataProvider opDataProvider)
	{
		for (int i = 0; i < condNumber; i++)
		{
			int value = cond[i].getCondition(opDataProvider);
			if (value == 1)
				return dynImage[i];
		}

		return null;
	}

	public Vector getProcessPoints()
	{
		if (condNumber == 0)
			return null;
		Vector points = new Vector();
		for (int i = 0; i < condNumber; i++)
		{
			Vector v = cond[i].getProcessPoints();
			if (v != null)
			{
				for (int j = 0; j < v.size(); j++)
				{
					String pointname = (String)v.elementAt(j);
					if (!points.contains(pointname))
						points.add(pointname);
				}

			}
		}

		return points;
	}

	public DCSConditionImage Clone()
	{
		DCSConditionImage condImage = new DCSConditionImage(toString());
		return condImage;
	}

	public void write(DataOutputStream dataoutputstream)
		throws IOException
	{
	}

	public void read(DataInputStream datainputstream)
		throws IOException
	{
	}

	public static void main(String args1[])
	{
	}

}
