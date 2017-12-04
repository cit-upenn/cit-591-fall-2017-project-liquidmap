import java.util.ArrayList;

public class MainTester {

	public static void main(String[] args) {
		ArrayList<PointWorld> points = new ArrayList<>();
		VectorDataSource vds= new VectorDataSource("VDSTest.txt");
		points = vds.getPoints();
		System.out.println(points.size());
		
		for (PointWorld point : points) {
			System.out.print(point.toString());
			System.out.println(" Weight: " + point.getWeight());
		}
	}

}
