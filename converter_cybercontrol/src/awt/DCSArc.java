// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSArc.java

package awt;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.io.*;
import java.util.Vector;

import org.apache.log4j.Logger;

import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, FillMode, DCSCondition, 
//			AWTContext, GBLineStyle, DCSEvent

public class DCSArc extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 5;
	public byte arcStyle;
	public short x;
	public short y;
	public short width;
	public short height;
	public short angle1;
	public short angle2;
	public byte lineStyle;
	public byte lineWidth;
	public DCSColor fgColor;
	public DCSColor bgColor;
	public FillMode fillMode;
	DCSEvent dcsEvent;
	Rectangle bounds;
	int vx;
	int vy;
	int vw;
	int vh;
	int version;
	private Logger logger = Logger.getLogger(DCSArc.class);

	public DCSArc()
	{
		arcStyle = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		bounds = null;
		this.angle1=0;
		this.angle2=180;
		version = 1001;
	}

	public DCSArc(int vsn)
	{
		arcStyle = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		bounds = null;
		version = vsn;
	}

	public byte getType()
	{
		return 5;
	}

	public DCSArc(int x1, int y1, int w1, int h1, int a1, int a2)
	{
		arcStyle = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		bounds = null;
		version = 1001;
		x = (short)x1;
		y = (short)y1;
		width = (short)w1;
		height = (short)h1;
		angle1 = (short)a1;
		angle2 = (short)a2;
		bounds = getBounds();
	}

	public void setAttributes(DCSColor fc, DCSColor bc, byte ls, byte lw, FillMode fm)
	{
		fgColor = fc;
		bgColor = bc;
		lineStyle = ls;
		lineWidth = lw;
		fillMode = fm;
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
		if (isShowCondition != null)
		{
			Vector vsh = isShowCondition.getProcessPoints();
			if (vsh != null)
			{
				for (int i = 0; i < vsh.size(); i++)
				{
					String pname = (String)vsh.elementAt(i);
					if (!points.contains(pname))
						points.add(pname);
				}

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
		if (bounds == null)
			return false;
		else
			return bounds.contains(p);
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
	public void transform(Graphics g, Dimension d)
	{
		vx = CoordinateTransform.X_StandardToView(d, x);
		vy = CoordinateTransform.Y_StandardToView(d, y);
		vw = CoordinateTransform.X_StandardToView(d, width);
		vh = CoordinateTransform.Y_StandardToView(d, height);
	}

	public Shape getShape(Dimension d)
	{
		Arc2D.Float arc = new Arc2D.Float(vx, vy, vw, vh, angle1, angle2, 0);
		return arc;
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
		Arc2D.Float arc;
		if (arcStyle == 0)
			arc = new Arc2D.Float(vx, vy, vw, vh, angle1, angle2, 0);
		else
		if (arcStyle == 1)
			arc = new Arc2D.Float(vx, vy, vw, vh, angle1, angle2, 1);
		else
			arc = new Arc2D.Float(vx, vy, vw, vh, angle1, angle2, 2);
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		Graphics2D g2 = (Graphics2D)g;
		fillMode.fill(g2, bc, arc);
		if (lineWidth > 0)
		{
			g2.setColor(fc);
			GBLineStyle.setLine(g, lineStyle, lineWidth);
			g2.draw(arc);
		}
		arc = null;
	}

	public Object clone()
	{
		DCSArc arc = new DCSArc(x, y, width, height, angle1, angle2);
		arc.version = version;
		arc.setAttributes(fgColor, bgColor, lineStyle, lineWidth, fillMode);
		return arc;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		
	
		out.writeByte(5);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeShort(angle1);
		out.writeShort(angle2);
		out.writeByte(lineStyle);
		out.writeByte(lineWidth);
		out.writeByte(arcStyle);
		out.writeUTF(fillMode.toString());
		fgColor.write(out);
		bgColor.write(out);
		//System.out.println(bgColor.toString());
		if (dcsEvent == null)
		{
			out.writeByte(0);
		} else
		{
			out.writeByte(1);
			dcsEvent.write(out);
		}
		logger.debug(version);
		if (version >= 2001)
		{
			logger.debug(showType);
			out.writeByte(showType);
			if (showType > 0)
				
				out.writeUTF(isShowCondition.toString());
		}
	}

	public int read(DataInputStream in)
		throws IOException
	{
		x = in.readShort();
		y = in.readShort();
		width = in.readShort();
		height = in.readShort();
		angle1 = in.readShort();
		angle2 = in.readShort();
		lineStyle = in.readByte();
		lineWidth = in.readByte();
		arcStyle = in.readByte();
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
		if (version >= 2001)
		{
			showType = in.readByte();
			if (showType > 0)
			{
				isShowCondition = new DCSCondition();
				isShowCondition.parse(in.readUTF());
			}
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
		bounds = getBounds();
	}

	public void setColor(DCSColor fgColor, DCSColor bgColor) {
		// TODO Auto-generated method stub
		this.fgColor = fgColor;
		this.bgColor = bgColor;
	}
}
