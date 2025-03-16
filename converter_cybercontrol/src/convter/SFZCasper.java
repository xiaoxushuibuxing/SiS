package convter;

import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Vector;

import SfnUI.GUI.ZCompoundDraw;
import SfnUI.UIPlatform.IDrawLibServer;
import org.apache.log4j.Logger;


import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.ZCasper;
import SfnUI.GUI.ZCasperDraw;
import SfnUI.i18n.MyResourceManager;
import config.Config;
import awt.DCSGroup;

public class SFZCasper extends SFObject{

	private Rectangle rectangle;
	public DCSGroup group;
	private Logger logger = Logger.getLogger(SFZCasper.class);
	private int version;
	public SFZCasper(Rectangle rectangle,int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
		group = new DCSGroup(version);
		this.rectangle = rectangle;
	}

	public int parse(BaseDrawOb baseDrawOb) {

		
		
		ZCasperDraw obj = (ZCasperDraw) baseDrawOb;
		System.out.println(obj.getObjMark()+"-------------");
		//ZCasper casper = ZCasper.fromBaseDraw(baseDrawOb);
		int xs = Integer.parseInt(obj.getProp("", MyResourceManager.Left).toString());
		int ys = Integer.parseInt(obj.getProp("", MyResourceManager.Up).toString());
		int widthTag = Integer.parseInt(obj.getProp("", MyResourceManager.Width).toString());
		int heightTag = Integer.parseInt(obj.getProp("", MyResourceManager.Height).toString());
		short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		Rectangle rectangle = new Rectangle(x, y, width, height);
		logger.debug(rectangle);
		LinkedList drawList = obj.getActionDraws();
		logger.debug(drawList.size());
		Vector vector = obj.seekAllRefVar(null);
		logger.debug(vector.size());
		for (int i = 0; i < vector.size(); i++){
			System.out.println(vector.get(i));
		}
		if (obj.isObj()) {
			logger.error(obj + " is not obj");
		}
		String name =obj.getProp("", MyResourceManager.Casper + MyResourceManager.Component).toString();
		logger.debug(name);
		Object user
				=obj.getVar( MyResourceManager.PropertyX_Custom + MyResourceManager.PropertyX_Type);
		logger.debug(user);
		if (!name.equals("") && name!=null) {
			//logger.debug("aaa");
			String[] split = obj.getProp("", MyResourceManager.Casper + MyResourceManager.Component).toString().split("\\.");
			logger.info (split);

			if (split.length != 2){
				return -1;


			}
			String file = Config.getProjectPath() + "\\graph\\Casper\\" + split[0] + "\\" + split[1] + ".cas";
			logger.info(file);
			ZCasper zCasper = ZCasper.fromAloneStream(file);
			if (zCasper == null){
				logger.warn("casper is null");
				SFZCasperLib zCasperLib = SFZCasperLib.getInstance();
				IDrawLibServer iDrawLibServer = zCasperLib.getiDrawLibServer();
				zCasper = iDrawLibServer.getZCasper(split[0],split[1]);
			}
			if (zCasper == null) {
				logger.info("casper is null");
				return -1;
			}

			ZCompoundDraw compoundDraw =zCasper.translateToZCompoundDraw();
			SFZCompoundDraw group2 = new SFZCompoundDraw(rectangle,version);
			if (group2.parse(compoundDraw)>=0) {
				group=group2.group;
			}

		} else {
			logger.error("casper is null");
			return -1;
		}

		

		return 0;
	}

	private void parseObj(BaseDrawOb baseDrawOb) {
		//Rectangle rectangle = new Rectangle(0, 0, 1920, 1080);
		logger.info(baseDrawOb.GetDrawObType());
		//BaseDrawOb baseDrawOb = ( layer).GetDrawObject();
		if (baseDrawOb.GetDrawObType()== 11) { // 文字
			SFText text = new SFText(rectangle, version);
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
		if(baseDrawOb.GetDrawObType()== 1 ) { //不闭合多边形 -----线
			SFlines lines = new SFlines(rectangle, version);
		    if (lines.parse(baseDrawOb)>=0) {
		    	group.addObject(lines.lines);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 3 || baseDrawOb.GetDrawObType()== 4 ) {
			SFRect rect = new SFRect(rectangle, version);
		   	if (rect.parse(baseDrawOb)>=0) {
		   		group.addObject(rect.rect);
		    }
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 62 ) {
			SFlines lines = new SFlines(rectangle, version);
		    if (lines.parse(baseDrawOb)>=0) {
		    	group.addObject(lines.lines);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 20 ) {
			SFButton button = new SFButton(rectangle, version);
			if (button.parse(baseDrawOb)>=0) {
				group.addObject(button.button);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 16 ) {
			SFRoundRect rect = new SFRoundRect(rectangle,version);
		    if (rect.parse(baseDrawOb)>=0) {
		    	group.addObject(rect.rect);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 40 ) {
			SFZCasper group1 = new SFZCasper(rectangle, version);
		    if (group1.parse(baseDrawOb)>=0) {
		    	group.addObject(group1.group);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 9 || baseDrawOb.GetDrawObType()== 10) {
			SFPolygon polygon = new SFPolygon(rectangle, version);   	
			if (polygon.parse(baseDrawOb)>=0) {
				group.addObject(polygon.polygon);
			}
			return;
		} 
		if(baseDrawOb.GetDrawObType()== 23) {
			SFZCompoundDraw group1 = new SFZCompoundDraw(rectangle,version);
	    	
		      if (group1.parse(baseDrawOb)>=0) {
		    	  group.addObject(group1.group);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 34) {
			SFCu cua = new SFCu(rectangle, version);
	    	
		      if (cua.parse(baseDrawOb)>=0) {
		    	  if(cua.isLine)
		    		  group.addObject(cua.lines);
		    	  else if(cua.isPolygon)
		    		  group.addObject(cua.polygon);
		    	  else if(cua.group!= null) {
		    		  System.out.println(group.getObjects().size());
		    		  group.addObject(cua.group);
		    	  } else
		    		  group.addObject(cua.arc);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 5) {
			SFEllipse ellipse = new SFEllipse(rectangle, version);
	    	
		      if (ellipse.parse(baseDrawOb)>=0) {
		    	  group.addObject(ellipse.ellipse);
		      }
		      return;
		}if(baseDrawOb.GetDrawObType()== 12) { //图片
			SFImage image = new SFImage(rectangle,version);

			if (image.parse(baseDrawOb)>=0) {
				
				group.addObject(image.image);
			}
			return;
		}
		int type = baseDrawOb.GetDrawObType();
		int [] a = { 2, 8, 9, 17, 27, 34, 37, 38, 39, 41, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 67, 68, 70, 72, 73, 74, 75, 76, 87, 89};
		if (!Arrays.asList(a).contains(type)) {
			System.out.println(this.getClass()+""+baseDrawOb.GetDrawObType()+" is not parse");
		}
		
	}

}
