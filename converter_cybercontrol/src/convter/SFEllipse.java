package convter;

import java.awt.Color;
import java.awt.Rectangle;

import magus.util.DriverUtils;
import org.apache.log4j.Logger;

import com.sfauto.toolkits.utils.RM;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.DCSDriver;
import SfnUI.GUI.BaseDraw.EllipseObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSColor;
import awt.DCSConditionColor;
import awt.DCSEllipse;
import awt.FillMode;
import magus.util.FillUtils;
import magus.util.PointUtils;

public class SFEllipse extends SFObject{

	public DCSEllipse ellipse;
	private Rectangle rectangle;
	private org.apache.log4j.Logger logger = Logger.getLogger(getClass());

	public SFEllipse(Rectangle rectangle,int version) {
		ellipse = new DCSEllipse(version);
		this.rectangle =rectangle;
	}

	public int parse(BaseDrawOb baseDrawOb) {
		// TODO Auto-generated method stub
		if (baseDrawOb instanceof EllipseObject) {
			EllipseObject obj = (EllipseObject) baseDrawOb;
			Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
			Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
			Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
			Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());

			ellipse.x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
			ellipse.y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
			ellipse.width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
			ellipse.height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);


			ellipse.fgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
			ellipse.bgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));

			ellipse.lineStyle=Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString());
			ellipse.lineWidth=Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString());


			Integer fillModeNum =Integer.valueOf(obj.getProp("", MyResourceManager.FillStyle).toString());
			Color color= (Color) obj.getProp("", MyResourceManager.GradiantColor);
			FillMode fillMode= FillUtils.createFillMode(fillModeNum,"",color);
			if (fillMode == null) {
				logger.error("fillModeNum="+fillModeNum);
			}
			ellipse.fillMode = fillMode;
			DriverUtils driverUtils = DriverUtils.parseDrivers(obj);
			if (driverUtils != null) {
				if (driverUtils.showCondition != null)
					ellipse.setShowCondition(driverUtils.showCondition);
				if (driverUtils.dcsConditionColor != null)
					ellipse.bgColor.setConditionColor(driverUtils.dcsConditionColor);

		}
			return 0;
		} else if (baseDrawOb instanceof SfnUI.GUI.BaseDraw.s) {
			SfnUI.GUI.BaseDraw.s  obj = (SfnUI.GUI.BaseDraw.s ) baseDrawOb;
			Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
			Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
			Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
			Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());

			ellipse.x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
			ellipse.y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
			ellipse.width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
			ellipse.height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);


			ellipse.fgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.LineColor));
			ellipse.bgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));

			ellipse.lineStyle=Byte.valueOf( obj.getProp("", MyResourceManager.LineStyle).toString());
			ellipse.lineWidth=Byte.valueOf(obj.getProp("", MyResourceManager.LineWidth).toString());


			Boolean isFill =Boolean.valueOf(obj.getProp("", RM.gs("是否填充")).toString());
			ellipse.fillMode = new FillMode();
			if (isFill) {
				ellipse.fillMode.parseFillMode("1");
			} else {
				ellipse.fillMode.parseFillMode("0");
			}

			DriverUtils driverUtils = DriverUtils.parseDrivers(obj);
			if (driverUtils != null) {
				if (driverUtils.showCondition != null)
					ellipse.setShowCondition(driverUtils.showCondition);
				if (driverUtils.dcsConditionColor != null)
					ellipse.bgColor.setConditionColor(driverUtils.dcsConditionColor);

			}
			return 0;
		}

		return -1;
	}

}
