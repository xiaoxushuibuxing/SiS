// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSText.java

package awt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.*;
import java.util.Vector;

import org.apache.log4j.Logger;

import magus.net.provide.OPDataProvider;
import magus.util.CoordinateTransform;
import magus.util.ReadHandle;

// Referenced classes of package magus.awt:
//			AbstractDCSObject, DCSColor, DCSConditionText, DCSCondition, 
//			DCSDiagram, AWTContext, GBLineStyle, VectText, 
//			DCSEvent

public class DCSText extends AbstractDCSObject
{

	public static final byte OBJECT_TYPE = 6;
	public String name;
	public short x;
	public short y;
	public byte fontType;
	public Font font;
	public short charWidth;
	public short charHeight;
	public byte overstrike;
	public byte align;
	public DCSColor fgColor;
	public DCSColor bgColor;
	static VectText vectText = null;
	public DCSEvent dcsEvent;
	public Rectangle bounds;
	public DCSConditionText condText;
	DCSDiagram parent;
	Font deriveFont;
	int vx;
	int vy;
	int twidth;
	int biasCharWidth;
	int biasCharHeight;
	public int version;
	private Logger logger = Logger.getLogger(getClass());

	public DCSText(DCSDiagram diagram)
	{
		fontType = 0;
		font = new Font("Dialog", 0, 10);
		overstrike = 0;
		align = 0;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		dcsEvent = null;
		bounds = null;
		condText = null;
		version = 1001;
		parent = diagram;
		twidth = 0;
	}

	public DCSText(DCSDiagram diagram, int vsn)
	{
		fontType = 0;
		font = new Font("Dialog", 0, 18);
		overstrike = 0;
		align = 0;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		dcsEvent = null;
		bounds = null;
		condText = null;
		version = 1001;
		parent = diagram;
		version = vsn;
		twidth = 0;
	}

	public DCSText(DCSDiagram diagram, String s, int x1, int y1)
	{
		fontType = 0;
		font = new Font("Dialog", 0, 18);
		overstrike = 0;
		align = 0;
		fgColor = new DCSColor(Color.white);
		bgColor = new DCSColor(Color.black);
		dcsEvent = null;
		bounds = null;
		condText = null;
		version = 1001;
		parent = diagram;
		name = s;
		x = (short)x1;
		y = (short)y1;
		twidth = 0;
	}

	public void setAttributes(DCSColor fc, DCSColor bc, byte ft, Font fnt, byte os, byte aln)
	{
		fgColor = fc;
		bgColor = bc;
		fontType = ft;
		font = fnt;
		overstrike = os;
		align = aln;
	}

	public void setAttributes(DCSColor fc, DCSColor bc, byte ft, short cw, short ch, byte os, byte aln)
	{
		fgColor = fc;
		bgColor = bc;
		fontType = ft;
		charWidth = cw;
		charHeight = ch;
		overstrike = os;
		align = aln;
	}

	public void setConditionText(DCSConditionText t1)
	{
		condText = t1;
	}

	public void parseDCSText(String s)
	{
		s = s.trim();
		int c1 = s.indexOf('"');
		if (c1 < 0)
			return;
		int c2 = s.indexOf('"', c1 + 1);
		if (c2 < 0)
			return;
		name = s.substring(c1 + 1, c2);
		if (c2 == s.length() - 1)
		{
			condText = null;
		} else
		{
			String s2 = s.substring(c2 + 1);
			condText = new DCSConditionText();
			condText.parseConditionText(s2);
		}
	}

	public String getDefaultText()
	{
		return name;
	}

	public void setDefaultText(String name)
	{
		this.name = name;
	}
	
	public DCSConditionText getConditionText()
	{
		return condText;
	}

	public String getText(OPDataProvider opDataProvider)
	{
		if (condText == null)
			return name;
		String s = condText.getText(opDataProvider);
		if (s == null)
			return name;
		else
			return s;
	}

	public String toString()
	{
		String s = "\"" + name + "\"";
		if (condText != null)
			s = s + " " + condText.toString();
		return s;
	}

	
	
	public byte getType()
	{
		return 6;
	}

	public String toTextString() {
		return "DCSText [name=" + name + ", x=" + x + ", y=" + y + ", fontType=" + fontType + ", font=" + font
				+ ", charWidth=" + charWidth + ", charHeight=" + charHeight + ", overstrike=" + overstrike + ", align="
				+ align + ", fgColor=" + fgColor + ", bgColor=" + bgColor + ", dcsEvent=" + dcsEvent + ", bounds=" + bounds
				+ ", condText=" + condText + ", parent=" + parent + ", deriveFont=" + deriveFont + ", vx=" + vx + ", vy="
				+ vy + ", twidth=" + twidth + ", biasCharWidth=" + biasCharWidth + ", biasCharHeight=" + biasCharHeight
				+ ", version=" + version + "]";
	}

	public DCSEvent getEvent()
	{
		return dcsEvent;
	}

	public Vector getProcessPoints()
	{
		Vector points = new Vector();
		if (condText != null)
		{
			Vector vtxt = condText.getProcessPoints();
			if (vtxt != null)
			{
				for (int i = 0; i < vtxt.size(); i++)
				{
					String pname = (String)vtxt.elementAt(i);
					if (!points.contains(pname))
						points.add(pname);
				}

			}
		}
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
		int len = name.length();
		if (fontType == 0)
		{
			FontMetrics fontMetrics = g.getFontMetrics();
			int as = fontMetrics.getAscent();
			int ds = fontMetrics.getDescent();
			int cw = fontMetrics.charWidth('W');
			int ch = as + ds;
			int sw = fontMetrics.stringWidth(name);
			as = CoordinateTransform.Y_ViewToStandard(d, as);
			cw = CoordinateTransform.X_ViewToStandard(d, cw);
			ch = CoordinateTransform.Y_ViewToStandard(d, ch);
			sw = CoordinateTransform.X_ViewToStandard(d, sw);
			if (align == 0)
				bounds = new Rectangle(x, y, sw, ch);
			else
				bounds = new Rectangle(x, y, cw, ch * len);
		} else
		if (align == 0)
			bounds = new Rectangle(x, y, charWidth * len, charHeight);
		else
			bounds = new Rectangle(x, y, charWidth, charHeight * len);
	}

	public boolean contains(Point p)
	{
		if (bounds == null || p == null)
			return false;
		else
			return bounds.contains(p);
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
		Color fc = fgColor.getColor(awtContext.getOpDataProvider());
		Color bc = bgColor.getColor(awtContext.getOpDataProvider());
		if (fontType == 0)
			draw_bitmap(g, d, fc, bc);
		else
			draw_vect(g, d, fc, bc);
	}

	public void draw_bitmap(Graphics g, Dimension d, Color fc, Color bc)
	{
		g.setFont(deriveFont);
		if (name == null)
			return;
		if (bounds == null)
			createBounds(d, g);
		FontMetrics fontMetrics = g.getFontMetrics();
		int as = fontMetrics.getAscent();
		int ds = fontMetrics.getDescent();
		int ch = as + ds;
		int cw = fontMetrics.charWidth('W');
		String tmpName = getText(awtContext.getOpDataProvider());
		int sw = fontMetrics.stringWidth(tmpName);
		if (overstrike == 1)
		{
			g.setColor(bc);
			if (align == 0)
				g.fillRect(vx, vy, sw, ch);
			else
				g.fillRect(vx, vy, cw, ch * tmpName.length());
		}
		g.setColor(fc);
		if (align == 0)
		{
			String ss[] = ReadHandle.sscan(tmpName, "\\n", false);
			for (int i = 0; i < ss.length; i++)
				g.drawString(ss[i], vx, vy + as + i * ch);

		} else
		{
			for (int i4 = 0; i4 < tmpName.length(); i4++)
			{
				String c = tmpName.substring(i4, i4 + 1);
				g.drawString(c, vx, vy + as + ch * i4);
			}

		}
	}

	public void draw_vect(Graphics g, Dimension d, Color fc, Color bc)
	{
		if (bounds == null)
			createBounds(d, g);
		String tmpName = getText(awtContext.getOpDataProvider());
		int len = tmpName.length();
		if (overstrike == 1)
		{
			g.setColor(bc);
			if (align == 0)
				g.fillRect(vx, vy, biasCharWidth * len, biasCharHeight);
			else
				g.fillRect(vx, vy, biasCharWidth, len * biasCharHeight);
		}
		g.setColor(fc);
		GBLineStyle.setLine(g, 0, 1);
		if (vectText == null)
			vectText = new VectText();
		vectText.draw(g, tmpName, vx, vy, biasCharWidth, biasCharHeight, align);
	}

	public Object clone()
	{
		DCSText text = new DCSText(parent, name, x, y);
		text.version = version;
		if (fontType == 0)
			text.setAttributes(fgColor, bgColor, fontType, font, overstrike, align);
		else
			text.setAttributes(fgColor, bgColor, fontType, charWidth, charHeight, overstrike, align);
		text.setConditionText(condText);
		if (bounds != null)
			text.bounds = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
		return text;
	}

	public void write(DataOutputStream out)
		throws IOException
	{	
		out.writeByte(6);
		out.writeUTF(toString());
		out.writeShort(x);
		out.writeShort(y);
		out.writeByte(align);
		out.writeByte(overstrike);
		out.writeByte(fontType);
		if (fontType == 1)
		{
			out.writeShort(charWidth);
			out.writeShort(charHeight);
		} else
		{
			out.writeUTF(font.getFamily());
			out.writeInt(font.getStyle());
			//out.write(Font.BOLD);
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
		if (version >= 2001)
		{
			out.writeByte(showType);
			if (showType > 0)
				out.writeUTF(isShowCondition.toString());
		}
	}

	public int read(DataInputStream in)
		throws IOException
	{
		parseDCSText(in.readUTF());
		x = in.readShort();
		y = in.readShort();
		align = in.readByte();
		overstrike = in.readByte();
		fontType = in.readByte();
		if (fontType == 1)
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
		if (version >= 2001)
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
		this.x = (short)(int)((float)this.x * width_zoom + (float)x);
		this.y = (short)(int)(((float)this.y * height_zoom + (float)y) - 2.0F);
		font = font.deriveFont((float)font.getSize() * width_zoom + 6F);
	}

	public void setBounds(short x1, short y1, short w1, short h1)
	{

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
	
	

}
