// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OLRect.java

package awt;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.util.Vector;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, FillMode, AWTContext, 
//			GBLineStyle, DCSEvent

public class OLRect extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 17;
	short x;
	short y;
	short width;
	short height;
	short ra;
	short rb;
	byte lineStyle;
	byte lineWidth;
	DCSColor fgColor;
	DCSColor bgColor;
	FillMode fillMode;
	DCSEvent dcsEvent;
	int vx;
	int vy;
	int vw;
	int vh;
	int vra;
	int vrb;

	public OLRect()
	{
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		ra = 50;
		rb = 50;
	}

	public OLRect(int x1, int y1, int w1, int h1)
	{
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		x = (short)x1;
		y = (short)y1;
		width = (short)w1;
		height = (short)h1;
		ra = 50;
		rb = 50;
	}

	public void setAttributes(DCSColor fc, DCSColor bc, byte ls, byte lw, FillMode fm)
	{
		fgColor = fc;
		bgColor = bc;
		lineStyle = ls;
		lineWidth = lw;
		fillMode = fm;
	}
	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
    }
	public byte getType()
	{
		return 17;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public byte getLineWidth()
	{
		return lineWidth;
	}

	public byte getLineStyle()
	{
		return lineStyle;
	}

	public FillMode getFillMode()
	{
		return fillMode;
	}

	public DCSColor getFGColor()
	{
		return fgColor;
	}

	public DCSColor getBGColor()
	{
		return bgColor;
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
		Vector vbg = bgColor.getProcessPoints();
		if (vbg != null)
		{
			for (int i = 0; i < vbg.size(); i++)
			{
				String pname = (String)vbg.elementAt(i);
				if (!points.contains(pname))
					points.add(pname);
			}

		}
		return points;
	}

	public Rectangle getBounds()
	{
		Rectangle bounds = new Rectangle(x, y, width, height);
		return bounds;
	}

	public boolean contains(Point p)
	{
		return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
	}

	public boolean contains(int xp, int yp)
	{
		return xp >= x && xp <= x + width && yp >= y && yp <= y + height;
	}

	public void transform(Graphics g, Dimension d)
	{
		vx = CoordinateTransform.X_StandardToView(d, x);
		vy = CoordinateTransform.Y_StandardToView(d, y);
		vw = CoordinateTransform.X_StandardToView(d, width);
		vh = CoordinateTransform.Y_StandardToView(d, height);
		vra = CoordinateTransform.X_StandardToView(d, ra);
		vrb = CoordinateTransform.Y_StandardToView(d, rb);
	}

	public Shape getShape(Dimension d)
	{
		RoundRectangle2D.Float rect = new RoundRectangle2D.Float(vx, vy, vw, vh, vra, vrb);
		return rect;
	}

	public void draw(Graphics g, Dimension d)
	{
		RoundRectangle2D.Float rect = new RoundRectangle2D.Float(vx, vy, vw, vh, vra, vrb);
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		Graphics2D g2 = (Graphics2D)g;
		fillMode.fill(g2, bc, rect);
		if (lineWidth > 0)
		{
			g2.setColor(fc);
			GBLineStyle.setLine(g, lineStyle, lineWidth);
			g2.draw(rect);
		}
		rect = null;
	}

	public Object clone()
	{
		OLRect olrect = new OLRect(x, y, width, height);
		olrect.setAttributes(fgColor, bgColor, lineStyle, lineWidth, fillMode);
		return olrect;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(17);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeShort(ra);
		out.writeShort(rb);
		out.writeByte(lineStyle);
		out.writeByte(lineWidth);
		out.writeUTF(fillMode.toString());
		fgColor.write(out);
		bgColor.write(out);
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
		x = in.readShort();
		y = in.readShort();
		width = in.readShort();
		height = in.readShort();
		ra = in.readShort();
		rb = in.readShort();
		lineStyle = in.readByte();
		lineWidth = in.readByte();
		fillMode = new FillMode();
		fillMode.parseFillMode(in.readUTF());
		fgColor = new DCSColor();
		fgColor.read(in);
		bgColor = new DCSColor();
		bgColor.read(in);
		if (in.readByte() == 1)
		{
			dcsEvent = new DCSEvent();
			dcsEvent.read(in);
		}
		return 0;
	}

	public void transform(Dimension dimension)
	{
	}

	public void offset(int i, int j, float f, float f1)
	{
	}

	public void setBounds(short word0, short word1, short word2, short word3)
	{
	}
}
