// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSBar.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Vector;

import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, AWTContext, GBLineStyle, 
//			DCSEvent

public class DCSBar extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 12;
	public byte direct;
	public short x;
	public short y;
	public short width;
	public short height;
	public double lowLimit;
	public double highLimit;
	public double value;
	short lowType;
	short hiType;
	String lowName;
	String lowFD;
	String hiName;
	String hiFD;
	public String pointName;
	public String FD;
	public DCSColor fgColor;
	public DCSColor bgColor;
	DCSEvent dcsEvent;
	int vx;
	int vy;
	int vw;
	int vh;

	public DCSBar()
	{
		dcsEvent = null;
	}

	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
        
    }
	public DCSBar(String pn, short x1, short y1, short w1, short h1)
	{
		dcsEvent = null;
		pointName = pn;
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}

	public void setAttributes(byte d, float ll, float hl)
	{
		direct = d;
		lowLimit = ll;
		highLimit = hl;
	}

	public void setAttributes(DCSColor fg, DCSColor bg, byte d, double ll, double hl)
	{
		fgColor = fg;
		bgColor = bg;
		direct = d;
		lowLimit = ll;
		highLimit = hl;
	}

	public byte getType()
	{
		return 12;
	}

	public Rectangle getBounds()
	{
		Rectangle b = new Rectangle(x, y, width, height);
		return b;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
		points.add(pointName);
		if (lowType == 1)
			points.add(lowName);
		if (hiType == 1)
			points.add(hiName);
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

	public boolean contains(Point p)
	{
		return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
	}

	public void getLimit()
	{
		if (lowType == 1)
			lowLimit = awtContext.getOpDataProvider().getValueFloat(lowName, lowFD);
		if (hiType == 1)
			highLimit = awtContext.getOpDataProvider().getValueFloat(hiName, hiFD);
		if (highLimit <= lowLimit)
			highLimit = lowLimit + 100D;
	}

	public void transform(Graphics g, Dimension d)
	{
		vx = CoordinateTransform.X_StandardToView(d, x);
		vy = CoordinateTransform.Y_StandardToView(d, y);
		vw = CoordinateTransform.X_StandardToView(d, width);
		vh = CoordinateTransform.Y_StandardToView(d, height);
	}

	public Shape getShape(Dimension d)
	{
		Rectangle rect = new Rectangle(vx, vy, vw, vh);
		return rect;
	}

	public void draw(Graphics g, Dimension d)
	{
		boolean valid = false;
		getLimit();
		value = awtContext.getOpDataProvider().getValueFloat(pointName, FD);
		if (value < lowLimit)
			value = lowLimit;
		else
		if (value > highLimit)
			value = highLimit;
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		GBLineStyle.setLine(g, 0, 1);
		switch (direct)
		{
		case 0: // '\0'
			drawUpBar(g, vx, vy, vw, vh, fc, bc);
			break;

		case 1: // '\001'
			drawDownBar(g, vx, vy, vw, vh, fc, bc);
			break;

		case 2: // '\002'
			drawLeftBar(g, vx, vy, vw, vh, fc, bc);
			break;

		case 3: // '\003'
			drawRightBar(g, vx, vy, vw, vh, fc, bc);
			break;

		case 4: // '\004'
			drawVBiasBar(g, vx, vy, vw, vh, fc, bc);
			break;

		case 5: // '\005'
			drawHBiasBar(g, vx, vy, vw, vh, fc, bc);
			break;
		}
	}

	public void drawUpBar(Graphics g, int x0, int y0, int w0, int h0, Color fc, Color bc)
	{
		int h1 = (int)(((double)h0 * (highLimit - value)) / (highLimit - lowLimit));
		g.setColor(bc);
		g.fillRect(x0, y0, w0, h1);
		g.setColor(fc);
		g.fillRect(x0, y0 + h1, w0, h0 - h1);
	}

	public void drawDownBar(Graphics g, int x0, int y0, int w0, int h0, Color fc, Color bc)
	{
		int h1 = (int)(((double)h0 * (value - lowLimit)) / (highLimit - lowLimit));
		g.setColor(bc);
		g.fillRect(x0, y0 + h1, w0, h0 - h1);
		g.setColor(fc);
		g.fillRect(x0, y0, w0, h1);
	}

	public void drawLeftBar(Graphics g, int x0, int y0, int w0, int h0, Color fc, Color bc)
	{
		int w1 = (int)(((double)w0 * (highLimit - value)) / (highLimit - lowLimit));
		g.setColor(bc);
		g.fillRect(x0, y0, w1, h0);
		g.setColor(fc);
		g.fillRect(x0 + w1, y0, w0 - w1, h0);
	}

	public void drawRightBar(Graphics g, int x0, int y0, int w0, int h0, Color fc, Color bc)
	{
		int w1 = (int)(((double)w0 * (value - lowLimit)) / (highLimit - lowLimit));
		g.setColor(bc);
		g.fillRect(x0 + w1, y0, w0 - w1, h0);
		g.setColor(fc);
		g.fillRect(x0, y0, w1, h0);
	}

	public void drawVBiasBar(Graphics g, int x0, int y0, int w0, int h0, Color fc, Color bc)
	{
		int h1 = (int)(((double)h0 * (value - (highLimit + lowLimit) / 2D)) / (highLimit - lowLimit));
		int y1;
		if (h1 > 0)
		{
			y1 = (y0 + h0 / 2) - h1;
		} else
		{
			h1 = Math.abs(h1);
			y1 = y0 + h0 / 2;
		}
		g.setColor(bc);
		g.fillRect(x0, y0, w0, h0);
		g.setColor(fc);
		g.fillRect(x0, y1, w0, h1);
	}

	public void drawHBiasBar(Graphics g, int x0, int y0, int w0, int h0, Color fc, Color bc)
	{
		int w1 = (int)(((double)w0 * (value - (highLimit + lowLimit) / 2D)) / (highLimit - lowLimit));
		int x1;
		if (w1 > 0)
		{
			x1 = x0 + w0 / 2;
		} else
		{
			w1 = Math.abs(w1);
			x1 = (x0 + w0 / 2) - w1;
		}
		g.setColor(bc);
		g.fillRect(x0, y0, w0, h0);
		g.setColor(fc);
		g.fillRect(x1, y0, w1, h0);
	}

	public Object clone()
	{
		DCSBar bar = new DCSBar(pointName, x, y, width, height);
		bar.setAttributes(fgColor, bgColor, direct, lowLimit, highLimit);
		return bar;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(12);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeByte(direct);
		out.writeUTF(pointName);
		out.writeUTF(FD);
		out.writeByte(lowType);
		if (lowType == 0)
		{
			out.writeFloat((float)lowLimit);
		} else
		{
			out.writeUTF(lowName);
			out.writeUTF(lowFD);
		}
		out.writeByte(hiType);
		if (lowType == 0)
		{
			out.writeFloat((float)highLimit);
		} else
		{
			out.writeUTF(hiName);
			out.writeUTF(hiFD);
		}
		fgColor.write(out);
		if (bgColor == null)
			bgColor = new DCSColor(Color.BLACK);
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
		direct = in.readByte();
		pointName = in.readUTF();
		FD = in.readUTF();
		lowType = in.readByte();
		if (lowType == 0)
		{
			lowLimit = in.readFloat();
		} else
		{
			lowName = in.readUTF();
			lowFD = in.readUTF();
		}
		hiType = in.readByte();
		if (hiType == 0)
		{
			highLimit = in.readFloat();
		} else
		{
			hiName = in.readUTF();
			hiFD = in.readUTF();
		}
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

	public void transform(Dimension d)
	{
		x = (short)CoordinateTransform.X_ViewToStandard(d, x);
		y = (short)CoordinateTransform.Y_ViewToStandard(d, y);
		width = (short)CoordinateTransform.X_ViewToStandard(d, width);
		height = (short)CoordinateTransform.Y_ViewToStandard(d, height);
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
