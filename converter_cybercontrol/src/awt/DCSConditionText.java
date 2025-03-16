// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSConditionText.java

package awt;

import java.io.*;
import java.util.Vector;
import magus.net.provide.OPDataProvider;

public class DCSConditionText
{
	static int MAX_CONDITION = 30;
	String dynText[];
	DCSCondition cond[];
	int condNumber;

	public DCSConditionText()
	{
		condNumber = 0;
	}

	public DCSConditionText(String s)
	{
		parseConditionText(s);
	}

	public String toString()
	{
		String s = new String("");
		if (condNumber > 1)
			s = s + "{ ";
		for (int i = 0; i < condNumber; i++)
		{
			s = s + cond[i].toString() + " ";
			s = s + "\"" + dynText[i] + "\" ";
		}

		if (condNumber > 1)
			s = s + "}";
		return s;
	}

	public void parseConditionText(String cs)
	{
		//System.out.println("进入parseConditionText方法");
		cs = cs.replace('\n', ' ');
		cs = cs.trim();
		int len = cs.length();
		if (cs.indexOf('{') == 0 && cs.lastIndexOf('}') == len - 1)
		{
			cs = cs.substring(1, len - 1);
			cs = cs.trim();
		}
		//System.out.println(cs);
		String condString[] = new String[MAX_CONDITION];
		String condText[] = new String[MAX_CONDITION];
		condNumber = 0;
		int status = 0;
		int fromIndex = 0;
		int lastIndex = 0;
		String s[] = cs.split("\"");
		condNumber = s.length / 2;
		cond = new DCSCondition[condNumber];
		dynText = new String[condNumber];
		for (int i = 0; i < condNumber; i++)
		{
			cond[i] = new DCSCondition(s[2 * i]);
			if (cond[i].getConditionNumber() <= 0)
			{
				System.out.println("errorHandle");
				errorHandle(s[2 * i]);
				return;
			}
			dynText[i] = s[2 * i + 1];
		}

	}
	public void errorHandle(String condString)
	{
		condNumber = 0;
		System.out.println("error condition: " + condString);
	}

	public String getText(OPDataProvider opDataProvider)
	{
		for (int i = 0; i < condNumber; i++)
		{
			int value = cond[i].getCondition(opDataProvider);
			if (value == 1)
				return dynText[i];
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

	public DCSConditionText Clone()
	{
		DCSConditionText condText = new DCSConditionText(toString());
		return condText;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeInt(condNumber);
		for (int i = 0; i < condNumber; i++)
		{
			out.writeUTF(dynText[i]);
			out.writeUTF(cond[i].toString());
		}

	}

	public void read(DataInputStream in)
		throws IOException
	{
		condNumber = in.readInt();
		dynText = new String[condNumber];
		cond = new DCSCondition[condNumber];
		for (int i = 0; i < condNumber; i++)
		{
			dynText[i] = in.readUTF();
			cond[i] = new DCSCondition();
			cond[i].parse(in.readUTF());
		}
		
	}
	public String getText()
	  {
	    for (int i = 0; i < this.condNumber; i++)
	    {
	      int j = this.cond[i].getCondition();
	      if (j == 1)
	        return this.dynText[i];
	    }
	    return null;
	  }
	 public static void main(String[] paramArrayOfString)
	  {
	    String str1 = new String("{(W3.YBS_UNIT3.FMAFL25F DS = SET) (W3.YBS_UNIT3.FMAFL25 DS = OFF)  \"NO\" }");
	    DCSConditionText localDCSConditionText = new DCSConditionText(str1);
	    String str2 = localDCSConditionText.getText();
	    System.out.println(localDCSConditionText.condNumber);
	    if (str2 != null)
	      System.out.println("Terminal Text " + str2);
	    else
	      System.out.println("No Cond text");
	    System.out.println("**********************");
	    System.out.println("CONDITION COLOR: " + localDCSConditionText.toString());
	    System.out.println("**********************");
	  }

}
