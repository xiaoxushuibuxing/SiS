package magus.util;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class ColorParser
{
  static Hashtable colorTable = new Hashtable();
  
  static
  {
    colorTable.put("white", Color.white);
    colorTable.put("black", new Color(0, 0, 0));
    colorTable.put("red", Color.red);
    colorTable.put("DarkRed", new Color(139, 0, 0));
    colorTable.put("green", Color.green);
    colorTable.put("SpringGreen", new Color(0, 255, 127));
    colorTable.put("DarkGreen", new Color(0, 100, 0));
    colorTable.put("turquoise", new Color(64, 224, 208));
    colorTable.put("aquamarine", new Color(127, 255, 212));
    colorTable.put("blue", Color.blue);
    colorTable.put("DarkBlue", new Color(0, 0, 139));
    colorTable.put("navy", new Color(0, 0, 128));
    colorTable.put("RoyalBlue", new Color(65, 105, 225));
    colorTable.put("sky", new Color(135, 206, 235));
    colorTable.put("LightBlue", new Color(173, 216, 230));
    colorTable.put("cyan", Color.cyan);
    colorTable.put("LightCyan", new Color(224, 255, 255));
    colorTable.put("DarkCyan", new Color(0, 139, 139));
    colorTable.put("lightGray", Color.lightGray);
    colorTable.put("DimGray", new Color(105, 105, 105));
    colorTable.put("SlateGray", new Color(112, 128, 144));
    colorTable.put("gray", Color.gray);
    colorTable.put("darkGray", Color.darkGray);
    colorTable.put("DarkSlateGrey", new Color(47, 79, 79));
    colorTable.put("magenta", Color.magenta);
    colorTable.put("orange", Color.orange);
    colorTable.put("tomato", new Color(255, 99, 71));
    colorTable.put("OrangeRed", new Color(255, 69, 0));
    colorTable.put("LightPink", new Color(255, 182, 193));
    colorTable.put("pink", Color.pink);
    colorTable.put("DeepPink", new Color(255, 20, 147));
    colorTable.put("yellow", Color.yellow);
    colorTable.put("LightYellow", new Color(255, 255, 224));
    colorTable.put("gold", new Color(255, 215, 0));
    colorTable.put("goldenrod", new Color(218, 165, 32));
    colorTable.put("ivory", new Color(255, 255, 240));
    colorTable.put("snow", new Color(255, 250, 250));
    colorTable.put("linen", new Color(250, 240, 230));
    colorTable.put("bisque", new Color(255, 228, 196));
    colorTable.put("RosyBrown", new Color(188, 143, 143));
    colorTable.put("IndianRed", new Color(205, 92, 92));
    colorTable.put("sienna", new Color(160, 82, 45));
    colorTable.put("wheat", new Color(245, 222, 179));
    colorTable.put("beige", new Color(245, 245, 220));
    colorTable.put("tan", new Color(210, 180, 140));
    colorTable.put("chocolate", new Color(210, 105, 30));
    colorTable.put("brown", new Color(165, 42, 42));
    colorTable.put("salmon", new Color(250, 128, 114));
    colorTable.put("violet", new Color(238, 130, 238));
    colorTable.put("VioletRed", new Color(208, 32, 144));
    colorTable.put("orchid", new Color(218, 112, 214));
    colorTable.put("purple", new Color(160, 32, 240));
    colorTable.put("chartreuse", new Color(127, 255, 0));
    colorTable.put("LimeGreen", new Color(50, 205, 50));
    colorTable.put("YellowGreen", new Color(154, 205, 50));
    colorTable.put("ForestGreen", new Color(34, 139, 34));
    colorTable.put("plum", new Color(221, 160, 221));
    colorTable.put("CDMSBlue", new Color(0, 0, 180));
    colorTable.put("CDMSGreen", new Color(0, 180, 0));
    colorTable.put("CDMSCyan", new Color(0, 169, 169));
  }
  
  public static Vector getColorNames()
  {
    Vector v = new Vector();
    for (Enumeration e = colorTable.keys(); e.hasMoreElements();)
    {
      String s = (String)e.nextElement();
      v.addElement(s);
    }
    return v;
  }
  
  public static Color getColor(String cname)
  {
    for (Enumeration e = colorTable.keys(); e.hasMoreElements();)
    {
      String s = (String)e.nextElement();
      if (s.equalsIgnoreCase(cname)) {
        return (Color)colorTable.get(s);
      }
    }
    try
    {
    	if (cname.startsWith("##")) {
    		cname.replaceAll("##", "#");
		}
      return Color.decode(cname);
    }
    catch (NumberFormatException ne) { ne.printStackTrace();}
    return Color.black;
  }
  
  public static String toString(Color c1)
  {
    for (Enumeration e = colorTable.keys(); e.hasMoreElements();)
    {
      String s = (String)e.nextElement();
      Color c = (Color)colorTable.get(s);
      if (c.equals(c1)) {
        return s;
      }
    }
    int rgb = c1.getRGB();
    if (rgb ==0) {
		return "black";
	}
    String s = Integer.toHexString(rgb);
    StringBuilder sb = new StringBuilder(s);
    int length = s.length();
    while (length<8) {
    	sb.insert(0, "0");
    	length = sb.length();
    	
    }
    s =sb.toString();
    s = s.substring(2);
    s = "#" + s;
    return s;
  }
  
  public static Color brighter(Color c)
  {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    r += 50;
    g += 50;
    b += 50;
    if (r > 255) {
      r = 255;
    }
    if (g > 255) {
      g = 255;
    }
    if (b > 255) {
      b = 255;
    }
    return new Color(r, g, b);
  }
  
  public static Color darker(Color c)
  {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    r -= 50;
    g -= 50;
    b -= 50;
    if (r < 0) {
      r = 0;
    }
    if (g < 0) {
      g = 0;
    }
    if (b < 0) {
      b = 0;
    }
    return new Color(r, g, b);
  }
  
  public static Color XORColor(Color c)
  {
    int r = c.getRed();
    int g = c.getGreen();
    int b = c.getBlue();
    
    return new Color(255 - r, 255 - g, 255 - b);
  }
  
  public static void main(String[] argv)
  {
    System.out.println("Darkgray: " + toString(new Color(0, 0, 0)));
    System.out.println("Total color: " + colorTable.size());
    String cname = "#0";
  }
}
