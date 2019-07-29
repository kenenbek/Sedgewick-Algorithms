import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;
    private LineSegment[] segments;
    private int size;
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) throw new java.lang.IllegalArgumentException();

        Arrays.sort(points);
        Point prev = new Point(-568, 2568);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null || prev.equals(points[i])) throw new java.lang.IllegalArgumentException();
            prev = points[i];
        }
        segments = new LineSegment[1];
        int a = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i+1; j < points.length; j++) {
                for (int k = j+1; k < points.length; k++) {
                    for (int l = k+1; l < points.length; l++) {
                        double s1 = points[i].slopeTo(points[j]);
                        double s2 = points[j].slopeTo(points[k]);
                        double s3 = points[k].slopeTo(points[l]);
                        if (s1 == s2 && s2 == s3) {
                            segments[a++] = new LineSegment(points[i], points[k]);
                            segments = resizeL(segments, a);
                        }
                    }
                }
                
            }
            
        }
    }

    private LineSegment[] resizeL(LineSegment[] intputArray, int len) {
        if (len >= intputArray.length){
            LineSegment[] newArray = new LineSegment[intputArray.length * 2];
            for (int i = 0; i < intputArray.length; i++) {
                newArray[i] = intputArray[i];
            }
            return newArray;
        } else return intputArray;
    }

    public           int numberOfSegments()        // the number of line segments
    {
        return segments.length;
    }
    public LineSegment[] segments()                // the line segments
    {
        return segments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
