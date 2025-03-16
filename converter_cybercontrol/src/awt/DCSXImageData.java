// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSXImageData.java

package awt;


public class DCSXImageData
{

	public int w;
	public int h;
	public byte pixel[];

	public DCSXImageData(int i, int j)
	{
		pixel = null;
		w = i;
		h = j;
		pixel = new byte[w * h];
	}
}
