// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   GBFileFilter.java

package magus.util;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class GBFileFilter extends FileFilter
{

	String appendix;
	String description;

	public GBFileFilter(String ap, String desc)
	{
		appendix = ap;
		description = desc;
	}

	public boolean accept(File f)
	{
		if (f.isDirectory())
			return true;
		String extension = getExtension(f);
		if (extension != null)
			return extension.equals(appendix);
		else
			return false;
	}

	public String getAppendix()
	{
		return appendix;
	}

	public String getDescription()
	{
		return description;
	}

	public static String getExtension(File f)
	{
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1)
			ext = s.substring(i + 1).toLowerCase();
		return ext;
	}

	public static String getExtension(String s)
	{
		int i = s.lastIndexOf('.');
		String ext = null;
		if (i > 0 && i < s.length() - 1)
			ext = s.substring(i + 1).toLowerCase();
		return ext;
	}
}
