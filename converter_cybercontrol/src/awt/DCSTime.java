package awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import magus.util.CoordinateTransform;

public class DCSTime
  extends AbstractDCSObject
{
  public static final byte OBJECT_TYPE = 9;
  SimpleDateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  short x;
  short y;
  byte fontType;
  Font font;
  short charWidth;
  short charHeight;
  byte overstrike = 0;
  DCSColor fgColor;
  DCSColor bgColor;
  DCSEvent dcsEvent = null;
  Rectangle bounds = null;
  static VectText vectText = null;
  static Numeral numeral = null;
  DCSDiagram parent;
  Font deriveFont;
  int vx;
  int vy;
  int biasCharWidth;
  int biasCharHeight;
  
  public DCSTime(DCSDiagram diagram)
  {
    this.parent = diagram;
  }
  
  public DCSTime(DCSDiagram diagram, short x1, short y1)
  {
    this.parent = diagram;
    this.x = x1;
    this.y = y1;
    this.fontType = 0;
    this.font = new Font("Dialog", 0, 12);
    this.fgColor = new DCSColor(Color.white);
    this.bgColor = new DCSColor(Color.black);
  }
  
  public void setAttributes(DCSColor fg, DCSColor bg, byte ft, Font fnt)
  {
    this.fgColor = fg;
    this.bgColor = bg;
    this.fontType = ft;
    this.font = fnt;
  }
  
  public void setAttributes(DCSColor fg, DCSColor bg, byte ft, short cw, short ch)
  {
    this.fgColor = fg;
    this.bgColor = bg;
    this.fontType = ft;
    this.charWidth = cw;
    this.charHeight = ch;
  }
  
  public byte getType()
  {
    return 9;
  }
  
  public DCSEvent getEvent()
  {
    return this.dcsEvent;
  }
  
  public Vector getProcessPoints()
  {
    Vector points = new Vector();
    Vector vfg = this.fgColor.getProcessPoints();
    if (vfg != null) {
      for (int i = 0; i < vfg.size(); i++)
      {
        String pname = (String)vfg.elementAt(i);
        if (!points.contains(pname)) {
          points.add(pname);
        }
      }
    }
    Vector vbg = this.bgColor.getProcessPoints();
    if (vbg != null) {
      for (int i = 0; i < vbg.size(); i++)
      {
        String pname = (String)vbg.elementAt(i);
        if (!points.contains(pname)) {
          points.add(pname);
        }
      }
    }
    return points;
  }
  
  public Rectangle getBounds()
  {
    return this.bounds;
  }
  
  public void createBounds(Dimension d, Graphics g)
  {
    String sv = getString();
    int len = sv.length();
    if (this.fontType == 0)
    {
      FontMetrics fontMetrics = g.getFontMetrics();
      int as = fontMetrics.getAscent();
      int ds = fontMetrics.getDescent();
      int cw = fontMetrics.charWidth('W');
      int ch = as + ds;
      int sw = fontMetrics.stringWidth(sv);
      as = CoordinateTransform.Y_ViewToStandard(d, as);
      cw = CoordinateTransform.X_ViewToStandard(d, cw);
      ch = CoordinateTransform.Y_ViewToStandard(d, ch);
      sw = CoordinateTransform.X_ViewToStandard(d, sw);
      this.bounds = new Rectangle(this.x, this.y - as, sw, ch);
    }
    else
    {
      this.bounds = new Rectangle(this.x, this.y, this.charWidth * len, this.charHeight);
    }
  }
  
  public boolean contains(Point p)
  {
    if (this.bounds == null) {
      return false;
    }
    return this.bounds.contains(p);
  }
  
  public String getString()
  {
    String s = "";
    

   // long srvtime = ((Object) this.awtContext.getOpDataProvider()).getDBServerTime();
    Time t = new Time(new Date().getTime());
    s = this.dateFmt.format(t);
    return s;
  }
  
  public void transform(Graphics g, Dimension d)
  {
    this.vx = CoordinateTransform.X_StandardToView(d, this.x);
    this.vy = CoordinateTransform.Y_StandardToView(d, this.y);
    if (this.fontType == 0)
    {
      AffineTransform AT = new AffineTransform();
      float t1 = 1.0F * d.width / this.parent.getWidth();
      float t2 = 1.0F * d.height / this.parent.getHeight();
      AT.setToScale(t1, t2);
      this.deriveFont = this.font.deriveFont(AT);
    }
    else
    {
      this.biasCharWidth = CoordinateTransform.X_StandardToView(d, this.charWidth);
      this.biasCharHeight = CoordinateTransform.Y_StandardToView(d, this.charHeight);
    }
  }
  
  public Shape getShape(Dimension d)
  {
    if (this.bounds == null) {
      return null;
    }
    int vx = CoordinateTransform.X_StandardToView(d, this.bounds.x);
    int vy = CoordinateTransform.Y_StandardToView(d, this.bounds.y);
    int vw = CoordinateTransform.X_StandardToView(d, this.bounds.width);
    int vh = CoordinateTransform.Y_StandardToView(d, this.bounds.height);
    Rectangle rect = new Rectangle(vx, vy, vw, vh);
    return rect;
  }
  
  public void draw(Graphics g, Dimension d)
  {
    String s = getString();
    Color fc = this.fgColor.getColor(this.awtContext.getOpDataProvider());
    Color bc = this.bgColor.getColor(this.awtContext.getOpDataProvider());
    if (this.fontType == 0) {
      draw_bitmap(g, d, s, fc, bc);
    } else if (this.fontType == 1) {
      draw_vect(g, d, s, fc, bc);
    } else if (this.fontType == 2) {
      draw_digit(g, d, s, fc, bc);
    }
  }
  
  public void draw_bitmap(Graphics g, Dimension d, String sv, Color fc, Color bc)
  {
    g.setFont(this.deriveFont);
    if (this.bounds == null) {
      createBounds(d, g);
    }
    FontMetrics fontMetrics = g.getFontMetrics();
    int as = fontMetrics.getAscent();
    int ds = fontMetrics.getDescent();
    int ch = as + ds;
    int cw = fontMetrics.charWidth('W');
    int sw = fontMetrics.stringWidth(sv);
    if (this.overstrike == 1)
    {
      g.setColor(bc);
      g.fillRect(this.vx, this.vy, sw, ch);
    }
    g.setColor(fc);
    g.drawString(sv, this.vx, this.vy + as);
  }
  
  public void draw_vect(Graphics g, Dimension d, String sv, Color fc, Color bc)
  {
    if (this.bounds == null) {
      createBounds(d, g);
    }
    int len = sv.length();
    if (this.overstrike == 1)
    {
      g.setColor(bc);
      g.fillRect(this.vx, this.vy, this.biasCharWidth * len, this.biasCharHeight);
    }
    g.setColor(fc);
    if (vectText == null) {
      vectText = new VectText();
    }
    vectText.draw(g, sv, this.vx, this.vy, this.biasCharWidth, this.biasCharHeight, 0);
  }
  
  public void draw_digit(Graphics g, Dimension d, String sv, Color fc, Color bc)
  {
    if (this.bounds == null) {
      createBounds(d, g);
    }
    int len = sv.length();
    if (this.overstrike == 1)
    {
      g.setColor(bc);
      g.fillRect(this.vx, this.vy, this.biasCharWidth * len, this.biasCharHeight);
    }
    g.setColor(fc);
    if (numeral == null) {
      numeral = new Numeral();
    }
    numeral.draw(g, sv, this.vx, this.vy, this.biasCharWidth, this.biasCharHeight);
  }
  
  public Object clone()
  {
    DCSTime dcsTime = new DCSTime(this.parent, this.x, this.y);
    if (this.fontType == 0) {
      dcsTime.setAttributes(this.fgColor, this.bgColor, this.fontType, this.font);
    } else {
      dcsTime.setAttributes(this.fgColor, this.bgColor, this.fontType, this.charWidth, this.charHeight);
    }
    if (this.bounds != null) {
      dcsTime.bounds = new Rectangle(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
    }
    return dcsTime;
  }
  
  public void write(DataOutputStream out)
    throws IOException
  {
    out.writeByte(9);
    out.writeUTF(this.dateFmt.toPattern());
    out.writeShort(this.x);
    out.writeShort(this.y);
    out.writeByte(this.overstrike);
    out.writeByte(this.fontType);
    if ((this.fontType == 1) || (this.fontType == 2))
    {
      out.writeShort(this.charWidth);
      out.writeShort(this.charHeight);
    }
    else
    {
      out.writeUTF(this.font.getFamily());
      out.writeInt(this.font.getStyle());
      out.writeInt(this.font.getSize());
    }
    this.fgColor.write(out);
    this.bgColor.write(out);
    if (this.dcsEvent == null)
    {
      out.writeByte(0);
    }
    else
    {
      out.writeByte(1);
      this.dcsEvent.write(out);
    }
  }
  
  public int read(DataInputStream in)
    throws IOException
  {
    String fmtstr = in.readUTF();
    try
    {
      this.dateFmt = new SimpleDateFormat(fmtstr);
    }
    catch (Exception e)
    {
      this.dateFmt = new SimpleDateFormat("yyyy-MM-dd");
    }
    this.x = in.readShort();
    this.y = in.readShort();
    this.overstrike = in.readByte();
    this.fontType = in.readByte();
    if ((this.fontType == 1) || (this.fontType == 2))
    {
      this.charWidth = in.readShort();
      this.charHeight = in.readShort();
    }
    else
    {
      this.font = new Font(in.readUTF(), in.readInt(), in.readInt());
    }
    this.fgColor = new DCSColor();
    this.fgColor.read(in);
    this.bgColor = new DCSColor();
    this.bgColor.read(in);
    if (in.readByte() == 1)
    {
      this.dcsEvent = new DCSEvent();
      this.dcsEvent.read(in);
    }
    return 0;
  }
  
  public void transform(Dimension d) {}
  
  public void offset(int x, int y, float width_zoom, float height_zoom) {}
  
  public void setBounds(short x1, short y1, short w1, short h1)
  {
    if ((this.fontType == 1) || (this.fontType == 2))
    {
      this.x = x1;
      this.y = y1;
      String sv = getString();
      int len = sv.length();
      this.charWidth = ((short)(int)(1.0F * w1 / len + 0.5F));
      this.charHeight = h1;
      this.bounds = new Rectangle(x1, y1, w1, h1);
    }
  }
  public void move(int paramInt1, int paramInt2)
  {
      this.x = ((short)(this.x + paramInt1));
      this.y = ((short)(this.y + paramInt2));
      if (this.bounds != null)
      {
          this.bounds.x = this.x;
          this.bounds.y = this.y;
      }
  }
}
