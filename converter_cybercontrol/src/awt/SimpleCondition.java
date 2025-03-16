// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SimpleCondition.java

package awt;

import java.util.StringTokenizer;
import java.util.Vector;
import magus.net.DBPoint;
import magus.net.OPNetwork;
import magus.net.provide.OPDataProvider;

// Referenced classes of package magus.awt:
//			ICondition, DCSMask

public class SimpleCondition
	implements ICondition
{

	int type;
	String name;
	String fld;
	int relation;
	String right;
	String rightFD;
	float rightVal;
	DCSMask rightMask;
	DBPoint leftPoint;
	DBPoint rightPoint;
	String nameTemp;
	public SimpleCondition()
	{
	}

	public SimpleCondition(String s)
	{
		parse(s);
	}

	public Vector getProcessPoints()
	{
		Vector v = new Vector();
		v.add(name);
		if (type == 2 && !right.equals(name))
			v.add(right);
		return v;
	}

	public boolean parse(String cs)
	{
		cs = cs.trim().toUpperCase();
		int len = cs.length();
		if (cs.indexOf('(') == 0 && cs.indexOf(')') == len - 1)
			cs = cs.substring(1, len - 1);
		else
		if (cs.indexOf('(') >= 0 || cs.indexOf(')') > 0)
		{
			errorHandle(cs, -1);
			return false;
		}
		relation = getRelationType(cs);
		if (relation < 0)
		{
			errorHandle(cs, -2);
			return false;
		}
		String ls = "";
		String rs = "";
		int sn = 0;
		switch (relation)
		{
		case 0: // '\0'
		{
			int id = cs.indexOf("=");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 1).trim();
			break;
		}

		case 1: // '\001'
		{
			int id = cs.indexOf(">");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 1).trim();
			break;
		}

		case 2: // '\002'
		{
			int id = cs.indexOf("<");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 1).trim();
			break;
		}

		case 3: // '\003'
		{
			int id = cs.indexOf("!=");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 2).trim();
			break;
		}

		case 4: // '\004'
		{
			int id = cs.indexOf(">=");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 2).trim();
			break;
		}

		case 5: // '\005'
		{
			int id = cs.indexOf("<=");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 2).trim();
			break;
		}
		}
		StringTokenizer stoken = new StringTokenizer(ls);
		String ss[] = new String[5];
		sn = 0;
		while (stoken.hasMoreTokens()) 
		{
			ss[sn] = stoken.nextToken();
			if (++sn > 4)
				break;
		}
		if (sn != 2)
		{
			errorHandle(cs, -3);
			return false;
		}
		name = ss[0];
		nameTemp=name;
		fld = ss[1];
		leftPoint = OPNetwork.getPointByGlobalName(name);
		stoken = new StringTokenizer(rs);
		ss = new String[5];
		sn = 0;
		while (stoken.hasMoreTokens()) 
		{
			ss[sn] = stoken.nextToken();
			if (++sn > 4)
				break;
		}
		if (sn < 1 || sn > 2)
		{
			errorHandle(cs, -4);
			return false;
		}
		type = getConditionType(ss, sn);
		if (type < 0)
		{
			errorHandle(cs, -5);
			return false;
		}
		switch (type)
		{
		case 0: // '\0'
			rightVal = Float.valueOf(ss[0]).floatValue();
			break;

		case 1: // '\001'
			if (relation==3) {
				relation=0;
				rightMask = DCSMask.getTransMaskByName(ss[0]);
			} else {
				rightMask = DCSMask.getMaskByName(ss[0]);
			}
			
			
			break;

		case 2: // '\002'
			right = ss[0];
			rightFD = ss[1];
			rightPoint = OPNetwork.getPointByGlobalName(name);
			break;
		}
		return true;
	}

	private void errorHandle(String cs, int eid)
	{
		type = -1;
		System.out.println("Error: " + eid + ". please check the condition string!");
		System.out.println("Conition: " + cs + "]");
	}

	private int getConditionType(String rs[], int num)
	{
		int tp = -1;
		if (num == 1)
		{
			if (isAConstant(rs[0]))
				tp = 0;
			else
			if (DCSMask.isAMask(rs[0]))
				tp = 1;
		} else
		if (DCSMask.isAMask(rs[1]))
			tp = 2;
		return tp;
	}

	public int getRelationType(String str)
	{
		int opr = -1;
		if (str.indexOf("<=") > 0)
			opr = 5;
		else
		if (str.indexOf(">=") > 0)
			opr = 4;
		else
		if (str.indexOf("!=") > 0)
			opr = 3;
		else
		if (str.indexOf("<") > 0)
			opr = 2;
		else
		if (str.indexOf(">") > 0)
			opr = 1;
		else
		if (str.indexOf("=") > 0)
			opr = 0;
		return opr;
	}

	public String getRelation(int i)
	{
		String rel = "";
		switch (i)
		{
		case 0: // '\0'
			rel = "=";
			break;

		case 1: // '\001'
			rel = ">";
			break;

		case 2: // '\002'
			rel = "<";
			break;

		case 3: // '\003'
			rel = "!=";
			break;

		case 4: // '\004'
			rel = ">=";
			break;

		case 5: // '\005'
			rel = "<=";
			break;

		default:
			rel = "=";
			break;
		}
		return rel;
	}

	private boolean isAConstant(String s)
	{
		boolean flag = true;
		float value;
		try
		{
			value = Float.parseFloat(s);
		}
		catch (Exception nfe)
		{
			flag = false;
		}
		return flag;
	}

	public String toString()
	{
		String s = "";
		if (name== null) {
			return s;
		}
		
		if (name.indexOf(nameTemp) !=-1) {
			
			if (type == 0)
				s = name + " " + fld + " " + getRelation(relation) + " " + rightVal;
			else
				if (type == 1) {
					//System.out.println(rightMask.getName()+"------------------------");
					if(rightMask.getName().equals("SET") || rightMask.getName().equals("RESET")) {
						fld ="DS";
					}
					s = name + " " + fld + " " + getRelation(relation) + " " + rightMask.getName();
				}
			else
			if (type == 2)
				s = name + " " + fld + " " + getRelation(relation) + " " + right + " " + rightFD;
			s = "(" + s + ")";
		} else {
			if (nameTemp.indexOf("_MODE")!=-1 && name.endsWith("MAN")) {
				if (type == 0)
					s = name + " " + fld + " " + getRelation(relation) + " " + rightVal;
				else
				if (type == 1) {
					if (rightMask == DCSMask.getMaskByName("SET")) {
						s = name + " " + fld + " " + getRelation(relation) + " " + "RESET";
					} else
						s = name + " " + fld + " " + getRelation(relation) + " " + rightMask.getName();
				}
					
				else
				if (type == 2)
					s = name + " " + fld + " " + getRelation(relation) + " " + right + " " + rightFD;
				s = "(" + s + ")";
				
				
			} else {
				if (type == 0)
					s = name + " " + fld + " " + getRelation(relation) + " " + rightVal;
				else
				if (type == 1) {
					//System.out.println(rightMask.getName()+"------------------------");
					if(rightMask.getName().equals("SET") || rightMask.getName().equals("RESET")) {
						fld ="DS";
					}
					s = name + " " + fld + " " + getRelation(relation) + " " + rightMask.getName();
				}
					
				else
				if (type == 2)
					s = name + " " + fld + " " + getRelation(relation) + " " + right + " " + rightFD;
				s = "(" + s + ")";
			}
		}
		
		return s;
	}

	private double getValueFloat(String name, String fd, OPDataProvider opDataProvider)
	{
		double value = opDataProvider.getValueFloat(name, fd);
		return value;
	}

	private int getValueInt(String name, String fd, OPDataProvider opDataProvider)
	{
		int value = opDataProvider.getValueInt(name, fd);
		if (value == -1)
			value &= 1;
		return value;
	}

	private double getValueFloat(DBPoint dbpoint, String fd, OPDataProvider opDataProvider)
	{
		if (dbpoint == null)
		{
			return 0.0D;
		} else
		{
			String pname = dbpoint.getGlobalName();
			double value = opDataProvider.getValueFloat(pname, fd);
			return value;
		}
	}

	private int getValueInt(DBPoint dbpoint, String fd, OPDataProvider opDataProvider)
	{
		if (dbpoint == null)
			return 0;
		String pname = dbpoint.getGlobalName();
		int value = opDataProvider.getValueInt(pname, fd);
		if (value == -1)
			value &= 1;
		return value;
	}

	private int compare(int left, int right, int op)
	{
		int value = 0;
		switch (op)
		{
		default:
			break;

		case 0: // '\0'
			if (left == right)
				value = 1;
			break;

		case 1: // '\001'
			if (left > right)
				value = 1;
			break;

		case 2: // '\002'
			if (left < right)
				value = 1;
			break;

		case 3: // '\003'
			if (left != right)
				value = 1;
			break;

		case 4: // '\004'
			if (left >= right)
				value = 1;
			break;

		case 5: // '\005'
			if (left <= right)
				value = 1;
			break;
		}
		return value;
	}

	private int compare(double left, double right, int op)
	{
		int value = 0;
		switch (op)
		{
		default:
			break;

		case 0: // '\0'
			if (left == right)
				value = 1;
			break;

		case 1: // '\001'
			if (left > right)
				value = 1;
			break;

		case 2: // '\002'
			if (left < right)
				value = 1;
			break;

		case 3: // '\003'
			if (left != right)
				value = 1;
			break;

		case 4: // '\004'
			if (left >= right)
				value = 1;
			break;

		case 5: // '\005'
			if (left <= right)
				value = 1;
			break;
		}
		return value;
	}

	public int getCondition(OPDataProvider opDataProvider)
	{
		int result = 0;
		switch (type)
		{
		default:
			break;

		case 0: // '\0'
		{
			double left0 = getValueFloat(leftPoint, fld, opDataProvider);
			result = compare(left0, rightVal, relation);
			break;
		}

		case 1: // '\001'
		{
			int left1 = getValueInt(leftPoint, fld, opDataProvider);
			int right1 = rightMask.getValue();
			int msk1 = rightMask.getMask();
			if ((left1 & msk1) == right1)
				result = 1;
			break;
		}

		case 2: // '\002'
		{
			double left0 = getValueFloat(leftPoint, fld, opDataProvider);
			double right0 = getValueFloat(rightPoint, rightFD, opDataProvider);
			result = compare(left0, right0, relation);
			break;
		}
		}
		return result;
	}

	public static void main(String args1[])
	{
	}

	@Override
	public int getCondition() {
		// TODO Auto-generated method stub
		return 0;
	}
}
