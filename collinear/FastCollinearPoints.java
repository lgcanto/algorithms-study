import java.util.Arrays;

public class FastCollinearPoints {
   private int numberOfSegments = 0;
   private LineSegment[] lineSegments;

   public FastCollinearPoints(Point[] points) {  // finds all line segments containing 4 or more points
      checkPoints(points);
      Point[] sortedPoints = points.clone();
      for (int i = 0; i < points.length; i++) {
         Point aPoint = points[i];
         checkPoint(aPoint);

         Point[] beginEndPoint = { aPoint, null };
         double collinearSlope = 0;
         boolean firstSlope = true;
         int collinearPointsCounter = 1;

         Arrays.sort(sortedPoints, aPoint.slopeOrder());
         for (int j = 1; j < sortedPoints.length; j++) {
            Point bPoint = sortedPoints[j];
            double currentSlope = aPoint.slopeTo(bPoint);
            checkSlope(currentSlope);
            if (firstSlope || Double.compare(collinearSlope, currentSlope) == 0) {
               firstSlope = false;
               collinearSlope = currentSlope;
               collinearPointsCounter++;

               beginEndPoint = getNewBeginEndPoint(beginEndPoint, bPoint);

               if (collinearPointsCounter > 3 && j == (sortedPoints.length - 1)) {
                  insertNewLineSegment(beginEndPoint);
               }
            }
            else {
               if (collinearPointsCounter > 3) {
                  insertNewLineSegment(beginEndPoint);
               }
               collinearSlope = currentSlope;
               collinearPointsCounter = 2;
               beginEndPoint = getNewBeginEndPoint(new Point[]{aPoint, null}, bPoint);
            }
         }
         sortedPoints = removePointFromArray(sortedPoints, aPoint);
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
         return new LineSegment[0];
      }
   }

   private Point[] removePointFromArray(Point[] points, Point point) {
      Point[] newPoints = new Point[points.length - 1];
      int j = 0;
      for (int i = 0; i < newPoints.length; i++)
      {
         if (point.compareTo(points[j]) == 0) {
            j++;
         }
         newPoints[i] = points[j];
         j++;
      }
      return newPoints;
   }

   private void insertNewLineSegment(Point[] beginEndPoint) {
      LineSegment lineSegment = new LineSegment(beginEndPoint[0], beginEndPoint[1]);
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

   private void checkSlope(double slope) {
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