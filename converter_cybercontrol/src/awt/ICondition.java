// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ICondition.java

package awt;

import java.util.Vector;
import magus.net.provide.OPDataProvider;

public interface ICondition
{

	public abstract boolean parse(String s);

	public abstract String toString();

	public abstract int getCondition(OPDataProvider opdataprovider);

	public abstract Vector getProcessPoints();

	public abstract int getCondition();
}
