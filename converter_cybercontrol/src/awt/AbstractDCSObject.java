// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AbstractDCSObject.java

package awt;


// Referenced classes of package magus.awt:
//			DCSObject, AWTContext, DCSCondition

public abstract class AbstractDCSObject
	implements DCSObject
{

	protected AWTContext awtContext;
	protected byte showType =0;
	protected DCSCondition isShowCondition;

	protected AbstractDCSObject()
	{
		showType = 0;
		isShowCondition = null;
	}

	public AbstractDCSObject(AWTContext awtContext)
	{
		showType = 0;
		isShowCondition = null;
		this.awtContext = awtContext;
	}

	public AWTContext getAwtContext()
	{
		return awtContext;
	}

	public void setAwtContext(AWTContext awtContext)
	{
		this.awtContext = awtContext;
	}

	public void setShowCondition(DCSCondition showCondition)
	{
		System.out.println("------------------------------showCondition--------------------------------------");
		System.out.println(showCondition.toString());
		isShowCondition = showCondition;
		if (showCondition == null || showCondition.equals("") || showCondition.toString().length()<10)
			showType = 0;
		else
			showType = 1;
		System.out.println("------------------------------showCondition--------------------------------------");
	}

	public abstract Object clone();

	public byte getShowType()
	{
		return showType;
	}

	public void setShowType(byte showType)
	{
		this.showType = showType;
	}
}
