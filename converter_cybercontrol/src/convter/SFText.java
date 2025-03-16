package convter;

import java.awt.*;
import java.util.Vector;

import magus.util.DriverUtils;
import org.apache.log4j.Logger;

import com.sfauto.toolkits.utils.RM;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.DCSDriver;
import SfnUI.GUI.BaseDraw.TextObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSColor;
import awt.DCSCondition;
import awt.DCSConditionText;
import awt.DCSDasPoint;
import awt.DCSText;
import magus.util.PointUtils;

public class SFText extends SFObject{

	public DCSText text;
	public Rectangle rectangle;
	public boolean isDasPoint = false;
	public DCSDasPoint dasPoint;
	private int version;
	private Logger logger = Logger.getLogger(getClass());
	public SFText(Rectangle rectangle, int version) {
		this.version = version;
		this.rectangle=rectangle;
		
	}
	public int parse(BaseDrawOb baseDrawOb) {
		// TODO Auto-generated method stub
		//logger.debug("parseText");
		
		if (baseDrawOb instanceof TextObject) {
			TextObject obj = (TextObject) baseDrawOb;
			Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
			Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
			int xv = (int)((double)(xs * 16000) / rectangle.width + 0.5D);
			int yv = (int)((double)(ys * 16000) / rectangle.height + 0.5D);
			logger.debug(xv+"------"+yv);
			text = new DCSText(null,null,xv,yv);
			text.version = this.version;
			//logger.error(obj.getText());
			Vector<?> arrays = obj.GetTextArray();
			if (arrays == null || arrays.size() ==0) {
				return -1;
			} else if(arrays.size()==1) {
				this.text.setDefaultText(arrays.get(0).toString());
			} else {
				StringBuilder sb =new StringBuilder();
				for (int i = 0; i < arrays.size(); i++) {
					sb.append(arrays.get(i));
					if (i!=arrays.size()-1) {
						sb.append("\\n");
					}
				}
				logger.info(sb);
				this.text.setDefaultText(sb.toString());
			}
			DCSColor fgColor=new DCSColor(obj.GetTextColor());
			this.text.fgColor=(fgColor);
			Font logFont = (Font)obj.get(MyResourceManager.TextFont);
			logger.debug(logFont.getFamily());
			Font font = null;
			if (logFont.getFamily().equals("楷体_GB2312")) {
				font = new Font("KaiTi_GB2312",obj.GetLogFont().getStyle(), obj.GetLogFont().getSize());
			} else if (logFont.getFamily().equals("楷体")) {
				font = new Font("KaiTi",obj.GetLogFont().getStyle(), obj.GetLogFont().getSize());
			} else
				font = new Font(Font.DIALOG,obj.GetLogFont().getStyle(), obj.GetLogFont().getSize());

			if (this.text.name.equals("M") || this.text.name.equals("A")) {
				font = new Font(font.getFamily(),font.getStyle(),14);
			}
			this.text.font=font;
			logger.debug(text.font);
			this.text.align=0;
			DriverUtils dUtils = DriverUtils.parseDrivers(baseDrawOb);
			if (dUtils!=null) {
				if (dUtils.isDasPoint) {
					isDasPoint = true;
					byte donum = Byte.valueOf(obj.getProp("",  MyResourceManager.BitNum).toString());
					//logger.debug(PointUtils.getPointName(driver.strDriverExpr));
					dasPoint = new DCSDasPoint(null,dUtils.pointName,(short)xv,(short)yv,(byte)6,donum);
					dasPoint.setFD("AV");
					dasPoint.version = this.version;
					dasPoint.fgColor = this.text.fgColor;

					dasPoint.font=text.font;
				}
				if (dUtils.conditionText != null) {
					text.setConditionText(dUtils.conditionText);
				}
				if (dUtils.dcsConditionColor != null){
					text.fgColor.setConditionColor(dUtils.dcsConditionColor);
				}
				if (dUtils.showCondition != null) {
					text.setShowCondition(dUtils.showCondition);
				}
			}
		} else {
			logger.error("不是文本");
			return -1;
		}
		return 0;
	}

}
