// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSDiagram.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Vector;
import magus.net.DBPoint;
import magus.net.OPNetwork;

// Referenced classes of package magus.awt:
//			DCSObject, DCSDasPoint, DCSLines, DCSRect, 
//			DCSPolygon, DCSEllipse, DCSArc, DCSText, 
//			DCSTime, DCSImage, DCSButton, DCSBar, 
//			DCSTrend, CalcPoint, DCSXImage, DCSGrid, 
//			OLRect, DCSOLRect, DCSGroup, AbstractDCSObject, 
//			AWTContext

public class DCSDiagram
{

	public int version;
	public short x;
	public short y;
	public short width;
	public short height;
	public Color bgColor;
	public boolean isNew;
	public boolean isModified;
	public boolean isLocale;
	public Vector objectLink;
	protected AWTContext awtContext;

	public DCSDiagram(AWTContext awtContext)
	{
		version = 2001;
		bgColor = Color.black;
		isNew = true;
		isModified = false;
		isLocale = true;
		objectLink = new Vector();
		this.awtContext = awtContext;
		x = 0;
		y = 0;
		width = 800;
		height = 600;
	}

	public DCSDiagram(int x1, int y1, int w1, int h1)
	{
		version = 2001;
		bgColor = Color.black;
		isNew = true;
		isModified = false;
		isLocale = true;
		objectLink = new Vector();
		x = (short)x1;
		y = (short)y1;
		width = (short)w1;
		height = (short)h1;
	}

	public DCSDiagram() {
		version = 2001;
		bgColor = Color.black;
		isNew = true;
		isModified = false;
		isLocale = true;
		objectLink = new Vector();
		x = 0;
		y = 0;
		width = 800;
		height = 600;
	}

	public Vector getAXPoints()
	{
		Vector points = new Vector();
		for (int i = 0; i < objectLink.size(); i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			if (obj.getType() == 7)
			{
				DCSDasPoint daspoint = (DCSDasPoint)obj;
				String pname = daspoint.getName();
				String fd = daspoint.getFD();
				if (fd.equals("AV"))
				{
					String desc = OPNetwork.getValueString(pname, "ED");
					String s = pname + ", " + desc;
					points.add(s);
				}
			}
		}

		return points;
	}

	public Vector getExportPointsInfo(byte exportPointType)
	{
		Vector pointInfos = new Vector();
		Vector pointnames = getProcessPoints();
		for (int i = 0; i < pointnames.size(); i++)
		{
			String pointName = (String)pointnames.elementAt(i);
			DBPoint dbPoint = OPNetwork.getPointByGlobalName(pointName);
			if (dbPoint != null)
			{
				int recordType = dbPoint.getRecordType();
				if (exportPointType == recordType)
				{
					String pointDesc = OPNetwork.getValueString(pointName, "ED");
					String pointInfo = pointName + ", " + pointDesc;
					pointInfos.add(pointInfo);
				}
			}
		}

		return pointInfos;
	}

	public short getX()
	{
		return x;
	}

	public short getY()
	{
		return y;
	}

	public short getWidth()
	{
		return width;
	}

	public short getHeight()
	{
		return height;
	}

	public void setWidth(int w)
	{
		width = (short)w;
	}

	public void setHeight(int h)
	{
		height = (short)h;
	}

	public void setBGColor(Color c)
	{
		bgColor = c;
	}

	public Color getBGColor()
	{
		return bgColor;
	}

	public int size()
	{
		return objectLink.size();
	}

	public Vector getObjectLink()
	{
		return objectLink;
	}

	public void addObject(DCSObject obj)
	{
		objectLink.addElement(obj);
	}

	public void addObject(int i, DCSObject obj)
	{
		objectLink.add(i, obj);
	}

	public void delete(DCSObject obj)
	{
		objectLink.remove(obj);
	}

	public void delete(Vector vect)
	{
		int n = vect.size();
		for (int i = 0; i < n; i++)
		{
			DCSObject obj = (DCSObject)vect.elementAt(i);
			objectLink.remove(obj);
		}

	}

	public void clearAll()
	{
		objectLink.removeAllElements();
	}

	public void free()
	{
		clearAll();
		objectLink = null;
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
					if (pointname == null) {
						continue;
					}
					if (!points.contains(pointname))
						points.add(pointname);
				}

			}
		}

		return points;
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

	public void draw(Graphics g, Dimension d)
	{
		int n = 0;
		try
		{
			g.setColor(bgColor);
			g.fillRect(0, 0, d.width, d.height);
			n = objectLink.size();
			for (int i = 0; i < n; i++)
			{
				
				DCSObject obj = (DCSObject)objectLink.elementAt(i);
				obj.draw(g, d);
			}

		}
		catch (Exception e_)
		{
			e_.printStackTrace();
		}
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		
		out.writeInt(version);
		out.writeShort(x);
		out.writeShort(y);
		out.writeShort(this.width);
		out.writeShort(this.height);
		out.writeInt(bgColor.getRGB());
		for (int i = 0; i < size(); i++)
		{
			DCSObject obj = (DCSObject)objectLink.elementAt(i);
			if (obj!= null) {
				obj.write(out);
			}
			
		}

	}

	public void read(DataInputStream in)
		throws IOException
	{
		
		byte obj_type = -1;
		version = in.readInt();
		x = in.readShort();
		y = in.readShort();
		width = in.readShort();
		height = in.readShort();
		bgColor = new Color(in.readInt());
		try
		{
			boolean isError = false;
			do
			{
				AbstractDCSObject dcsObject = null;
				obj_type = in.readByte();
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
					dcsObject = new DCSText(this, version);
					break;

				case 7: // '\007'
					dcsObject = new DCSDasPoint(this, version);
					break;

				case 9: // '\t'
					dcsObject = new DCSTime(this);
					break;

				case 10: // '\n'
					dcsObject = new DCSImage(version);
					break;

				case 11: // '\013'
					dcsObject = new DCSButton(this);
					break;

				case 12: // '\f'
					dcsObject = new DCSBar();
					break;

				case 13: // '\r'
					dcsObject = new DCSTrend();
					break;

				case 14: // '\016'
					dcsObject = new CalcPoint(this);
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
					int retval = group.read(in, this);
					if (retval == 0)
						addObject(group);
					break;

				case 8: // '\b'
				case 18: // '\022'
				case 19: // '\023'
				case 20: // '\024'
				case 22: // '\026'
				case 23: // '\027'
				case 24: // '\030'
				case 25: // '\031'
				case 26: // '\032'
				case 27: // '\033'
				case 28: // '\034'
				case 29: // '\035'
				default:
					isError = true;
					break;
				}
				if (isError)
				{
					System.out.println("error object type " + obj_type);
					break;
				}
				if (dcsObject != null)
				{
					dcsObject.setAwtContext(awtContext);
					int retval = dcsObject.read(in);
					if (retval == 0)
						addObject(dcsObject);
				}
			} while (true);
		}
		catch (EOFException eofexception) { }
	}
}
