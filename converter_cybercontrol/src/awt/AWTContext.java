// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AWTContext.java

package awt;

import java.awt.Color;
import java.io.IOException;
import java.util.Vector;
import javax.xml.parsers.*;
import magus.net.OPNetwork;
import magus.net.provide.OPDataProvider;
import magus.net.provide.OPNullDataProvider;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

// Referenced classes of package magus.awt:
//			UnitMenu

public class AWTContext
{
	

	private int LoadState;
	private static final int LoadState_CONNECT_OP_DB = 0;
	private static final int LoadState_LOAD_AWT_CONFIG = 1;
	private String homePath;
	private String awtConfigFilePath;
	private OPDataProvider opDataProvider;
	private static final OPNullDataProvider opNullDataProvider = new OPNullDataProvider();
	private OPNetwork opNetwork;
	private boolean IsAlarm;
	private Color AlarmColorHigh;
	private Color AlarmColorLow;
	private String mainPage;
	private String diagramPath;
	private String imagePath;
	private String xImagePath;
	private String glibPath;
	private Vector unitList;

	public AWTContext(String homePath, String awtConfigFilePath)
	{
		LoadState = -1;
		this.awtConfigFilePath = "/config/awt.xml";
		glibPath = null;
		unitList = new Vector();
		loading(homePath, awtConfigFilePath);
	}

	private boolean loading(String homePath, String awtConfigFilePath)
	{
		this.homePath = homePath;
		if (awtConfigFilePath != null)
			this.awtConfigFilePath = awtConfigFilePath;
		System.out.println("homePath: " + homePath + "\nawtConfigFilePath: " + this.awtConfigFilePath);
		try
		{
			System.out.println("���ڶ�ȡ ������ ...");
			LoadState = 1;
			loadMainConfig(homePath + this.awtConfigFilePath);
		}
		catch (ParserConfigurationException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (SAXException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;	
		}
		System.out.println("�������� Openplant ...");
		LoadState = 0;
		opNetwork = OPNetwork.getNetworkByURL(homePath);
		return opNetwork != null;
	}
	

	private void loadMainConfig(String filePath)
		throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document mfDocument = builder.parse(filePath);
		mfDocument.normalize();
		boolean autoConfig = true;
		Node n = mfDocument.getElementsByTagName("AutoConfig").item(0);
		if (n != null)
		{
			String s = n.getFirstChild().getNodeValue().trim();
			if (s.compareToIgnoreCase("true") == 0)
				autoConfig = true;
		}
		mainPage = mfDocument.getElementsByTagName("MainPage").item(0).getFirstChild().getNodeValue();
		if (!autoConfig)
		{
			diagramPath = mfDocument.getElementsByTagName("DiagramPath").item(0).getFirstChild().getNodeValue();
			imagePath = mfDocument.getElementsByTagName("ImagePath").item(0).getFirstChild().getNodeValue();
			xImagePath = mfDocument.getElementsByTagName("XImagePath").item(0).getFirstChild().getNodeValue();
			glibPath = mfDocument.getElementsByTagName("GLibPath").item(0).getFirstChild().getNodeValue();
			String IsAlarmStr = mfDocument.getElementsByTagName("IsAlarm").item(0).getFirstChild().getNodeValue();
			IsAlarm = IsAlarmStr.equalsIgnoreCase("true");
			if (IsAlarm)
			{
				String AlarmColorHighStr = mfDocument.getElementsByTagName("AlarmColorHigh").item(0).getFirstChild().getNodeValue();
				String AlarmColorLowStr = mfDocument.getElementsByTagName("AlarmColorLow").item(0).getFirstChild().getNodeValue();
				AlarmColorHigh = Color.decode(AlarmColorHighStr);
				AlarmColorLow = Color.decode(AlarmColorLowStr);
			}
		} else
		{
			String ophome = homePath;
			IsAlarm = false;
			int id = homePath.indexOf("/webapp");
			if (id > 0)
				ophome = homePath.substring(0, id);
			diagramPath = ophome + "/diagram/";
			imagePath = ophome + "/diagram/images/";
			xImagePath = ophome + "/lib/xbmp/";
			glibPath = ophome + "/lib/glib/";
		}
		NodeList nodes = mfDocument.getElementsByTagName("UnitMenu");
		if (nodes.getLength() > 0)
		{
			nodes = nodes.item(0).getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++)
			{
				Node node = nodes.item(i);
				if (node.getNodeType() == 1)
				{
					NamedNodeMap attributes = node.getAttributes();
					UnitMenu item = new UnitMenu();
					item.name = node.getNodeName();
					item.title = attributes.getNamedItem("title").getNodeValue();
					item.page = attributes.getNamedItem("page").getNodeValue();
					if (item.title.compareTo("") == 0)
						item.title = item.name;
					unitList.add(item);
				}
			}

		}
	}

	public Vector getUnitList()
	{
		return unitList;
	}

	public String getUnitTitle(String unitName)
	{
		for (int i = 0; i < unitList.size(); i++)
		{
			UnitMenu item = (UnitMenu)unitList.elementAt(i);
			if (item.name.compareToIgnoreCase(unitName) == 0)
				return item.title;
		}

		return null;
	}

	public String getUnitPage(String unitTitle)
	{
		for (int i = 0; i < unitList.size(); i++)
		{
			UnitMenu item = (UnitMenu)unitList.elementAt(i);
			if (item.title.compareToIgnoreCase(unitTitle) == 0)
				return item.page;
		}

		return null;
	}

	public String[] getAllUnitTitle()
	{
		int size = unitList.size();
		String allTitle[] = new String[size];
		for (int i = 0; i < size; i++)
		{
			UnitMenu item = (UnitMenu)unitList.elementAt(i);
			allTitle[i] = item.title;
		}

		return allTitle;
	}

	public String getMainPage()
	{
		return mainPage;
	}

	public String getHomePath()
	{
		return homePath;
	}

	public String getDiagramPath()
	{
		return diagramPath;
	}

	public String getImagePath()
	{
		return imagePath;
	}

	public String getXImagePath()
	{
		return xImagePath;
	}

	public String getGLibPath()
	{
		return glibPath;
	}

	public OPDataProvider getOpDataProvider()
	{
		if (opDataProvider == null)
			return opNullDataProvider;
		else
			return opDataProvider;
	}

	
	public void setOpDataProvider(OPDataProvider opDataProvider)
	{
		this.opDataProvider = opDataProvider;
	}

	public String getAwtConfigFilePath()
	{
		return awtConfigFilePath;
	}

	public void setAwtConfigFilePath(String awtConfigFilePath)
	{
		this.awtConfigFilePath = awtConfigFilePath;
	}

	public int getLoadState()
	{
		return LoadState;
	}

	public void setLoadState(int loadState)
	{
		LoadState = loadState;
	}

	public boolean isAlarm()
	{
		return IsAlarm;
	}

	public void setAlarm(boolean isAlarm)
	{
		IsAlarm = isAlarm;
	}

	public Color getAlarmColorHigh()
	{
		return AlarmColorHigh;
	}

	public void setAlarmColorHigh(Color alarmColorHigh)
	{
		AlarmColorHigh = alarmColorHigh;
	}

	public Color getAlarmColorLow()
	{
		return AlarmColorLow;
	}

	public void setAlarmColorLow(Color alarmColorLow)
	{
		AlarmColorLow = alarmColorLow;
	}

}
