// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PrintContent.java

package magus.util;

import java.awt.*;
import java.awt.print.*;
import javax.swing.JPanel;

public class PrintContent
	implements Printable
{

	JPanel parent;
	Image pageImage;

	public PrintContent(JPanel jpanel, Image img)
	{
		parent = jpanel;
		pageImage = img;
	}

	public void setImage(Image img)
	{
		pageImage = img;
	}

	public int print(Graphics g, PageFormat pf, int index)
		throws PrinterException
	{
		int px = (int)pf.getImageableX();
		int py = (int)pf.getImageableY();
		int pw = (int)pf.getImageableWidth();
		int ph = (int)pf.getImageableHeight();
		g.setColor(Color.white);
		g.fillRect(px, py, pw, ph);
		Graphics2D g2 = (Graphics2D)g;
		g2.translate(px, py);
		double scaleX = (double)pw / (double)pageImage.getWidth(null);
		double scaleY = (double)ph / (double)pageImage.getHeight(null);
		g2.scale(scaleX, scaleY);
		g2.drawImage(pageImage, 0, 0, null);
		return 0;
	}
}
