package magus.util;

import java.util.StringTokenizer;

import org.apache.log4j.Category;
import org.apache.log4j.Logger;

import awt.DCSMask;
import magus.net.DBPoint;
import magus.net.OPNetwork;

public class ConditionUtils {
	private Logger logger = Logger.getLogger(ConditionUtils.class);
	private int relation;
	private String right;
	private String rightFD;
	private String name;
	private String nameTemp;
	private String fld;
	private float rightVal =-1;
	private int type;
	private DCSMask rightMask;
	public boolean changeDriverExpr(String cs) {
		cs = cs.trim().toUpperCase();
		logger.info(cs);
		int len = cs.length();
		if (cs.indexOf('(') == 0 && cs.indexOf(')') == len - 1)
			cs = cs.substring(1, len - 1);
		else
		if (cs.indexOf('(') >= 0 || cs.indexOf(')') > 0)
		{
			errorHandle(cs, -1);
			return false;
		}
		logger.info(cs);
		relation = getRelationType(cs);
		logger.info(relation);
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
			int id = cs.indexOf("==");
			ls = cs.substring(0, id).trim();
			rs = cs.substring(id + 2).trim();
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
		System.out.println(ls);
		System.out.println(rs);
		ls = changeLs(ls);
		if (ls == null)
		{
			errorHandle(cs, -2);
			return false;
		}
		StringTokenizer stoken = new StringTokenizer(ls);
		String ss[] = new String[5];
		sn = 0;
		logger.info(ss[0]);
		while (stoken.hasMoreTokens()) 
		{
			ss[sn] = stoken.nextToken();
			if (++sn > 4)
				break;
		}
		logger.info(ss[0]);
		if (sn != 2)
		{
			errorHandle(cs, -3);
			return false;
		}
		logger.info(ss[0]);
		name = ss[0];
		nameTemp=name;
		fld = ss[1];
		//OPNetwork.getPointByGlobalName(name);
		stoken = new StringTokenizer(rs);
		ss = new String[5];
		sn = 0;
		logger.info(ss[0]);
		while (stoken.hasMoreTokens()) 
		{
			ss[sn] = stoken.nextToken();
			if (++sn > 4)
				break;
		}
		logger.info(ss[0]);
		if (sn < 1 || sn > 2)
		{
			errorHandle(cs, -4);
			return false;
		}
		type = getConditionType(ss, sn);
		logger.info(type);
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
				rightVal = Float.valueOf(ss[0]).floatValue();
				DCSMask.getTransMaskByName(ss[0]);
			} else {
				rightVal = Float.valueOf(ss[0]).floatValue();
				DCSMask.getMaskByName(ss[0]);
			}
			
			
			break;

		case 2: // '\002'
			right = ss[0];
			rightFD = ss[1];
			OPNetwork.getPointByGlobalName(name);
			break;
		}
		return true;
	}

	private String changeLs(String ls) {
		String[] split = ls.split("\\$");
		logger.info(split.length);
		if (split.length ==3) {
			logger.info(split[0]);
			String pointName = PointUtils.getPointName(ls);
			if (split[0].equals("DI")) {
				return pointName+" DS";
			} else {
				return pointName+" AV";
			}
		}
		return null;
	}

	private void errorHandle(String cs, int eid)
	{
		type = -1;
		System.out.println("Error: " + eid + ". please check the condition string!");
		System.out.println("Conition: " + cs + "]");
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
	private int getConditionType(String rs[], int num)
	{
		if (this.fld.equals("DS")) {
			return 1;
		}
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

	public static int getRelationType(String str)
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
		if (str.indexOf("==") > 0)
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
	public String toString()
	{
		String s = "";
		if (name== null) {
			return s;
		}
		//logger.info(name.indexOf(type));
		logger.info(name.indexOf(nameTemp));
		if (name.indexOf(nameTemp) !=-1) {
			
			if (type == 0)
				s = name + " " + fld + " " + getRelation(relation) + " " + rightVal;
			else
				if (type == 1) {
					logger.info(fld);
					logger.info(rightVal);
					if (fld.equals("DS") && rightVal >=0) {
						if (rightVal==1) {
							rightMask = DCSMask.getMaskByName("SET");
						} else {
							rightMask = DCSMask.getMaskByName("RESET");
						}
					}
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
	public static void main(String[] args) {
		ConditionUtils conditionUtils = new ConditionUtils();
		boolean changeDriverExpr = conditionUtils.changeDriverExpr("DI$MEP2B1COM_18$iVal");
		System.out.println(conditionUtils.toString());
	}
}
