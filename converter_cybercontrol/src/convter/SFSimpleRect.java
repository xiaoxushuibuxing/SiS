package convter;

import java.awt.Color;
import java.awt.Rectangle;

import com.sfauto.toolkits.utils.RM;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.DCSDriver;
import SfnUI.GUI.BaseDraw.RectObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSColor;
import awt.DCSConditionColor;
import awt.DCSImage;
import awt.DCSRect;
import awt.FillMode;
import magus.util.DriverUtils;
import magus.util.FillUtils;
import magus.util.PointUtils;

public class SFSimpleRect extends SFObject{

	public DCSRect rect;
	private Rectangle rectangle;
	private int version;
	public DCSImage image;
	public boolean isImage;

	public SFSimpleRect(Rectangle rectangle, int version) {
		this.rectangle = rectangle;
		this.version =version;
	}

	public int parse(BaseDrawOb baseDrawOb) {
		// TODO Auto-generated method stub4
		if(baseDrawOb instanceof SfnUI.GUI.BaseDraw.ImageObject) {
			SfnUI.GUI.BaseDraw.ImageObject obj = (SfnUI.GUI.BaseDraw.ImageObject)baseDrawOb;
			Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
			Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
			Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
			Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());
			
			short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
			short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
			short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
			short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
			
			String wholeName = obj.get(MyResourceManager.ImageFile).toString();
			String name = wholeName.replace("DCSCasperLib.", "");
			image = new DCSImage(name, x, y, width, height);
			isImage = true;
			return 0;
		}
		SfnUI.GUI.BaseDraw.m obj = (SfnUI.GUI.BaseDraw.m) baseDrawOb;
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
		
		Boolean object = (Boolean) obj.get(RM.gs("是否填充"));
		FillMode mode = new FillMode();
		if (object) {
			mode.parseFillMode("1");
		} else {
			mode.parseFillMode("0");
		}
		rect.setFillMode(mode);
		//rect.setFillMode(FillUtils.createFillMode(fillMode,"",color));

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
