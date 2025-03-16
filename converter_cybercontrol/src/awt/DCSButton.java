// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSButton.java

package awt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.Vector;
import magus.util.ColorParser;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, AWTContext, DCSDiagram, 
//			DCSEvent

public class DCSButton extends AbstractDCSObject
{

	final byte OBJECT_TYPE = 11;
	public short x;
	public short y;
	public short width;
	public short height;
	public short border=20;
	public String text;
	public Font font;
	public DCSColor fgColor;
	public DCSColor bgColor;
	Rectangle bounds;
	DCSEvent dcsEvent;
	boolean state;
	DCSDiagram parent;
	int vx;
	int vy;
	int vw;
	int vh;
	private int version;

	public DCSButton(DCSDiagram diagram)
	{
		dcsEvent = null;
		state = false;
		parent = diagram;
	}

	public DCSButton(DCSDiagram diagram, short x1, short y1, short w1, short h1)
	{
		dcsEvent = null;
		state = false;
		parent = diagram;
		x = x1;
		y = y1;
		width = w1;
		height = h1;
		border = 100;
		text = "Button1";
		font = new Font("Dialog", 1, 18);
		fgColor = new DCSColor(Color.black);
		bgColor = new DCSColor(Color.lightGray);
	}

	public DCSButton(int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
	}

	public void setText(String s)
	{
		text = s;
	}

	public void setFont(Font fnt)
	{
		font = fnt;
	}

	public void setEvent(DCSEvent e)
	{
		dcsEvent = e;
	}

	public void setAttributes(DCSColor fg, DCSColor bg, short b)
	{
		fgColor = fg;
		bgColor = bg;
		border = b;
	}

	public byte getType()
	{
		return 11;
	}

	public Rectangle getBounds()
	{
		Rectangle bounds = new Rectangle(x, y, width, height);
		return bounds;
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

	public void setState(boolean st)
	{
		state = st;
	}

	public boolean getState()
	{
		return state;
	}

	public boolean contains(Point p)
	{
		if (bounds == null)
			return false;
		return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
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
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		g.setColor(bc);
		g.fillRect(vx, vy, vw, vh);
		drawBorder(g, d);
		AffineTransform AT = new AffineTransform();
		float t1 = (1.0F * (float)d.width) / (float)parent.getWidth();
		float t2 = (1.0F * (float)d.height) / (float)parent.getHeight();
		AT.setToScale(t1, t2);
		Font deriveFont = font.deriveFont(AT);
		g.setFont(deriveFont);
		FontMetrics fontMetrics = g.getFontMetrics();
		int ascent = fontMetrics.getAscent();
		int descent = fontMetrics.getDescent();
		int leading = fontMetrics.getLeading();
		int charHeight = ascent + descent;
		int charWidth = fontMetrics.charWidth('W');
		int strWidth = fontMetrics.stringWidth(text);
		vx = CoordinateTransform.X_StandardToView(d, x);
		vy = CoordinateTransform.Y_StandardToView(d, y);
		vw = CoordinateTransform.X_StandardToView(d, width);
		vh = CoordinateTransform.Y_StandardToView(d, height);
		int xt = vx + (int)((float)(vw - strWidth) / 2.0F + 0.5F);
		int yt = vy + (int)((float)(vh - charHeight) / 2.0F + 0.5F) + ascent;
		g.setColor(fgColor.getColor(awtContext.getOpDataProvider()));
		g.drawString(text, xt, yt);
	}

	public void drawBorder(Graphics g, Dimension d)
	{
		int xp[] = new int[6];
		int yp[] = new int[6];
		xp[0] = x;
		yp[0] = y;
		xp[1] = x + width;
		yp[1] = y;
		xp[2] = (x + width) - border;
		yp[2] = y + border;
		xp[3] = x + border;
		yp[3] = y + border;
		xp[4] = x + border;
		yp[4] = (y + height) - border;
		xp[5] = x;
		yp[5] = y + height;
		for (int i = 0; i < 6; i++)
		{
			xp[i] = CoordinateTransform.X_StandardToView(d, xp[i]);
			yp[i] = CoordinateTransform.Y_StandardToView(d, yp[i]);
		}

		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		if (state)
		{
			g.setColor(ColorParser.darker(bc));
			g.fillPolygon(xp, yp, 6);
		} else
		{
			g.setColor(ColorParser.brighter(bc));
			g.fillPolygon(xp, yp, 6);
		}
		xp[0] = x;
		yp[0] = y + height;
		xp[1] = x + width;
		yp[1] = y + height;
		xp[2] = x + width;
		yp[2] = y;
		xp[3] = (x + width) - border;
		yp[3] = y + border;
		xp[4] = (x + width) - border;
		yp[4] = (y + height) - border;
		xp[5] = x + border;
		yp[5] = (y + height) - border;
		for (int i = 0; i < 6; i++)
		{
			xp[i] = CoordinateTransform.X_StandardToView(d, xp[i]);
			yp[i] = CoordinateTransform.Y_StandardToView(d, yp[i]);
		}

		if (state)
		{
			g.setColor(ColorParser.brighter(bc));
			g.fillPolygon(xp, yp, 6);
		} else
		{
			g.setColor(ColorParser.darker(bc));
			g.fillPolygon(xp, yp, 6);
		}
	}

	public Object clone()
	{
		DCSButton btn = new DCSButton(parent, x, y, width, height);
		btn.setAttributes(fgColor, bgColor, border);
		btn.setText(text);
		btn.setFont(font);
		btn.setEvent(dcsEvent);
		return btn;
	}

	@Override
	public String toString() {
		return "DCSButton [OBJECT_TYPE=" + OBJECT_TYPE + ", x=" + x + ", y=" + y + ", width=" + width + ", height="
				+ height + ", border=" + border + ", text=" + text + ", font=" + font + ", fgColor=" + fgColor
				+ ", bgColor=" + bgColor + ", bounds=" + bounds + ", dcsEvent=" + dcsEvent + ", state=" + state
				+ ", parent=" + parent + ", vx=" + vx + ", vy=" + vy + ", vw=" + vw + ", vh=" + vh + "]";
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(11);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeShort(border);
		out.writeUTF(text);
		if (font == null)
			font = new Font("Dialog", 0, 12);
		out.writeUTF(font.getFamily());
		out.writeInt(font.getStyle());
		out.writeInt(font.getSize());
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
		border = in.readShort();
		text = in.readUTF();
		font = new Font(in.readUTF(), in.readInt(), in.readInt());
		fgColor = new DCSColor();
		fgColor.read(in);
		bgColor = new DCSColor();
		bgColor.read(in);
		if (in.readByte() == 1)
		{
			dcsEvent = new DCSEvent();
			dcsEvent.read(in);
		}
		bounds = getBounds();
		return 0;
	}

	public void transform(Dimension d)
	{
		x = (short)CoordinateTransform.X_ViewToStandard(d, x);
		y = (short)CoordinateTransform.Y_ViewToStandard(d, y);
		width = (short)CoordinateTransform.X_ViewToStandard(d, width);
		height = (short)CoordinateTransform.Y_ViewToStandard(d, height);
	}

	public void offset(int x, int y, float width_zoom, float height_zoom)
	{
		this.x = (short)(int)((float)this.x * width_zoom + (float)x);
		this.y = (short)(int)((float)this.y * height_zoom + (float)y);
		width = (short)(int)((float)width * width_zoom);
		height = (short)(int)((float)height * height_zoom);
	}

	public void setBounds(short x1, short y1, short w1, short h1)
	{
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}

	public void setColor(DCSColor fgColor, DCSColor bgColor) {
		this.fgColor=fgColor;
		this.bgColor=bgColor;
		
	}
	
	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
        if (this.bounds != null)
        {
            this.bounds.x = this.x;
            this.bounds.y = this.y;
        }
    }

	public void setBorder(short s) {
		this.border=s;
		
	}
	
}
