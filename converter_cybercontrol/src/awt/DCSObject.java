// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSObject.java

package awt;

import java.awt.*;
import java.io.*;
import java.util.Vector;

// Referenced classes of package magus.awt:
//			DCSEvent, DCSCondition

public interface DCSObject
{

	public abstract byte getType();

	public abstract Rectangle getBounds();

	public abstract Shape getShape(Dimension dimension);

	public abstract boolean contains(Point point);

	public abstract DCSEvent getEvent();

	public abstract Vector getProcessPoints();

	public abstract void transform(Graphics g, Dimension dimension);

	public abstract void draw(Graphics g, Dimension dimension);

	public abstract int read(DataInputStream datainputstream)
		throws IOException;

	public abstract void write(DataOutputStream dataoutputstream)
		throws IOException;

	public abstract Object clone();

	public abstract void transform(Dimension dimension);

	public abstract void offset(int i, int j, float f, float f1);

	public abstract void setShowCondition(DCSCondition dcscondition);

	public abstract void setBounds(short word0, short word1, short word2, short word3);

	public abstract void move(int x, int y);
}
