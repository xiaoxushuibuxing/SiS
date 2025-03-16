// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSOLRect.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Vector;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSEvent

public class DCSOLRect extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 21;
	short x;
	short y;
	short width;
	short height;
	boolean state;
	Color olColor;
	DCSEvent dcsEvent;
	Vector controlPoint;
	int vx;
	int vy;
	int vw;
	int vh;

	public DCSOLRect()
	{
		state = false;
		olColor = Color.gray;
		dcsEvent = null;
		controlPoint = null;
	}

	public DCSOLRect(int x1, int y1, int w1, int h1)
	{
		state = false;
		olColor = Color.gray;
		dcsEvent = null;
		controlPoint = null;
		x = (short)x1;
		y = (short)y1;
		width = (short)w1;
		height = (short)h1;
		controlPoint = getControlPoint();
	}

	public void setAttributes(Color c, boolean st)
	{
		olColor = c;
		state = st;
	}

	public byte getType()
	{
		return 21;
	}

	public Vector getProcessPoints()
	{
		return null;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public void move(int paramInt1, int paramInt2)
    {
        this.x = ((short)(this.x + paramInt1));
        this.y = ((short)(this.y + paramInt2));
    }
	public Rectangle getBounds()
	{
		Rectangle bounds = new Rectangle(x, y, width, height);
		return bounds;
	}

	public Vector getControlPoint()
	{
		Point points[] = new Point[5];
		Vector pList = new Vector();
		points[0] = new Point(x + width / 2, y + height / 2);
		points[1] = new Point(x, y);
		points[2] = new Point(x + width, y);
		points[3] = new Point(x + width, y + height);
		points[4] = new Point(x, y + height);
		for (int i = 0; i < 5; i++)
			pList.addElement(points[i]);

		return pList;
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
		g.setColor(olColor);
		g.fill3DRect(vx, vy, vw, vh, state);
	}

	public Object clone()
	{
		DCSOLRect olrect = new DCSOLRect(x, y, width, height);
		olrect.setAttributes(olColor, state);
		return olrect;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(21);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(width);
		out.writeShort(height);
		out.writeInt(olColor.getRGB());
		out.writeBoolean(state);
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
		olColor = new Color(in.readInt());
		state = in.readBoolean();
		if (in.readByte() == 1)
		{
			dcsEvent = new DCSEvent();
			dcsEvent.read(in);
		}
		controlPoint = getControlPoint();
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
		controlPoint = getControlPoint();
	}
}
