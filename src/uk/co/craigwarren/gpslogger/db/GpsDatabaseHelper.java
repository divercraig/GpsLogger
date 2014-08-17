/**
 * 
 */
package uk.co.craigwarren.gpslogger.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author craig
 *
 */
public class GpsDatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DB_NAME = "gpslog.db";
	private static final int DB_VERSION = 1;
	
	private static final String CREATE_GPSLOG_TABLE = 
			"CREATE TABLE "+Contract.GpsLog.TABLE_NAME + "("
			+ Contract.GpsLog._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Contract.GpsLog.ACCURACY + " REAL, "
			+ Contract.GpsLog.ALTITUDE + " REAL, "
			+ Contract.GpsLog.BEARING + " REAL, "
			+ Contract.GpsLog.LATITUDE + " REAL, "
			+ Contract.GpsLog.LONGITUDE + " REAL, "
			+ Contract.GpsLog.PROVIDER + " TEXT, "
			+ Contract.GpsLog.SPEED + " REAL, "
			+ Contract.GpsLog.TIME + " INTEGER "
			+")";

	public GpsDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_GPSLOG_TABLE);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		throw new UnsupportedOperationException("Datbase does not support upgrades as it is currently on version 1" );
	}

}
