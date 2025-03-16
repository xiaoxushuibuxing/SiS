package convter;

import java.awt.Color;
import java.awt.Rectangle;

import com.sfauto.toolkits.utils.RM;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.DCSDriver;
import SfnUI.GUI.BaseDraw.RectObject;
import SfnUI.GUI.BaseDraw.RoundRectObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSColor;
import awt.DCSConditionColor;
import awt.DCSDiagram;
import awt.DCSObject;
import awt.DCSRect;
import magus.util.DriverUtils;
import magus.util.FillUtils;
import magus.util.PointUtils;

public class SFRoundRect extends SFObject{

	public DCSRect rect;
	private Rectangle rectangle;
	private int version;

	public SFRoundRect(Rectangle rectangle, int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
		//this.rect = new DCSRect(version);
		this.rectangle =rectangle;
	}

	public int parse(BaseDrawOb baseDrawOb) {
		RoundRectObject obj = (RoundRectObject) baseDrawOb;
		Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
		Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
		Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
		Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());
		
		short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		rect = new DCSRect(x, y, width, height);
		rect.version = version;
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
			if (driverUtils.showCondition != null)
				rect.setShowCondition(driverUtils.showCondition);
		}
		return 0;
	}

}
