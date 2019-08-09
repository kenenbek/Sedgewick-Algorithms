import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.LinkedList;

public class PointSET {
    private SET<Point2D> set;
    public         PointSET() {                              // construct an empty set of points
        set = new SET<Point2D>();
    }
    public           boolean isEmpty() {                      // is the set empty?
        return set.isEmpty();
    }
    public               int size() {                         // number of points in the set
        return set.size();
    }
    public              void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        checkArgument(p);
        set.add(p);
    }

    public           boolean contains(Point2D p) {            // does the set contain point p?
        checkArgument(p);
        return set.contains(p);
    }
    public              void draw() {                         // draw all points to standard draw
        for (Point2D p: set) {
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        checkArgument(rect);
        LinkedList<Point2D> list = new LinkedList<>();

        double x1 = rect.xmin();
        double x2 = rect.xmax();
        double y1 = rect.ymin();
        double y2 = rect.ymax();

        for (Point2D p: set) {
            double px = p.x();
            double py = p.y();
            if (x1 <= px && px <= x2 && y1 <= py && py <= y2){
                list.add(p);
            }
        }
        return list;
    }
    public           Point2D nearest(Point2D p){             // a nearest neighbor in the set to point p; null if the set is empty
        checkArgument(p);
        if (size() == 0) return null;
        double minDistance = Double.POSITIVE_INFINITY;
        Point2D minPoint = null;
        for (Point2D myPoint: set) {
            double px = p.x();
            double py = p.y();
            double currentDistance = myPoint.distanceTo(p);
            if (currentDistance < minDistance){
                minDistance = currentDistance;
                minPoint = myPoint;
            }
        }
        return minPoint;
    }
    private void checkArgument(Object obj){
        if (obj == null){
            throw new java.lang.IllegalArgumentException();
        }
    }
    public static void main(String[] args)                  // unit testing of the methods (optional)
    {

    }
}
