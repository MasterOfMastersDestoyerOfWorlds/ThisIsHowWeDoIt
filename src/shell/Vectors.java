package shell;




/**
 * This class is used to do distance and angle calculations between points in space
 */
public class Vectors {

	/**
	 * @param a
	 * @param b
	 * @param p
	 * @return returns the angle ABC
	 */
	public static double findAngleSegments(PointND a, PointND b, PointND p) {
		PointND BC = new PointND.Double(p.toVector(b).getCoordList());
		PointND AB = new PointND.Double(a.toVector(b).getCoordList());
		double magAB = magnitude(AB), magBC = magnitude(BC);
		double dot = findDotProduct(AB,BC);
		if(dot/(magAB*magBC) >= 1) {
			return 180;
		}
		return Math.acos(dot/(magAB*magBC))*180/Math.PI;
	}

	/**
	 * @param A
	 * @return the distance from the origin to A
	 */
	public static double magnitude(PointND A) {
		return A.distance(new PointND.Double());
	}

	/**
	 * @param AB
	 * @param BC
	 * @return the dot product of AB and BC
	 */
	public static double findDotProduct(PointND AB, PointND BC){
		double sum = 0;

		int length = Math.max(AB.getDim(), BC.getDim());
		
		for (int i = 0; i < length; i++) {
			
			double val;
			if(i >= AB.getDim()) {
				val = AB.getCoord(i)*0;
			}
			else if(i >= BC.getDim()) {
				val = BC.getCoord(i)*0;
			}
			else {
				val = AB.getCoord(i)*BC.getCoord(i);
			}
			sum += val;
		}
		return sum;
		
	}

	/**
	 * Calculates the change in distance when adding q into the path between currPoint and lastPoint.
	 *  First it calculates the the distance between currPoint and last Point equal to AC.
	 *  Then it calcualtes the distance from currPoint to q equal to AB and distance from q to lastPoint equal to BC
	 * @param lastPoint
	 * @param currPoint
	 * @param q 2d point in between lastPoint and currPoint
	 * @return AB + BC - AC
	 */
	public static double distanceChanged(PointND lastPoint, PointND currPoint, PointND q) {
		
		// i think this is wrong or is being used incorrectly because if q is embedded in the 
		// path already then you also have to consider the distance to its neighbors
		PointND AB = new PointND.Double(q.toVector(lastPoint).getCoordList());
		PointND AC = new PointND.Double(currPoint.toVector(lastPoint).getCoordList());
		PointND BC = new PointND.Double(q.toVector(currPoint).getCoordList());

		return magnitude(AB) + magnitude(BC) -magnitude(AC);
	}


	/*This is currently not used, but keeping for history and potential future use

	public static double getProjectionScalar(PointND v, PointND u) {
		// TODO Auto-generated method stub
		return findDotProduct(v, u)/Math.pow(magnitude(u), 2);
	}

	public static double distanceToSegment(PointND lastPoint, PointND currPoint, PointND q) {
		// this is a poor metric and should probably not be used for optimization
		PointND AC = new PointND.Double(q.getX()-lastPoint.getX(), q.getY()-lastPoint.getY());
		PointND AB = new PointND.Double(currPoint.getX()-lastPoint.getX(), currPoint.getY()-lastPoint.getY());
		double y1 =lastPoint.getY(), y2 = currPoint.getY(), y0 = q.getY(),
				x1 =lastPoint.getX(), x2 = currPoint.getX(), x0 =q.getX();
		double projScalar = getProjectionScalar(AC, AB);
		if(projScalar >= 1) {
			return currPoint.distance(q);
		}
		else if(projScalar <= 0) {
			return lastPoint.distance(q);
		}
		else {
			return Math.abs((y2 - y1)*x0 - (x2 - x1)*y0 + x2*y1 - y2*x1)/Math.sqrt(Math.pow((y2-y1), 2) + Math.pow((x2-x1), 2));
		}
	}
	*/

}