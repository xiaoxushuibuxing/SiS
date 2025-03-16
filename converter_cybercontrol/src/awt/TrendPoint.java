// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrendPoint.java

package awt;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;
import magus.net.DBPoint;
import magus.net.OPNetwork;
import magus.util.ColorParser;
import magus.util.ReadHandle;

// Referenced classes of package magus.awt:
//			DCSTrend, AWTContext, GBLineStyle

public class TrendPoint
{

	private DCSTrend parent;
	private String name;
	private String FD;
	private byte lowType;
	private float lowLimit;
	private String lowName;
	private String lowFD;
	private byte hiType;
	private float hiLimit;
	private String hiName;
	private String hiFD;
	private Color drawColor;
	private Vector saveData;

	public TrendPoint(DCSTrend trend)
	{
		lowType = 0;
		lowLimit = 0.0F;
		hiType = 0;
		hiLimit = 100F;
		drawColor = Color.blue;
		saveData = new Vector();
		parent = trend;
	}

	public TrendPoint(DCSTrend trend, String pn, String fd)
	{
		lowType = 0;
		lowLimit = 0.0F;
		hiType = 0;
		hiLimit = 100F;
		drawColor = Color.blue;
		saveData = new Vector();
		parent = trend;
		name = pn;
		FD = fd;
	}

	public DBPoint getDBPoint()
	{
		DBPoint dbPoint = OPNetwork.getPointByGlobalName(name);
		return dbPoint;
	}

	public String getName()
	{
		return name;
	}

	public String getFD()
	{
		return FD;
	}

	public float getLowLimit()
	{
		return lowLimit;
	}

	public float getHighLimit()
	{
		return hiLimit;
	}

	public Color getColor()
	{
		return drawColor;
	}

	public void setName(String pn)
	{
		name = pn;
	}

	public void setFD(String fd)
	{
		FD = fd;
	}

	public void setLowLimit(float ll)
	{
		lowLimit = ll;
	}

	public void setHighLimit(float hl)
	{
		hiLimit = hl;
	}

	public void setColor(Color c)
	{
		drawColor = c;
	}

	public void parse(String str)
	{
		String s[] = ReadHandle.sscan(str, 3);
		name = s[0];
		FD = s[1];
		drawColor = ColorParser.getColor(s[2]);
		int i1 = str.indexOf("$LL");
		int i2 = str.indexOf("$HL");
		String llstr = str.substring(i1, i2);
		String hlstr = str.substring(i2);
		s = ReadHandle.sscan(llstr, 4);
		lowType = Byte.parseByte(s[1]);
		if (lowType == 0)
		{
			lowLimit = Float.parseFloat(s[2]);
		} else
		{
			lowName = s[2];
			lowFD = s[3];
		}
		s = ReadHandle.sscan(hlstr, 4);
		hiType = Byte.parseByte(s[1]);
		if (hiType == 0)
		{
			hiLimit = Float.parseFloat(s[2]);
		} else
		{
			hiName = s[2];
			hiFD = s[3];
		}
	}

	public String toString()
	{
		String s = name + " " + FD + " " + ColorParser.toString(drawColor) + " ";
		s = s + "$LL " + lowType + " ";
		if (lowType == 0)
			s = s + lowLimit + " ";
		else
			s = s + lowName + " " + lowFD + " ";
		s = s + "$HL " + hiType + " ";
		if (hiType == 0)
			s = s + hiLimit + " ";
		else
			s = s + hiName + " " + hiFD + " ";
		return s;
	}

	public void clearSSData()
	{
		saveData.clear();
	}

	public void addSSData(double v)
	{
		int nsamples = parent.getNumberOfSamples();
		if (nsamples <= 0)
			return;
		if (saveData.size() >= nsamples)
			saveData.removeElementAt(0);
		saveData.addElement(new Float(v));
	}

	public double getCurrentValue()
	{
		double v = parent.awtContext.getOpDataProvider().getValueFloat(name, FD);
		return v;
	}

	public void draw(Graphics g, int vx, int vy, int vw, int vh)
	{
		if (saveData.size() <= 1)
			return;
		double LL = 0.0D;
		if (lowType == 0)
			LL = lowLimit;
		else
			LL = parent.awtContext.getOpDataProvider().getValueFloat(lowName, lowFD);
		double HL = 0.0D;
		if (lowType == 0)
		{
			HL = hiLimit;
		} else
		{
			HL = parent.awtContext.getOpDataProvider().getValueFloat(hiName, hiFD);
			if (HL <= 0.0D)
				HL = 100D;
		}
		int nsamples = parent.getNumberOfSamples();
		int tx[] = new int[nsamples];
		int ty[] = new int[nsamples];
		int ndata = saveData.size();
		for (int i = 0; i < ndata; i++)
		{
			double v1 = ((Float)saveData.elementAt(i)).floatValue();
			if (v1 > HL)
				v1 = HL;
			if (v1 < LL)
				v1 = LL;
			tx[i] = (vx + vw) - ((ndata - i - 1) * vw) / nsamples;
			ty[i] = (vy + vh) - (int)(((double)vh * (v1 - LL)) / (HL - LL) + 0.5D);
		}

		g.setColor(drawColor);
		GBLineStyle.setLine(g, 0, 1);
		g.drawPolyline(tx, ty, ndata);
	}
}
