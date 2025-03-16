// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FillMode.java

package awt;

import java.awt.*;
import java.awt.image.BufferedImage;

import org.apache.log4j.Logger;

import magus.util.ColorParser;
import magus.util.ReadHandle;

public class FillMode
{
	Logger logger = Logger.getLogger(FillMode.class);
	static byte TOTAL_PATTERN = 18;
	static String styleName[] = {
		"NOFILL", "SOLID", "PATTERN", "GRADIENT"
	};
	byte style;
	byte pattern;
	byte direction;
	byte morph;
	Color ptColor;

	public FillMode()
	{
		style = 0;
		pattern = 0;
		direction = 0;
		morph = 0;
		ptColor = Color.white;
	}

	public void parseFillMode(String str)
	{
		//logger.info(str);
		String s[] = ReadHandle.sscan(str, 1);
		style = Byte.parseByte(s[0]);
		//logger.info(style);
		if (style == 2)
		{
			s = ReadHandle.sscan(str, 3);
			pattern = Byte.parseByte(s[1]);
			ptColor = ColorParser.getColor(s[2]);
		} else
		if (style == 3)
		{
			s = ReadHandle.sscan(str, 4);
			direction = Byte.parseByte(s[1]);
			morph = Byte.parseByte(s[2]);
			ptColor = ColorParser.getColor(s[3]);
		}
	}

	public void setStyle(byte s)
	{
		style = s;
	}

	public void setPattern(byte ptn)
	{
		pattern = ptn;
	}

	public void setDirection(byte d)
	{
		direction = d;
	}

	public void setMorph(byte m)
	{
		morph = m;
	}

	public void setColor(Color c)
	{
		ptColor = c;
	}

	public byte getStyle()
	{
		return style;
	}

	public byte getPattern()
	{
		return pattern;
	}

	public byte getDirection()
	{
		return direction;
	}

	public byte getMorph()
	{
		return morph;
	}

	public Color getColor()
	{
		return ptColor;
	}

	public String toString()
	{
		String s = "";
		s = s + style;
		if (style == 2)
			s = s + " " + pattern + " " + ColorParser.toString(ptColor);
		else
		if (style == 3)
			s = s + " " + direction + " " + morph + " " + ColorParser.toString(ptColor);
		return s;
	}

	public void fill(Graphics g, Color bc, Shape shp)
	{
		Graphics2D g2 = (Graphics2D)g;
		if (style == 1)
		{
			g2.setColor(bc);
			g2.fill(shp);
		} else
		if (style == 2)
			fillPattern(g2, bc, shp);
		else
		if (style == 3)
			fillGradient(g2, bc, shp);
		else
			return;
	}

	public FillMode Clone()
	{
		FillMode fm = new FillMode();
		fm.style = style;
		fm.pattern = pattern;
		fm.direction = direction;
		fm.morph = morph;
		fm.ptColor = ptColor;
		return fm;
	}

	public void fillPattern(Graphics2D g2, Color bc, Shape shp)
	{
		int width1 = 8;
		int height1 = 8;
		int width2 = 4;
		int height2 = 4;
		int width3 = 2;
		int height3 = 2;
		switch (pattern)
		{
		case 0: // '\0'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 5, 0);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 1: // '\001'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 0, 5);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 2: // '\002'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 5, 0);
			bg.drawLine(0, 0, 0, 5);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 3: // '\003'
		{
			BufferedImage bimage = new BufferedImage(2, 2, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 2, 2);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 2, 0);
			Rectangle r = new Rectangle(0, 0, 2, 2);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 4: // '\004'
		{
			BufferedImage bimage = new BufferedImage(2, 2, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 2, 2);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 0, 2);
			Rectangle r = new Rectangle(0, 0, 2, 2);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 5: // '\005'
		{
			BufferedImage bimage = new BufferedImage(3, 3, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 3, 3);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 3, 0);
			bg.drawLine(0, 0, 0, 3);
			Rectangle r = new Rectangle(0, 0, 3, 3);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 6: // '\006'
		{
			BufferedImage bimage = new BufferedImage(8, 8, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 8, 8);
			bg.setColor(ptColor);
			bg.drawLine(0, 7, 7, 0);
			Rectangle r = new Rectangle(0, 0, 8, 8);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 7: // '\007'
		{
			BufferedImage bimage = new BufferedImage(8, 8, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 8, 8);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 8, 8);
			Rectangle r = new Rectangle(0, 0, 8, 8);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 8: // '\b'
		{
			BufferedImage bimage = new BufferedImage(8, 8, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 8, 8);
			bg.setColor(ptColor);
			bg.drawLine(0, 8, 8, 0);
			bg.drawLine(0, 0, 8, 8);
			Rectangle r = new Rectangle(0, 0, 8, 8);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 9: // '\t'
		{
			BufferedImage bimage = new BufferedImage(4, 4, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 4, 4);
			bg.setColor(ptColor);
			bg.drawLine(0, 3, 3, 0);
			Rectangle r = new Rectangle(0, 0, 4, 4);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 10: // '\n'
		{
			BufferedImage bimage = new BufferedImage(4, 4, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 4, 4);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 4, 4);
			Rectangle r = new Rectangle(0, 0, 4, 4);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 11: // '\013'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(ptColor);
			bg.drawLine(0, 5, 5, 0);
			bg.drawLine(0, 0, 5, 5);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 12: // '\f'
		{
			BufferedImage bimage = new BufferedImage(10, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 10, 5);
			bg.setColor(ptColor);
			bg.drawLine(0, 4, 4, 0);
			bg.drawLine(5, 0, 10, 5);
			Rectangle r = new Rectangle(0, 0, 10, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 13: // '\r'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 10, 0);
			bg.drawLine(0, 5, 10, 5);
			bg.drawLine(0, 0, 0, 5);
			bg.drawLine(5, 5, 5, 10);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 14: // '\016'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 5, 0);
			bg.drawLine(5, 5, 10, 5);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 15: // '\017'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(ptColor);
			bg.drawLine(0, 0, 0, 5);
			bg.drawLine(5, 5, 5, 10);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 16: // '\020'
		{
			BufferedImage bimage = new BufferedImage(8, 8, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 8, 8);
			bg.setColor(ptColor);
			bg.fillOval(0, 0, 1, 1);
			bg.fillOval(4, 4, 1, 1);
			Rectangle r = new Rectangle(0, 0, 8, 8);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 17: // '\021'
		{
			BufferedImage bimage = new BufferedImage(4, 4, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bc);
			bg.fillRect(0, 0, 4, 4);
			bg.setColor(ptColor);
			bg.fillOval(0, 0, 1, 1);
			bg.fillOval(2, 2, 1, 1);
			Rectangle r = new Rectangle(0, 0, 4, 4);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}
		}
		g2.fill(shp);
	}

	public void fillGradient(Graphics2D g2, Color bc, Shape shp)
	{
		Rectangle r = shp.getBounds();
		int vx = r.x;
		int vy = r.y;
		int vw = r.width;
		int vh = r.height;
		switch (direction)
		{
		case 4: // '\004'
		case 5: // '\005'
		default:
			break;

		case 0: // '\0'
			switch (morph)
			{
			case 0: // '\0'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, bc, vx, vy + vh, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 1: // '\001'
			{
				GradientPaint gradient = new GradientPaint(vx, vy + vh, bc, vx, vy, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 2: // '\002'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, bc, vx, vy + vh / 2, ptColor, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 3: // '\003'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, ptColor, vx, vy + vh / 2, bc, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}
			}
			break;

		case 1: // '\001'
			switch (morph)
			{
			case 0: // '\0'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, bc, vx + vw, vy, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 1: // '\001'
			{
				GradientPaint gradient = new GradientPaint(vx + vw, vy, bc, vx, vy, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 2: // '\002'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, bc, vx + vw / 2, vy, ptColor, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 3: // '\003'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, ptColor, vx + vw / 2, vy, bc, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}
			}
			break;

		case 2: // '\002'
			switch (morph)
			{
			case 0: // '\0'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, bc, vx + vw, vy + vh, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 1: // '\001'
			{
				GradientPaint gradient = new GradientPaint(vx + vw, vy + vh, bc, vx, vy, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 2: // '\002'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, bc, vx + vw / 2, vy + vh / 2, ptColor, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 3: // '\003'
			{
				GradientPaint gradient = new GradientPaint(vx, vy, ptColor, vx + vw / 2, vy + vh / 2, bc, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}
			}
			break;

		case 3: // '\003'
			switch (morph)
			{
			case 0: // '\0'
			{
				GradientPaint gradient = new GradientPaint(vx, vy + vh, bc, vx + vw, vy, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 1: // '\001'
			{
				GradientPaint gradient = new GradientPaint(vx + vw, vy, bc, vx, vy + vh, ptColor);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 2: // '\002'
			{
				GradientPaint gradient = new GradientPaint(vx, vy + vh, bc, vx + vw / 2, vy + vh / 2, ptColor, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}

			case 3: // '\003'
			{
				GradientPaint gradient = new GradientPaint(vx, vy + vh, ptColor, vx + vw / 2, vy + vh / 2, bc, true);
				g2.setPaint(gradient);
				g2.fill(shp);
				break;
			}
			}
			break;
		}
	}

	public static void setFillMode(Graphics g, Color fgColor, Color bgColor, byte fillMode)
	{
		Graphics2D g2 = (Graphics2D)g;
		switch (fillMode)
		{
		case 1: // '\001'
		{
			g2.setPaint(bgColor);
			break;
		}

		case 2: // '\002'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(fgColor);
			bg.drawLine(0, 3, 5, 3);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 3: // '\003'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(fgColor);
			bg.drawLine(3, 0, 3, 5);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 4: // '\004'
		{
			BufferedImage bimage = new BufferedImage(5, 5, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 5, 5);
			bg.setColor(fgColor);
			bg.drawLine(0, 4, 4, 4);
			bg.drawLine(4, 4, 4, 0);
			Rectangle r = new Rectangle(0, 0, 5, 5);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 5: // '\005'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(fgColor);
			bg.drawLine(0, 10, 10, 0);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 6: // '\006'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(fgColor);
			bg.drawLine(0, 0, 10, 10);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 7: // '\007'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(fgColor);
			bg.drawLine(0, 0, 10, 10);
			bg.drawLine(0, 10, 10, 0);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}

		case 8: // '\b'
		{
			BufferedImage bimage = new BufferedImage(10, 10, 1);
			Graphics2D bg = bimage.createGraphics();
			bg.setColor(bgColor);
			bg.fillRect(0, 0, 10, 10);
			bg.setColor(fgColor);
			bg.fillOval(3, 3, 4, 4);
			Rectangle r = new Rectangle(0, 0, 10, 10);
			g2.setPaint(new TexturePaint(bimage, r));
			break;
		}
		}
	}

}
