// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSDasPoint.java

package awt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.Vector;

import magus.util.CoordinateTransform;
import magus.util.ReviewParser;


public class DCSDasPoint extends AbstractDCSObject
{
	public static final byte OBJECT_TYPE = 7;
	public String pointName;
	public String FD;
	public byte strLen;//长度
	public byte dotNum;//小数点位
	public short x;
	public short y;
	public byte fontType;
	public Font font;
	short charWidth;
	short charHeight;
	public byte adjust;//对齐方式
	byte overstrike;
	public DCSColor fgColor;
	public DCSColor bgColor;
	DCSEvent dcsEvent;
	Rectangle bounds;
	static VectText vectText = null;
	static Numeral numeral = null;
	DCSDiagram parent;
	Font deriveFont;
	int vx;
	int vy;
	int biasCharWidth;
	int biasCharHeight;
	private byte quality;
	public int version;

	public DCSDasPoint(DCSDiagram diagram)
	{
		overstrike = 0;
		dcsEvent = null;
		bounds = null;
		quality = 0;
		version = 1001;
		parent = diagram;
	}

	public DCSDasPoint(DCSDiagram diagram, int vsn)
	{
		overstrike = 0;
		dcsEvent = null;
		bounds = null;
		quality = 0;
		version = 1001;
		parent = diagram;
		adjust = 0;
		version = vsn;
	}

	public DCSDasPoint(DCSDiagram diagram, String s, short x1, short y1, byte sl, byte dn)
	{
		overstrike = 0;
		dcsEvent = null;
		bounds = null;
		quality = 0;
		version = 1001;
		parent = diagram;
		pointName = s;
		FD = "AV";
		x = x1;
		y = y1;
		strLen = sl;
		dotNum = dn;
		fontType = 0;
		adjust =2;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
	}

	public void setAttributes(DCSColor fg, DCSColor bg, byte ft, Font fnt, byte ad)
	{
		fgColor = fg;
		bgColor = bg;
		fontType = ft;
		font = fnt;
		adjust = ad;
	}

	public void setAttributes(DCSColor fg, DCSColor bg, byte ft, short cw, short ch, byte ad)
	{	
		fgColor = fg;
		bgColor = bg;
		fontType = ft;
		charWidth = cw;
		charHeight = ch;
		adjust = ad;
	}

	public byte getType()
	{
		return 7;
	}

	public String getName()
	{
		return pointName;
	}

	public String getFD()
	{
		return FD;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
		points.add(pointName);
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
		return bounds;
	}

	public void createBounds(Dimension d, Graphics g)
	{
		String sv = getString();
		int len = sv.length();
		if (fontType == 0)
		{
			FontMetrics fontMetrics = g.getFontMetrics();
			int as = fontMetrics.getAscent();
			int ds = fontMetrics.getDescent();
			int cw = fontMetrics.charWidth('W');
			int ch = as + ds;
			int sw = fontMetrics.stringWidth(sv);
			as = CoordinateTransform.Y_ViewToStandard(d, as);
			cw = CoordinateTransform.X_ViewToStandard(d, cw);
			ch = CoordinateTransform.Y_ViewToStandard(d, ch);
			sw = CoordinateTransform.X_ViewToStandard(d, sw);
			bounds = new Rectangle(x, y, sw, ch);
		} else
		{
			bounds = new Rectangle(x, y, charWidth * len, charHeight);
		}
	}

	public boolean contains(Point p)
	{
		if (bounds == null || p == null)
			return false;
		else
			return bounds.contains(p);
	}

	public String getString()
	{
		String s = null;
		if (dotNum == -1)
			dotNum = (byte)awtContext.getOpDataProvider().getValueInt(pointName, "FM");
		if (FD.compareTo("AV") == 0)
		{
			double value = awtContext.getOpDataProvider().getValueFloat(pointName, FD);
			if (quality == 0)
				s = ReviewParser.getDecimalString(value, strLen, dotNum, adjust);
			else
				s = ReviewParser.getDecimalString(value, strLen - 1, dotNum, adjust);
		} else
		{
			s = awtContext.getOpDataProvider().getValueString(pointName, FD);
		}
		if (s == null)
			s = new String("?" + FD);
		return s;
	}

	public String getQualityString()
	{
		int status = awtContext.getOpDataProvider().getValueInt(pointName, "AS");
		int bit8 = (status & 0x100) >> 8;
		int bit9 = (status & 0x200) >> 9;
		int i = bit9 * 2 + bit8;
		switch (i)
		{
		case 0: // '\0'
			return " ";

		case 1: // '\001'
			return "F";

		case 2: // '\002'
			return "P";

		case 3: // '\003'
			return "B";
		}
		return " ";
	}

	public Color getQualityColor(String str)
	{
		if (str.compareTo("G") == 0)
			return Color.green;
		if (str.compareTo("F") == 0)
			return Color.magenta;
		if (str.compareTo("P") == 0)
			return Color.white;
		if (str.compareTo("B") == 0)
			return Color.red;
		else
			return Color.blue;
	}

	public void transform(Graphics g, Dimension d)
	{
		vx = CoordinateTransform.X_StandardToView(d, x);
		vy = CoordinateTransform.Y_StandardToView(d, y);
		if (fontType == 0)
		{
			AffineTransform AT = new AffineTransform();
			float t1 = (1.0F * (float)d.width) / (float)parent.getWidth();
			float t2 = (1.0F * (float)d.height) / (float)parent.getHeight();
			AT.setToScale(t1, t2);
			deriveFont = font.deriveFont(AT);
		} else
		{
			biasCharWidth = CoordinateTransform.X_StandardToView(d, charWidth);
			biasCharHeight = CoordinateTransform.Y_StandardToView(d, charHeight);
		}
	}

	public Shape getShape(Dimension d)
	{
		if (bounds == null)
		{
			return null;
		} else
		{
			int vx = CoordinateTransform.X_StandardToView(d, bounds.x);
			int vy = CoordinateTransform.Y_StandardToView(d, bounds.y);
			int vw = CoordinateTransform.X_StandardToView(d, bounds.width);
			int vh = CoordinateTransform.Y_StandardToView(d, bounds.height);
			Rectangle rect = new Rectangle(vx, vy, vw, vh);
			return rect;
		}
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
		String s = getString();
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		if (fontType == 0)
			draw_bitmap(g, d, s, fc, bc);
		else
		if (fontType == 1)
			draw_vect(g, d, s, fc, bc);
		else
		if (fontType == 2)
			draw_digit(g, d, s, fc, bc);
	}

	public void draw_bitmap(Graphics g, Dimension d, String sv, Color fc, Color bc)
	{
		g.setFont(deriveFont);
		if (bounds == null)
			createBounds(d, g);
		FontMetrics fontMetrics = g.getFontMetrics();
		int as = fontMetrics.getAscent();
		int ds = fontMetrics.getDescent();
		int ch = as + ds;
		int cw = fontMetrics.charWidth('W');
		int sw = fontMetrics.stringWidth(sv);
		if (overstrike == 1)
		{
			g.setColor(bc);
			g.fillRect(vx, vy, sw, ch);
		}
		if (FD.compareTo("AV") == 0 && awtContext.isAlarm())
		{
			int alarmStatus = awtContext.getOpDataProvider().getValueInt(pointName, "AS") & 0x8c;
			if (alarmStatus > 0)
			{
				if (alarmStatus == 132)
					g.setColor(awtContext.getAlarmColorLow());
				else
					g.setColor(awtContext.getAlarmColorHigh());
			} else
			{
				g.setColor(fc);
			}
		} else
		{
			g.setColor(fc);
		}
		g.drawString(sv, vx, vy + as);
	}

	public void draw_vect(Graphics g, Dimension d, String sv, Color fc, Color bc)
	{
		if (bounds == null)
			createBounds(d, g);
		int len = sv.length();
		if (overstrike == 1)
		{
			g.setColor(bc);
			g.fillRect(vx, vy, biasCharWidth * len, biasCharHeight);
		}
		g.setColor(fc);
		GBLineStyle.setLine(g, 0, 1);
		if (vectText == null)
			vectText = new VectText();
		if (FD.compareTo("AV") == 0)
		{
			int alarmStatus = awtContext.getOpDataProvider().getValueInt(pointName, "AS") & 0x8c;
			if (alarmStatus > 0 && awtContext.isAlarm())
			{
				if (alarmStatus == 132)
					g.setColor(awtContext.getAlarmColorLow());
				else
					g.setColor(awtContext.getAlarmColorHigh());
				g.fillRect(vx, vy, biasCharWidth * len, biasCharHeight);
			}
			if (quality == 1)
			{
				String strQuality = getQualityString();
				int qx = vx + len * biasCharWidth;
				int qy = vy;
				g.setColor(getQualityColor(strQuality));
				vectText.draw(g, strQuality, qx, qy, biasCharWidth, biasCharHeight, 0);
			}
		}
		g.setColor(fc);
		vectText.draw(g, sv, vx, vy, biasCharWidth, biasCharHeight, 0);
	}

	public void draw_digit(Graphics g, Dimension d, String sv, Color fc, Color bc)
	{
		if (bounds == null)
			createBounds(d, g);
		int len = sv.length();
		if (overstrike == 1)
		{
			g.setColor(bc);
			g.fillRect(vx, vy, biasCharWidth * len, biasCharHeight);
		}
		g.setColor(fc);
		if (numeral == null)
			numeral = new Numeral();
		numeral.draw(g, sv, vx, vy, biasCharWidth, biasCharHeight);
	}

	public void setFD(String fd)
	{
		FD = fd;
	}

	public Object clone()
	{
		DCSDasPoint dasPoint = new DCSDasPoint(parent, pointName, x, y, strLen, dotNum);
		dasPoint.version = version;
		dasPoint.setFD(FD);
		if (fontType == 0)
			dasPoint.setAttributes(fgColor, bgColor, fontType, font, adjust);
		else
			dasPoint.setAttributes(fgColor, bgColor, fontType, charWidth, charHeight, adjust);
		if (bounds != null)
			dasPoint.bounds = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
		return dasPoint;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(7);
		out.writeUTF(pointName);
		out.writeUTF(FD);
		out.writeShort(x);
		out.writeShort(y);
		out.writeByte(strLen);
		out.writeByte(dotNum);
		out.writeByte(adjust);
		out.writeByte(quality);
		out.writeByte(fontType);
		if (fontType == 1 || fontType == 2)
		{
			out.writeShort(charWidth);
			out.writeShort(charHeight);
		} else
		{
			out.writeUTF(font.getFamily());
			out.writeInt(Font.BOLD);
			out.writeInt(font.getSize());
		}
		if (fgColor != null)
			fgColor.write(out);
		else
			fgColor = new DCSColor(Color.white);
		bgColor.write(out);
		if (dcsEvent == null)
		{
			out.writeByte(0);
		} else
		{
			out.writeByte(1);
			dcsEvent.write(out);
		}
		if (version == 2002)
		{
			if (isShowCondition == null)
				showType = 0;
			out.writeByte(showType);
			if (showType > 0)
				out.writeUTF(isShowCondition.toString());
		}
	}

	public int read(DataInputStream in)
		throws IOException
	{
		pointName = in.readUTF();
		FD = in.readUTF();
		x = in.readShort();
		y = in.readShort();
		strLen = in.readByte();
		dotNum = in.readByte();
		adjust = in.readByte();
		quality = in.readByte();
		fontType = in.readByte();
		if (fontType == 1 || fontType == 2)
		{
			charWidth = in.readShort();
			charHeight = in.readShort();
		} else
		{
			font = new Font(in.readUTF(), in.readInt(), in.readInt());
		}
		fgColor = new DCSColor();
		fgColor.read(in);
		bgColor = new DCSColor();
		bgColor.read(in);
		if (in.readByte() == 1)
		{
			dcsEvent = new DCSEvent();
			dcsEvent.read(in);
		}
		if (version >= 2002)
		{
			showType = in.readByte();
			if (showType > 0)
			{
				isShowCondition = new DCSCondition();
				isShowCondition.parse(in.readUTF());
			}
		}
		return 0;
	}

	public void transform(Dimension d)
	{
		x = (short)CoordinateTransform.X_ViewToStandard(d, x);
		y = (short)CoordinateTransform.Y_ViewToStandard(d, y);
	}

	public void offset(int x, int y, float width_zoom, float height_zoom)
	{
		this.x += x;
		this.y += y;
	}

	public void setBounds(short x1, short y1, short w1, short h1)
	{
		x = (short) (x+x1);
		y = (short) (y+y1);

		if (fontType == 0)
		{

			bounds = null;
		} else
		{

			bounds = new Rectangle(x1, y1, charWidth, charHeight);
		}
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

	@Override
	public String toString() {
		return "DCSDasPoint [pointName=" + pointName + ", FD=" + FD + ", strLen=" + strLen + ", dotNum=" + dotNum
				+ ", x=" + x + ", y=" + y + ", fontType=" + fontType + ", font=" + font + ", charWidth=" + charWidth
				+ ", charHeight=" + charHeight + ", adjust=" + adjust + ", overstrike=" + overstrike + ", fgColor="
				+ fgColor + ", bgColor=" + bgColor + ", dcsEvent=" + dcsEvent + ", bounds=" + bounds + ", parent="
				+ parent + ", deriveFont=" + deriveFont + ", vx=" + vx + ", vy=" + vy + ", biasCharWidth="
				+ biasCharWidth + ", biasCharHeight=" + biasCharHeight + ", quality=" + quality + ", version=" + version
				+ "]";
	}

}
