import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;

public class KdTree {
    private Node2D root;
    private int size;

    private class Node2D{
        private Node2D left;
        private Node2D right;
        private Point2D point;
        private RectHV rect;

    }
    public         KdTree() {                               // construct an empty set of points
        root = new Node2D();
    }

    public           boolean isEmpty() {                      // is the set empty?
        return root == null;
    }
    public               int size() {                         // number of points in the set
        return size;
    }
    public              void insert(Point2D p) {              // add the point to the set (if it is not already in the set)
        checkArgument(p);
        root = insert(root, p, true);
    }

    private Node2D insert(Node2D current, Point2D p, boolean odd){
        checkArgument(p);
        if (current == null) {
            Node2D newNode = new Node2D();
            newNode.point = p;
            return newNode;

        }

        double delimeter = (odd) ? current.point.y() : current.point.x();
        double pValue = (odd) ? p.y() : p.x();

        if (pValue < delimeter) {
            // go left
            current.left = insert(current.left, p, !odd);
        }else {
            // go right
            current.right = insert(current.right, p, !odd);
        }
        return current;
    }
    public           boolean contains(Point2D p) {            // does the set contain point p?
        boolean odd = false;
        Node2D current = root;

        while (current != null) {
            if (p.x() == current.point.x() && p.y() == current.point.y()){
                return true;
            }
            double delimeter = (odd) ? current.point.y() : current.point.x();
            double pValue = (odd) ? p.y() : p.x();

            if (pValue < delimeter) {
                // go left
                current = current.left;
            }else {
                // go right
                current.right = insert(current.right, p, !odd);
            }

            odd = !odd;
        }

        return false;
    }

    public              void draw() {                         // draw all points to standard draw
        // boolean odd = false;
        // Node2D current = root;
        //
        // while (current != null) {
        //
        //     current.point.draw();
        //     StdDraw.line();
        //
        //     if (pValue < delimeter) {
        //         // go left
        //         current = current.left;
        //     }else {
        //         // go right
        //         current.right = insert(current.right, p, !odd);
        //     }
        //
        //     odd = !odd;
        // }
    }

    public Iterable<Point2D> range(RectHV rect) {             // all points that are inside the rectangle (or on the boundary)
        checkArgument(rect);
        LinkedList<Point2D> list = new LinkedList<>();
        Node2D current = root;

        addToLinkedListWhileRanging(rect, root, list);
        return list;
    }

    private void addToLinkedListWhileRanging(RectHV rect, Node2D current, LinkedList<Point2D> list) {
        if (current == null) return;
        double x = current.point.x();
        double y = current.point.y();

        double x0 = rect.xmin();
        double y0 = rect.ymin();

        double x1 = rect.xmax();
        double y1 = rect.ymax();

        if (x0 <= x && x <= x1 && y0 <= y &&  y <= y1) {
            list.add(current.point);
        }

        if (current.left.rect.intersects(rect)){
            addToLinkedListWhileRanging(rect, current.left, list);
        }
        if (current.right.rect.intersects(rect)){
            addToLinkedListWhileRanging(rect, current.right, list);
        }

    }

    public Point2D nearest(Point2D p) {             // a nearest neighbor in the set to point p; null if the set is empty
        checkArgument(p);
        if (root == null) {
            return null;
        }
    }

    private Point2D find_nearest(Node2D current, Point2D p, double minDistance) {
        double curDistance =
        if (current.point.distanceTo(p) < minDistance){
            minDistance =
        }
    }

        private void checkArgument(Object obj){
        if (obj == null){
            throw new java.lang.IllegalArgumentException();
        }
    }
    public static void main(String[] args) {               // unit testing of the methods (optional)
    }
}
