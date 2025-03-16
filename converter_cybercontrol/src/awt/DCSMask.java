// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DCSMask.java

package awt;

import java.util.Hashtable;

public class DCSMask
{

	public String name;
	public int value;
	public int mask;
	public static Hashtable<String, DCSMask> maskTable;
	public static Hashtable<String, DCSMask> transMaskTable;
	public DCSMask(String s, int v, int m)
	{
		name = s;
		value = v;
		mask = m;
	}

	public String getName()
	{
		return name;
	}

	public int getValue()
	{
		return value;
	}

	public int getMask()
	{
		return mask;
	}

	public String toString()
	{
		return name;
	}

	public static DCSMask getMaskByName(String sm)
	{
		return (DCSMask)maskTable.get(sm);
	}

	public static boolean isAMask(String sm)
	{
		boolean flag;
		if (maskTable.get(sm) == null)
			flag = false;
		else
			flag = true;
		return flag;
	}

	static 
	{
		maskTable = new Hashtable<String, DCSMask>();
		transMaskTable = new Hashtable<String, DCSMask>();
		String onMask;
		for (int i = 0; i < 32; i++)
		{
			int mask = 1 << i;
			onMask = "ON" + i;
			String offMask = "OFF" + i;
			maskTable.put(onMask, new DCSMask(onMask, mask, mask));
			transMaskTable.put(offMask, new DCSMask(onMask, mask, mask));
			maskTable.put(offMask, new DCSMask(offMask, 0, mask));
			transMaskTable.put(onMask, new DCSMask(offMask, mask, mask));
		}

		int mask = 1;
		onMask = "ON";
		maskTable.put(onMask, new DCSMask("SET", 1, 32769));
		transMaskTable.put(onMask, new DCSMask("RESET", 0, 32769));
		onMask = "OFF";
		maskTable.put(onMask, new DCSMask("RESET", 0, 32769));
		transMaskTable.put(onMask, new DCSMask("SET", 1, 32769));
		
		
		transMaskTable.put("RESET", new DCSMask("SET", 1, 32769));
		transMaskTable.put("SET", new DCSMask("RESET", 0, 32769));
		
		maskTable.put("HDWRFAIL", new DCSMask("HDWRFAIL", 32768, 32768));
		maskTable.put("BAD", new DCSMask("BAD", 768, 33536));
		maskTable.put("POOR", new DCSMask("POOR", 512, 33536));
		maskTable.put("FAIR", new DCSMask("FAIR", 256, 33536));
		maskTable.put("GOOD", new DCSMask("GOOD", 0, 33536));
		maskTable.put("NORMAL", new DCSMask("NORMAL", 0, 32896));
		maskTable.put("ALARM", new DCSMask("ALARM", 128, 32896));
		maskTable.put("ALARMACK", new DCSMask("ALARMACK", 32, 32800));
		maskTable.put("ALARMOFF", new DCSMask("ALARMOFF", 8192, 40960));
		maskTable.put("CUTOUT", new DCSMask("CUTOUT", 64, 32832));
		maskTable.put("SCANOFF", new DCSMask("SCANOFF", 2048, 34816));
		maskTable.put("ENTERVALUE", new DCSMask("ENTERVALUE", 1024, 33792));
		maskTable.put("DROPALM", new DCSMask("DROPALM", 128, 128));
		maskTable.put("DROPCLEAR", new DCSMask("DROPCLEAR", 0, 128));
		maskTable.put("DROPFAULT", new DCSMask("DROPFAULT", 64, 64));
		maskTable.put("MCB0OFFLIN", new DCSMask("MCB0OFFLIN", 256, 256));
		maskTable.put("MCB1OFFLIN", new DCSMask("MCB1OFFLIN", 512, 512));
		maskTable.put("UPDATETIME", new DCSMask("UPDATETIME", 1024, 1024));
		maskTable.put("OPATTN", new DCSMask("OPATTN", 16, 16));
		maskTable.put("LIMITOFF", new DCSMask("LIMITOFF", 4096, 36864));
		maskTable.put("HIGHALARM", new DCSMask("HIGHALARM", 136, 32908));
		maskTable.put("LOWALARM", new DCSMask("LOWALARM", 132, 32908));
		maskTable.put("SENSORALM", new DCSMask("SENSORALM", 140, 32908));
		maskTable.put("SENSORMODE", new DCSMask("SENSORMODE", 16, 32784));
		maskTable.put("BETTER", new DCSMask("BETTER", 1, 32771));
		maskTable.put("WORSE", new DCSMask("WORSE", 2, 32771));
		maskTable.put("RESET", new DCSMask("RESET", 0, 32769));
		maskTable.put("SET", new DCSMask("SET", 1, 32769));
		maskTable.put("SETALM", new DCSMask("SETALM", 2, 32770));
		maskTable.put("RESETALM", new DCSMask("RESETALM", 0, 32770));
		maskTable.put("TOGGLE", new DCSMask("TOGGLE", 16384, 49152));
	}

	public static DCSMask getTransMaskByName(String string) {
		return (DCSMask)transMaskTable.get(string);
	}
}
