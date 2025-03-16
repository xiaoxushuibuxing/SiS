// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSGrid.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Vector;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, GBLineStyle, DCSEvent

public class DCSGrid extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 16;
	short x;
	short y;
	short width;
	short height;
	public Color fgColor;
	public Color bgColor;
	public byte overstrike;
	public byte frameLineWidth;
	public short numXGrid;
	public short numYGrid;
	public byte gridLineStyle;
	public byte gridLineWidth;
	public Color gridColor;
	public DCSEvent dcsEvent;
	int vx;
	int vy;
	int vw;
	int vh;

	public DCSGrid()
	{
		x = 0;
		y = 0;
		width = 6000;
		height = 4000;
		fgColor = Color.blue;
		bgColor = Color.lightGray;
		overstrike = 1;
		frameLineWidth = 1;
		numXGrid = 5;
		numYGrid = 5;
		gridLineStyle = 0;
		gridLineWidth = 1;
		gridColor = Color.blue;
		dcsEvent = null;
	}

	public DCSGrid(short x1, short y1, short w1, short h1)
	{

		fgColor = Color.blue;
		bgColor = Color.lightGray;
		overstrike = 1;
		frameLineWidth = 1;
		numXGrid = 5;
		numYGrid = 5;
		gridLineStyle = 0;
		gridLineWidth = 1;
		gridColor = Color.blue;
		dcsEvent = null;
		x = x1;
		y = y1;
		width = w1;
		height = h1;
	}

	public void setAttributes(Color fc, Color bc, Color gc, byte ovs, byte flw, int nx, int ny, 
			byte gls, byte glw)
	{
		fgColor = fc;
		bgColor = bc;
		gridColor = gc;
		overstrike = ovs;
		frameLineWidth = flw;
		numXGrid = (short)nx;
		numYGrid = (short)ny;
		gridLineStyle = gls;
		gridLineWidth = glw;
	}

	public byte getType()
	{
		return 16;
	}

	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
    }
	public Color getFGColor()
	{
		return fgColor;
	}

	public Color getBGColor()
	{
		return bgColor;
	}

	public byte getOverstrike()
	{
		return overstrike;
	}

	public byte getLineWidthOfFrame()
	{
		return frameLineWidth;
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

	public byte getLineWidthOfGrid()
	{
		return gridLineWidth;
	}

	public Color getGridColor()
	{
		return gridColor;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
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
		if (overstrike == 1)
		{
			g.setColor(bgColor);
			g.fillRect(vx, vy, vw, vh);
		}
		drawGrid(g, vx, vy, vw, vh);
	}

	private void drawGrid(Graphics g, int x0, int y0, int w0, int h0)
	{
		GBLineStyle.setLine(g, gridLineStyle, gridLineWidth);
		g.setColor(gridColor);
		if (numXGrid > 0)
		{
			for (int i = 1; i < numXGrid; i++)
				g.drawLine(x0 + (i * w0) / numXGrid, y0, x0 + (i * w0) / numXGrid, y0 + h0);

		}
		if (numYGrid > 0)
		{
			for (int i = 1; i < numYGrid; i++)
				g.drawLine(x0, y0 + (i * h0) / numYGrid, x0 + w0, y0 + (i * h0) / numYGrid);

		}
		g.setColor(fgColor);
		GBLineStyle.setLine(g, 0, frameLineWidth);
		g.drawRect(x0, y0, w0, h0);
	}

	public Object clone()
	{
		DCSGrid grid = new DCSGrid(x, y, width, height);
		grid.setAttributes(fgColor, bgColor, gridColor, overstrike, frameLineWidth, numXGrid, numYGrid, gridLineStyle, gridLineWidth);
		return grid;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(16);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeInt(fgColor.getRGB());
		out.writeInt(bgColor.getRGB());
		out.writeByte(overstrike);
		out.writeByte(frameLineWidth);
		out.writeShort(numXGrid);
		out.writeShort(numYGrid);
		out.writeInt(gridColor.getRGB());
		out.writeByte(gridLineStyle);
		out.writeByte(gridLineWidth);
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
		fgColor = new Color(in.readInt());
		bgColor = new Color(in.readInt());
		overstrike = in.readByte();
		frameLineWidth = in.readByte();
		numXGrid = in.readShort();
		numYGrid = in.readShort();
		gridColor = new Color(in.readInt());
		gridLineStyle = in.readByte();
		gridLineWidth = in.readByte();
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
