// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CoordinateTransform.java

package magus.util;

import java.awt.*;

public class CoordinateTransform
{

	static double standardWidth = 16000D;
	static double standardHeight = 16000D;

	public CoordinateTransform()
	{
	}

	public static Rectangle rectViewToStandard(Dimension d, Rectangle r)
	{
		int xs = (int)(((double)r.x * standardWidth) / (double)d.width + 0.5D);
		int ys = (int)(((double)r.y * standardHeight) / (double)d.height + 0.5D);
		int ws = (int)(((double)r.width * standardWidth) / (double)d.width + 0.5D);
		int hs = (int)(((double)r.height * standardHeight) / (double)d.height + 0.5D);
		Rectangle rs = new Rectangle(xs, ys, ws, hs);
		return rs;
	}

	public static Point pointViewToStandard(Dimension dim, Point p)
	{
		int xv = p.x;
		int yv = p.y;
		int xs = (int)(((double)xv * standardWidth) / (double)dim.width + 0.5D);
		int ys = (int)(((double)yv * standardHeight) / (double)dim.height + 0.5D);
		Point ps = new Point(xs, ys);
		return ps;
	}

	public static Point pointStandardToView(Dimension dim, Point p)
	{
		int xs = p.x;
		int ys = p.y;
		int xv = (int)((double)(xs * dim.width) / standardWidth + 0.5D);
		int yv = (int)((double)(ys * dim.height) / standardHeight + 0.5D);
		Point pv = new Point(xv, yv);
		return pv;
	}

	public static int X_ViewToStandard(Dimension dim, int vx)
	{
		int sx = (int)(((double)vx * standardWidth) / (double)dim.width + 0.5D);
		return sx;
	}

	public static int Y_ViewToStandard(Dimension dim, int vy)
	{
		int sy = (int)(((double)vy * standardHeight) / (double)dim.height + 0.5D);
		return sy;
	}

	public static int X_StandardToView(Dimension dim, int sx)
	{
		int vx = (int)((double)(sx * dim.width) / standardWidth + 0.5D);
		return vx;
	}

	public static int Y_StandardToView(Dimension dim, int sy)
	{
		int vy = (int)((double)(sy * dim.height) / standardHeight + 0.5D);
		return vy;
	}

}
