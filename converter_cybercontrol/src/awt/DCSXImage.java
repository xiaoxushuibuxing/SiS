// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSXImage.java

package awt;

import java.awt.*;
import java.awt.image.IndexColorModel;
import java.awt.image.MemoryImageSource;
import java.io.*;
import java.util.Vector;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSConditionImage, AWTContext, DCSColor, 
//			DCSXImageLib, DCSXImageData, DCSEvent

public class DCSXImage extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 15;
	String name;
	short x;
	short y;
	short width;
	short height;
	Image dcsImage;
	DCSEvent dcsEvent;
	Vector controlPoint;
	DCSColor fgColor;
	DCSConditionImage condImage;
	DCSXImageData xbm;
	MemoryImageSource imgSrc;

	public DCSXImage()
	{
		dcsImage = null;
		dcsEvent = null;
		controlPoint = null;
		condImage = null;
		xbm = null;
		imgSrc = null;
	}

	public DCSXImage(String s, short x1, short y1, short w1, short h1)
	{
		dcsImage = null;
		dcsEvent = null;
		controlPoint = null;
		condImage = null;
		xbm = null;
		imgSrc = null;
		name = s;
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}

	public void parseXImage(String s)
	{
		s = s.trim();
		int c1 = s.indexOf(' ');
		if (c1 < 0)
		{
			name = s;
			condImage = null;
			return;
		} else
		{
			name = s.substring(0, c1);
			String s1 = s.substring(c1 + 1);
			condImage = new DCSConditionImage();
			condImage.parseConditionImage(s1);
			return;
		}
	}

	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
    }
	public void setDefaultImage(String s)
	{
		name = s;
	}

	public void setConditionImage(DCSConditionImage img1)
	{
		condImage = img1;
	}

	public String getDefaultImage()
	{
		return name;
	}

	public DCSConditionImage getConditionImage()
	{
		return condImage;
	}

	public String getImage()
	{
		if (condImage == null)
			return name;
		String s = condImage.getImage(awtContext.getOpDataProvider());
		if (s == null)
			return name;
		else
			return s;
	}

	public String toString()
	{
		String s = name;
		if (condImage != null)
			s = s + " " + condImage.toString();
		return s;
	}

	public byte getType()
	{
		return 15;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
		Vector vfg = fgColor.getProcessPoints();
		if (vfg != null)
		{
			for (int i = 0; i < vfg.size(); i++)
			{
				String pname = (String)vfg.elementAt(i);
				if (!points.contains(pname))
					points.add(pname);
			}

		}
		if (condImage == null)
			return points;
		Vector vimg = condImage.getProcessPoints();
		if (vimg != null)
		{
			for (int i = 0; i < vimg.size(); i++)
			{
				String pname = (String)vimg.elementAt(i);
				if (!points.contains(pname))
					points.add(pname);
			}

		}
		return points;
	}

	public Rectangle getBounds()
	{
		Rectangle b = new Rectangle(x, y, width, height);
		return b;
	}

	public boolean contains(Point p)
	{
		return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
	}

	public boolean loadImage(String filename)
	{
		xbm = DCSXImageLib.getInstance().loadBitmapFile(awtContext.getXImagePath() + filename);
		if (xbm == null)
			return false;
		Color defColor = fgColor.getDefaultColor();
		Color dynColor[] = fgColor.getDynamicColor();
		int bits = 1;
		int size = 2;
		if (dynColor != null)
		{
			if (dynColor.length > 6)
				return false;
			bits = 3;
			size += dynColor.length;
		}
		int cmap[] = new int[size];
		cmap[0] = -1;
		cmap[1] = defColor.getRGB();
		for (int i = 0; i < size - 2; i++)
			cmap[i + 2] = dynColor[i].getRGB();

		IndexColorModel cm = new IndexColorModel(bits, size, cmap, 0, false, 0, 0);
		imgSrc = new MemoryImageSource(xbm.w, xbm.h, cm, xbm.pixel, 0, xbm.h);
		imgSrc.setAnimated(true);
		dcsImage = Toolkit.getDefaultToolkit().createImage(imgSrc);
		return true;
	}

	public void transform(Graphics g1, Dimension dimension)
	{
	}

	public Shape getShape(Dimension d)
	{
		int vx = CoordinateTransform.X_StandardToView(d, x);
		int vy = CoordinateTransform.Y_StandardToView(d, y);
		int vw = CoordinateTransform.X_StandardToView(d, width);
		int vh = CoordinateTransform.Y_StandardToView(d, height);
		Rectangle rect = new Rectangle(vx, vy, vw, vh);
		return rect;
	}

	public void draw(Graphics g, Dimension d)
	{
		loadImage(getImage());
		if (dcsImage == null || width <= 0 || height <= 0)
			return;
		int clr = fgColor.getColorIndex(awtContext.getOpDataProvider()) + 1;
		for (int i = 0; i < xbm.w; i++)
		{
			for (int j = 0; j < xbm.h; j++)
			{
				int k = i * xbm.h + j;
				if (xbm.pixel[k] > 0)
					xbm.pixel[k] = (byte)clr;
			}

		}

		imgSrc.newPixels(0, 0, xbm.w, xbm.h);
		int vx = CoordinateTransform.X_StandardToView(d, x);
		int vy = CoordinateTransform.Y_StandardToView(d, y);
		int vw = CoordinateTransform.X_StandardToView(d, width);
		int vh = CoordinateTransform.Y_StandardToView(d, height);
		g.drawImage(dcsImage, vx, vy, vw, vh, null);
	}

	public Object clone()
	{
		DCSXImage ximg = new DCSXImage(name, x, y, width, height);
		ximg.setFGColor(fgColor);
		ximg.setEvent(dcsEvent);
		ximg.loadImage(name);
		ximg.setConditionImage(condImage);
		return ximg;
	}

	public void setFGColor(DCSColor fc)
	{
		fgColor = fc;
	}

	public void setEvent(DCSEvent e)
	{
		dcsEvent = e;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(15);
		out.writeUTF(toString());
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		fgColor.write(out);
		if (dcsEvent == null)
		{
			out.writeByte(0);
		} else
		{
			out.writeByte(1);
			dcsEvent.write(out);
		}
	}

	public int read(DataInputStream in)
		throws IOException
	{
		name = in.readUTF();
		parseXImage(name);
		x = in.readShort();
		y = in.readShort();
		width = in.readShort();
		height = in.readShort();
		fgColor = new DCSColor();
		fgColor.read(in);
		if (in.readByte() == 1)
		{
			dcsEvent = new DCSEvent();
			dcsEvent.read(in);
		}
		return loadImage(name) ? 0 : -1;
	}

	public int read(BufferedReader in)
	{
		return 0;
	}

	public void transform(Dimension dimension)
	{
	}

	public void offset(int i, int j, float f, float f1)
	{
	}

	public void setBounds(short x1, short y1, short w1, short h1)
	{
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}
}
