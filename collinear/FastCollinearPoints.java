import java.util.Arrays;

public class FastCollinearPoints {
   private int numberOfSegments = 0;
   private LineSegment[] lineSegments;

   public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
      checkPoints(points);

      for (int i = 0; i < points.length; i++) {
         Point aPoint = points[i];
         checkPoint(aPoint);

         Point[] beginEndPoint = { aPoint, null };
         Double collinearSlope = null;
         int collinearPointsCounter = 1;

         Arrays.sort(points, aPoint.slopeOrder());
         for (int j = 1; j < points.length; j++) {
            Point bPoint = points[j];
            Double currentSlope = aPoint.slopeTo(bPoint);
            checkSlope(currentSlope);
            if (collinearSlope == null || collinearSlope.equals(currentSlope)) {
               collinearSlope = currentSlope;
               collinearPointsCounter++;

               beginEndPoint = getNewBeginEndPoint(beginEndPoint, bPoint);
            }
            else if (collinearPointsCounter > 3) {
               insertNewLineSegment(beginEndPoint[0], beginEndPoint[1]);
               break;
            }
            else {
               collinearSlope = currentSlope;
               collinearPointsCounter = 2;
               beginEndPoint = getNewBeginEndPoint(new Point[]{aPoint, null}, bPoint);
            }
         }
      }
   }

   public int numberOfSegments() {      // the number of line segments
      return numberOfSegments;
   }

   public LineSegment[] segments() {              // the line segments
      if (lineSegments != null) {
         return lineSegments.clone();
      }
      else {
         return null;
      }
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

   private Point[] getNewBeginEndPoint(Point[] beginEndPoint, Point newPoint) {
      Point beginPoint = beginEndPoint[0];
      Point endPoint = beginEndPoint[1];
      Point newBeginPoint = beginPoint;
      Point newEndPoint = endPoint;
      if (beginPoint.compareTo(newPoint) > 0) {
         if (endPoint == null) {
            newEndPoint = beginPoint;
         }
         newBeginPoint = newPoint;
      }
      else if (beginPoint.compareTo(newPoint) == 0) {
         throw new IllegalArgumentException();
      }
      else if (endPoint == null || newPoint.compareTo(endPoint) > 0) {
         newEndPoint = newPoint;
      }
      else if (newPoint.compareTo(endPoint) == 0) {
         throw new IllegalArgumentException();
      }
      return new Point[]{newBeginPoint, newEndPoint};
   }

   private void checkSlope(Double slope) {
      if (slope == Double.NEGATIVE_INFINITY) {
         throw new IllegalArgumentException();
      }
   }

   private void checkPoint(Point point) {
      if (point == null) {
         throw new IllegalArgumentException();
      }
   }

   private void checkPoints(Point[] points) {
      if (points == null) {
         throw new IllegalArgumentException();
      }
   }
}