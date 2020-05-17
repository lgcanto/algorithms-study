import java.util.Arrays;

public class FastCollinearPoints {
   private int numberOfSegments = 0;
   private LineSegment[] lineSegments;

   public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
      if (points == null ) {
         throw new IllegalArgumentException();
      }

      for (int i = 0; i < points.length; i++) {
         Point aPoint = points[i];
         checkNullPoint(aPoint);
         Point beginPoint = aPoint;
         Point endPoint = null;
         Double collinearSlope = null;
         int slopeCounter = 0;
         Point[] sortedPoints = points;
         Arrays.sort(sortedPoints, aPoint.slopeOrder());
         for (int j = 1; j < sortedPoints.length; j++) {
            Point bPoint = points[j];
            Double currentSlope = aPoint.slopeTo(bPoint);
            checkSlope(currentSlope);
            if (collinearSlope == currentSlope) {
               slopeCounter++;
               if (beginPoint.compareTo(bPoint) == 1) {
                  if (endPoint == null) {
                     endPoint = beginPoint;
                  }
                  beginPoint = bPoint;
               }
               else if (beginPoint.compareTo(bPoint) == 0) {
                  throw new IllegalArgumentException();
               }
               else if (endPoint == null || bPoint.compareTo(endPoint) == 1) {
                  endPoint = bPoint;
               }
               else if (bPoint.compareTo(endPoint) == 0) {
                  throw new IllegalArgumentException();
               }
            }
            else if (slopeCounter > 2) {
               insertNewLineSegment(beginPoint, endPoint);
               break;
            }
            else {
               collinearSlope = currentSlope;
               slopeCounter = 1;
               if (aPoint.compareTo(bPoint) == 1) {
                  beginPoint = bPoint;
                  endPoint = aPoint;
               }
               else {
                  beginPoint = aPoint;
                  endPoint = bPoint;
               }
            }
         }
      }
   }

   public int numberOfSegments() {      // the number of line segments
      return numberOfSegments;
   }

   public LineSegment[] segments() {              // the line segments
      return lineSegments;
   }

   private void insertNewLineSegment(Point beginPoint, Point endPoint) {
      LineSegment lineSegment = new LineSegment(beginPoint, endPoint);
      numberOfSegments++;
      if (lineSegments != null) {
         LineSegment[] newLineSegments = new LineSegment[numberOfSegments];
         for (int i = 0; i < lineSegments.length; i++) {
            newLineSegments[i] = lineSegments[i];
         }
         newLineSegments[numberOfSegments - 1] = lineSegment;
         lineSegments = newLineSegments;
      }
      else {
         lineSegments = new LineSegment[]{lineSegment};
      }
   }

   private void checkSlope(Double slope) {
      if (slope == Double.NEGATIVE_INFINITY) {
         throw new IllegalArgumentException();
      }
   }

   private void checkNullPoint(Point point) {
      if (point == null ) {
         throw new IllegalArgumentException();
      }
   }
}