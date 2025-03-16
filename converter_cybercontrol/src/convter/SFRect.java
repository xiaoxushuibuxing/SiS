package convter;

import java.awt.Color;
import java.awt.Rectangle;

import awt.*;
import com.enterprisedt.util.debug.Logger;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.BaseDraw.RectObject;
import SfnUI.i18n.MyResourceManager;
import magus.util.DriverUtils;
import magus.util.FillUtils;


public class SFRect extends SFObject {

	public DCSRect rect;
	public Rectangle rectangle;
	public DCSBar bar;
	public boolean isBar = false;
	private int version;
	Logger logger = Logger.getLogger(SFRect.class);
	public SFRect(Rectangle rectangle, int version) {
	//	rect = new DCSRect();
		this.version = version;
		this.rectangle = rectangle;
	}
	public int parse(BaseDrawOb baseDrawOb) {
		// TODO Auto-generated method stub
		RectObject obj = (RectObject) baseDrawOb;
		logger.error(obj.getObjMark());
		Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
		Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
		Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
		Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());
		
		short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		rect = new DCSRect(x, y, width, height);
		this.rect.version = version;
		DCSColor fgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
		DCSColor bgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));
		rect.setColor(fgColor, bgColor);
		
		byte lineStyle=Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString());
		byte lineWidth=Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString());
		rect.setLineStyle(lineStyle);
		rect.setLineWidth(lineWidth);
		
		Integer fillMode =Integer.valueOf(obj.getProp("", MyResourceManager.FillStyle).toString());
		Color color= (Color) obj.getProp("", MyResourceManager.GradiantColor);
		
		rect.setFillMode(FillUtils.createFillMode(fillMode,"",color));

		DriverUtils driverUtils = DriverUtils.parseDrivers(obj);

		if (driverUtils != null) {
			if (driverUtils.dcsConditionColor != null)
				rect.bgColor.setConditionColor(driverUtils.dcsConditionColor);
			if (driverUtils.isBar) {
				isBar = true;
				bar = new DCSBar();
				bar.x = rect.x;
				bar.y = rect.y;
				bar.width = rect.width;
				bar.height = rect.height;
				bar.highLimit = driverUtils.bar.highLimit;
				bar.lowLimit = driverUtils.bar.lowLimit;
				bar.direct = driverUtils.bar.direct;
				bar.fgColor = this.rect.fgColor;
				bar.bgColor = this.rect.bgColor;
				bar.pointName = driverUtils.bar.pointName;
				bar.FD = "AV";
			}
		}
		if (driverUtils == null) {
			driverUtils = DriverUtils.parseDrivers(obj);
		}
		//DriverUtils
		if (driverUtils!= null && driverUtils.dcsConditionColor!=null) {
			rect.bgColor.setConditionColor(driverUtils.dcsConditionColor);
		}
		if (driverUtils!= null && driverUtils.showCondition!=null) {
			rect.setShowCondition(driverUtils.showCondition);
		}
		return 0;
	}


}
