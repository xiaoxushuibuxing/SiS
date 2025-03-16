package magus.util;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D.Float;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class ArcUtils {

	private static List<Point2D.Float> points;
	private static Logger logger = Logger.getLogger(ArcUtils.class);
	public static short[] getAngle(List<Double> arcPoints,Point center) {
		// 计算角度和弧度
		short [] angle = new short[2];
		double[] angles = calculateAngles(arcPoints, center);
		double startAngle = angles[0];
		double endAngle = angles[1];
		double angleDifference = calculateAngleDifference(startAngle, endAngle);

		// 输出结果
		//logger.error("Start Angle (radians): " + startAngle);
		//logger.error("End Angle (radians): " + endAngle);
		//logger.error("Angle Difference (radians): " + angleDifference);
		angle[0]= (short) startAngle;
		angle[1] = (short) angleDifference;
        return angle;
    }

	public static double[] calculateAngles(List<Point2D.Double> arcPoints, Point center) {
		double minAngle = java.lang.Double.MAX_VALUE;
		double maxAngle = java.lang.Double.MIN_VALUE;

		for (Point2D.Double point : arcPoints) {
			double dx = point.getX() - center.getX();
			double dy = point.getY() - center.getY();
			double angle = Math.atan2(dy, dx);

			if (angle < minAngle) {
				minAngle = angle;
			}
			if (angle > maxAngle) {
				maxAngle = angle;
			}
		}

		return new double[]{minAngle, maxAngle};
	}

	public static double calculateAngleDifference(double startAngle, double endAngle) {
		double angleDifference = endAngle - startAngle;
		if (angleDifference < 0) {
			angleDifference += 2 * Math.PI;
		}
		return angleDifference;
	}

	// 其他方法保持不变
	public static List<Point2D.Float> change2Points(List<Point2D.Double> arcPoints, Rectangle rectangle) {
		points = null;
		if (arcPoints == null || arcPoints.size() == 0) {
			return null;
		}
		for (int i = 0; i < arcPoints.size(); i++) {
			if (points == null) {
				points = new ArrayList<Point2D.Float>();
			}
			Point2D.Float point = new Point2D.Float();
			point.x = (float) (arcPoints.get(i).getX() * 16000 / rectangle.width);
			point.y = (float) (arcPoints.get(i).getY() * 16000 / rectangle.height);
			points.add(point);
		}
		return points;
	}

	public static Rectangle getBounds(List<Point2D.Double> arcPoints, Rectangle rectangle) {
		if (arcPoints == null || arcPoints.size() == 0) {
			return null;
		}
		int[] x = new int[arcPoints.size()];
		int[] y = new int[arcPoints.size()];
		for (int i = 0; i < arcPoints.size(); i++) {
			x[i] = (int) (arcPoints.get(i).getX() * 16000 / rectangle.width);
			y[i] = (int) (arcPoints.get(i).getY() * 16000 / rectangle.height);
		}
		Polygon p = new Polygon(x, y, arcPoints.size());
		return p.getBounds();
	}

	public static Point getCenter(List<Point2D.Double> arcPoints, Rectangle rectangle) {
		if (arcPoints == null || arcPoints.size() == 0) {
			return null;
		}
		int[] x = new int[arcPoints.size()];
		int[] y = new int[arcPoints.size()];
		for (int i = 0; i < arcPoints.size(); i++) {
			x[i] = (int) (arcPoints.get(i).getX() * 16000 / rectangle.width);
			y[i] = (int) (arcPoints.get(i).getY() * 16000 / rectangle.height);
		}
		Polygon p = new Polygon(x, y, arcPoints.size());
		Rectangle bounds = p.getBounds();
		return new Point(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
	}
}
