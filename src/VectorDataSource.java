
public class VectorDataSource implements DataSource {

	public VectorDataSource(String fileName) {
		// import parse file etc.
	}

	@Override
	public Point getRandPoint() {
		return new PointScreen(0, 0);
	}

}
