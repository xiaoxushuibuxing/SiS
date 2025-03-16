// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSPolygon.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Vector;

import org.apache.log4j.Logger;

import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, FillMode, DCSCondition, 
//			AWTContext, GBLineStyle, FillDynamic, FillPoint, 
//			DCSEvent

public class DCSPolygon extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 3;
	public int npoints;
	public short xpoints[];
	public short ypoints[];
	public byte lineStyle;
	public byte lineWidth;
	public DCSColor fgColor;
	public DCSColor bgColor;
	public FillMode fillMode;
	DCSEvent dcsEvent;
	Rectangle bounds;
	public int version;
	FillDynamic fillDynamic;
	int vx[];
	int vy[];
	private Logger logger = Logger.getLogger(DCSPolygon.class);

	public DCSPolygon()
	{
		npoints = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		bounds = null;
		version = 1001;
		fillDynamic = null;
	}

	public DCSPolygon(int vsn)
	{
		npoints = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		bounds = null;
		version = 1001;
		fillDynamic = null;
		version = vsn;
	}

	public DCSPolygon(short px[], short py[], int num)
	{
		npoints = 0;
		lineStyle = 0;
		lineWidth = 1;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		fillMode = new FillMode();
		dcsEvent = null;
		bounds = null;
		version = 1001;
		fillDynamic = null;
        xpoints = px;
		ypoints = py;
		npoints = num;
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

	public byte getType()
	{
		return 3;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public FillDynamic getFillDynamic()
	{
		return fillDynamic;
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
		int x1 = 32767;
		int y1 = 32767;
		int x2 = -32768;
		int y2 = -32768;
		for (int i = 0; i < npoints; i++)
		{
			x1 = Math.min(x1, xpoints[i]);
			y1 = Math.min(y1, ypoints[i]);
			x2 = Math.max(x2, xpoints[i]);
			y2 = Math.max(y2, ypoints[i]);
		}

		Rectangle bounds;
		if (x1 > x2 || y1 > y2)
			bounds = null;
		else
			bounds = new Rectangle(x1, y1, x2 - x1, y2 - y1);
		return bounds;
	}

	public boolean contains(Point p)
	{
		if (bounds == null)
			return false;
		return p.x >= bounds.x && p.x <= bounds.x + bounds.width && p.y >= bounds.y && p.y <= bounds.y + bounds.height;
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
		Polygon plgn = new Polygon(vx, vy, npoints);
		return plgn;
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
		if (fillDynamic != null)
		{
			fillPolygon(g, d);
			return;
		}
		if (npoints <= 2)
			return;
		Polygon plgn = new Polygon(vx, vy, npoints);
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		Graphics2D g2 = (Graphics2D)g;
		fillMode.fill(g2, bc, plgn);
		if (lineWidth > 0)
		{
			g2.setColor(fc);
			GBLineStyle.setLine(g, lineStyle, lineWidth);
			g2.draw(plgn);
		}
		plgn = null;
	}

	public void fillPolygon(Graphics g, Dimension d)
	{
		int sx = CoordinateTransform.X_StandardToView(d, bounds.x);
		int sy = CoordinateTransform.Y_StandardToView(d, bounds.y);
		int sw = CoordinateTransform.X_StandardToView(d, bounds.width);
		int sh = CoordinateTransform.Y_StandardToView(d, bounds.height);
		float lolimit = fillDynamic.getLoLimit();
		float hilimit = fillDynamic.getHiLimit();
		Vector fpoints = fillDynamic.getPoints();
		Rectangle rect = new Rectangle(sx, sy, sw, sh);
		Graphics2D g2 = (Graphics2D)g;
		g.setClip(rect);
		Shape shp = getShape(d);
		g2.setColor(fillDynamic.getBGColor());
		g2.fill(shp);
		g.setClip(null);
		double value = 0.0D;
		double lastValue = 0.0D;
		for (int i = 0; i < fpoints.size(); i++)
		{
			FillPoint fillPoint = (FillPoint)fpoints.elementAt(i);
			value = fillPoint.getValue(awtContext.getOpDataProvider());
			if (i == 0)
			{
				rect = new Rectangle(sx, sy + (int)(((double)sh * ((double)hilimit - value)) / (double)(hilimit - lolimit) + 0.5D), sw, (int)(((double)sh * value) / (double)(hilimit - lolimit) + 0.5D));
				lastValue = value;
			} else
			{
				if (value <= 0.0D)
					continue;
				rect = new Rectangle(sx, sy + (int)(((double)sh * ((double)hilimit - lastValue)) / (double)(hilimit - lolimit) + 0.5D), sw, (int)(((double)sh * (lastValue - value)) / (double)(hilimit - lolimit) + 0.5D));
				lastValue = value;
			}
			g.setClip(rect);
			g2.setColor(fillPoint.getColor());
			g2.fill(shp);
			g.setClip(null);
		}

		int vx[] = new int[npoints];
		int vy[] = new int[npoints];
		for (int i = 0; i < npoints; i++)
		{
			vx[i] = CoordinateTransform.X_StandardToView(d, xpoints[i]);
			vy[i] = CoordinateTransform.Y_StandardToView(d, ypoints[i]);
		}

		Polygon plgn = new Polygon(vx, vy, npoints);
		g2.setColor(fgColor.getDefaultColor());
		GBLineStyle.setLine(g, lineStyle, lineWidth);
		g2.draw(plgn);
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

		DCSPolygon polygon = new DCSPolygon(x, y, npoints);
		polygon.setAttributes(fgColor, bgColor, lineStyle, lineWidth, fillMode.Clone());
		polygon.version = version;
		return polygon;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		logger.debug(version);
		out.writeByte(3);
		out.writeInt(npoints);
		for (int i = 0; i < npoints; i++)
		{
			out.writeShort(xpoints[i]);
			out.writeShort(ypoints[i]);
		}

		out.writeByte(lineStyle);
		out.writeByte(lineWidth);
		if (fillMode !=null)
			out.writeUTF(fillMode.toString());
		else
			out.writeUTF( new FillMode().toString());
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
		if (version >= 2001)
		{
			out.writeByte(showType);
			if (showType > 0)
				out.writeUTF(isShowCondition.toString());
		}
		if (version == 2003)
			if (fillDynamic == null)
			{
				out.writeByte(0);
			} else
			{
				out.writeByte(1);
				fillDynamic.write(out);
			}
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
		if (version == 2003 && in.readByte() == 1)
		{
			fillDynamic = new FillDynamic();
			fillDynamic.read(in);
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

	@Override
	public String toString() {
		return "DCSPolygon [npoints=" + npoints + ", xpoints=" + Arrays.toString(xpoints) + ", ypoints="
				+ Arrays.toString(ypoints) + ", lineStyle=" + lineStyle + ", lineWidth=" + lineWidth + ", fgColor="
				+ fgColor + ", bgColor=" + bgColor + ", fillMode=" + fillMode + ", dcsEvent=" + dcsEvent + ", bounds="
				+ bounds + ", version=" + version + ", fillDynamic=" + fillDynamic + ", vx=" + Arrays.toString(vx)
				+ ", vy=" + Arrays.toString(vy) + "]";
	}

	public void setColor(DCSColor fgColor, DCSColor bgColor) {
		this.fgColor=fgColor;
		this.bgColor=bgColor;
		
	}
	
	public void setLineStyle(Byte lineStyle) {
		this.lineStyle= lineStyle;
	}

	public void setLineWidth(Byte lineWidth) {
		this.lineWidth=lineWidth;
		
	}

	public void setFillMode(FillMode createFillMode) {
		this.fillMode = createFillMode;
		
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
}
