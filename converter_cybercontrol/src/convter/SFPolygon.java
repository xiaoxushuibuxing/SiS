package convter;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;
import java.util.List;

import awt.FillMode;
import magus.util.*;
import org.apache.log4j.Logger;

import com.sfauto.toolkits.utils.RM;
import SfnUI.GUI.cu;
import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.BaseDraw.ComplexLineObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSColor;
import awt.DCSPolygon;

public class SFPolygon extends SFObject {

	public DCSPolygon polygon;
	private Rectangle rectangle;
	private Logger logger = Logger.getLogger(SFPolygon.class);
	private int version;

	public SFPolygon(Rectangle rectangle, int version) {
		this.version = version;
		this.rectangle = rectangle;
		// TODO Auto-generated constructor stub
	}

	public int parse(BaseDrawOb baseDrawOb) {
		//logger.error("---------------ObjMark------------------"+baseDrawOb.getObjMark());
		if (baseDrawOb instanceof SfnUI.GUI.BaseDraw.k) {
			SfnUI.GUI.BaseDraw.k obj = (SfnUI.GUI.BaseDraw.k) baseDrawOb;
			int pointNum = Integer.valueOf(obj.getProp("", RM.gs("点数")).toString());
			short[] xpoints = new short[pointNum];
			short[] ypoints = new short[pointNum];
			for (int i = 0; i <pointNum; i++) {
				Point2D point = (Point2D) obj.getProp("", RM.gs("第"+(i+1)+"点"));
				logger.info(point);

				int xv = (short)((point.getX() * 16000) / rectangle.width + 0.5D);
				int yv = (short)((point.getY() * 16000) / rectangle.height + 0.5D);
				xpoints[i] =(short) xv;
				ypoints[i]=(short) yv;
				logger.info(xpoints[i]+","+ypoints[i]);

			}
			this.polygon = new DCSPolygon(xpoints,ypoints,pointNum);
			this.polygon.version = version;
			DCSColor fgColor=new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
			DCSColor bgColor=new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));
			polygon.setColor(fgColor, bgColor);
			polygon.setLineStyle( Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString()));
			polygon.setLineWidth( Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString()));
			System.out.println(this.polygon.getBounds());
			boolean isFill =Boolean.valueOf(obj.getProp("", RM.gs("是否填充")).toString());
			FillMode fillMode = new FillMode();
			if (isFill) {
				fillMode.parseFillMode("1");
			} else {
				fillMode.parseFillMode("0");
			}
			polygon.fillMode = fillMode;


			driverUtils = DriverUtils.parseDrivers(obj);

			//DriverUtils
			if (driverUtils!= null && driverUtils.dcsConditionColor!=null) {
				polygon.bgColor.setConditionColor(driverUtils.dcsConditionColor);
			}
			if (driverUtils!= null && driverUtils.showCondition!=null) {
				polygon.setShowCondition(driverUtils.showCondition);
			}
			return 0;
		}
		// TODO Auto-generated method stub
		ComplexLineObject obj = (ComplexLineObject) baseDrawOb;
		int pointNum = Integer.valueOf(obj.getProp("", RM.gs("点数")).toString());
		short[] xpoints = new short[pointNum];
		short[] ypoints = new short[pointNum];
		for (int i = 0; i <pointNum; i++) {
			Point2D point = (Point2D) obj.getProp("", RM.gs("第"+(i+1)+"点"));
			logger.info(point);

			int xv = (short)((point.getX() * 16000) / rectangle.width + 0.5D);
			int yv = (short)((point.getY() * 16000) / rectangle.height + 0.5D);
			xpoints[i] =(short) xv;
			ypoints[i]=(short) yv;
			logger.info(xpoints[i]+","+ypoints[i]);

		}
		this.polygon = new DCSPolygon(xpoints,ypoints,pointNum);
		this.polygon.version = version;
		DCSColor fgColor=new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
		DCSColor bgColor=new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));
		polygon.setColor(fgColor, bgColor);
		polygon.setLineStyle( Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString()));
		polygon.setLineWidth( Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString()));
		System.out.println(this.polygon.getBounds());
		Integer fillMode =Integer.valueOf(obj.getProp("", MyResourceManager.FillStyle).toString());
		Color color= (Color) obj.getProp("", MyResourceManager.GradiantColor);
		//System.out.println(polygon.getBounds());
		polygon.setFillMode(FillUtils.createFillMode(fillMode,"",color));
		driverUtils = DriverUtils.parseDrivers(obj);

		//DriverUtils
		if (driverUtils!= null && driverUtils.dcsConditionColor!=null) {
			polygon.bgColor.setConditionColor(driverUtils.dcsConditionColor);
		}
		if (driverUtils!= null && driverUtils.showCondition!=null) {
			polygon.setShowCondition(driverUtils.showCondition);
		}
		return 0;
	}

	public int parse(cu cua,  List<Double> polygonPoints,int version) {
		this.polygon = new DCSPolygon(version);
		this.polygon.fgColor.setDefaultColor(cua.GetLineColor());
		this.polygon.bgColor.setDefaultColor(cua.GetFillColor());
		this.polygon.lineStyle = (byte) cua.GetLineStyle();
		this.polygon.lineWidth = (byte) cua.GetLineThick();
		
		List<Float> points = ArcUtils.change2Points(polygonPoints, rectangle);
		short[] x = new short[points.size()];
		short[] y = new short[points.size()];
		for (int i = 0; i < points.size(); i++) {
			x[i] = (short) points.get(i).getX();
			y[i] = (short) points.get(i).getY();
		}
		FillMode fillMode = new FillMode();
		if (!cua.IsFill()) {
			fillMode.parseFillMode("0");
		} else {
			Integer fillModeNum = Integer.valueOf(cua.getProp("", MyResourceManager.FillStyle).toString());
			Color color= (Color) cua.getProp("", MyResourceManager.GradiantColor);
			fillMode = FillUtils.createFillMode(fillModeNum, "", color);
			if (fillMode == null) {
				fillMode.parseFillMode("1");
			}
		}
		this.polygon.fillMode=fillMode;
		polygon.npoints = points.size();
		polygon.xpoints =x; 
		polygon.ypoints =y; 
		if (perDriverUtil!=null) {
			if (perDriverUtil.dcsConditionColor!=null)
				polygon.bgColor.setConditionColor(perDriverUtil.dcsConditionColor);
			if (perDriverUtil.showCondition!=null)
				polygon.setShowCondition(perDriverUtil.showCondition);
		}
		driverUtils = DriverUtils.parseDrivers(cua);
		//polygon.fillMode = drawUtils.fillMode;
		if (driverUtils!=null) {
			if (driverUtils.showCondition!=null) {
				polygon.setShowCondition(driverUtils.showCondition);
			}
			if (driverUtils.dcsConditionColor!=null){
				polygon.bgColor.setConditionColor(driverUtils.dcsConditionColor);
			}
		}


		//System.out.println(polygon.getBounds());FillUtils.createFillMode(fillMode,"",color)

		driverUtils = DriverUtils.parseDrivers(cua);
		if (driverUtils != null ){
			if (driverUtils.dcsConditionColor!=null)
				polygon.bgColor.setConditionColor(driverUtils.dcsConditionColor);
			if (driverUtils.showCondition!=null)
				polygon.setShowCondition(driverUtils.showCondition);
		}

		if (this.polygon.lineWidth == 0) {
			this.polygon.lineWidth =1;
			this.polygon.fgColor = this.polygon.bgColor;
		}
		return 0;
	}
}
