import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;

public class FastCollinearPoints {
    private LineSegment[] segments;
    
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if (points == null) throw new java.lang.IllegalArgumentException();
        int N = points.length;
        Arrays.sort(points);
        Point prev = new Point(-568, 2568);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null || prev.equals(points[i])) throw new java.lang.IllegalArgumentException();
            prev = points[i];
        }
        segments = new LineSegment[1];

        int s = 0;
        for (int i = 0; i < points.length; i++) {
            Point p = points[i];
            Point[] sortedBySlope = points.clone();
            Arrays.sort(sortedBySlope, p.slopeOrder());

            int j = 1;
            while (j < N){
                double etalon = p.slopeTo(sortedBySlope[j]);
                Point[] candidates = new Point[1];

                int k = 0;
                do {
                    candidates[k++] = sortedBySlope[j++];
                    candidates = resizeP(candidates, k);

                } while (j < N && p.slopeTo(sortedBySlope[j]) == etalon);

                if (candidates.length >= 3){
                    segments[s++] = new LineSegment(candidates[0], candidates[candidates.length-1]);
                    segments = resizeL(segments, s);
                }
            }
        }
    }

    private Point[] resizeP(Point[] intputArray, int len) {
        if (len >= intputArray.length){
            Point[] newArray = new Point[intputArray.length * 2];
            for (int i = 0; i < intputArray.length; i++) {
                newArray[i] = intputArray[i];
            }
            return newArray;
        } else return intputArray;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}