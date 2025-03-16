// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CalcPoint.java

package awt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.Vector;
import magus.jep.*;
import magus.util.CoordinateTransform;
import magus.util.ReviewParser;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, DCSDiagram, AWTContext, 
//			GBLineStyle, VectText, Numeral, DCSEvent

public class CalcPoint extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 14;
	private static VectText vectText = null;
	private static Numeral numeral = null;
	private String pointName;
	private byte strLen;
	private byte dotNum;
	private short x;
	private short y;
	private byte fontType;
	private Font font;
	private short charWidth;
	private short charHeight;
	private byte adjust;
	private DCSColor fgColor;
	DCSColor bgColor;
	DCSEvent dcsEvent;
	Rectangle bounds;
	DCSDiagram parent;
	Font deriveFont;
	int vx;
	int vy;
	int biasCharWidth;
	int biasCharHeight;

	public CalcPoint(DCSDiagram diagram)
	{
		pointName = null;
		strLen = 0;
		dotNum = 0;
		fontType = 0;
		font = new Font("Dialog", 0, 12);
		charWidth = 0;
		charHeight = 0;
		adjust = 0;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		dcsEvent = null;
		bounds = null;
		parent = null;
		parent = diagram;
	}

	public CalcPoint(DCSDiagram diagram, String s, short x1, short y1, byte sl, byte dn)
	{
		pointName = null;
		strLen = 0;
		dotNum = 0;
		fontType = 0;
		font = new Font("Dialog", 0, 12);
		charWidth = 0;
		charHeight = 0;
		adjust = 0;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		dcsEvent = null;
		bounds = null;
		parent = null;
		parent = diagram;
		pointName = s;
		x = x1;
		y = y1;
		strLen = sl;
		dotNum = dn;
		fontType = 0;
		adjust = 0;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
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
		return 14;
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public String getPointName()
	{
		return pointName;
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
		return points;
	}

	public byte getStrLen()
	{
		return strLen;
	}

	public byte getDotNum()
	{
		return dotNum;
	}

	public byte getAdjust()
	{
		return adjust;
	}

	public byte getFontType()
	{
		return fontType;
	}

	public Font getFont()
	{
		return font;
	}

	public short getCharWidth()
	{
		return charWidth;
	}

	public short getCharHeight()
	{
		return charHeight;
	}

	public DCSColor getFGColor()
	{
		return fgColor;
	}

	public DCSColor getBGColor()
	{
		return bgColor;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}

	public void createBounds(Dimension d, Graphics g)
	{
		String sv = getString(false);
		int len = sv.length();
		if (fontType == 0)
		{
			g.setFont(font);
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

	public Vector getControlPoint()
	{
		Point points[] = new Point[3];
		Vector pList = new Vector();
		points[0] = new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
		points[1] = new Point(bounds.x, bounds.y);
		points[2] = new Point(bounds.x + bounds.width, bounds.y + bounds.height);
		for (int i = 0; i < 3; i++)
			pList.addElement(points[i]);

		return pList;
	}

	public boolean contains(Point p)
	{
		if (bounds == null)
			return false;
		else
			return bounds.contains(p);
	}

	public boolean contains(int xp, int yp)
	{
		return bounds.contains(new Point(xp, yp));
	}

	public String getString(boolean flag)
	{
		String s = "";
		if (flag)
		{
			float value = getValue();
			s = ReviewParser.getDecimalString(value, strLen, dotNum, adjust);
		} else
		{
			s = "";
			for (int i = 0; i < strLen - dotNum - 1; i++)
				s = s + "#";

			s = s + ".";
			for (int i = 0; i < dotNum; i++)
				s = s + "#";

		}
		return s;
	}

	private float getValue()
	{
		ExpressionI opExp = null;
		float value = 0.0F;
		try
		{
			opExp = ExpressionFactory.createOPExpression(pointName, true);
			opExp.evaluate();
			value = opExp.getValue().floatValue();
		}
		catch (ParseJEPException e)
		{
			System.err.println(e.getMessage());
		}
		catch (EvaluateJEPException e1)
		{
			System.err.println(e1.getMessage());
		}
		return value;
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
		String s = getString(true);
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
		g.setColor(bc);
		g.fillRect(vx, vy, sw, ch);
		g.setColor(fc);
		g.drawString(sv, vx, vy + as);
	}

	public void draw_vect(Graphics g, Dimension d, String sv, Color fc, Color bc)
	{
		if (bounds == null)
			createBounds(d, g);
		int len = sv.length();
		g.setColor(bc);
		g.fillRect(vx, vy, biasCharWidth * len, biasCharHeight);
		g.setColor(fc);
		GBLineStyle.setLine(g, 0, 1);
		if (vectText == null)
			vectText = new VectText();
		vectText.draw(g, sv, vx, vy, biasCharWidth, biasCharHeight, 0);
	}

	public void draw_digit(Graphics g, Dimension d, String sv, Color fc, Color bc)
	{
		if (bounds == null)
			createBounds(d, g);
		int len = sv.length();
		g.setColor(bc);
		g.fillRect(vx, vy, biasCharWidth * len, biasCharHeight);
		g.setColor(fc);
		if (numeral == null)
			numeral = new Numeral();
		numeral.draw(g, sv, vx, vy, biasCharWidth, biasCharHeight);
	}

	public Object clone()
	{
		CalcPoint calcPoint = new CalcPoint(parent, pointName, x, y, strLen, dotNum);
		if (fontType == 0)
			calcPoint.setAttributes(fgColor, bgColor, fontType, font, adjust);
		else
			calcPoint.setAttributes(fgColor, bgColor, fontType, charWidth, charHeight, adjust);
		if (bounds != null)
			calcPoint.bounds = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
		return calcPoint;
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeByte(14);
		out.writeUTF(pointName);
		out.writeShort(x);
		out.writeShort(y);
		out.writeByte(strLen);
		out.writeByte(dotNum);
		out.writeByte(adjust);
		out.writeByte(fontType);
		if (fontType == 1 || fontType == 2)
		{
			out.writeShort(charWidth);
			out.writeShort(charHeight);
		} else
		{
			out.writeUTF(font.getFamily());
			out.writeInt(font.getStyle());
			out.writeInt(font.getSize());
		}
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
	}

	public int read(DataInputStream in)
		throws IOException
	{
		pointName = in.readUTF();
		x = in.readShort();
		y = in.readShort();
		strLen = in.readByte();
		dotNum = in.readByte();
		adjust = in.readByte();
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
		String sv = getString(false);
		int len = sv.length();
		if (fontType == 0)
		{
			int fsize = font.getSize();
			float zf = (1.0F * (float)h1) / (float)bounds.height;
			font = font.deriveFont((float)fsize * zf);
			bounds = null;
		} else
		{
			charWidth = (short)(int)((1.0F * (float)w1) / (float)len + 0.5F);
			charHeight = h1;
			bounds = new Rectangle(x1, y1, w1, h1);
		}
	}

}
