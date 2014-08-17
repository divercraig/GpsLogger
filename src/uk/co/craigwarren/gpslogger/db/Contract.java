/**
 * 
 */
package uk.co.craigwarren.gpslogger.db;

import android.provider.BaseColumns;

/**
 * @author craig
 *
 */
public class Contract {
	
	public static class GpsLog implements BaseColumns{
		
		public static final String TABLE_NAME = "GpsLog";
		public static final String LATITUDE = "latitude";
		public static final String LONGITUDE = "longitude";
		public static final String ACCURACY = "accuracy";
		public static final String ALTITUDE = "altitude";
		public static final String BEARING = "bearing";
		public static final String SPEED = "speed";
		public static final String TIME = "time";
		public static final String PROVIDER = "provider";
		
	}

}
