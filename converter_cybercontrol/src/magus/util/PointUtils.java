package magus.util;

import java.awt.Color;

import awt.DCSCondition;
import awt.DCSConditionColor;
import org.apache.log4j.Logger;

import transform.TransForm;

public class PointUtils {

	private static Logger logger = Logger.getLogger(PointUtils.class);
	public static String getPointName(String strDriverExpr) {
		// TODO Auto-generated method stub
		logger.info(strDriverExpr);
		logger.info(strDriverExpr.length());
		if (strDriverExpr== null || strDriverExpr.length() <3) {
			return "W3.FW1.";
		}
		
		String node = "W3.FW1.";
		String[] split = strDriverExpr.split("\\$");
		if (split.length==1) {
			return node+split[0];
		}
		return node+split[1];
	}
	public static void main(String[] args) {
		System.out.println(changeShowCondition("(AI$FL1A_M111_PRE$fVal<0.2)||(DI$FL1_M111_ST$iVal&&DI$FL1_M112_ST$iVal)"));
	}
	public static String getDxChangeText(String strDriverExpr, Object prop, Object prop2) {
		// TODO Auto-generated method stub
		if (strDriverExpr ==null) {
			return null;
		}
		String pointName = getPointName(strDriverExpr);
		if (prop != null && prop2!= null) {
			return "{ ("+pointName +" DS =SET) \"" +prop.toString() +"\" ("
					+pointName +" DS =RESET) \"" +prop2.toString() +"\" }";
		} else if (prop != null) {
			return "("+pointName +" DS =SET) \"" +prop.toString() +"\"";
		} else if (prop2 != null) {
			return "("+pointName +" DS =RESET) \"" +prop2.toString() +"\"";
		} else {
			return null;
		}
		
	}
	public static String changeMoerTextCondition(Object prop, Object prop2) {
		String[] splits = prop.toString().split("\\&");
		boolean isBit =false;
		String pointName = "";
		int bitNum=0;
		if (splits.length ==2) {
			pointName = getPointName(splits[0]);
			//System.out.println(splits[1]);
			if (splits[1].contains("<<")) {
				isBit = true;
				String[] split = splits[1].replaceAll("\\(", "").replaceAll("\\)", "").split("<<");
				bitNum =Integer.valueOf(split[1]).intValue();
			}
		}
		if (pointName != null && isBit) {
			return "("+pointName+" AV=ON"+bitNum+") \""+prop2+"\"";
		} else if(pointName!= null && !isBit){
			return "("+pointName+" DS=SET) \""+prop2+"\"";
		}
		
		return null;
		
	}
	public static String ParseDxConditionColor(String strDriverExpr, Object prop, Object prop2) {
		// TODO Auto-generated method stub
		if (strDriverExpr ==null) {
			return null;
		}
		String pointName = getPointName(strDriverExpr);
		if (prop != null && prop2!= null) {
			return "{ ("+pointName +" DS =SET) " + ColorParser.toString((Color) prop) +" ("
					+pointName +" DS =RESET) " +ColorParser.toString((Color) prop2) +" }";
		} else if (prop != null) {
			return "("+pointName +" DS =SET) " +ColorParser.toString((Color) prop) ;
		} else if (prop2 != null) {
			return "("+pointName +" DS =RESET) " +ColorParser.toString((Color) prop2) ;
		} else {
			return null;
		}
		
	}
	public static String changeMoerColorCondition(Object prop, Object prop2) {
		String[] splits = prop.toString().split("\\&");
		boolean isBit =false;
		String pointName = "";
		int bitNum=0;
		if (splits.length ==2) {
			pointName = getPointName(splits[0]);
			//System.out.println(splits[1]);
			if (splits[1].contains("<<")) {
				isBit = true;
				String[] split = splits[1].replaceAll("\\(", "").replaceAll("\\)", "").split("<<");
				bitNum =Integer.valueOf(split[1]).intValue();
			}
		} else if (splits.length ==1) {
			pointName = getPointName(splits[0]);
		}
		if (pointName != null && isBit) {
			return " ("+pointName+" AV=ON"+bitNum+") \""+ColorParser.toString((Color) prop2) ;
		} else if(pointName!= null && !isBit){
			return " ("+pointName+" DS = SET) "+ColorParser.toString((Color) prop2) ;
		}
		
		return null;
	}
	public static String changeShowCondition(String expr) {
		expr=expr.replaceAll("\\|\\|", " OR ").replaceAll("&&", " AND ");
		expr=expr.replaceAll("AI\\$", TransForm.nodeName).replaceAll("DI\\$", TransForm.nodeName);
		expr = expr.replaceAll("\\$fValidLL", " LL");
		expr = expr.replaceAll("\\$iVal==0", " DS = RESET");
		expr = expr.replaceAll("\\$iVal==1", " DS = SET");
		expr=expr.replaceAll("\\$fVal", " AV ").replaceAll("\\$iVal", " DS = SET ");
		if(!expr.startsWith("(")) {
			expr="("+expr+")";
		} else {
			expr="{"+expr+"}";
		}
		logger.info(expr);
		return expr;
	}
	public static String changeConditionColor(String expr, Color color) {
		logger.info(expr+"=======================================");
		logger.info(color+"=======================================");
		String condition = changeShowCondition(expr);
		DCSConditionColor dcsConditionColor = new DCSConditionColor(1);
		dcsConditionColor.cond[0] =new DCSCondition(condition);
		dcsConditionColor.dynColor[0] =color;
		if (dcsConditionColor.toString().length()>5){
			return dcsConditionColor.toString();
		}
		return null;
	}


	public static String changeConditionColor(String pointName, double a, Color color) {
		String point = getPointName(pointName);
		String conditionColor =  null;
		if (a ==1 && pointName.endsWith("iVal")) {
			conditionColor = ("("+point+ " DS = SET ) " + ColorParser.toString(color)+" ");
		} else if (a ==0 && pointName.endsWith("iVal")) {
			conditionColor = ("("+point+ " DS = RESET ) " + ColorParser.toString(color)+" ");
		} else {
			conditionColor = ("("+point+ " AV = "+a+" ) " + ColorParser.toString(color)+" ");
		}
		return conditionColor;
	}

	public static String changeConditionText(String pointName, int intX, String strX) {
		String point = getPointName(pointName);
		String conditionText =  null;
		if (intX ==1 && pointName.endsWith("iVal")) {
			conditionText = ("("+point+ " DS = SET ) \"" + strX+"\" ");
		} else if (intX ==0 && pointName.endsWith("iVal")) {
			conditionText = ("("+point+ " DS = RESET ) \"" + strX+"\" ");
		} else {
			conditionText = ("("+point+ " AV = "+intX+" ) \"" + strX+"\" ");
		}
		return conditionText;
	}
}
