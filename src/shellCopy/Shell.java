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

/*NOTES:
 * 
 * ==============================================
 * Scheduling:
 * ==============================================
 * 
 * Andrew: working on the weekends and sometimes at night after work
 * 
 * Ares: pretty much the same
 * 
 * meetup every saturday
 * 
 * ==============================================
 * Ownership rules:
 * ==============================================
 * 
 * super shares?
 * 
 * ownershares are worth zero dollars and constitute one vote
 * 
 * one entity can have one vote
 * 
 * can destroy ownershare at anytime for no financial compensation
 * 
 * to create and ownershare everyone must agree
 * 
 * public shares are normal but don't constitute board membership
 * 
 * everyones shares get split during splitting ( no one man gets fucked policy)
 * 
 * trade secrets including algorithms must be kept secret until death and beyond
 * 
 * 
 * 
 * ================================================
 * Paper Credits
 * ================================================
 * 
 * how do recognize Clayton and how much does he deserve: 
 * definitely on the paper but is he a special thanks or last author?
 * 
 * Andrew Wollack and Ares Shackleford are first co-authors 
 * 
 * Special thanks to: Clayton Chu, Edward Wollack, idk who else yet
 * 
 * ================================================
 * Monetization Plan:
 * ================================================
 * Should we even tell people about this
 * 
 * Ryan and Alec to look at this? how are we willing to offer, how do we establish trust, 
 * 
 * 
 * given the current recession and virus situation is it prudent to  release our findings to the public or pursue 
 * business ventures given the lack of capital available?
 * 
 * will we go to jail for releasing this?
 * 
 * do we know the full implications of P=NP?
 * 
 * should we release the code or just the general algorithm? is it any different?
 * 
 * 
 * Sectors:
 *  * security: steal things?
 *  * networking: verizon
 *  * stock market:  how to stop, trade secret?
 *  * delivery routing amazon?
 *  * neural network architecture replacement:
 *  
 *  https://cacm.acm.org/magazines/2009/9/38904-the-status-of-the-p-versus-np-problem/fulltext
 *  
 *  * Finding a DNA sequence that best fits a collection of fragments of the sequence (see Gusfield20).
 *	* Finding a ground state in the Ising model of phase transitions (see Cipra8).
 *	* Finding Nash Equilbriums with specific properties in a number of environments (see Conitzer9).
 *	* Finding optimal protein threading procedures.26
 *	* Determining if a mathematical statement has a short proof (follows from Cook10).
 * 
 *  example:
 *  *Google pagerank is not that complicated the business moat is number of people using hte platform.
 *  
 *  Patent: could we patent and would it be worth it? would we have to make a product that relies on 
 *  the algorithm to patent
 *  
 *  how to prove:?
 *  Two cases:
 *  A shell is a SHell of S then A is a TSP Path of A
 *  
 *  Does a shell collapse onto a Tsp path result in another tsp path if the tsp path was enclosing the shell
 *  
 *  How to generalize in higher dimensions? is this natural from the algorithm we have currently?
 *  
 * =========================================
 * Definitions:
 * =========================================
 * Collapse function - takes all of A and inserts each point into the closest neighboring segment of B 
 * where the number of segments in B grows with each insert (Clayton)
 * 
 * Reduce function - takes a Shell and makes single replacements until the shell is in a minimal state
 * 
 * X> = collapse function i.e. AX>B =collapse of A onto B and A <X B = collapse of B onto A
 * NOTE: the path on the left always encloses the path on the right
 * 
 * Solve Set - the collection of points to solve TSP on. Abbreviated as S
 * 
 * Shell - the minimal closed polygon with vertices that are in the Solve Set  that encloses the Solve Set.
 * 
 * Shell Function - F(S) = Shell of S
 * 
 * Related Shells - R=
 * A = F(S)
 * S2 = S - A
 * B = F(S2)
 * A and B are Related Shells and this relationship is associative so if (A R= B) and (B R= C) then (A R= C)
 * 
 * Shell Order -how many internal Related Shells a Shell has  in the previous example A has 2 internal shells, 
 * B has 1, and C has 0, so A has Order 2, B has Order 1 etc.
 * 
 * TSP Path - A closed polygon with every point in S in TSP Path such that the distance is minimized
 * 
 * =======================================
 * Equations:
 * =======================================
 * 
 * 1111111111111111111111111111111111111111111
 * A is a Shell of S then A is a TSP Path of A
 * 
 * let A and B be Related Shells where the order of A is larger than the order of B
 * 
 * C = A <X B
 * 
 * C is a TSP Path and not a Shell
 * 
 * 2222222222222222222222222222222222222222222
 * let D and E be TSP Paths
 * 
 * G = D X> E
 * 
 * H = E X> D
 * 
 * G dosen't necissarily equal H
 * 
 * 3333333333333333333333333333333333333333333
 * 
 * Least comfortable with this one
 * 
 * If you have a path J constructed by ((A1 <X A2) <X ...) <X AN where AN is the order 0 Shell of S and A1 is
 * the maximal shell, Then J is a TSP path so long as the shell order from one vertex to the next never changes by more
 * than 1. 
 * 
 * NOT TURE
 * 
 * 4444444444444444444444444444444444444444444
 * 
 * We can make 3 related shells B,C and Enclosing TSP Path A into a TSP Path via the following consensus algorithm
 * 
 * Unsure if A can be a TSP Path or if only works for three related shells, if not i dont know what to do.
 * 
 * let D be A <X B with shell order (A + B )/2
 * 
 * let E be B X> C  with shell order (B + C)/2
 * 
 * 
 * let TSPSol  = D & E
 * for example A = [ 1 , 2, 3 ]
 * 			   B = [ 4 , 5, 6 ]
 * 			   C = [ 7 , 8, 9 ] 
 *
 * say that    D = [1, 2, 4, 5, 3, 6]
 * and that    E = [4, 7, 8, 9, 5, 6]
 *    
 * then        TSPSOL = [1, 2, 4, 7, 8, 9, 5, 3, 6]
 *   
 * this is an example with no conflicts
 *   
 * still need to figure out how to resolve conflicts
 *   
 * Conflict example:
 *   
 * say that D = [1, 4, 2, 5, 3, 6]
 * and that E = [4, 7, 8, 9, 5, 6]
 *    
 * how would we sort 2, 7, 8, and 9
 * 
 * by their distance to 4 and 5?
 * 
 * or
 * 
 * collapse of 2,7,8,9 onto the line segment formed by 4,5
 * 
 * or 
 * 
 * idfk
 * 
 * it is unclear if this works for more than three shells( i think it does)
 * 
 * 
 * 555555555555555555555555555555555555555555555555555
 * 
 * 
 * let A be a TSPPath such that A encloses minimal  Shell B
 * 
 * then A <X B is TSPPath
 * 
 * this is probably false unless???
 * 
 * 666666666666666666666666666666666666666666666666666
 * 
 * It is  interesting to note that while the property that the order from one vertex to the next never changes by more
 * than 1 does not hold for all TSP Paths, each shell can be thought of as dividing the TSP Path into  into sections where 
 * each vertex of the shell owns a  part of the TSP PAth that leads to the next vertex of the shell in the clockwise or
 *  counter clockwise direction however you can not say this about any ordered group of points on the path.
 * 
 * ^ this is common but false in general
 * I am pretty sure that the points will not always be in a clockwise or counter clockwise order with respect to each 
 * other, but they will always be in the same order when merged with the shells around them can this even possibly be true?
 * 
 * WRONG WALL 
 * 
 * =======================================================================================
 * OPEN QUESTIONS:
 * =======================================================================================
 * Q:	Are there any collapse and reduce functions that could possibly be used to  maintain tsppath without doing the ]
 * 		consensus algorithm on the induction step?
 * 
 * A:	So far no, any collapse/reduce function that I have come up with cannot be used to do simple induction without 
 * 		a consensus function.
 * 
 * =======================================================================================
 * Q: 	Is the consensus function the same as collapsing every shell onto its neighbors and doing consensus on those 
 * 		collapsed shells?
 * 
 * A:	I think that this is the case and is the reason why you can't just collapse and reduce the parent shell onto its
 * 		children recursively.
 * 
 * ======================================================================================
 * Q: 	Is the algorithm a good optimizer or does it actually solve TSP?
 * 
 * A:	There is no way to know this without a proof that the algorithm optimally solves TSP, but my intuition is that
 * 		it does solve TSP.
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * PLAN Z:
 * 
 * Step 1: do everything like normal
 * Step 2: when we start to do the consensus algorithm with the subsets in between the barrier points we need to change our algo
 * Step 3: retrofit existing architecture to use ND points instead of 2D points (this will complicate visualization a lot
 * 			probably best to only visualize a point if it only has two coords.
 * Step 4: new algorithm is:
 * 			1. Let Points S and E from the barrier shell be the start and end points that we want to optimize around
 * 			2. Create a new PointSet P_SE and let it be all of the points in between S and E including S and E
 * 			3. From the P_SE create a distance matrix DM out of the points
 * 			4. Add a new dummy point D that will act as a wormhole between S and E to DM
 * 				4a. D will have distance 0 to S and E in DM
 * 				4b. Let M be the maximum distance in DM
 * 				4c. D will have distance M to all other points besides S and E
 * 			5. Triangle time baby
 * 				5a. Add M to all points in DM so that it is TSPMetric form
 * 				5b. use the triangle inequality to triangulate the points in DM and add them to P_SE'
 * 				5c. while adding the points make sure to label them with their original coords in P_SE
 * 			6. run TSP solver on P_SE'
 * 			7. add the points back to the original Point Set removing D and add the min order from S to E. 			 
 * 
 */
public class Shell extends LinkedList<Point2D> {
	private static final long serialVersionUID = -5904334592585016845L;
	private int ORDER = 0;
	private boolean maximal, minimal;
	private Shell parent, child;
	private static PointSet ps;

	public Shell(Shell parent, Shell child, PointSet ps) {
		this.parent = parent;
		this.child = child;
		this.updateOrder();
		if (!this.isMaximal()) {
			parent.updateOrder();
		}
		this.ps = ps;
	}

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

	public void drawShell(JComponent frame, Graphics2D g2, Random colorSeed, boolean drawChildren, Color c) {
		if (c == null) {
			Main.drawPath(frame, g2, shellToPath(this),
					new Color(colorSeed.nextFloat(), colorSeed.nextFloat(), colorSeed.nextFloat()), ps, true, false,
					false);
		} else {
			Main.drawPath(frame, g2, shellToPath(this), c, ps, true, false, false);
		}
		if (!this.isMinimal() && drawChildren) {
			child.drawShell(frame, g2, colorSeed, drawChildren, c);
		}
	}

	public Shell getMinimalShell() {
		if (this.isMinimal()) {
			return this;
		} else {
			return child.getMinimalShell();
		}
	}

	public Shell getMaximalShell() {
		if (this.isMaximal()) {
			return this;
		} else {
			return child.getMaximalShell();
		}
	}

	public boolean isMaximal() {
		return parent == null;
	}

	public boolean isMinimal() {
		return child == null;
	}

	public Shell getParent() {
		return parent;
	}

	public Shell getChild() {
		return child;
	}

	public int updateOrder() {
		if (!this.isMinimal()) {
			this.ORDER = child.updateOrder() + 1;
		} else {
			this.ORDER = 1;
		}
		return this.ORDER;
	}

	public void setChild(Shell child) {
		this.child = child;
		minimal = false;
		this.updateOrder();
		if (parent != null) {
			parent.updateOrder();
		}
	}

	public Shell collapseChildOntoShell() {
		if (this.isMinimal()) {
			return this;
		}
		Shell result = collapseReduce(this, this.child);
		result.child = result.child.child;
		return result;
	}

	public Shell collapseShellOntoParent() {
		if (this.isMaximal()) {
			return this;
		}
		Shell result = collapseReduce(this.parent, this);
		result.child = result.child.child;
		return result;
	}

	public double distanceToNeighbors(Point2D p) {
		Point2D prevP = prevPoint(p), nextP = nextPoint(p);

		return p.distance(prevP.getX(), prevP.getY()) + p.distance(nextP.getX(), nextP.getY());

	}

	public double distanceBetweenNeighbors(Point2D p) {
		Point2D prevP = prevPoint(p), nextP = nextPoint(p);

		return prevP.distance(nextP.getX(), nextP.getY());

	}

	public double distanceToNeighborsOnLine(Point2D p) {
		Point2D prevP = prevPointOnLine(p), nextP = nextPointOnLine(p);

		return p.distance(prevP.getX(), prevP.getY()) + p.distance(nextP.getX(), nextP.getY());

	}

	public double distanceBetweenNeighborsOnLine(Point2D p) {
		Point2D prevP = prevPointOnLine(p), nextP = nextPointOnLine(p);

		return prevP.distance(nextP.getX(), nextP.getY());

	}

	public Point2D prevPoint(Point2D p) {
		int i = this.indexOf(p), before = 0;
		if (i == 0) {
			before = this.size() - 1;
		} else {
			before = i - 1;
		}
		return this.get(before);
	}

	public Point2D nextPoint(Point2D p) {
		int i = this.indexOf(p), after = 0;
		if (i == this.size() - 1) {
			after = 0;
		} else {
			after = i + 1;
		}
		return this.get(after);
	}

	private Point2D nextPointOnLine(Point2D p) {
		int i = this.indexOf(p), after = 0;
		if (i == this.size() - 1) {
			after = i;
		} else {
			after = i + 1;
		}
		return this.get(after);
	}

	private Point2D prevPointOnLine(Point2D p) {
		int i = this.indexOf(p), before = 0;
		if (i == 0) {
			before = i;
		} else {
			before = i - 1;
		}
		return this.get(before);
	}

	/*
	 * A onto B this is where the real problem lies i think the issue is that the
	 * line version does not work TODO: change so that keeps collapsing onto self
	 * until last self = self
	 */
	public static Shell collapseFirst(Shell A, Shell B, boolean isLine, boolean reduce) {
		Shell result = A.copyRecursive();
		Shell copy = B.copyRecursive();
		boolean notConfirmed = true;

		while (notConfirmed) {
			Point2D pointChosen = null;
			int chosenParent = 0;
			boolean first = true, changed = false;
			Point2D lastPoint, currPoint = null;
			double minDist = java.lang.Double.MAX_VALUE;
			for (int i = 0; i < result.size(); i++) {
				lastPoint = currPoint;
				currPoint = result.get(i);
				if (first && !isLine) {
					lastPoint = result.getLast();
					first = false;
				} else if (first && isLine) {
					lastPoint = currPoint;
					first = false;
					i++;
					currPoint = result.get(i);
				}
				for (Point2D q : copy) {
					double dist = Vectors.distanceChanged(lastPoint, currPoint, q);
					if (dist < minDist) {
						minDist = dist;
						pointChosen = q;
						chosenParent = i;
						changed = true;
					}
				}

			}
			if (changed) {
				result.remove(pointChosen);
				result.add(chosenParent, pointChosen);
				copy.remove(pointChosen);
			}

			notConfirmed = changed;
		}
		if (reduce) {
			reduceShell(result, isLine);
		}
		return result;
	}

	public static Shell collapseBOntoA(Shell A, Shell B) {
		return collapseReduce(A, B);
	}
	
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
	
	public Shell collapseAllShells() {
		int order = this.updateOrder();

		System.out.println(order);
		if(this.isMinimal()) {
			return this;
		}
		if(order%2 == 0) {
			ArrayList<Shell> popList = this.popMin();
			
			Shell A = popList.get(0).collapseAllShells();
			Shell B = popList.get(1);
			return collapseBOntoA(A, B);
		}
		else {
			int splitVal = (this.updateOrder()-1)/2;

			if(splitVal%2 == 0) {
				splitVal= splitVal+1;
			}
			System.out.println("splitval: " + splitVal);
			ArrayList<Shell> splitList = this.split(splitVal);
			Shell A = splitList.get(0).collapseAllShells();
			Shell B = splitList.get(1);
			Shell C = splitList.get(2).collapseAllShells();
			Shell AB = collapseBOntoA(A, B);
			Shell BC = collapseBOntoA(C, B);
			return consensus(AB, BC);
			
		}
	}

	/**
	 * TODO fix this shit its broken.
	 * 
	 * two possible cases since i think that the actual reduce part is correct 1. we
	 * also need to reduce lines during the consensus algorithm. not sure how to do
	 * this or why my previous aproach didnt work 2. we need to combine the reduce
	 * and the collapse functions into one function. i can see why this would be the
	 * case, but it would make me not happy.
	 * 
	 * @param result
	 * @param isLine
	 */
	public static void reduceShell(Shell result, boolean isLine) {
		boolean notConfirmed = true;
		while (notConfirmed) {
			Point2D pointChosen = null;
			int chosenParent = 0;
			boolean first = true, changed = false;
			Point2D lastPoint, currPoint = null;
			double minDist = java.lang.Double.MAX_VALUE;
			for (int i = 0; i < result.size(); i++) {
				lastPoint = currPoint;
				currPoint = result.get(i);
				if (first && !isLine) {
					lastPoint = result.getLast();
					first = false;
				} else if (first && isLine) {
					lastPoint = currPoint;
					first = false;
					i++;
					currPoint = result.get(i);
				}
				for (Point2D p : result) {
					if (!currPoint.equals(p) && !lastPoint.equals(p)) {
						double distanceChanged = Vectors.distanceChanged(lastPoint, currPoint, p)
								+ (result.distanceBetweenNeighbors(p) - result.distanceToNeighbors(p));
						if (distanceChanged < minDist && distanceChanged < 0) {
							minDist = distanceChanged;
							pointChosen = p;
							chosenParent = i;
							changed = true;
						}
					}
				}

			}
			if (changed) {
				result.remove(pointChosen);
				result.add(chosenParent, pointChosen);
			}
			notConfirmed = changed;
		}
	}

	public static Shell collapseReduce(Shell A, Shell B) {
		Shell result = A.copyRecursive();
		Shell copy = B.copyRecursive();
		boolean notConfirmed = true;

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
								+ (result.distanceBetweenNeighbors(p) - result.distanceToNeighbors(p));
						
						if (distanceChanged < minDist && distanceChanged < 0) {
							minDist = distanceChanged;
							pointChosen = p;
							chosenParent = i;
							changed = true;
						}
					}
				}

			}
			if (changed) {
				result.remove(pointChosen);
				result.add(chosenParent, pointChosen);
				copy.remove(pointChosen);
			}

			notConfirmed = changed;
		}

		// reduceShell(result, isLine);
		return result;
	}
	
	public static Shell collapseReduceLine(Segment s, Shell A, Shell B) {
		Shell result = new Shell(null, null, ps);
		Shell copy = new Shell(null, null, ps);
		
		result.add(s.first);
		result.addAll(A);
		result.add(s.last);
		
		if(copy != null) {
			copy.addAll(B);
		}
		
		boolean notConfirmed = true;

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
						double dist = Vectors.distanceChanged(lastPoint, currPoint, q) 
								+ (copy.distanceBetweenNeighborsOnLine(q) - copy.distanceToNeighborsOnLine(q));
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
						if (distanceChanged < minDist && distanceChanged < 0) {
							minDist = distanceChanged;
							pointChosen = p;
							chosenParent = i;
							changed = true;
						}
					}
				}
			}
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

	


	public static Shell consensus(Shell AB, Shell BC) {

		AB = AB.copyRecursive();
		BC = BC.copyRecursive();
		Shell B = pointsInCommon(AB, BC);
		ArrayList<Segment> ABKeys = new ArrayList<Segment>(), BCKeys = new ArrayList<Segment>();

		HashMap<Segment, Shell> ABsections = AB.splitBy(B, ABKeys);
		HashMap<Segment, Shell> BCsections = BC.splitBy(B, BCKeys);

		Shell result = new Shell(null, BC.child, ps);
		for (Segment s : ABKeys) {
			if (!BCsections.containsKey(s)) {
				// TODO: set start and end to be where they connect to the B points
				Point2D point = AB.nextPoint(s.first);
				while (!point.equals(s.last)) {
					result.add(point);
					point = AB.nextPoint(point);
				}
				result.add(s.last);
			} else {
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
				System.out.println(s);
				leftOverKeys.add(s);
				System.out.println(result.contains(s.first));

				System.out.println(result.contains(s.last));

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

	private static Shell pointsInCommon(Shell AB, Shell BC) {

		Shell result = new Shell(null, null, ps);

		for (Point2D p : AB) {
			if (BC.contains(p)) {
				result.add(p);
			}
		}
		return result;
	}

	public Shell copyRecursive() {
		Shell copy = null;
		if (!isMinimal()) {
			copy = new Shell(this.parent, this.child.copyRecursive(), this.ps);
		} else {
			copy = new Shell(this.parent, null, this.ps);
		}
		for (Point2D q : this) {
			copy.add(q);
		}
		return copy;
	}

	public Shell copyShallow() {
		Shell copy = new Shell(this.parent, null, this.ps);

		for (Point2D q : this) {
			copy.add(q);
		}
		return copy;
	}

	private Collection<? extends Point2D> reverse(Shell shell) {
		// TODO Auto-generated method stub

		for (int i = 0; i < shell.size(); i++) {
			Point2D first = shell.pop();
			shell.add(first);

		}
		return shell;
	}

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
		// System.out.println(s);
		result.put(s, firstTemp);
		keys.add(s);
		return result;
	}

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

}
