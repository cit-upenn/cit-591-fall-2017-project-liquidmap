/**
 * This interface allows for the user of getRandPoint() not to worry about where
 * the data for the PointWorld is coming from (vector or raster).
 *
 * @author sgb
 *
 */
public interface DataSource {

	public PointWorld getRandPoint();

}
