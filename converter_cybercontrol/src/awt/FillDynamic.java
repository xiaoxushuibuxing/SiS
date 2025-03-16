// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FillDynamic.java

package awt;

import java.awt.Color;
import java.io.*;
import java.util.Vector;

// Referenced classes of package magus.awt:
//			FillPoint

public class FillDynamic
{

	public static int MAX_POINT = 5;
	private float loLimit;
	private float hiLimit;
	Color bgColor;
	Vector pointList;

	public FillDynamic()
	{
		loLimit = 0.0F;
		hiLimit = 100F;
		bgColor = Color.white;
		pointList = new Vector();
	}

	public Vector getPoints()
	{
		return pointList;
	}

	public float getLoLimit()
	{
		return loLimit;
	}

	public float getHiLimit()
	{
		return hiLimit;
	}

	public Color getBGColor()
	{
		return bgColor;
	}

	public void setPoints(Vector vp)
	{
		pointList = vp;
	}

	public void setLoLimit(float ll)
	{
		loLimit = ll;
	}

	public void setHiLimit(float hl)
	{
		hiLimit = hl;
	}

	public void setBGColor(Color bc)
	{
		bgColor = bc;
	}

	public void addPoint(FillPoint fp)
	{
		pointList.add(fp);
	}

	public void write(DataOutputStream out)
		throws IOException
	{
		out.writeFloat(loLimit);
		out.writeFloat(hiLimit);
		out.writeInt(bgColor.getRGB());
		int num = pointList.size();
		out.writeByte(num);
		for (int i = 0; i < num; i++)
		{
			FillPoint fpoint = (FillPoint)pointList.elementAt(i);
			out.writeUTF(fpoint.getName());
			out.writeUTF(fpoint.getFD());
			out.writeInt(fpoint.getColor().getRGB());
		}

	}

	public void read(DataInputStream in)
		throws IOException
	{
		loLimit = in.readFloat();
		hiLimit = in.readFloat();
		bgColor = new Color(in.readInt());
		int num = in.readByte();
		pointList.clear();
		for (int i = 0; i < num; i++)
		{
			FillPoint fpoint = new FillPoint();
			fpoint.setName(in.readUTF());
			fpoint.setFD(in.readUTF());
			fpoint.setColor(new Color(in.readInt()));
			pointList.add(fpoint);
		}

	}

}
