// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSTrend.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Date;
import java.util.Vector;
import magus.net.*;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, TrendPoint, AWTContext, GBLineStyle, 
//			DCSEvent

public class DCSTrend extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 13;
	public static int MAX_POINT = 8;
	short x;
	short y;
	short width;
	short height;
	Vector pointList;
	short interval;
	short nsamples;
	Color fgColor;
	Color bgColor;
	short numXGrid;
	short numYGrid;
	byte gridLineStyle;
	Color gridColor;
	DCSEvent dcsEvent;
	long lastTime;
	boolean hasData;
	int preconnect;
	int vx;
	int vy;
	int vw;
	int vh;

	public DCSTrend()
	{
		x = 0;
		y = 0;
		width = 6000;
		height = 4000;
		pointList = new Vector();
		interval = 1;
		nsamples = 60;
		fgColor = Color.blue;
		bgColor = Color.lightGray;
		numXGrid = 5;
		numYGrid = 5;
		gridLineStyle = 3;
		gridColor = Color.blue;
		dcsEvent = null;
		lastTime = 0L;
		hasData = false;
		preconnect = 0;
	}

	public DCSTrend(Vector v, short x1, short y1, short w1, short h1)
	{
		x = 0;
		y = 0;
		width = 6000;
		height = 4000;
		pointList = new Vector();
		interval = 1;
		nsamples = 60;
		fgColor = Color.blue;
		bgColor = Color.lightGray;
		numXGrid = 5;
		numYGrid = 5;
		gridLineStyle = 3;
		gridColor = Color.blue;
		dcsEvent = null;
		lastTime = 0L;
		hasData = false;
		preconnect = 0;
		pointList = v;
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}

	public void setAttributes(int itv, int ns, Color fc, Color bc, Color gc, int nx, int ny, 
			byte gls)
	{
		interval = (short)itv;
		nsamples = (short)ns;
		fgColor = fc;
		bgColor = bc;
		gridColor = gc;
		numXGrid = (short)nx;
		numYGrid = (short)ny;
		gridLineStyle = gls;
	}

	public byte getType()
	{
		return 13;
	}

	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
    }
	public void setPoints(Vector vp)
	{
		pointList = vp;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
		for (int i = 0; i < pointList.size(); i++)
		{
			TrendPoint tp = (TrendPoint)pointList.elementAt(i);
			points.add(tp.getName());
		}

		return points;
	}

	public Vector getPoints()
	{
		return pointList;
	}

	public int getInterval()
	{
		return interval;
	}

	public int getNumberOfSamples()
	{
		return nsamples;
	}

	public Color getFGColor()
	{
		return fgColor;
	}

	public Color getBGColor()
	{
		return bgColor;
	}

	public int getNumberOfXGrid()
	{
		return numXGrid;
	}

	public int getNumberOfYGrid()
	{
		return numYGrid;
	}

	public byte getLineStyleOfGrid()
	{
		return gridLineStyle;
	}

	public Color getGridColor()
	{
		return gridColor;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
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
	}

	public Shape getShape(Dimension d)
	{
		Rectangle rect = new Rectangle(vx, vy, vw, vh);
		return rect;
	}

	public void draw(Graphics g, Dimension d)
	{
		g.setColor(bgColor);
		g.fillRect(vx, vy, vw, vh);
		drawGrid(g, vx, vy, vw, vh);
		//long dcstime = awtContext.getOpDataProvider().getDBServerTime();
		long dcstime = new Date().getTime();
		if (!hasData)
			getHistoryData(dcstime);
		if (lastTime == 0L)
			lastTime = dcstime - (long)(interval * 1000);
		if (dcstime >= lastTime + (long)(interval * 1000))
		{
			for (int i = 0; i < pointList.size(); i++)
			{
				TrendPoint tp = (TrendPoint)pointList.elementAt(i);
				if (preconnect > 0)
					tp.addSSData(tp.getCurrentValue());
				tp.draw(g, vx, vy, vw, vh);
			}

			if (preconnect < 3)
				preconnect++;
			lastTime = dcstime;
		} else
		{
			for (int i = 0; i < pointList.size(); i++)
			{
				TrendPoint tp = (TrendPoint)pointList.elementAt(i);
				tp.draw(g, vx, vy, vw, vh);
			}

		}
	}

	public void getHistoryData(long dcstime)
	{
		Date endDate = new Date(dcstime);
		Date beginDate = new Date(dcstime - (long)(nsamples * interval * 1000));
		short req = 0;
		for (int i = 0; i < pointList.size(); i++)
		{
			TrendPoint tp = (TrendPoint)pointList.elementAt(i);
			magus.net.DBPoint dbpoint = tp.getDBPoint();
			if (dbpoint != null)
			{
				PointHistory ph = new PointHistory(dbpoint, beginDate, endDate, interval, req);
				//int history = OPNetwork.requestPointHistory(ph);
				tp.clearSSData();
				Vector v = ph.getRecords();
				for (int j = 0; j < v.size(); j++)
				{
					HistoryRecord record = (HistoryRecord)v.elementAt(j);
					tp.addSSData(record.getValueFloat());
				}

			}
		}

		hasData = true;
	}

	private void drawGrid(Graphics g, int x0, int y0, int w0, int h0)
	{
		g.setColor(fgColor);
		GBLineStyle.setLine(g, 0, 1);
		g.drawRect(x0, y0, w0, h0);
		GBLineStyle.setLine(g, gridLineStyle, 1);
		g.setColor(gridColor);
		if (numXGrid > 0)
		{
			for (int i = 1; i < numXGrid + 1; i++)
				g.drawLine(x0 + (i * w0) / (numXGrid + 1), y0, x0 + (i * w0) / (numXGrid + 1), y0 + h0);

		}
		if (numYGrid > 0)
		{
			for (int i = 1; i < numYGrid + 1; i++)
				g.drawLine(x0, y0 + (i * h0) / (numYGrid + 1), x0 + w0, y0 + (i * h0) / (numYGrid + 1));

		}
	}

	public Object clone()
	{
		DCSTrend trend = new DCSTrend((Vector)pointList.clone(), x, y, width, height);
		trend.setAttributes(interval, nsamples, fgColor, bgColor, gridColor, numXGrid, numYGrid, gridLineStyle);
		return trend;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(13);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeInt(pointList.size());
		for (int i = 0; i < pointList.size(); i++)
		{
			TrendPoint tp = (TrendPoint)pointList.elementAt(i);
			out.writeUTF(tp.toString());
		}

		out.writeShort(interval);
		out.writeShort(nsamples);
		out.writeInt(fgColor.getRGB());
		out.writeInt(bgColor.getRGB());
		out.writeShort(numXGrid);
		out.writeShort(numYGrid);
		out.writeInt(gridColor.getRGB());
		out.writeByte(gridLineStyle);
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
		int num = in.readInt();
		for (int i = 0; i < num; i++)
		{
			String ps = in.readUTF();
			TrendPoint tp = new TrendPoint(this);
			tp.parse(ps.trim());
			pointList.add(tp);
		}

		interval = in.readShort();
		nsamples = in.readShort();
		fgColor = new Color(in.readInt());
		bgColor = new Color(in.readInt());
		numXGrid = in.readShort();
		numYGrid = in.readShort();
		gridColor = new Color(in.readInt());
		gridLineStyle = in.readByte();
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

	public void setBounds(short x1, short y1, short w1, short h1)
	{
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}

}
