package shell;

public class PointDistanceWrapper<T> implements Comparable{
	Double distance;
	T s;

	public PointDistanceWrapper(Double distance, T s) {
		this.distance = distance;
		this.s = s;
	}


	@Override
	public int compareTo(Object o) {
		return this.distance.compareTo(((PointDistanceWrapper<T>)o).distance);
	}
	
	public String toString() {
		return s.toString();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof PointDistanceWrapper<?>) {
			return s.equals(((PointDistanceWrapper<T>) obj).s);
		}else {
			return s.equals(obj);
		}
	}


	public void update(double var) {
		if(var > distance) {
			distance = var;
		}
		
	}
	
}
