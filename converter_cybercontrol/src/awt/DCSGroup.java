// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSGroup.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Vector;

import org.apache.log4j.Logger;

import magus.net.provide.OPDataProvider;
import magus.util.CoordinateTransform;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSObject, DCSCondition, AWTContext, 
//			FillDynamic, FillPoint, DCSEvent, DCSLines, 
//			DCSRect, DCSPolygon, DCSEllipse, DCSArc, 
//			DCSText, DCSDasPoint, DCSTime, DCSImage, 
//			DCSButton, DCSBar, DCSTrend, CalcPoint, 
//			DCSXImage, DCSGrid, OLRect, DCSOLRect, 
//			DCSDiagram

public class DCSGroup extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 30;
	public Vector objectLink;
	DCSEvent dcsEvent;
	Rectangle bounds;
	int version;
	FillDynamic fillDynamic;
	private Logger logger = Logger.getLogger(DCSGroup.class);

	public DCSGroup()
	{
		objectLink = new Vector();
		dcsEvent = null;
		bounds = null;
		version = 2001;
		
		this.showType =0;
		fillDynamic = null;
	}

	public DCSGroup(int vsn)
	{
		objectLink = new Vector();
		dcsEvent = null;
		bounds = null;
		version = 1001;
		fillDynamic = null;
		version = vsn;
	}

	public DCSGroup(Vector v)
	{
		objectLink = new Vector();
		dcsEvent = null;
		bounds = null;
		version = 1001;
		fillDynamic = null;
		for (int i = 0; i < v.size(); i++)
		{
			DCSObject obj = (DCSObject)v.elementAt(i);
			objectLink.add(obj);
		}

		bounds = getBounds();
	}

	public void setEvent(DCSEvent e)
	{
		dcsEvent = e;
	}

	public void addObject(DCSObject obj)
	{
		objectLink.addElement(obj);
	}

	public Vector getObjects()
	{
		return objectLink;
	}

	public byte getType()
	{
		return 30;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
		for (int i = 0; i < objectLink.size(); i++)
		{
			DCSObject dcsObject = (DCSObject)objectLink.elementAt(i);
			Vector v = dcsObject.getProcessPoints();
			if (v != null)
			{
				for (int j = 0; j < v.size(); j++)
				{
					String pointname = (String)v.elementAt(j);
					if (!points.contains(pointname))
						points.add(pointname);
				}

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

	public Vector getDynamicPoints()
	{
		Vector points = new Vector();
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

	public FillDynamic getFillDynamic()
	{
		return fillDynamic;
	}

	public Rectangle getBounds()
	{
		int x1 = 16384;
		int y1 = 16384;
		int x2 = 0;
		int y2 = 0;
		int n = objectLink.size();
		Rectangle b;
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			if (obj.getBounds() != null)
			{
				b = obj.getBounds();
				x1 = Math.min(x1, b.x);
				y1 = Math.min(y1, b.y);
				x2 = Math.max(x2, b.x + b.width);
				y2 = Math.max(y2, b.y + b.height);
			}
		}

		int x = x1;
		int y = y1;
		int width = x2 - x1;
		int height = y2 - y1;
		b = new Rectangle(x, y, width, height);
		if (b == null)
			System.out.println();
		return b;
	}

	public boolean contains(Point p)
	{
		if (bounds == null || p == null)
			return false;
		return p.x >= bounds.x && p.x <= bounds.x + bounds.width && p.y >= bounds.y && p.y <= bounds.y + bounds.height;
	}

	public void transform(Graphics g, Dimension d)
	{
		int n = objectLink.size();
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			obj.transform(g, d);
		}

	}

	public Shape getShape(Dimension d)
	{
		if (bounds == null)
			getBounds();
		int vx = CoordinateTransform.X_StandardToView(d, bounds.x);
		int vy = CoordinateTransform.Y_StandardToView(d, bounds.y);
		int vw = CoordinateTransform.X_StandardToView(d, bounds.width);
		int vh = CoordinateTransform.Y_StandardToView(d, bounds.height);
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
		if (fillDynamic != null)
		{
			fillGroup(g, d, awtContext.getOpDataProvider());
			return;
		}
		int n = objectLink.size();
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			obj.draw(g, d);
		}

		if (bounds == null)
			bounds = getBounds();
	}

	public void fillGroup(Graphics g, Dimension d, OPDataProvider opDataProvider)
	{
		int sx = CoordinateTransform.X_StandardToView(d, bounds.x);
		int sy = CoordinateTransform.Y_StandardToView(d, bounds.y);
		int sw = CoordinateTransform.X_StandardToView(d, bounds.width);
		int sh = CoordinateTransform.Y_StandardToView(d, bounds.height);
		float lolimit = fillDynamic.getLoLimit();
		float hilimit = fillDynamic.getHiLimit();
		Vector fpoints = fillDynamic.getPoints();
		int num = objectLink.size();
		for (int i = 0; i < num; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			obj.draw(g, d);
		}

		Rectangle rect = new Rectangle(sx, sy, sw, sh);
		Graphics2D g2 = (Graphics2D)g;
		g.setClip(rect);
		for (int i = 0; i < num; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			Shape shp = obj.getShape(d);
			if (shp != null)
			{
				g2.setColor(fillDynamic.getBGColor());
				g2.fill(shp);
			}
		}

		g.setClip(null);
		double value = 0.0D;
		double lastValue = 0.0D;
		for (int i = 0; i < fpoints.size(); i++)
		{
			FillPoint fillPoint = (FillPoint)fpoints.elementAt(i);
			value = fillPoint.getValue(opDataProvider);
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
			for (int j = 0; j < num; j++)
			{
				DCSObject obj = (DCSObject)objectLink.elementAt(j);
				Shape shp = obj.getShape(d);
				if (shp != null)
				{
					g2.setColor(fillPoint.getColor());
					g2.fill(shp);
				}
			}

			g.setClip(null);
		}

	}

	public int read(DataInputStream in)
		throws IOException
	{
		return 0;
	}

	public Object clone()
	{
		Vector v = new Vector();
		int n = objectLink.size();
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			DCSObject obj1 = (DCSObject)obj.clone();
			v.addElement(obj1);
		}

		DCSGroup grp = new DCSGroup(v);
		grp.version = version;
		grp.setEvent(dcsEvent);
		return grp;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(30);
		int size = objectLink.size();
		out.writeInt(size);
		logger.debug(version);
		logger.debug(showType);
		for (int i = 0; i < size; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			//logger.error(obj.getBounds());
			if (obj!= null)
				obj.write(out);
		}

		if (version >= 2001)
		{
			if (dcsEvent == null)
			{
				out.writeByte(0);
			} else
			{
				out.writeByte(1);
				dcsEvent.write(out);
			}
			out.writeByte(showType);
			if (showType > 0) {
				System.out.println(isShowCondition.toString());
				out.writeUTF(isShowCondition.toString());	
			}
				
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

	public int read(DataInputStream in, DCSDiagram diagram)
		throws IOException
	{
		int size = in.readInt();
		for (int i = 0; i < size; i++)
		{
			AbstractDCSObject dcsObject = null;
			byte obj_type = in.readByte();
			switch (obj_type)
			{
			case 1: // '\001'
				dcsObject = new DCSLines(version);
				break;

			case 2: // '\002'
				dcsObject = new DCSRect(version);
				break;

			case 3: // '\003'
				dcsObject = new DCSPolygon(version);
				break;

			case 4: // '\004'
				dcsObject = new DCSEllipse(version);
				break;

			case 5: // '\005'
				dcsObject = new DCSArc(version);
				break;

			case 6: // '\006'
				dcsObject = new DCSText(diagram, version);
				break;

			case 7: // '\007'
				dcsObject = new DCSDasPoint(diagram, version);
				break;

			case 9: // '\t'
				dcsObject = new DCSTime(diagram);
				break;

			case 10: // '\n'
				dcsObject = new DCSImage(version);
				break;

			case 11: // '\013'
				dcsObject = new DCSButton(diagram);
				break;

			case 12: // '\f'
				dcsObject = new DCSBar();
				break;

			case 13: // '\r'
				dcsObject = new DCSTrend();
				break;

			case 14: // '\016'
				dcsObject = new CalcPoint(diagram);
				break;

			case 15: // '\017'
				dcsObject = new DCSXImage();
				break;

			case 16: // '\020'
				dcsObject = new DCSGrid();
				break;

			case 17: // '\021'
				dcsObject = new OLRect();
				break;

			case 21: // '\025'
				dcsObject = new DCSOLRect();
				break;

			case 30: // '\036'
				DCSGroup group = new DCSGroup(version);
				group.setAwtContext(awtContext);
				int retval = group.read(in, diagram);
				if (retval == 0)
					addObject(group);
				break;
			}
			if (dcsObject != null)
			{
				dcsObject.setAwtContext(awtContext);
				int retval = dcsObject.read(in);
				if (retval == 0)
					addObject(dcsObject);
			}
		}

		if (version >= 2001)
		{
			if (in.readByte() == 1)
			{
				dcsEvent = new DCSEvent();
				dcsEvent.read(in);
			}
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
		return 0;
	}

	public void createBounds(int x, int y, int width, int height)
	{
		bounds = new Rectangle(x, y, width, height);
	}

	public void transform(Dimension d)
	{
		int n = objectLink.size();
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			obj.offset(bounds.x, bounds.y, (float)bounds.width / 400F, (float)bounds.height / 400F);
			obj.transform(d);
		}

	}

	public void offset(int x, int y, float width, float height)
	{
		int n = objectLink.size();
		Rectangle b = getBounds();
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			obj.offset(bounds.x, bounds.y, ((float)bounds.width * 1.0F) / (float)b.width, ((float)bounds.height * 1.0F) / (float)b.height);
		}

	}

	public void setBounds(short x1, short y1, short w1, short h1)
	{
		bounds = getBounds();
		int n = objectLink.size();
		for (int i = 0; i < n; i++)
		{
			if(objectLink.elementAt(i)  instanceof DCSText){
				DCSText obj = (DCSText)objectLink.elementAt(i);
				
				
				Rectangle b = obj.getBounds();
				System.out.println(b);
				if (b==null) {
					b=bounds;
				}
				short x0 = (short)(int)((float)x1 + (1.0F * (float)(b.x - bounds.x) * (float)w1) / (float)bounds.width + 0.5F);
				short y0 = (short)(int)((float)y1 + (1.0F * (float)(b.y - bounds.y) * (float)h1) / (float)bounds.height + 0.5F);
				short w0 = (short)(int)((1.0F * (float)b.width * (float)w1) / (float)bounds.width + 0.5F);
				short h0 = (short)(int)((1.0F * (float)b.height * (float)h1) / (float)bounds.height + 0.5F);
				obj.bounds=b;
				obj.setBounds(x0, y0, w0, h0);
			}
			
			DCSObject obj = (DCSObject)objectLink.elementAt(i);		
			Rectangle b = obj.getBounds();
			if (b==null) {
				b=bounds;
			}
			short x0 = (short)(int)((float)x1 + (1.0F * (float)(b.x - bounds.x) * (float)w1) / (float)bounds.width + 0.5F);
			short y0 = (short)(int)((float)y1 + (1.0F * (float)(b.y - bounds.y) * (float)h1) / (float)bounds.height + 0.5F);
			short w0 = (short)(int)((1.0F * (float)b.width * (float)w1) / (float)bounds.width + 0.5F);
			short h0 = (short)(int)((1.0F * (float)b.height * (float)h1) / (float)bounds.height + 0.5F);
			
			obj.setBounds(x0, y0, w0, h0);
			//System.out.println(obj.getBounds());
		}

		bounds = new Rectangle(x1, y1, w1, h1);
	}
	public void move(int x, int y)
    {
        int i = this.objectLink.size();
        for (int j = 0; j < i; j++)
        {
            DCSObject localDCSObject = (DCSObject)this.objectLink.elementAt(j);
            localDCSObject.move(x, y);
        }
        this.bounds = getBounds();
    }
}
