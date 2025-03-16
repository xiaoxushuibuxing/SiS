package magus.util;

import java.awt.geom.Arc2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class ArcUtil {

    public static List<Arc2D> convertCurvesToArcs(PathIterator pathIterator) {
        List<Arc2D> arcs = new ArrayList<>();
        float[] coords = new float[6];
        Point2D lastPoint = null;

        while (!pathIterator.isDone()) {
            int segmentType = pathIterator.currentSegment(coords);
            switch (segmentType) {
                case PathIterator.SEG_MOVETO:
                    lastPoint = new Point2D.Float(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    if (lastPoint != null) {
                        arcs.add(convertQuadToArc(lastPoint, new Point2D.Float(coords[0], coords[1]), new Point2D.Float(coords[2], coords[3])));
                        lastPoint = new Point2D.Float(coords[2], coords[3]);
                    }
                    break;
                case PathIterator.SEG_CUBICTO:
                    if (lastPoint != null) {
                        arcs.addAll(convertCubicToArcs(lastPoint, new Point2D.Float(coords[0], coords[1]), new Point2D.Float(coords[2], coords[3]), new Point2D.Float(coords[4], coords[5])));
                        lastPoint = new Point2D.Float(coords[4], coords[5]);
                    }
                    break;
                default:
                    break;
            }
            pathIterator.next();
        }

        // 合并相邻的弧线
        List<Arc2D> mergedArcs = mergeArcs(arcs);
        return mergedArcs;
    }

    private static Arc2D convertQuadToArc(Point2D p0, Point2D p1, Point2D p2) {
        // 简单近似算法，实际应用中可能需要更复杂的算法
        double centerX = (p0.getX() + 2 * p1.getX() + p2.getX()) / 4;
        double centerY = (p0.getY() + 2 * p1.getY() + p2.getY()) / 4;
        double radius = p0.distance(new Point2D.Double(centerX, centerY));

        double startAngle = Math.toDegrees(Math.atan2(p0.getY() - centerY, p0.getX() - centerX));
        double endAngle = Math.toDegrees(Math.atan2(p2.getY() - centerY, p2.getX() - centerX));

        return new Arc2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius, startAngle, endAngle - startAngle, Arc2D.OPEN);
    }

    private static List<Arc2D> convertCubicToArcs(Point2D p0, Point2D p1, Point2D p2, Point2D p3) {
        List<Arc2D> arcs = new ArrayList<>();
        // 简单近似算法，实际应用中可能需要更复杂的算法
        // 这里将三次贝塞尔曲线分成两个二次贝塞尔曲线进行近似
        Point2D q1 = new Point2D.Float((float) ((p0.getX() + 2 * p1.getX()) / 3), (float) ((p0.getY() + 2 * p1.getY()) / 3));
        Point2D q2 = new Point2D.Float((float) ((p0.getX() + 3 * p1.getX() + p2.getX()) / 3), (float) ((p0.getY() + 3 * p1.getY() + p2.getY()) / 3));
        Point2D q3 = new Point2D.Float((float) ((2 * p2.getX() + p1.getX()) / 3), (float) ((2 * p2.getY() + p1.getY()) / 3));

        arcs.add(convertQuadToArc(p0, q1, q2));
        arcs.add(convertQuadToArc(q2, q3, p3));

        return arcs;
    }

    private static List<Arc2D> mergeArcs(List<Arc2D> arcs) {
        List<Arc2D> mergedArcs = new ArrayList<>();
        if (arcs.isEmpty()) {
            return mergedArcs;
        }

        Arc2D currentArc = arcs.get(0);
        for (int i = 1; i < arcs.size(); i++) {
            Arc2D nextArc = arcs.get(i);
            if (canMerge(currentArc, nextArc)) {
                currentArc = merge(currentArc, nextArc);
            } else {
                mergedArcs.add(currentArc);
                currentArc = nextArc;
            }
        }
        mergedArcs.add(currentArc);

        return mergedArcs;
    }

    private static boolean canMerge(Arc2D arc1, Arc2D arc2) {
        // 简单的合并条件，实际应用中可能需要更复杂的条件
        return arc1.getCenterX() == arc2.getCenterX() && arc1.getCenterY() == arc2.getCenterY() && arc1.getWidth() == arc2.getWidth() && arc1.getHeight() == arc2.getHeight();
    }

    private static Arc2D merge(Arc2D arc1, Arc2D arc2) {
        // 简单的合并逻辑，实际应用中可能需要更复杂的逻辑
        double startAngle = arc1.getAngleStart();
        double endAngle = arc2.getAngleStart() + arc2.getAngleExtent();
        return new Arc2D.Double(arc1.getX(), arc1.getY(), arc1.getWidth(), arc1.getHeight(), startAngle, endAngle - startAngle, Arc2D.OPEN);
    }

    public static void main(String[] args) {
        // 示例使用
        // 假设有一个 Path2D 对象 path
        // Path2D path = new Path2D.Double();
        // path.moveTo(0, 0);
        // path.quadTo(50, 50, 100, 0);
        // path.curveTo(150, 50, 200, 50, 250, 0);

        // PathIterator pathIterator = path.getPathIterator(null);
        // List<Arc2D> arcs = convertCurvesToArcs(pathIterator);
        // for (Arc2D arc : arcs) {
        //     System.out.println(arc);
        // }
    }
}