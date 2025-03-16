// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSImage.java

package awt;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.Vector;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, AWTContext, DCSCondition, DCSEvent

public class DCSImage extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 10;
	String name;
	short x;
	short y;
	short width;
	short height;
	Image dcsImage;
	DCSEvent dcsEvent;
	int vx;
	int vy;
	int vw;
	int vh;
	public int version;
	byte showType;
	DCSCondition isShowCondition;

	public DCSImage()
	{
		dcsImage = null;
		dcsEvent = null;
		version = 1001;
		showType = 0;
		isShowCondition = null;
	}

	public DCSImage(int vsn)
	{
		dcsImage = null;
		dcsEvent = null;
		//version = 1001;
		showType = 0;
		isShowCondition = null;
		version = vsn;
	}

	public DCSImage(String s, short x1, short y1, short w1, short h1)
	{
		dcsImage = null;
		dcsEvent = null;
		version = 1001;
		showType = 0;
		isShowCondition = null;
		name = s;
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}
	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
    }
	public URL getURL(String imagename)
	{
		String urlname =  imagename;
		System.out.println(urlname);
		URL imageURL;
		try
		{
			imageURL = new URL(urlname);
		}
		catch (IOException e)
		{
			imageURL = null;
		}
		return imageURL;
	}

	public byte getType()
	{
		return 10;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		return null;
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

	public void transform(Graphics g, Dimension d)
	{
		if (width < 0 || height < 0)
		{
			vw = dcsImage.getWidth(null);
			vh = dcsImage.getHeight(null);
			width = (short)CoordinateTransform.X_ViewToStandard(d, vw);
			height = (short)CoordinateTransform.Y_ViewToStandard(d, vh);
		} else
		{
			vw = CoordinateTransform.X_StandardToView(d, width);
			vh = CoordinateTransform.Y_StandardToView(d, height);
		}
		vx = CoordinateTransform.X_StandardToView(d, x);
		vy = CoordinateTransform.Y_StandardToView(d, y);
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
		if (showType != 0)
		{
			if (showType == 1 && isShowCondition.getCondition(awtContext.getOpDataProvider()) != 1)
				return;
			if (showType == 2 && isShowCondition.getCondition(awtContext.getOpDataProvider()) == 1)
				return;
		}
		g.drawImage(dcsImage, vx, vy, vw, vh, null);
	}

	public Object clone()
	{
		DCSImage img = new DCSImage(name, x, y, width, height);
		img.version = version;
		return img;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(10);
		out.writeUTF(name);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		if (dcsEvent == null)
		{
			out.writeByte(0);
		} else
		{
			out.writeByte(1);
			dcsEvent.write(out);
		}
		if (version >= 2001)
			if (isShowCondition == null)
			{
				out.writeByte(0);
			} else
			{
				out.writeByte(1);
				out.writeUTF(isShowCondition.toString());
			}
	}

	public int read(DataInputStream in)
		throws IOException
	{
		name = in.readUTF();
		x = in.readShort();
		y = in.readShort();
		width = in.readShort();
		height = in.readShort();
		if (in.readByte() == 1)
		{
			dcsEvent = new DCSEvent();
			dcsEvent.read(in);
		}
		if (version >= 2001)
		{
			showType = in.readByte();
			if (showType > 0)
			{
				isShowCondition = new DCSCondition();
				isShowCondition.parse(in.readUTF());
			}
		}
		dcsImage = Toolkit.getDefaultToolkit().getImage(getURL(name));
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
