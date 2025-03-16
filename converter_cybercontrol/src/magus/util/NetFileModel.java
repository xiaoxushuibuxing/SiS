// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NetFileModel.java

package magus.util;

import java.util.Vector;
import javax.swing.table.AbstractTableModel;

public class NetFileModel extends AbstractTableModel
{

	private String name;
	private int column;
	private String columnNames[];
	private Vector data;

	public NetFileModel(String s, int col)
	{
		name = s;
		column = col;
		columnNames = new String[column];
		data = new Vector();
	}

	public String toString()
	{
		return name;
	}

	public void setData(Vector v)
	{
		data.removeAllElements();
		data = v;
	}

	public void add(String dataStr[])
	{
		data.add(dataStr);
	}

	public void remove(int i)
	{
		data.remove(i);
	}

	public void clear()
	{
		data.clear();
	}

	public int getColumnCount()
	{
		return column;
	}

	public int getRowCount()
	{
		return data.size();
	}

	public Object getValueAt(int i, int j)
	{
		String item[] = (String[])data.elementAt(i);
		return item[j];
	}

	public void setColumnName(int i, String name)
	{
		columnNames[i] = name;
	}

	public String getColumnName(int i)
	{
		return columnNames[i];
	}

	public Class getColumnClass(int i)
	{
		return getValueAt(0, i).getClass();
	}

	public boolean isCellEditable(int i, int j)
	{
		return false;
	}

	public void setValueAt(Object obj, int i, int j)
	{
		Object old = getValueAt(i, j);
		old = obj;
	}
}
