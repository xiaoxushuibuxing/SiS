package convter;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.actuate.util.Version;
import com.sfauto.toolkits.utils.RM;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.GRAPHHEADER;
import SfnUI.GUI.GraphObject;
import SfnUI.GUI.XUIComponent;
import SfnUI.i18n.MyResourceManager;
import awt.DCSDiagram;

public class SFDiagram {

	public DCSDiagram diagram;
	public Rectangle rectangle;
	private Logger logger = Logger.getLogger(SFDiagram.class);
	public SFDiagram() {
		//diagram = new DCSDiagram(0,0);
	}
	
	public SFDiagram(GraphObject graphObj) {
		if (graphObj!= null)
			parse(graphObj);
	}

	private void parse(GraphObject graphObj) {
		GRAPHHEADER graphHeader = graphObj.GetGraphHeader();
		diagram = new DCSDiagram();
		diagram.width = (short) graphHeader.width;
		diagram.height = (short) graphHeader.height;
		rectangle = new Rectangle(0, 0, diagram.width, diagram.height);
		if (graphHeader.m_BackGround.m_crFillColor.equals(Color.black)) {
			diagram.setBGColor(new Color(117,117,162));
		} else {
			diagram.setBGColor(graphHeader.m_BackGround.m_crFillColor);
		}
		//System.out.println();
		String string = graphObj.getProp("", RM.gs("样式")).toString();
		if (string!= null && !string.equals(MyResourceManager.None)) {
			parseStrPattern(string);
		}
		
		LinkedList<?> childs = graphObj.getAllChilds();
		for (Object object : childs) {
			parseObj((XUIComponent) object);
		}
	}

	private void parseStrPattern(String pattrenName) {
		if (pattrenName==null || pattrenName.equals("")) {
			return;
		}
		String path = "C:\\Users\\Administrator\\Desktop\\新建文件夹 (5)\\一期辅控网20241118\\一期辅控网\\graph\\图形资源\\图形样式\\";
		String fixStr=".drw";
		GraphObject graphObj = GraphObject.CreateGraphFromPath(path+pattrenName+fixStr);
		if (graphObj == null) {
			return;
		}
		GRAPHHEADER graphHeader = graphObj.GetGraphHeader();
		
		
		if (graphHeader.m_BackGround.m_crFillColor.equals(Color.black)) {
			diagram.setBGColor(new Color(117,117,162));
		} else {
			diagram.setBGColor(graphHeader.m_BackGround.m_crFillColor);
		}
		LinkedList childs = graphObj.getAllChilds();
		
		for (Object object : childs) {
			parseObj((XUIComponent) object);
		}
	}

	private void parseObj(XUIComponent layer) {
		logger.info("layer"+layer.toString());
		BaseDrawOb baseDrawOb = ( layer).GetDrawObject();
		
		logger.info(baseDrawOb.GetDrawObType());
		if(baseDrawOb.GetDrawObType()==0 ) { //趋势
			logger.error("点元件不处理");
			return;
		}
		if(baseDrawOb.GetDrawObType()== 1 ) { //不闭合多边形 -----线
			SFlines lines = new SFlines(rectangle,diagram.version);
		    if (lines.parse(baseDrawOb)>=0) {
				diagram.addObject(lines.lines);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 3 || baseDrawOb.GetDrawObType()== 4 ) { //矩形
			SFRect rect = new SFRect(rectangle,diagram.version);
		   	if (rect.parse(baseDrawOb)>=0) {
				   logger.info("rect is bar:"+rect.isBar);
				   if (rect.isBar) {
					   diagram.addObject(rect.bar);
				   }else
						diagram.addObject(rect.rect);
		    }
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 9 || baseDrawOb.GetDrawObType()== 10 || baseDrawOb.GetDrawObType()== 63) { //多边形
			SFPolygon polygon = new SFPolygon(rectangle, diagram.version);   	
			if (polygon.parse(baseDrawOb)>=0) {
				diagram.addObject(polygon.polygon);
			}
			return;
		} 
		if (baseDrawOb.GetDrawObType() ==11) { // 文字
			SFText text = new SFText(rectangle,diagram.version);
		    if (text.parse(baseDrawOb)>=0) {
		    	if (text.isDasPoint) {
		    		logger.info("text is DasPoint");
		    		text.dasPoint.move(-200, -20);
		    		diagram.addObject(text.dasPoint);
				} else {
					diagram.addObject(text.text);
				}
			   }
			 return; 
		}
		if(baseDrawOb.GetDrawObType()==13 ) { //趋势
			logger.error("文本框不处理");
			return;
		}
		if(baseDrawOb.GetDrawObType()== 16 ) { //圆角矩形
			SFRoundRect rect = new SFRoundRect(rectangle,diagram.version);
		    if (rect.parse(baseDrawOb)>=0) {
				diagram.addObject(rect.rect);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 20 ) { //按钮
			SFButton button = new SFButton(rectangle,diagram.version);
			if (button.parse(baseDrawOb)>=0) {
				if (button.isRect) {
					logger.info("button is rect");
					diagram.addObject(button.rect);
				} else if(button.isImage) {
					logger.info("button is image");
					diagram.addObject(button.image);
				} else {
					diagram.addObject(button.button);
				}
				
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 23) {//单元元件 & 块元件
			SFZCompoundDraw group1 = new SFZCompoundDraw(rectangle, diagram.version);
	    	
		      if (group1.parse(baseDrawOb)>=0) {
		    	  diagram.addObject(group1.group);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()==24 ) { //趋势
			logger.error("下拉框不处理");
			return;
		}
		if(baseDrawOb.GetDrawObType()== 34) {
			SFCu cua = new SFCu(rectangle,diagram.version);
	    	
		      if (cua.parse(baseDrawOb)>=0) {
		    	  if(cua.isRect)
		    		  diagram.addObject(cua.lines);
		    	  else if(cua.isPolygon)
		    		  diagram.addObject(cua.polygon);
		    	  else
		    		  diagram.addObject(cua.group);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 40 ) {//精灵元件
			logger.info("精灵元件"+baseDrawOb.GetDrawObType());
			SFZCasper group = new SFZCasper(rectangle,diagram.version);
		    if (group.parse(baseDrawOb)>=0) {
				diagram.addObject(group.group);
			}
		    return;
		}
		if(baseDrawOb.GetDrawObType()== 62 ) {//线
			SFlines lines = new SFlines(rectangle,diagram.version);
		    if (lines.parse(baseDrawOb)>=0) {
				diagram.addObject(lines.lines);
			}
		    return;
		}
		
		
		
		
		
		if(baseDrawOb.GetDrawObType()== 5) {
			SFEllipse ellipse = new SFEllipse(rectangle, diagram.version);
	    	
		      if (ellipse.parse(baseDrawOb)>=0) {
		    	  diagram.addObject(ellipse.ellipse);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 60) { //简单矩形
			SFSimpleRect rect = new SFSimpleRect(rectangle, diagram.version);
	    	
		      if (rect.parse(baseDrawOb)>=0) {
		    	  diagram.addObject(rect.rect);
		      }
		      return;
		}
		if(baseDrawOb.GetDrawObType()== 12) { //图片
			SFImage image = new SFImage(rectangle,diagram.version);

			if (image.parse(baseDrawOb)>=0) {
				
				diagram.addObject(image.image);
			}
			return;
		}
		if(baseDrawOb.GetDrawObType()== 7) { //弧形
			SFArc arc = new SFArc(rectangle,diagram.version);

			if (arc.parse(baseDrawOb)>=0) {

				diagram.addObject(arc.arc);
			}
			return;
		}

		if(baseDrawOb.GetDrawObType()==15 || baseDrawOb.GetDrawObType()== 30) { //趋势
			logger.error("趋势不处理");
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
		if (baseDrawOb.GetDrawObType()>=100) {
			logger.error("未知类型不处理");
		}
		logger.error(baseDrawOb.strName);
			logger.error(this.getClass()+""+baseDrawOb.GetDrawObType()+" is not parse");
		
			
	}

		
}
