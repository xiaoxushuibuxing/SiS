// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSResource.java

package magus.util;

import java.util.*;

public class DCSResource
{

	static Hashtable resources = new Hashtable();
	static Locale myLocale = new Locale("zh", "CN");
	ResourceBundle myResource;

	public DCSResource(String resFileURL)
	{
		synchronized (resources)
		{
			myResource = (ResourceBundle)resources.get(resFileURL);
			if (myResource == null)
			{
				myResource = ResourceBundle.getBundle(resFileURL, myLocale);
				resources.put(resFileURL, myResource);
			}
		}
	}

	public String getString(String s)
	{
		if (myResource == null)
			return null;
		return myResource.getString(s);
		
	}

}
