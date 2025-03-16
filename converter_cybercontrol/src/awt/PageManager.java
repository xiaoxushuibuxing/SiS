// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PageManager.java

package awt;

import java.util.Vector;

// Referenced classes of package magus.awt:
//			DCSDiagram

public class PageManager
{

	Vector pageLink;
	int index;
	boolean lastFlag;
	boolean nextFlag;
	static int MAX_PAGE = 10;

	public PageManager()
	{
		pageLink = new Vector(MAX_PAGE);
		index = -1;
		lastFlag = false;
		nextFlag = false;
	}

	public void addPage(DCSDiagram diag)
	{
		if (pageLink.size() == MAX_PAGE)
		{
			DCSDiagram diagram = (DCSDiagram)pageLink.elementAt(0);
			diagram.free();
			pageLink.removeElementAt(0);
			if (index > 0)
				index--;
		}
		index++;
		pageLink.insertElementAt(diag, index);
		if (index > 0)
			if (index == pageLink.size() - 1)
			{
				lastFlag = true;
				nextFlag = false;
			} else
			{
				lastFlag = true;
				nextFlag = true;
			}
	}

	public DCSDiagram lastPage()
	{
		DCSDiagram diagram = null;
		if (lastFlag)
		{
			index--;
			diagram = (DCSDiagram)pageLink.elementAt(index);
			if (index == 0)
				lastFlag = false;
			nextFlag = true;
		}
		return diagram;
	}

	public DCSDiagram nextPage()
	{
		DCSDiagram diagram = null;
		if (nextFlag)
		{
			index++;
			diagram = (DCSDiagram)pageLink.elementAt(index);
			if (index == pageLink.size() - 1)
				nextFlag = false;
			lastFlag = true;
		}
		return diagram;
	}

}
