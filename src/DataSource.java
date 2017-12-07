/**
 * This interface allows for the user of getRandPoint() not to worry about where
 * the data for the Point is coming from (Vector or Raster).
 *
 * @author sgb
 *
 */
public interface DataSource {

	public PointWorld getRandPoint();

}
