// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Numeral.java

package awt;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

public class Numeral
{
	class FluorescentBar
	{

		Shape bar;

		public Shape getShape()
		{
			return bar;
		}

		public FluorescentBar()
		{
			bar = null;
			GeneralPath s = new GeneralPath();
			s.moveTo(5F, 5F);
			s.lineTo(10F, 0.0F);
			s.lineTo(40F, 0.0F);
			s.lineTo(45F, 5F);
			s.lineTo(40F, 10F);
			s.lineTo(10F, 10F);
			s.closePath();
			bar = s;
		}
	}


	Shape bars[];
	GeneralPath dot;
	GeneralPath colon;

	public Numeral()
	{
		bars = null;
		dot = null;
		colon = null;
		bars = new Shape[7];
		Shape s = (new FluorescentBar()).getShape();
		AffineTransform at = new AffineTransform();
		bars[0] = s;
		at.setToRotation(Math.toRadians(90D), 5D, 5D);
		bars[1] = s = at.createTransformedShape(bars[0]);
		at.setToTranslation(40D, 0.0D);
		bars[2] = s = at.createTransformedShape(s);
		at.setToTranslation(0.0D, 40D);
		bars[3] = at.createTransformedShape(bars[1]);
		bars[4] = at.createTransformedShape(s);
		bars[5] = s = at.createTransformedShape(bars[0]);
		bars[6] = at.createTransformedShape(s);
		dot = new GeneralPath();
		dot.append(new Rectangle(20, 80, 10, 10), false);
		colon = new GeneralPath();
		colon.append(new Rectangle(10, 20, 10, 10), false);
		colon.append(new Rectangle(10, 65, 10, 10), false);
	}

	public Shape getShape(char c)
	{
		GeneralPath shape = new GeneralPath();
		switch (c)
		{
		case 48: // '0'
			shape.append(bars[0], false);
			shape.append(bars[1], false);
			shape.append(bars[2], false);
			shape.append(bars[3], false);
			shape.append(bars[4], false);
			shape.append(bars[6], false);
			break;

		case 49: // '1'
			shape.append(bars[2], false);
			shape.append(bars[4], false);
			break;

		case 50: // '2'
			shape.append(bars[0], false);
			shape.append(bars[2], false);
			shape.append(bars[5], false);
			shape.append(bars[3], false);
			shape.append(bars[6], false);
			break;

		case 51: // '3'
			shape.append(bars[0], false);
			shape.append(bars[2], false);
			shape.append(bars[5], false);
			shape.append(bars[4], false);
			shape.append(bars[6], false);
			break;

		case 52: // '4'
			shape.append(bars[1], false);
			shape.append(bars[2], false);
			shape.append(bars[5], false);
			shape.append(bars[4], false);
			break;

		case 53: // '5'
			shape.append(bars[0], false);
			shape.append(bars[1], false);
			shape.append(bars[5], false);
			shape.append(bars[4], false);
			shape.append(bars[6], false);
			break;

		case 54: // '6'
			shape.append(bars[0], false);
			shape.append(bars[1], false);
			shape.append(bars[3], false);
			shape.append(bars[4], false);
			shape.append(bars[5], false);
			shape.append(bars[6], false);
			break;

		case 55: // '7'
			shape.append(bars[0], false);
			shape.append(bars[2], false);
			shape.append(bars[4], false);
			break;

		case 57: // '9'
			shape.append(bars[0], false);
			shape.append(bars[1], false);
			shape.append(bars[2], false);
			shape.append(bars[5], false);
			shape.append(bars[4], false);
			shape.append(bars[6], false);
			break;

		case 35: // '#'
		case 56: // '8'
			shape.append(bars[0], false);
			shape.append(bars[1], false);
			shape.append(bars[2], false);
			shape.append(bars[3], false);
			shape.append(bars[4], false);
			shape.append(bars[5], false);
			shape.append(bars[6], false);
			break;

		case 46: // '.'
			shape.append(dot, false);
			break;

		case 58: // ':'
			shape.append(colon, false);
			break;
		}
		return shape;
	}

	public Shape getShape(char c, int w1, int h1)
	{
		Shape shp = getShape(c);
		AffineTransform AT = new AffineTransform();
		float t1 = (0.9F * (float)w1) / 50F;
		float t2 = (1.0F * (float)h1) / 100F;
		AT.setToScale(t1, t2);
		shp = AT.createTransformedShape(shp);
		return shp;
	}

	public void draw(Graphics g, String sv, int vx, int vy, int cw, int ch)
	{
		Graphics2D g2 = (Graphics2D)g;
		AffineTransform at = new AffineTransform();
		int len = sv.length();
		for (int i = 0; i < len; i++)
		{
			at.setToTranslation(vx + i * cw, vy);
			char c = sv.charAt(i);
			Shape shp = getShape(c, cw, ch);
			shp = at.createTransformedShape(shp);
			g2.fill(shp);
		}

	}
}
