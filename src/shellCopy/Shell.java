package shellCopy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.JComponent;


/**
 * This class represents a list of some points in the point set.
 * 	Initially each shell is a convex hull, but they are eventually combined together to form the optimal
 * 	tsp path and they lose their convex property
 */
public class Shell extends LinkedList<Point2D> {
	private static final long serialVersionUID = -5904334592585016845L;
	private int ORDER = 0;
	private boolean maximal, minimal;
	private Shell parent, child;
	private static PointSet ps;

	/**
	 * Initializes a new shell with
	 * @param parent
	 * @param child
	 * @param ps
	 */
	public Shell(Shell parent, Shell child, PointSet ps) {
		this.parent = parent;
		this.child = child;
		this.updateOrder();
		if (!this.isMaximal()) {
			parent.updateOrder();
		}
		this.ps = ps;
	}

	/**
	 * Get the length of the shell
	 * @return the length of the path between all points in the shell
	 */
	public double getLength() {
		Point2D first = null, last = null;
		double length = 0.0;
		for (Point2D p : this) {
			if (first == null) {
				last = p;
				first = p;
			} else {
				length += last.distance(p);
				last = p;
			}
		}
		length += last.distance(first);
		return length;

	}

	/**
	 * Draws the Shell and its children if drawChildren is true
	 * @param frame where to draw the shell
	 * @param g2 graphics object for frame
	 * @param colorSeed only used if color is set to null in order to get a random color for the Shell drawing
	 * @param drawChildren whether or not to draw child shells
	 * @param c the color to draw the shell (set to null to get a random color)
	 */
	public void drawShell(JComponent frame, Graphics2D g2, boolean drawChildren, Color c) {
		if (c == null) {
			Random colorSeed = new Random();
			Main.drawPath(frame, g2, shellToPath(this),
					new Color(colorSeed.nextFloat(), colorSeed.nextFloat(), colorSeed.nextFloat()), ps, true, false,
					false);
		} else {
			Main.drawPath(frame, g2, shellToPath(this), c, ps, true, false, false);
		}
		if (!this.isMinimal() && drawChildren) {
			child.drawShell(frame, g2, drawChildren, c);
		}
	}

	/**
	 * Finds the minimal shell of the pointset
	 * @return the central most shell that does not have any children
	 */
	public Shell getMinimalShell() {
		if (this.isMinimal()) {
			return this;
		} else {
			return child.getMinimalShell();
		}
	}

	/**
	 * Determines whether the current shell is the outermost shell
	 * @return true if current shell is the outermost shell otherwise false
	 */
	public boolean isMaximal() {
		return parent == null;
	}

	/**
	 * Determines whether the current shell is the innermost shell
	 * @return true if current shell is the innermost shell otherwise false
	 */
	public boolean isMinimal() {
		return child == null;
	}

	/**
	 * Gets the child shell of the current shell
	 * @return the shell immediately inside of the current shell
	 */
	public Shell getChild() {
		return child;
	}

	/**
	 * Updates the order of the current shell to reflect how many shells are inside of it
	 * @return the number of shells inside the current shell + 1
	 */
	public int updateOrder() {
		if (!this.isMinimal()) {
			this.ORDER = child.updateOrder() + 1;
		} else {
			this.ORDER = 1;
		}
		return this.ORDER;
	}

	/**
	 * Updates what shell is considered the child of this shell
	 * @param child new child shell of current shell
	 */
	public void setChild(Shell child) {
		this.child = child;
		minimal = false;
		this.updateOrder();
		if (parent != null) {
			parent.updateOrder();
		}
	}

	/**
	 * Gets the distance from a point to its neighboring points in the shell
	 * @param p
	 * @return the sum of the distance from p to the prev point in the shell and the distance from p to the next point in the shell
	 */
	public double distanceToNeighbors(Point2D p) {
		Point2D prevP = prevPoint(p), nextP = nextPoint(p);

		return p.distance(prevP.getX(), prevP.getY()) + p.distance(nextP.getX(), nextP.getY());

	}

	/**
	 * Gets the distance from the point previous to p and the point after p in the shell
	 * @param p
	 * @return the sum of the distance from  the prev point in the shell  to the next point in the shell
	 */
	public double distanceBetweenNeighbors(Point2D p) {
		Point2D prevP = prevPoint(p), nextP = nextPoint(p);

		return prevP.distance(nextP.getX(), nextP.getY());

	}

	/**
	 * Gets the distance from the point previous to p and the point after p on the line
	 * @param p
	 * @return the sum of the distance from  the prev point on the line  to the next point on the line
	 */
	public double distanceToNeighborsOnLine(Point2D p) {
		Point2D prevP = prevPointOnLine(p), nextP = nextPointOnLine(p);

		return p.distance(prevP.getX(), prevP.getY()) + p.distance(nextP.getX(), nextP.getY());

	}

	/**
	 * Gets the distance from the point previous to p and the point after p in the shell
	 * @param p
	 * @return the sum of the distance from  the prev point in the shell  to the next point in the shell
	 */
	public double distanceBetweenNeighborsOnLine(Point2D p) {
		Point2D prevP = prevPointOnLine(p), nextP = nextPointOnLine(p);

		return prevP.distance(nextP.getX(), nextP.getY());

	}

	/**
	 * Finds the previous point in the shell
	 * @param p reference point
	 * @return the point that comes before p in the shell
	 */
	public Point2D prevPoint(Point2D p) {
		int i = this.indexOf(p), before = 0;
		if (i == 0) {
			before = this.size() - 1;
		} else {
			before = i - 1;
		}
		return this.get(before);
	}

	/**
	 * Finds the next point in the shell
	 * @param p reference point
	 * @return the point that comes after p in the shell
	 */
	public Point2D nextPoint(Point2D p) {
		int i = this.indexOf(p), after = 0;
		if (i == this.size() - 1) {
			after = 0;
		} else {
			after = i + 1;
		}
		return this.get(after);
	}

	//these methods are duplicate and we should comment them out and replace the calls

	/**
	 * Finds the next point on the line, duplicates nextPoint
	 * @param p reference point
	 * @return the point that comes before p on the line
	 */
	private Point2D nextPointOnLine(Point2D p) {
		int i = this.indexOf(p), after = 0;
		if (i == this.size() - 1) {
			after = i;
		} else {
			after = i + 1;
		}
		return this.get(after);
	}

	/**
	 * Finds the previous point on the line, duplicates prevPoint
	 * @param p reference point
	 * @return the point that comes before p on the line
	 */
	private Point2D prevPointOnLine(Point2D p) {
		int i = this.indexOf(p), before = 0;
		if (i == 0) {
			before = i;
		} else {
			before = i - 1;
		}
		return this.get(before);
	}

	/**
	 * Gives the shell, the barrier shell n levels below, and the first shell after the barrier
	 * @param firstN number of shells to split after
	 * @return an arraylist where index 0 is this, index 1 is the shell n levels below this, and index 2 is the child of index 1
	 */
	public ArrayList<Shell> split(int firstN) {
		
		Shell A = this.copyRecursive();
		
		Shell B = null, C = null, curr = A;

		for(int i = 0; i < firstN-1; i ++) {
			curr = curr.getChild();
		}
		B = curr.getChild().copyShallow();
		
		C = curr.getChild().getChild().copyRecursive();
		
		curr.child = null;
		
		ArrayList<Shell> result = new ArrayList<Shell>();
		result.add(A);
		result.add(B);
		result.add(C);
		
		return result;
		
	}

	/**
	 * Finds and removes the innermost shell
	 * @return an arraylist where index 0 is the new innermost shell and index 1 is the old innermost shell
	 */
	public ArrayList<Shell> popMin() {
		
		Shell A = this.copyRecursive();
		
		Shell B = null, curr = A;
		
		int order = A.updateOrder();
		for(int i = 0; i < order - 2; i ++) {
			curr = curr.getChild();
		}
		B = curr.getChild().copyShallow();
		curr.child = null;
		
		ArrayList<Shell> result = new ArrayList<Shell>();
		result.add(A);
		result.add(B);
		
		return result;
		
	}

	/**
	 * Collapses all shells into one shell that is the tsp path
	 * @return one shell that represents the optimal tsp path
	 */
	public Shell collapseAllShells() {
		int order = this.updateOrder();

		if(this.isMinimal()) {
			return this;
		}
		//the even case where we pop the min shell and collapse all shells other than that
		//before collapsing the min one at the end
		if(order%2 == 0) {
			ArrayList<Shell> popList = this.popMin();
			
			Shell A = popList.get(0).collapseAllShells();
			Shell B = popList.get(1);
			return collapseReduce(A, B);
		}
		//the odd case where we split the remaining shells in half and collapse shells on both sides of the barrier shell
		//before collapsing both sides onto the barrier shell and calling the consensus function
		else {
			int splitVal = (this.updateOrder()-1)/2;

			if(splitVal%2 == 0) {
				splitVal= splitVal+1;
			}
			ArrayList<Shell> splitList = this.split(splitVal);
			Shell A = splitList.get(0).collapseAllShells();
			Shell B = splitList.get(1);
			Shell C = splitList.get(2).collapseAllShells();
			Shell AB = collapseReduce(A, B);
			Shell BC = collapseReduce(C, B);
			return consensus(AB, BC);
			
		}
	}

	/**
	 * Collapses shell B onto shell A and reduces the tsp path
	 * @param A
	 * @param B the child shell of A
	 * @return one shell that represents the optimal tsp path for all points in shells A and B
	 */
	public static Shell collapseReduce(Shell A, Shell B) {
		Shell result = A.copyRecursive();
		Shell copy = B.copyRecursive();
		boolean notConfirmed = true;

		//once there is no change to result then the loop will exit
		//this will only happen once all points from copy are in result
		// and all points in result cannot be rearranged to form a shorter path
		while (notConfirmed) {
			Point2D pointChosen = null;
			int chosenParent = 0;
			boolean first = true, changed = false;
			Point2D lastPoint, currPoint = null;
			double minDist = java.lang.Double.MAX_VALUE;
			for (int i = 0; i < result.size(); i++) {
				lastPoint = currPoint;
				currPoint = result.get(i);
				if (first) {
					lastPoint = result.getLast();
					first = false;
				}
				for (Point2D q : copy) {
					double dist = Vectors.distanceChanged(lastPoint, currPoint, q);
					//store which point in b fits best between the two current points in result
					if (dist < minDist) {
						minDist = dist;
						pointChosen = q;
						chosenParent = i;
						changed = true;
					}
				}
				for (Point2D p : result) {
					if (!currPoint.equals(p) && !lastPoint.equals(p)) {
						double distanceChanged = java.lang.Double.MAX_VALUE;

						distanceChanged = Vectors.distanceChanged(lastPoint, currPoint, p)
								+ (result.distanceBetweenNeighbors(p) - result.distanceToNeighbors(p)); //why this second line

						//store which point if any already in result fits better in between curr and last points
						// instead of where it currently is
						if (distanceChanged < minDist && distanceChanged < 0) {
							minDist = distanceChanged;
							pointChosen = p;
							chosenParent = i;
							changed = true;
						}
					}
				}

			}
			//update result to add the closest point from B or to reduce result into a better tsp path
			if (changed) {
				result.remove(pointChosen);
				result.add(chosenParent, pointChosen);
				copy.remove(pointChosen);
			}

			notConfirmed = changed;
		}

		return result;
	}

	/**
	 * Collapse B onto A just within the segment s
	 * @param s
	 * @param A
	 * @param B
	 * @return a shell that is the optimal tsp path of the points in A and B
	 */
	public static Shell collapseReduceLine(Segment s, Shell A, Shell B) {
		Shell result = new Shell(null, null, ps);
		Shell copy = new Shell(null, null, ps);
		
		result.add(s.first);
		result.addAll(A);
		result.add(s.last);
		copy.addAll(B);
		
		boolean notConfirmed = true;

		//once there is no change to result then the loop will exit
		//this will only happen once all points from copy are in result
		// and all points in result cannot be rearranged to form a shorter path
		while (notConfirmed) {
			Point2D pointChosen = null;
			int chosenParent = 0;
			boolean first = true, changed = false;
			Point2D lastPoint, currPoint = null;
			double minDist = java.lang.Double.MAX_VALUE;
			for (int i = 0; i < result.size(); i++) {
				lastPoint = currPoint;
				currPoint = result.get(i);
				if (first) {
					lastPoint = currPoint;
					first = false;
					i++;
					currPoint = result.get(i);
				}
				for (Point2D q : copy) {
					if(!s.first.equals(q) && !s.last.equals(q)) {
						double dist = Vectors.distanceChanged(lastPoint, currPoint, q);
								//+ (copy.distanceBetweenNeighborsOnLine(q) - copy.distanceToNeighborsOnLine(q));
						//store which point in b fits best between the two current points in result
						if (dist < minDist) {
							minDist = dist;
							pointChosen = q;
							chosenParent = i;
							changed = true;
						}
					}
				}
				for (Point2D p : result) {
					if (!currPoint.equals(p) && !lastPoint.equals(p)) {
						double distanceChanged = Vectors.distanceChanged(lastPoint, currPoint, p)
								+ (result.distanceBetweenNeighborsOnLine(p) - result.distanceToNeighborsOnLine(p));
						//store which point if any already in result fits better in between curr and last points
						// instead of where it currently is
						if (distanceChanged < minDist && distanceChanged < 0) {
							minDist = distanceChanged;
							pointChosen = p;
							chosenParent = i;
							changed = true;
						}
					}
				}
			}
			//update result to add the closest point from B or to reduce result into a better tsp path
			if (changed) {
					result.remove(pointChosen);
					result.add(chosenParent, pointChosen);
					copy.remove(pointChosen);
			}

			notConfirmed = changed;
		}



		// reduceShell(result, isLine);
		if(result.size() > 2) {
			return result;
		}
		else {
			return copy;
		}
		
	}


	/**
	 * Finds a consensus between the merged shells AB and BC
	 * Determines how to order points from A and C that come between the same two points in B
	 * @param AB
	 * @param BC
	 * @return a shell that represents the optimal tsp path through shells A, B, and C
	 */
	public static Shell consensus(Shell AB, Shell BC) {

		AB = AB.copyRecursive();
		BC = BC.copyRecursive();
		Shell B = pointsInCommon(AB, BC);
		ArrayList<Segment> ABKeys = new ArrayList<Segment>(), BCKeys = new ArrayList<Segment>();

		HashMap<Segment, Shell> ABsections = AB.splitBy(B, ABKeys);
		HashMap<Segment, Shell> BCsections = BC.splitBy(B, BCKeys);

		Shell result = new Shell(null, BC.child, ps);
		for (Segment s : ABKeys) {
			//if the segment is in AB and not BC then add all non endpoints on the segment
			if (!BCsections.containsKey(s)) {
				// TODO: set start and end to be where they connect to the B points
				Point2D point = AB.nextPoint(s.first);
				while (!point.equals(s.last)) {
					result.add(point);
					point = AB.nextPoint(point);
				}
				result.add(s.last);
			}// otherwise do collapse reduce line to get a consensus between points from A and C that fit between the same points in B
			else {
				if (BCsections.containsKey(s) && ABsections.get(s).size() == 0 && BCsections.get(s).size() == 0) {
					result.add(s.last);
				} else {

					
					Shell line = collapseReduceLine(s, ABsections.get(s), BCsections.get(s));

					line.remove(s.first);
					result.addAll(line);

				}
			}
		}
		// split by the leftover keys and then do the collapse reduce above on the
		// leftovers

		ArrayList<Segment> leftOverKeys = new ArrayList<Segment>();

		for (Segment s : BCKeys) {
			if (!ABsections.containsKey(s)) {
				leftOverKeys.add(s);


			}
		}

		// split by the leftover keys and then do the collapse reduce above on the
		// leftovers
		for (Segment s : leftOverKeys) {
			Shell leftOverShell = new Shell(null, null, ps);
			leftOverShell.add(s.first);
			leftOverShell.add(s.last);
			HashMap<Point2D, Shell> resultSections = result.splitInHalf(leftOverShell, new ArrayList<Point2D>());
			Point2D minIndex = null;
			double minLengthChange = java.lang.Double.MAX_VALUE;
			Shell minShell = null;

			for (Point2D first : resultSections.keySet()) {
				Segment s1 = new Segment(null, null);
				if (first.equals(s.first)) {
					s1.first = s.first;
					s1.last = s.last;
				} else {
					s1.last = s.first;
					s1.first = s.last;
				}
				
				Shell beforeLine = new Shell(null, null, ps);
				beforeLine.add(s1.first);
				beforeLine.addAll(resultSections.get(first));
				beforeLine.add(s1.last);
				
				double firstLength = beforeLine.getLength();

				Shell line = collapseReduceLine(s, resultSections.get(first), BCsections.get(s));
				double changedLength = line.getLength();
				if (changedLength - firstLength < minLengthChange) {
					minLengthChange = changedLength - firstLength;
					minIndex = first;
					minShell = line;
				}
			}

			result = new Shell(null, BC.child, ps);
			for (Point2D first : resultSections.keySet()) {

				if (first.equals(minIndex)) {
					result.addAll(minShell);
				} else {
					result.addAll(resultSections.get(first));
				}
			}

		}
		return result;

	}

	/**
	 * Finds all points in common between the shells AB and BC
	 * @param AB
	 * @param BC
	 * @return a shell that has all of the points in common between AB and BC
	 */
	private static Shell pointsInCommon(Shell AB, Shell BC) {

		Shell result = new Shell(null, null, ps);

		for (Point2D p : AB) {
			if (BC.contains(p)) {
				result.add(p);
			}
		}
		return result;
	}

	/**
	 * Recursively copies a shell so that all of its children appear in the copy
	 * @return a shell that represents a complete copy of the current shell
	 */
	public Shell copyRecursive() {
		Shell copy = null;
		if (!isMinimal()) {
			copy = new Shell(this.parent, this.child.copyRecursive(), this.ps); //is parent shallow copied here could that cause problems
		} else {
			copy = new Shell(this.parent, null, this.ps);
		}
		for (Point2D q : this) {
			copy.add(q);
		}
		return copy;
	}

	/**
	 * Shallow copies a shell so that it does not point to any childern
	 * @return a copy of the current shell with no references to its children
	 */
	public Shell copyShallow() {
		Shell copy = new Shell(this.parent, null, this.ps);

		for (Point2D q : this) {
			copy.add(q);
		}
		return copy;
	}

	/**
	 * Creates a mapping from segments in B to shells that represent all points inbetween the endpoints of the segment
	 * @param b
	 * @param keys
	 * @return A hash map of segments in B to shells that represent all points in this that lie inbetween the endpoints of the segment
	 */
	private HashMap<Segment, Shell> splitBy(Shell b, ArrayList<Segment> keys) {
		HashMap<Segment, Shell> result = new HashMap<Segment, Shell>();
		int index = 0;
		Shell firstTemp = new Shell(null, null, ps);
		Point2D lastB = null, firstB = null, prevB = null, nextB = null;
		boolean first = true;
		Shell temp = new Shell(null, null, ps);
		int count = 0;
		for (Point2D p : this) {
			if (b.contains(p)) {
				count++;
				if (first) {
					firstB = p;
					prevB = p;
					nextB = prevB;
					first = false;
				} else {
					if (count == b.size()) {
						lastB = p;
					}

					prevB = nextB;
					nextB = p;
					Segment s = new Segment(prevB, nextB);
					result.put(s, temp);
					keys.add(s);
					temp = new Shell(null, null, ps);
				}
			} else { //is this guaranteed to work?
				if (first) {
					firstTemp.add(p);
				} else {
					temp.add(p);
				}
			}
		}
		int idx = 0;
		for (Point2D p : temp) {
			firstTemp.add(idx, p);
			idx++;
		}
		Segment s = new Segment(lastB, firstB);
		result.put(s, firstTemp);
		keys.add(s);
		return result;
	}

	/**
	 * Creates a mapping from each point in B to a shell that represents all points in this that come after the key and before the next point in b
	 * @param b
	 * @param startPoints all of the points in b
	 * @return A hashmap from points in b to shells that represents points in this between the key and the next key
	 */
	private HashMap<Point2D, Shell> splitInHalf(Shell b, ArrayList<Point2D> startPoints) {
		HashMap<Point2D, Shell> result = new HashMap<Point2D, Shell>();
		int index = 0;
		Shell firstTemp = new Shell(null, null, ps);
		Point2D lastB = null, firstB = null, prevB = null, nextB = null;
		boolean first = true;
		Shell temp = new Shell(null, null, ps);
		int count = 0;
		for (Point2D p : this) {
			if (b.contains(p)) {
				count++;
				if (first) {
					firstB = p;
					prevB = p;
					nextB = prevB;
					first = false;
				} else {
					if (count == b.size()) {
						lastB = p;
					}

					prevB = nextB;
					nextB = p;
					Segment s = new Segment(prevB, nextB);
					result.put(s.first, temp);
					startPoints.add(s.first);
					temp = new Shell(null, null, ps);
				}
			} else {
				if (first) {
					firstTemp.add(p);
				} else {
					temp.add(p);
				}
			}
		}
		int idx = 0;
		for (Point2D p : temp) {
			firstTemp.add(idx, p);
			idx++;
		}
		Segment s = new Segment(lastB, firstB);
		result.put(s.first, firstTemp);
		startPoints.add(s.first);
		return result;
	}

	/**
	 * Turns a shell into a path object
	 * @param shell
	 * @return a path that represnts the path through all points in the shell
	 */
	public static Path2D shellToPath(Shell shell) {
		Path2D path = new GeneralPath();
		boolean first = true;
		for (Point2D p : shell) {
			if (first) {
				path.moveTo(p.getX(), p.getY());
				first = false;
			} else {
				path.lineTo(p.getX(), p.getY());
			}

		}
		return path;

	}

	/**
	 * Determines equality of shells based on if they represent the same tsp path
	 * @param o shell to compare to
	 * @return true if the shells are equal and false if they are not
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Shell) {
			Shell other = (Shell) o;
			if(other.size() != this.size()){
				return false;
			}
			Point2D otherFirst = other.getFirst();
			int startIndex = -1;
			for (Point2D p : this){
				if(p.equals(otherFirst)){
					startIndex = this.indexOf(p);
					break;
				}
			}
			if(startIndex == -1){
				return false;
			}
			for(int i = 0; i < other.size(); i++){
				if(!other.get(i).equals(this.get(startIndex))){
					return false;
				}
				startIndex = (startIndex + 1) % other.size();
			}
			return true;
		}
		return false;

	}


}
