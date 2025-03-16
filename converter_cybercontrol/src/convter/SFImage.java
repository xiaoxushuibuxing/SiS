package convter;

import java.awt.Rectangle;

import org.apache.log4j.Logger;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.ImageResource;
import SfnUI.GUI.BaseDraw.ImageObject;
import SfnUI.i18n.MyResourceManager;
import awt.DCSImage;

public class SFImage {

	public DCSImage image;
	private Rectangle rectangle;
	private Logger logger = Logger.getLogger(getClass());
	private int version;

	public SFImage(Rectangle rectangle,int version) {
		this.rectangle =rectangle;
		this.version =version;
		//image=new DCSImage();
	}
	public int parse(BaseDrawOb baseDrawOb) {
		// TODO Auto-generated method stub
		ImageObject obj = (ImageObject) baseDrawOb;
		Integer xs = Integer.valueOf(obj.getProp("", MyResourceManager.Left).toString());
		Integer ys = Integer.valueOf(obj.getProp("", MyResourceManager.Up).toString());
		Integer widthTag = Integer.valueOf(obj.getProp("", MyResourceManager.Width).toString());
		Integer heightTag = Integer.valueOf(obj.getProp("", MyResourceManager.Height).toString());
		
		short x= (short)((double)(xs * 16000) / rectangle.width + 0.5D);
		short y = (short)((double)(ys * 16000) / rectangle.height + 0.5D);
		short width = (short)((double)(widthTag * 16000) / rectangle.width + 0.5D);
		short height = (short)((double)(heightTag * 16000) / rectangle.height + 0.5D);
		System.out.println("----------------------------------------");
		ImageResource imageResource = (ImageResource)obj.get(MyResourceManager.ImageFile);
		logger.info(imageResource);
		logger.info(imageResource.getSourcename());
		System.out.println("----------------------------------------");
		image = new DCSImage("fw1"+imageResource.getSourcename().toLowerCase(), x, y, width, height);
		image.version = this.version;
		return 0;
	}

}
