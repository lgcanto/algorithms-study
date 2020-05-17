public class BruteCollinearPoints {
   private int numberOfSegments = 0;
   private LineSegment[] lineSegments;
   private boolean[] isSegmentPoint;

   public BruteCollinearPoints(Point[] points) {  // finds all line segments containing 4 points
      if (points == null ) {
         throw new IllegalArgumentException();
      }

      isSegmentPoint = new boolean[points.length];

      for (int i = 1; i < points.length; i++) {
         Point aPoint = points[i];
         checkPoint(aPoint);
         if (isSegmentPoint[i]) {
            continue;
         }

         Point[] beginEndPoint = new Point[]{aPoint, null};
         Double collinearSlope = null;
         int[] collinearPointIndexes = new int[4];
         collinearPointIndexes[0] = i;
         int collinearPointsCounter = 1;

         for (int j = i + 1; j < points.length; j++) {
            Point bPoint = points[j];
            if (isSegmentPoint[j]) {
               continue;
            }

            Double currentSlope = aPoint.slopeTo(bPoint);
            if (currentSlope == Double.NEGATIVE_INFINITY) {
               throw new IllegalArgumentException();
            }

            if (collinearSlope == null || collinearSlope.equals(currentSlope)) {
               collinearSlope = currentSlope;
               collinearPointsCounter++;
               collinearPointIndexes[collinearPointsCounter - 1] = j;

               beginEndPoint = getNewBeginEndPoint(beginEndPoint, bPoint);

               if (collinearPointsCounter == 4) {
                  addSegmentPoints(collinearPointIndexes);
                  insertNewLineSegment(beginEndPoint[0], beginEndPoint[1]);
                  numberOfSegments++;
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

   private Point[] getNewBeginEndPoint(Point[] beginEndPoint, Point newPoint) {
      Point beginPoint = beginEndPoint[0];
      Point endPoint = beginEndPoint[1];
      Point newBeginPoint = null;
      Point newEndPoint = null;
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

   private void addSegmentPoints(int[] indexes) {
      for (int index : indexes) {
         isSegmentPoint[index] = true;
      }
   }

   private void checkPoint(Point point) {
      if (point == null ) {
         throw new IllegalArgumentException();
      }
   }
}