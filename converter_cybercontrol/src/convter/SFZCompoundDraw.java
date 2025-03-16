package convter;

import java.awt.Rectangle;
import java.util.Arrays;

import SfnUI.GUI.bv;
import magus.util.DriverUtils;
import org.apache.log4j.Logger;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.ZCompoundDraw;
import SfnUI.i18n.MyResourceManager;
import awt.DCSGroup;

public class SFZCompoundDraw extends SFObject{

	public DCSGroup group;
	private Rectangle rectangle;
	private Logger logger = Logger.getLogger(SFZCompoundDraw.class);
	private int version;
	private DriverUtils driverUtils;

	public SFZCompoundDraw(Rectangle rectangle, int version) {
		this.version = version;
		this.rectangle =rectangle;
		this.group = new DCSGroup();
		// TODO Auto-generated constructor stub
	}

	public int parse(BaseDrawOb baseDrawOb) {
		ZCompoundDraw obj = (ZCompoundDraw) baseDrawOb;
		//ZCasper casper = ZCasper.fromBaseDraw(baseDrawOb);
		bv bda = baseDrawOb.getBDA();

		Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
		Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
		Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
		Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());
		short x= (short)((double)(xs * 16000) / rectangle.x + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.y + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		Rectangle rect = new Rectangle(x, y, width, height);
		String[] enumChilds = obj.enumChilds();
		//logger.info("-----------------parseDriverForGroup start--------------------------");
		driverUtils = DriverUtils.parseDrivers(obj);
		if (driverUtils!= null){
			if (driverUtils.showCondition !=null) {
				logger.info("-----------------parseDriverForGroup start--------------------------");
				this.group.setShowType((byte) 1);
				group.setShowCondition(driverUtils.showCondition);
				logger.info("-----------------parseDriverForGroup over--------------------------");
			}
		}
		//logger.info("-----------------parseDriverForGroup over--------------------------");
		for (String string : enumChilds) {
			//logger.info("------------------"+string);
			BaseDrawOb drawByName = obj.getDrawByName(string);
			//logger.info(drawByName.GetDrawObType());
		//	logger.info("------------------");
			parseObj(drawByName,rectangle);
			//group.setBounds(x, y, width, height);
		}
		
		if(driverUtils!= null && driverUtils.showCondition!=null) {
			logger.info("-----------------parseDriverForGroup start--------------------------");
			this.group.setShowType((byte) 1);
			group.setShowCondition(driverUtils.showCondition);
			logger.info("-----------------parseDriverForGroup over--------------------------");
		}
		return 0;
	}

	private void parseObj(BaseDrawOb baseDrawOb,Rectangle rectan) {
		

		if (baseDrawOb.IsText()) { // 文字
			logger.debug("文字:"+baseDrawOb.GetDrawObType());
			SFText text = new SFText(rectan, version);
			text.perDriverUtil = driverUtils;
		    if (text.parse(baseDrawOb)>=0) {
		    	if (text.isDasPoint) {
		    		text.dasPoint.move(-200, -20);
		    		group.addObject(text.dasPoint);
				} else {
					group.addObject(text.text);
				}
			   }
			 return; 
		} 
		if(baseDrawOb.GetDrawObType()== 1 ) { //不闭合多边形 -----//改成多边形
			logger.debug("线:"+baseDrawOb.GetDrawObType());
			SFlines lines = new SFlines(rectan,version);
			lines.perDriverUtil = driverUtils;
		    if (lines.parse(baseDrawOb)>=0) {
		    	logger.info(lines.lines.getBounds());
		    	group.addObject(lines.lines);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 3 || baseDrawOb.GetDrawObType()== 4 ) {
			logger.debug("长方形:"+baseDrawOb.GetDrawObType());
			SFRect rect = new SFRect(rectan, version);
			rect.perDriverUtil = driverUtils;
		   	if (rect.parse(baseDrawOb)>=0) {
		   		group.addObject(rect.rect);
		    }
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 7) { //弧形
			SFArc arc = new SFArc(rectangle,version);

			if (arc.parse(baseDrawOb)>=0) {

				group.addObject(arc.arc);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 12) { //图片
			SFImage image = new SFImage(rectangle,version);

			if (image.parse(baseDrawOb)>=0) {

				group.addObject(image.image);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 62 ) {
			logger.debug("线:"+baseDrawOb.GetDrawObType());
			SFlines lines = new SFlines(rectan, version);
		    if (lines.parse(baseDrawOb)>=0) {
		    	logger.info(lines.lines.getBounds());
		    	group.addObject(lines.lines);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 20 ) {
			logger.debug("按钮:"+baseDrawOb.GetDrawObType());
			SFButton button = new SFButton(rectan, version);
			if (button.parse(baseDrawOb)>=0) {
				group.addObject(button.button);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 16 ) {
			logger.debug("长方形:"+baseDrawOb.GetDrawObType());
			SFRoundRect rect = new SFRoundRect(rectan, version);
		    if (rect.parse(baseDrawOb)>=0) {
		    	group.addObject(rect.rect);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 40 ) {
			logger.debug("精灵组:"+baseDrawOb.GetDrawObType());
			SFZCasper group1 = new SFZCasper(rectangle, version);
		    if (group1.parse(baseDrawOb)>=0) {
		    	group.addObject(group1.group);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 9 || baseDrawOb.GetDrawObType()== 10 || baseDrawOb.GetDrawObType()== 63) {
			logger.debug("多边形:"+baseDrawOb.GetDrawObType());
			SFPolygon polygon = new SFPolygon(rectan, version);  
			polygon.perDriverUtil = this.driverUtils;
			if (polygon.parse(baseDrawOb)>=0) {
				group.addObject(polygon.polygon);
			}
			return;
		} 
		if(baseDrawOb.GetDrawObType()== 23) {
			logger.debug("元件组:"+baseDrawOb.GetDrawObType());
			SFZCompoundDraw group1 = new SFZCompoundDraw(rectangle, version);
			group1.perDriverUtil = this.driverUtils;
		      if (group1.parse(baseDrawOb)>=0) {
		    	  group.addObject(group1.group);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 34) {
			logger.debug("面积组:"+baseDrawOb.GetDrawObType());
			SFCu cua = new SFCu(rectangle, version);
			cua.perDriverUtil = this.driverUtils;
			if (driverUtils!= null) {
				cua.setDriverUtils(driverUtils);
			}
		      if (cua.parse(baseDrawOb)>=0) {
		    	  if (cua.isArc) {
		    		  group.addObject(cua.arc);
				} else if (cua.isLine) {
					group.addObject(cua.lines);
				} else if(cua.isPolygon) {
					group.addObject(cua.polygon);
				} else {
					group.addObject(cua.group);
				}
		    	 // System.out.println(cua.group.objectLink.size());
		    	 // group.addObject(cua.group);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 5) {
			logger.debug("圆形:"+baseDrawOb.GetDrawObType());
			SFEllipse ellipse = new SFEllipse(rectan, version);
			ellipse.perDriverUtil = this.driverUtils;
		      if (ellipse.parse(baseDrawOb)>=0) {
		    	  System.out.println(ellipse.ellipse.getBounds());
		    	  group.addObject(ellipse.ellipse);
		      }
		      return;
		}if(baseDrawOb.GetDrawObType()== 61) {
			logger.debug("圆形:"+baseDrawOb.GetDrawObType());
			SFEllipse ellipse = new SFEllipse(rectan, version);
			ellipse.perDriverUtil = this.driverUtils;
			if (ellipse.parse(baseDrawOb)>=0) {
				System.out.println(ellipse.ellipse.getBounds());
				group.addObject(ellipse.ellipse);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()==15 || baseDrawOb.GetDrawObType()== 30) { //趋势
			logger.error("趋势不处理");
			return;
		}
		if(baseDrawOb.GetDrawObType()== 60) { //简单矩形
			SFSimpleRect rect = new SFSimpleRect(rectangle, version);
			rect.perDriverUtil = this.driverUtils;
			if (rect.parse(baseDrawOb)>=0) {
				group.addObject(rect.rect);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 21) { //表格
			logger.error("表格不处理");
			return;

		}
		if(baseDrawOb.GetDrawObType()== 28) { //标尺
			logger.error("标尺元件不处理");
			return;

		}
		int type = baseDrawOb.GetDrawObType();
		int [] a = { 2, 8, 9, 17, 27, 34, 37, 38, 39, 41, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 67, 68, 70, 72, 73, 74, 75, 76, 87, 89};
		if (!Arrays.asList(a).contains(type)) {
			logger.error(this.getClass()+""+baseDrawOb.GetDrawObType()+" is not parse");
		}
	}

}
