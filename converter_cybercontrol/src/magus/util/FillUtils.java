package magus.util;

import java.awt.Color;

import org.apache.log4j.Logger;

import SfnUI.GUI.BackRender;
import awt.FillMode;

public class FillUtils {
	static Logger logger = Logger.getLogger(FillUtils.class);
	public static FillMode createFillMode(Integer fillMode, String string, Color color) {
		
		logger.info(fillMode);
		if (fillMode == 0) {
			FillMode mode = new FillMode();
			mode.parseFillMode("0");
			return mode;
		} else if (fillMode == 1){
			FillMode mode = new FillMode();
			mode.parseFillMode("1");
			return mode;
		} else if(fillMode == 2) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(color);
			mode.setDirection((byte) 1);
			mode.setMorph((byte) 0);
			return mode;
		} else if(fillMode == 3) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(color);
			mode.setDirection((byte) 0);
			mode.setMorph((byte) 0);
			return mode;
		} else if(fillMode == 4) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(color);
			mode.setDirection((byte) 1);
			mode.setMorph((byte) 2);
			return mode;
		} else if(fillMode == 5) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(color);
			mode.setDirection((byte) 0);
			mode.setMorph((byte) 2);
			return mode;
		}else if(fillMode == 9) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 2);
			mode.setPattern((byte)11);
			return mode;
		}else if(fillMode == 10) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 1);
			mode.setPattern((byte)0);
			return mode;
		} else if(fillMode == 11) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 1);
			mode.setPattern((byte)0);
			return mode;
		} else {
			logger.info("fillMode="+fillMode);
			return null;
		}
		// TODO Auto-generated method stub
		
	}
	public static FillMode createFillMode(BackRender backRender) {
		//backRender.m_crFillColor;
		if(backRender.m_OptionPara == 0) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(backRender.m_crFillColor2);
			mode.setDirection((byte) 0);
			mode.setMorph((byte) 0);
			return mode;
		} else if(backRender.m_OptionPara == 1) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(backRender.m_crFillColor2);
			mode.setDirection((byte) 1);
			mode.setMorph((byte) 0);
			return mode;
		} else if(backRender.m_OptionPara == 2) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(backRender.m_crFillColor2);
			mode.setDirection((byte) 0);
			mode.setMorph((byte) 2);
			return mode;
		} else if(backRender.m_OptionPara == 3) {
			FillMode mode = new FillMode();
			mode.setStyle((byte) 3);
			mode.setColor(backRender.m_crFillColor2);
			mode.setDirection((byte) 1);
			mode.setMorph((byte) 2);
			return mode;
		}
		return null;
	}

}
