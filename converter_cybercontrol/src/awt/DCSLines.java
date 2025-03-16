// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSLines.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

import org.apache.log4j.Logger;

import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, DCSCondition, AWTContext, 
//			GBLineStyle, DCSEvent

public class DCSLines extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 1;
	public int npoints;
	public short xpoints[];
	public short ypoints[];
	public byte lineStyle;
	public byte lineWidth;
	public DCSColor fgColor;
	DCSEvent dcsEvent;
	Rectangle bounds;
	public int version;
	int vx[];
	int vy[];
	private Logger logger = Logger.getLogger(DCSLines.class);

	public DCSLines()
	{
		npoints = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		dcsEvent = null;
		bounds = null;
		version = 1001;
	}

	public DCSLines(int vsn)
	{
		npoints = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		dcsEvent = null;
		bounds = null;
		version = 1001;
		version = vsn;
	}

	public DCSLines(short px[], short py[], int num)
	{
		npoints = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		dcsEvent = null;
		bounds = null;
		version = 1001;
		xpoints = new short[num];
		ypoints = new short[num];
		xpoints = px;
		ypoints = py;
		npoints = num;
		bounds = getBounds();
	}

	public void setAttributes(DCSColor fc, byte ls, byte lw)
	{
		fgColor = fc;
		lineStyle = ls;
		lineWidth = lw;
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

	public byte getType()
	{
		return 1;
	}

	public Rectangle getBounds()
	{
		int x1 = 16384;
		int y1 = 16384;
		int x2 = 0;
		int y2 = 0;
		for (int i = 0; i < npoints; i++)
		{
			x1 = Math.min(x1, xpoints[i]);
			y1 = Math.min(y1, ypoints[i]);
			x2 = Math.max(x2, xpoints[i]);
			y2 = Math.max(y2, ypoints[i]);
		}

		Rectangle bounds;
		if (x1 > x2 || y1 > y2)
		{
			bounds = null;
		} else
		{
			bounds = new Rectangle(x1, y1, x2 - x1, y2 - y1);
			if (bounds.height < 1)
				bounds.height = 1;
			if (bounds.width < 1)
				bounds.width = 1;
		}
		return bounds;
	}

	public boolean contains(Point p)
	{
		if (bounds == null)
			getBounds();
		if (bounds == null)
			return false;
		System.out.println();
		return p.x >= bounds.x && p.x <= bounds.x + bounds.width && p.y >= bounds.y - 50 && p.y <= bounds.y + bounds.height + 50;
	}

	public void transform(Graphics g, Dimension d)
	{
		vx = new int[npoints];
		vy = new int[npoints];
		for (int i = 0; i < npoints; i++)
		{
			vx[i] = CoordinateTransform.X_StandardToView(d, xpoints[i]);
			vy[i] = CoordinateTransform.Y_StandardToView(d, ypoints[i]);
		}

	}

	public Shape getShape(Dimension d)
	{
		return null;
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
		if (npoints == 0)
			return;
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		g.setColor(fc);
		if (lineWidth > 0)
		{
			GBLineStyle.setLine(g, lineStyle, lineWidth);
			g.drawPolyline(vx, vy, npoints);
		}
	}

	public Object clone()
	{
		short x[] = new short[npoints];
		short y[] = new short[npoints];
		for (int i = 0; i < npoints; i++)
		{
			x[i] = xpoints[i];
			y[i] = ypoints[i];
		}

		DCSLines line = new DCSLines(x, y, npoints);
		line.setAttributes(fgColor, lineStyle, lineWidth);
		line.version = version;
		return line;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		logger.info(version);
		out.writeByte(1);
		out.writeInt(npoints);
		for (int i = 0; i < npoints; i++)
		{
			out.writeShort(xpoints[i]);
			out.writeShort(ypoints[i]);
		}

		out.writeByte(lineStyle);
		out.writeByte(lineWidth);
		fgColor.write(out);
		if (dcsEvent == null)
		{
			out.writeByte(0);
		} else
		{
			out.writeByte(1);
			dcsEvent.write(out);
		}
		if (version >= 2001)
		{
			out.writeByte(showType);
			if (showType > 0)
				out.writeUTF(isShowCondition.toString());
		}
	}

	@Override
	public String toString() {
		return "DCSLines [npoints=" + npoints + ", xpoints=" + Arrays.toString(xpoints) + ", ypoints="
				+ Arrays.toString(ypoints) + ", lineStyle=" + lineStyle + ", lineWidth=" + lineWidth + ", fgColor="
				+ fgColor + ", dcsEvent=" + dcsEvent + ", bounds=" + bounds + ", version=" + version + ", vx="
				+ Arrays.toString(vx) + ", vy=" + Arrays.toString(vy) + "]";
	}

	public int read(DataInputStream in)
		throws IOException
	{
		npoints = in.readInt();
		xpoints = new short[npoints];
		ypoints = new short[npoints];
		for (int i = 0; i < npoints; i++)
		{
			xpoints[i] = in.readShort();
			ypoints[i] = in.readShort();
		}

		lineStyle = in.readByte();
		lineWidth = in.readByte();
		fgColor = new DCSColor();
		fgColor.read(in);
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
		for (int i = 0; i < npoints; i++)
		{
			xpoints[i] = (short)CoordinateTransform.X_ViewToStandard(d, xpoints[i]);
			ypoints[i] = (short)CoordinateTransform.Y_ViewToStandard(d, ypoints[i]);
		}

	}

	public void offset(int x, int y, float width_zoom, float height_zoom)
	{
		for (int i = 0; i < npoints; i++)
		{
			xpoints[i] = (short)(int)((float)xpoints[i] * width_zoom + (float)x);
			ypoints[i] = (short)(int)((float)ypoints[i] * height_zoom + (float)y);
		}

	}

	public void setBounds(short x1, short y1, short w1, short h1)
	{
		bounds = getBounds();
		for (int i = 0; i < npoints; i++)
		{
			xpoints[i] = (short)(int)((float)x1 + (1.0F * (float)(xpoints[i] - bounds.x) * (float)w1) / (float)bounds.width + 0.5F);
			ypoints[i] = (short)(int)((float)y1 + (1.0F * (float)(ypoints[i] - bounds.y) * (float)h1) / (float)bounds.height + 0.5F);
		}

		bounds = getBounds();
	}

	public void setColor(DCSColor fgColor, DCSColor bgColor) {
		this.fgColor=fgColor;
		
	}

	public void move(int paramInt1, int paramInt2)
	  {
	    for (int i = 0; i < this.npoints; i++)
	    {
	      this.xpoints[i] = ((short)(this.xpoints[i] + paramInt1));
	      this.ypoints[i] = ((short)(this.ypoints[i] + paramInt2));
	    }
	    this.bounds = getBounds();
	  }

	public void setLineStyle(Byte lineStyle) {
		this.lineStyle= lineStyle;
	}

	public void setLineWidth(Byte lineWidth) {
		this.lineWidth=lineWidth;
		
	}
}
