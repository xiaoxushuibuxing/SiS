package convter;

import java.awt.*;
import java.awt.geom.Point2D.Double;
import java.util.List;
import java.util.Map;

import SfnUI.GUI.BaseDraw.SimpleDrawOb;
import magus.util.DriverUtils;
import magus.util.FillUtils;
import org.apache.log4j.Logger;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.cu;
import SfnUI.i18n.MyResourceManager;
import awt.DCSArc;
import awt.DCSColor;
import awt.DCSConditionColor;
import awt.FillMode;
import magus.util.ArcUtils;

public class SFArc extends SFObject{

	public DCSArc arc;
	private Rectangle rectangle;
	private Logger logger = Logger.getLogger(SFArc.class);
	private int version;

	public SFArc(Rectangle rectangle, int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
		this.rectangle = rectangle;
	}

	public SFArc(DriverUtils drawUtils, Rectangle rectangle) {
		// TODO Auto-generated constructor stub
		
	}

	public int parse(BaseDrawOb baseDrawOb) {
		// TODO Auto-generated method stub
		SfnUI.GUI.BaseDraw.h obj = (SfnUI.GUI.BaseDraw.h) baseDrawOb;
		Integer xs = java.lang.Double.valueOf(obj.getProp("", MyResourceManager.Left).toString()).intValue();
		Integer ys = java.lang.Double.valueOf(obj.getProp("", MyResourceManager.Up).toString()).intValue();
		Integer widthTag = java.lang.Double.valueOf(obj.getProp("", MyResourceManager.Width).toString()).intValue();
		Integer heightTag = java.lang.Double.valueOf(obj.getProp("", MyResourceManager.Height).toString()).intValue();
		short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		int arcStart = java.lang.Double.valueOf(obj.get(MyResourceManager.ComProperty_Arc_Start).toString()).intValue();
		Integer arcEnd = java.lang.Double.valueOf(obj.get(MyResourceManager.ComProperty_Arc_End).toString()).intValue();
		this.arc = new DCSArc(version);
		this.arc.x=x;
		this.arc.y= y;
		this.arc.width=width;
		this.arc.height = height;
		this.arc.angle1 =(short) arcStart;
		this.arc.angle2 =(short)((arcEnd-arcStart)*Math.PI/180);
		DCSColor fgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
		DCSColor bgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));
		this.arc.setColor(fgColor, bgColor);
		byte lineStyle=Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString());
		byte lineWidth=Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString());
		this.arc.lineStyle=(lineStyle);
		this.arc.lineWidth=(lineWidth);
		byte mode=Byte.valueOf(obj.getProp("", MyResourceManager.ComProperty_Arc_Mode).toString());
		FillMode fillMode = new FillMode();
		if (mode ==0) {
			fillMode.parseFillMode("0");
		} else {
			fillMode.parseFillMode("1");
		}
		this.arc.fillMode=fillMode;
		if (this.arc.lineWidth == 0) {
			this.arc.lineWidth =1;
			this.arc.fgColor = this.arc.bgColor;
		}
		return 0;
	}

	public int parse(cu cua, List<Double> arcPoints, int version) {
		this.arc = new DCSArc(version);
		//DrawUtils drawUtils = new DrawUtils().parse(cua);
		this.arc.fgColor.setDefaultColor(cua.GetLineColor());
		this.arc.bgColor.setDefaultColor(cua.GetFillColor());
		this.arc.lineStyle = (byte) cua.GetLineStyle();
		this.arc.lineWidth = (byte) cua.GetLineThick();
		FillMode fillMode = new FillMode();
		if (!cua.IsFill()) {
			fillMode.parseFillMode("0");
		} else {
			Integer fillModeNum =Integer.valueOf(cua.getProp("", MyResourceManager.FillStyle).toString());

			Color color= (Color) cua.getProp("", MyResourceManager.GradiantColor);
			fillMode = FillUtils.createFillMode(fillModeNum, "", color);
			if (fillMode == null) {
				fillMode.parseFillMode("1");
			}
			//fillMode.parseFillMode("1");
		}
		this.arc.fillMode=fillMode;
		DriverUtils driverUtils = DriverUtils.parseDrivers(cua);
		if (driverUtils != null) {
			if (driverUtils.dcsConditionColor != null)
				arc.bgColor.setConditionColor(driverUtils.dcsConditionColor);
			if (driverUtils.showCondition != null)
				arc.setShowCondition(driverUtils.showCondition);
		}
		this.arc.angle2 = 180;
		short[] angle = ArcUtils.getAngle(arcPoints, ArcUtils.getCenter(arcPoints, rectangle));
		//logger.error(angle[0]+"=============================="+angle[1]);
		setBounds(arcPoints,angle[1]);

		//System.out.println(polygon.getBounds());FillUtils.createFillMode(fillMode,"",color)
		if (perDriverUtil != null) {
			if (perDriverUtil.dcsConditionColor != null) {
				arc.bgColor.setConditionColor(perDriverUtil.dcsConditionColor);
			}
			if (perDriverUtil.showCondition != null) {
				arc.setShowCondition(perDriverUtil.showCondition);
			}
		}
		if (driverUtils!=null) {
			if (driverUtils.dcsConditionColor != null) {
				arc.bgColor.setConditionColor(driverUtils.dcsConditionColor);
			}
			if (driverUtils.showCondition != null) {
				arc.setShowCondition(driverUtils.showCondition);
			}
		}
		if (this.arc.lineWidth == 0) {
			this.arc.lineWidth =1;
			this.arc.fgColor = this.arc.bgColor;
		}
		return 0;
	}
	public void setBounds(List<Double> arcPoints,int angle1) {
		Rectangle bounds = ArcUtils.getBounds(arcPoints, rectangle);
		Point center = ArcUtils.getCenter(arcPoints, rectangle);
		if (angle1 == 90) {
			arc.setBounds((short)bounds.x, (short)bounds.y, (short)(bounds.width*2), (short)bounds.height);	
		} else if(angle1== 180) {
			arc.setBounds((short)bounds.x, (short)(bounds.y-bounds.height), (short)(bounds.width), (short)(bounds.height*2));	
		} else if(angle1 == 270) {
			arc.setBounds((short)(bounds.x-bounds.width), (short)(bounds.y-bounds.height), (short)(bounds.width*2), (short)(bounds.height));	
		} else {
			arc.setBounds((short)bounds.x, (short)bounds.y, (short)(bounds.width), (short)(bounds.height*2));	
		}
	}
}
