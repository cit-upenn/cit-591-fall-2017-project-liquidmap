public class Point implements Comparable<Point> {

	private double lat;
	private double lon;
	private double time;

	public Point(double lat, double lon, double time) {
		this.lat = lat;
		this.lon = lon;
		this.time = time;
	}

	public Point(double lat, double lon) {
		this(lat, lon, 0.);
	}

	public double[] getLLT() {
		double[] triple = new double[3];
		triple[0] = lat;
		triple[1] = lon;
		triple[2] = time;
		return triple;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getTime() {
		return time;
	}

	public void offsetTime(double timeOffset) {
		this.time += timeOffset;
	}

	public void scaleTime(double timeOffset) {
		this.time *= timeOffset;
	}

	@Override
	public String toString() {
		return String.format("(lat: %f  lon: %f  time: %f)", lat, lon, time);
	}

	@Override
	public int compareTo(Point otherPoint) {
		return Double.compare(this.getTime(), otherPoint.getTime());
	}

	public boolean equals(Point p2) {
		boolean latEqual = lat == p2.getLat();
		boolean lonEqual = lon == p2.getLon();
		boolean timeEqual = time == p2.getTime();
		return (latEqual && lonEqual && timeEqual);
	}

}
