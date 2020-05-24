public class BruteCollinearPoints {
   private int numberOfSegments = 0;
   private LineSegment[] lineSegments = new LineSegment[0];

   public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
      checkPoints(points);

      for (int a = 0; a < points.length; a++) {
         Point aPoint = points[a];
         checkPoint(aPoint);
         for (int b = 0; b < points.length; b++) {
            if (b == a) {
               continue;
            }
            Point bPoint = points[b];
            checkPoint(bPoint);
            double abSlope = aPoint.slopeTo(bPoint);
            checkSlope(abSlope);
            for (int c = 0; c < points.length; c++) {
               if (c == a || c == b) {
                  continue;
               }
               Point cPoint = points[c];
               checkPoint(cPoint);
               double bcSlope = bPoint.slopeTo(cPoint);
               checkSlope(abSlope);
               if (Double.compare(abSlope, bcSlope) == 0) {
                  for (int d = 0; d < points.length; d++) {
                     if (d == a || d == b || d == c) {
                        continue;
                     }
                     Point dPoint = points[d];
                     checkPoint(dPoint);
                     double cdSlope = cPoint.slopeTo(dPoint);
                     checkSlope(cdSlope);
                     if (Double.compare(bcSlope, cdSlope) == 0) {
                        Point [] collinearPoints = {aPoint, bPoint, cPoint, dPoint};
                        LineSegment lineSegment = new LineSegment(getBeginPoint(collinearPoints), getEndPoint(collinearPoints));
                        if (!lineSegmentAlreadyExists(lineSegment)) {
                           insertNewLineSegment(lineSegment);
                        }
                     }
                  }
               }
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
         return new LineSegment[0];
      }
   }

   private boolean lineSegmentAlreadyExists(LineSegment lineSegment) {
      for (int i = 0; i < lineSegments.length; i++)
      {
         if (lineSegment.toString().equals(lineSegments[i].toString())) {
            return true;
         }
      }
      return false;
   }

   private void insertNewLineSegment(LineSegment lineSegment) {
      numberOfSegments++;
      LineSegment[] newLineSegments = new LineSegment[numberOfSegments];
      for (int i = 0; i < lineSegments.length; i++) {
         newLineSegments[i] = lineSegments[i];
      }
      newLineSegments[numberOfSegments - 1] = lineSegment;
      lineSegments = newLineSegments;
   }

   private Point getBeginPoint(Point[] points) {
      Point beginPoint = points[0];
      for (int i = 1; i < points.length; i++)
      {
         if (beginPoint.compareTo(points[i]) > 0) {
            beginPoint = points[i];
         }
         else if (beginPoint.compareTo(points[i]) == 0) {
            throw new IllegalArgumentException();
         }
      }
      return beginPoint;
   }

   private Point getEndPoint(Point[] points) {
      Point endPoint = points[0];
      for (int i = 1; i < points.length; i++)
      {
         if (endPoint.compareTo(points[i]) < 0) {
            endPoint = points[i];
         }
         else if (endPoint.compareTo(points[i]) == 0) {
            throw new IllegalArgumentException();
         }
      }
      return endPoint;
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