package convter;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import com.sfauto.toolkits.utils.RM;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.BaseDraw.ComplexLineObject;
import SfnUI.GUI.BaseDraw.RectObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSColor;
import awt.DCSDiagram;
import awt.DCSLines;
import magus.util.DriverUtils;

public class SFlines extends SFObject {
	DCSLines lines;
	private Rectangle rectangle;
	private int version;
	public SFlines(Rectangle rectangle, int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
		this.rectangle =rectangle;	
	}
	
	public int parse(BaseDrawOb obj) {
		// TODO Auto-generated method stub
		if (obj.GetDrawObType()== 62) {
			SfnUI.GUI.BaseDraw.c obj2 = (SfnUI.GUI.BaseDraw.c)obj;
			int pointNum = Integer.valueOf(obj2.getProp("", RM.gs("点数")).toString());
			short[] xp = new short[pointNum];
			short[] yp = new short[pointNum];
			for (int i = 0; i <pointNum; i++) {
				Point2D point = (Point2D) obj2.getProp("", RM.gs("第"+(i+1)+"点"));
				int xv = (short)((double)(point.getX() * 16000) / rectangle.width + 0.5D);
				int yv = (short)((double)(point.getY() * 16000) / rectangle.height + 0.5D);
				xp[i] =(short) xv;
				yp[i]=(short) yv;
				
			}
			
			lines = new DCSLines(xp,yp,pointNum);
			lines.version = version;
			System.out.println(lines.getBounds());
			DCSColor fgColor=new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
			lines.setColor(fgColor, null);
			lines.lineStyle =(Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString()));
			lines.lineWidth=(Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString()));
			System.out.println(lines.lineWidth);
			 driverUtils = DriverUtils.parseDrivers(obj);
			 if (perDriverUtil!=null) {
				 if (perDriverUtil.dcsConditionColor!=null)
					 lines.fgColor.setConditionColor(perDriverUtil.dcsConditionColor);
				 if (perDriverUtil.showCondition!=null)
					 lines.setShowCondition(perDriverUtil.showCondition);
			 }
			if (driverUtils!=null	) {
				if (driverUtils.dcsConditionColor!=null)
					lines.fgColor.setConditionColor(driverUtils.dcsConditionColor);
				if (driverUtils.showCondition!=null)
					lines.setShowCondition(driverUtils.showCondition);
			}
			
		} else if(obj.GetDrawObType()== 1) {
			ComplexLineObject obj2 = (ComplexLineObject) obj;
			int pointNum = Integer.valueOf(obj2.getProp("", RM.gs("点数")).toString());
			short[] xp = new short[pointNum];
			short[] yp = new short[pointNum];
			for (int i = 0; i <pointNum; i++) {
				Point2D point = (Point2D) obj2.getProp("", RM.gs("第"+(i+1)+"点"));
				int xv = (short)((double)(point.getX() * 16000) / rectangle.width + 0.5D);
				int yv = (short)((double)(point.getY() * 16000) / rectangle.height + 0.5D);
				xp[i] =(short) xv;
				yp[i]=(short) yv;
				
			}
			lines = new DCSLines(xp,yp,pointNum);
			lines.version = version;
			System.out.println(lines.getBounds());
			DCSColor fgColor=new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
			lines.setColor(fgColor, null);
			lines.setLineStyle(Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString()));
			lines.setLineWidth(Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString()));
			lines.lineWidth +=1;
			DriverUtils driverUtils = DriverUtils.parseDrivers(obj);
			if (driverUtils!=null	) {
				if (driverUtils.dcsConditionColor!=null)
					lines.fgColor.setConditionColor(driverUtils.dcsConditionColor);
				if (driverUtils.showCondition!=null)
					lines.setShowCondition(driverUtils.showCondition);
			}
			System.out.println(lines.lineWidth);
		}
		
		
		
		return 0;
	}

}
