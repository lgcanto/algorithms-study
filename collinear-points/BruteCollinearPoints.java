public class BruteCollinearPoints {
   private int numberOfSegments = 0;
   private LineSegment[] lineSegments;
   private Point[] scannedPoints;

   public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
      if (points == null ) {
         throw new IllegalArgumentException();
      }

      scannedPoints = new Point[points.length];

      for (int i = 1; i < points.length; i++) {
         Point aPoint = points[i];

         if(isPointAlreadyCollinear(points[i])) {
            continue;
         }

         Point beginPoint = aPoint;
         Point endPoint = null;
         Double[] slopes = new Double[3];
         int slopeCounter = 0;

         for (int j = i + 1; j < points.length; j++) {
            Point bPoint = points[j];
            if(isPointAlreadyCollinear(bPoint)) {
               continue;
            }

            Double currentSlope = aPoint.slopeTo(bPoint);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
               throw new IllegalArgumentException();
            }

            if (slopeCounter > 0 && slopes[slopeCounter-1] != slopes[slopeCounter]) {
               continue;
            }
            else {
               slopes[slopeCounter] = currentSlope;
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

               if (slopeCounter == 3) {
                  insertNewLineSegment(beginPoint, endPoint);
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

   private boolean isPointAlreadyCollinear(Point point) {
      if (point == null ) {
         throw new IllegalArgumentException();
      }

      for (int i = 0; i < scannedPoints.length; i++) {
         if (point.equals(scannedPoints[i])) {
            return true;
         }
      }
      return false;
   }
}