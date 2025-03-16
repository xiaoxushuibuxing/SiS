package convter;

import java.awt.Color;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import com.sfauto.toolkits.utils.RM;
import com.sfauto.toolkits.utils.SysProperty;

import SfnUI.GUI.BackRender;
import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.GSEventProcess;
import SfnUI.GUI.BaseDraw.ButtonDrawObj;
import SfnUI.GUI.BaseDraw.RectObject;
import SfnUI.i18n.GUIGlossary;
import SfnUI.i18n.MyResourceManager;
import awt.DCSButton;
import awt.DCSColor;
import awt.DCSDiagram;
import awt.DCSEvent;
import awt.DCSImage;
import awt.DCSRect;
import awt.FillMode;
import magus.util.FillUtils;
import magus.util.PathUtils;
import transform.TransForm;

public class SFButton extends SFObject{

	public DCSButton button;
	private Rectangle rectangle;
	public boolean isRect= false;
	public DCSRect rect;
	private int version;
	public DCSImage image;
	public boolean isImage;
	private Logger logger = Logger.getLogger(getClass());

	public SFButton(Rectangle rectangle,int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
		button = new DCSButton(version);
		this.rectangle =rectangle;
	}

	public int parse(BaseDrawOb baseDrawOb) {
		ButtonDrawObj obj = (ButtonDrawObj) baseDrawOb;
		Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
		Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
		Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
		Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());
		button.setText(obj.GetButtonText());
		short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		button.setBounds(x, y, width, height);
		DCSColor fgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.ButtonTextColor));
		DCSColor bgColor = new DCSColor((Color) obj.getProp("", MyResourceManager.FillColor));
		button.setColor(fgColor, bgColor);
		button.setFont(obj.GetTextFont());
		button.setBorder((short) 30);

		Integer[] eventProc = obj.getAllEventProc();
		if (eventProc != null && eventProc.length >0) {
			for (Integer eventId : eventProc) {
				GSEventProcess process = obj.getEventProcess(eventId);
				if (process instanceof GSEventProcess.r) {
					GSEventProcess.r a= (GSEventProcess.r)process;
					Object prop = a.getProp("", RM.gs("推出画面"));
					if (prop != null) {
						String eventStr ="fw1" +prop.toString().replaceAll(".drw", ".ser").replaceAll(" ", "");
						eventStr = PathUtils.replaceHashWithNumber(eventStr);
						DCSEvent event= new DCSEvent(DCSEvent.CHANGE_PAGE,eventStr);
						button.setEvent(event);
					}
					
				}if (process instanceof GSEventProcess.e) {
					GSEventProcess.e a= (GSEventProcess.e)process;
					String prop = a.getProp("", MyResourceManager.Script).toString();
					String[] splits = prop.split(";");
					String eventTag="";
					logger.info("ObjMark="+obj.getObjMark());
					for (String str : splits) {
						logger.info(str);
						if (str != null && str.contains("openGraph")) {
							
						        String[] parts = str.split("\"");
						        if (parts.length > 1) {
						            logger.info(parts[1]);
						            eventTag=TransForm.dir+ parts[1].replaceAll(".drw", ".ser").replaceAll(" ", "")
											.replaceAll("\\(", "").replaceAll("\\)", "");
						            eventTag = PathUtils.replaceHashWithNumber(eventTag);
						            DCSEvent event= new DCSEvent(DCSEvent.CHANGE_PAGE,eventTag);
									button.setEvent(event);
									break;
						        }
						}
					}					
					
				} else {
					logger.info("------------------------------------"+eventId);
				}
				
			}
		}
		BackRender backRender = (BackRender)obj.get(GUIGlossary.Button_Bk);
		if (backRender.toString().equals(GUIGlossary.BackType_Desc_Gradiant)) {
			this.isRect = true;
			this.rect = new DCSRect(x, y, width, height);
			this.rect.version = this.version;
			this.rect.fgColor = this.button.fgColor;
			this.rect.bgColor = this.button.bgColor;
			this.rect.lineStyle=0;
			this.rect.lineWidth=1;
			FillMode fillMode = FillUtils.createFillMode(backRender);
			logger.info(fillMode);
			this.rect.setFillMode(fillMode);
			if(button.getEvent()!= null)
				this.rect.dcsEvent = this.button.getEvent();
		} else if(backRender.toString().equals(GUIGlossary.BackType_Desc_Bitmap)) {
			this.isImage = true;
			//this.rect.version = this.version;
			String string = backRender.m_lpszBitmapPath;
			
			String[] split = string.split(".");
			String path = "fw1/"+string.replace(TransForm.imagePath, "");
			System.out.println(path);
			this.image = new DCSImage(path.toLowerCase(), x, y, width, height); 
			
			this.image.version = this.version;
		}
		
		return 0;
	}

}
