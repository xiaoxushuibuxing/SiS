// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSConditionColor.java

package awt;

import java.awt.Color;
import java.io.*;
import java.util.Vector;

import magus.net.provide.OPDataProvider;
import magus.util.ColorParser;
import magus.util.ReadHandle;
import org.apache.log4j.Logger;

// Referenced classes of package magus.awt:
//			DCSCondition
public class DCSConditionColor
{
	public static int MAX_CONDITION = 100;
	public Color dynColor[];
	public DCSCondition cond[];
	public int condNumber;
	private Logger logger = Logger.getLogger(DCSConditionColor.class);

	public DCSConditionColor()
	{
		condNumber = 0;
	}
	public DCSConditionColor(int i)
	{
		cond = new DCSCondition[i];
		dynColor =new Color[i];
		condNumber = i;
	}
	public DCSConditionColor(String s)
	{
		parseConditionColor(s);
	}

	public String toString()
	{
		String s = new String("");
		if (condNumber > 1)
			s = s + "{ ";
		for (int i = 0; i < condNumber; i++)
		{
			s = s + cond[i].toString() + " ";
			if (dynColor!= null && dynColor[i]!=null) {
				s = s + ColorParser.toString(dynColor[i]) + " ";
			} else {
				s = s + ColorParser.toString(Color.BLACK) + " ";
			}
		}

		if (condNumber > 1)
			s = s + "}";
		return s;
	}

	public void parseConditionColor(String cs)
	{
		//logger.info(cs);
		if (cs.length() > 1000)
			System.out.println();
		cs = cs.replaceAll("\\\\n", " ");
		cs = cs.trim();
		//System.out.println("CS："+cs);
		int len = cs.length();
		if (cs.indexOf('{') == 0 && cs.lastIndexOf('}') == len - 1)
		{
			cs = cs.substring(1, len - 1);
			cs = cs.trim();
		}
		String condString[] = new String[MAX_CONDITION];
		String colorName[] = new String[MAX_CONDITION];
		condNumber = 0;
		int status = 0;
		int fromIndex = 0;
		int lastIndex = 0;
		while (condNumber < MAX_CONDITION) 
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
			
			
			
			String s1 = cs.substring(i2 + 1);
			s1 = s1.trim();
			String ss[] = ReadHandle.sscan(s1, 1);
			if (ss[0] == null)
			{
				errorHandle(ss[0]);
				return;
			}
			ss[0] = ss[0].trim();
			len = ss[0].length();
			if (len == 0)
			{
				errorHandle(ss[0]);
				return;
			}
			if ( ss[0].indexOf("(") <0) {
				condNumber++;
				condString[condNumber - 1] = cs.substring(lastIndex, i2 + 1);
				colorName[condNumber - 1] = ss[0];
			} else {
				continue;
			}
			
			lastIndex = cs.indexOf('(', fromIndex);
			if (lastIndex == -1)
				break;
			fromIndex = lastIndex;
		}
		cond = new DCSCondition[condNumber];
		dynColor = new Color[condNumber];
		for (int i = 0; i < condNumber; i++)
		{
			cond[i] = new DCSCondition(condString[i]);
			if (cond[i].getConditionNumber() <= 0)
			{
				errorHandle(condString[i]);
				return;
			}
			//logger.info(colorName[i]);
			Color color =new Color(0);
			try {
				color = ColorParser.getColor(colorName[i]);
			} catch (Exception e) {
				
			}
			
			//logger.info(color.toString());
			dynColor[i] = color;
		}
		System.out.println();
	}
	public void errorHandle(String condString)
	{
		condNumber = 0;
		System.out.println("error condition: " + condString);
	}
	public Color getColor(OPDataProvider opDataProvider)
	{
		for (int i = 0; i < condNumber; i++)
		{
			String s = cond[i].toString();
			if (s.indexOf("?D:") < 0)
			{
				int value = cond[i].getCondition(opDataProvider);
				if (value == 1)
					return dynColor[i];
			}
		}

		return null;
	}

	public Color[] getDynamicColor()
	{
		return dynColor;
	}

	public int getColorIndex(OPDataProvider opDataProvider)
	{
		for (int i = 0; i < condNumber; i++)
		{
			int value = cond[i].getCondition(opDataProvider);
			if (value == 1)
				return i;
		}

		return -1;
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

	public DCSConditionColor Clone()
	{
		DCSConditionColor condColor = new DCSConditionColor(toString());
		return condColor;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeInt(condNumber);
		for (int i = 0; i < condNumber; i++)
		{
			if (dynColor[i] == null)
				out.writeInt(Color.BLACK.getRGB());
			else
				out.writeInt(dynColor[i].getRGB());
			out.writeUTF(cond[i].toString());
		}

	}

	public void read(DataInputStream in)
		throws IOException
	{
		condNumber = in.readInt();
		dynColor = new Color[condNumber];
		cond = new DCSCondition[condNumber];
		for (int i = 0; i < condNumber; i++)
		{
			dynColor[i] = new Color(in.readInt());
			cond[i] = new DCSCondition();
			cond[i].parse(in.readUTF());
		}

	}

	public static void main(String args1[])
	{

	}

}
