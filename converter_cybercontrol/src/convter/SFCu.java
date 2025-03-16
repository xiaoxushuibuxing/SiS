package convter;

import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import magus.util.DriverUtils;
import org.apache.log4j.Logger;

import SfnUI.GUI.BaseDrawOb;
import SfnUI.GUI.cu;
import awt.DCSArc;
import awt.DCSGroup;
import awt.DCSLines;
import awt.DCSPolygon;
import magus.util.RegionUtil;

public class SFCu extends SFObject{

	private Rectangle rectangle;
	public boolean isLine;
	public boolean isPolygon;
	public boolean isArc;
	public DCSLines lines;
	public DCSPolygon polygon;
	public DCSArc arc;
	public DCSGroup group;
	public int objNum =0;
	Logger logger = Logger.getLogger(SFCu.class);
	//boolean left = false,up = false;
	private List<Point2D.Double> arcPoints;
	private List<Point2D.Double> polygonPoints;
	public boolean hasGroup;
	//private DriverUtils drawUtils;
	public boolean isRect;
	private Map<String, String> driverMap;
	private int version;
	private DriverUtils driverUtils;


	public SFCu(Rectangle rectangle, int version) {
		// TODO Auto-generated constructor stub
		this.version = version;
		this.rectangle =rectangle;
	}

	public int parse(BaseDrawOb baseDrawOb) {
		logger.info(baseDrawOb.GetDrawObType());
		SfnUI.GUI.cu cua = (SfnUI.GUI.cu) baseDrawOb;

		//System.out.println("cua.IsArc()"+cua.IsArc());


		//this.drawUtils = new DrawUtils().parse(cua);
		Area area = (Area) cua.ToShape(0d);
		if (area.isPolygonal()) {
			//System.out.println("多边形");
			if (parseAreaToArc2D(cua,area)) {
				this.group = null;
				this.isPolygon = true;
				logger.info("isPolygonal"+this.isPolygon);
				this.isLine = false;
				this.isArc = false;
				if (!parseAreaToPolygonPoints(cua,area)) {
					return -1;
				}
				SFPolygon sfPolygon = new SFPolygon(rectangle,version);

				//sfPolygon.setDriverMap(this.driverMap);
				if (sfPolygon.parse(cua, polygonPoints,version) >=0) {
					this.polygon = sfPolygon.polygon;
				}
			} else {
				return -1;
			}
		} else if(area.isRectangular())	 {
			logger.info("矩形");
			this.isRect = true;
			logger.info("isRect"+this.isRect);
		} else {
			if (!parseAreaToArc2D(cua,area)) {
				return -1;
			}
		}

		//boolean isSuccess = ;



		return 0;
	}

	private boolean parseAreaToArc2D(cu cua, Area area) {
		PathIterator iterator = area.getPathIterator(null);
		double [] coords = new double[6];
		int segment=0;
		List<Point2D.Double> allPoints = new ArrayList<>();
		while (!iterator.isDone()) {
			segment = iterator.currentSegment(coords);
			for (int i = 0; i < 6; i+=2) {
				if(segment == PathIterator.SEG_CUBICTO || segment == PathIterator.SEG_QUADTO) {

					if(arcPoints == null) {
						arcPoints = new ArrayList<Point2D.Double>();
					}
					Point2D.Double point = new Point2D.Double();
					point.setLocation(coords[i],coords[i+1]);
					if(!arcPoints.contains(point)) {
						arcPoints.add(point);
					}
					
				} else if (segment == PathIterator.SEG_LINETO && i <4) {

					if(polygonPoints == null) {
						polygonPoints = new ArrayList<Point2D.Double>();
					}
					Point2D.Double point = new Point2D.Double();
					point.setLocation(coords[i],coords[i+1]);

					if(!polygonPoints.contains(point)) {
						polygonPoints.add(point);
					}
					
				}
			}
			iterator.next();
		}
		
		if (arcPoints!= null && arcPoints.size() >2) {
			SFArc sfArc= new SFArc(rectangle,version);
			if (this.perDriverUtil != null) {
				sfArc.perDriverUtil = this.perDriverUtil;
			} else if (this.driverUtils != null) {
				sfArc.driverUtils = this.driverUtils;
			}
			if (sfArc.parse(cua,arcPoints,version)>=0) {
				logger.info("添加弧形");
				if (this.group == null) {
					this.group = new DCSGroup(version);
				}

				this.group.addObject(sfArc.arc);
			}
			arcPoints.clear();	
		}
		if (polygonPoints !=null && polygonPoints.size()>2) {

			SFPolygon polygon= new SFPolygon(rectangle, version);
			if (this.perDriverUtil != null) {
				polygon.perDriverUtil = this.perDriverUtil;
			} else if (this.driverUtils != null) {
				polygon.driverUtils = this.driverUtils;
			}
			if (polygon.parse(cua,polygonPoints, version)>=0 && !checkDomain(allPoints, polygonPoints)) {
				logger.info("添加多边形");
				if (this.group == null) {
					this.group = new DCSGroup();
				}
				//System.out.println(polygon.polygon.getBounds());
				this.group.addObject(polygon.polygon);
			}
			polygonPoints.clear();
			
		}
		return true;
	}
	private boolean parseAreaToPolygonPoints(cu cua, Area area) {
		PathIterator iterator = area.getPathIterator(null);
		double [] coords = new double[6];
		while (!iterator.isDone()) {

			int segment = iterator.currentSegment(coords);
			logger.info(segment);
			logger.info(coords[0]+"==="+coords[1]+"==="+coords[2]+"==="+coords[3]+"==="+coords[4]+"==="+coords[5]);
			logger.info((int)(coords[0]*16000/rectangle.width)+"==="+(int)(coords[1]*16000/rectangle.height)
					+"==="+(int)(coords[2]*16000/rectangle.width)+"==="+(int)(coords[3]*16000/rectangle.height)
					+"==="+(int)(coords[4]*16000/rectangle.width)+"==="+(int)(coords[5]*16000/rectangle.height));
			if (segment == PathIterator.SEG_LINETO || segment == PathIterator.SEG_MOVETO) {
				if(polygonPoints == null) {
					polygonPoints = new ArrayList<Point2D.Double>();
				}

				Point2D.Double point = new Point2D.Double();
				point.setLocation(coords[0],coords[1]);

				if(!polygonPoints.contains(point)) {
					polygonPoints.add(point);
				}
			}
			iterator.next();
		}
		return true;
	}
	private boolean checkDomain(List<Double> aPoints, List<Double> bPoints) {
		// TODO Auto-generated method stu
		if (aPoints == null || aPoints.size() ==0)
			return false;
		int i =0;
		for (Point2D.Double point : bPoints) {
			if(RegionUtil.IsPtInPoly(point,aPoints)) {
				continue;
			}else {
				i++;
			}
		}
		boolean result =(i == 0)?true:false;
		return result;
	}

	public void setDriverMap(Map<String, String> driverMap) {
		// TODO Auto-generated method stub
		this.driverMap = driverMap;

	}


	public void setDriverUtils(DriverUtils driverUtils) {
		this.driverUtils =driverUtils;
	}
}
