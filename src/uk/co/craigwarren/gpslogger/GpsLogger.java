package uk.co.craigwarren.gpslogger;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;
import uk.co.craigwarren.gpslogger.db.Contract;
import uk.co.craigwarren.gpslogger.db.GpsDatabaseHelper;

public class GpsLogger {
	private static final String TAG = GpsLogger.class.getSimpleName();
	
	
	public void logGpsLocation(Context context, Location loc) {
		GpsDatabaseHelper helper = new GpsDatabaseHelper(context);
		SQLiteDatabase db = helper.getWritableDatabase();
		long id = db.insert(Contract.GpsLog.TABLE_NAME, null, convertToContentValues(loc));
		Log.d(TAG, "Location "+loc.getLatitude()+" : "+loc.getLongitude()+" written to row "+id);
	}
	
	private ContentValues convertToContentValues(Location loc) {
		ContentValues values = new ContentValues();
		if(loc.hasAccuracy()) {
			values.put(Contract.GpsLog.ACCURACY, loc.getAccuracy());
		}
		if(loc.hasAltitude()) {
			values.put(Contract.GpsLog.ALTITUDE, loc.getAltitude());
		}
		if(loc.hasBearing()) {
			values.put(Contract.GpsLog.BEARING, loc.getBearing());
		}
		if(loc.hasSpeed()) {
			values.put(Contract.GpsLog.SPEED, loc.getSpeed());
		}
		values.put(Contract.GpsLog.LATITUDE, loc.getLatitude());
		values.put(Contract.GpsLog.LONGITUDE, loc.getLongitude());
		values.put(Contract.GpsLog.PROVIDER, loc.getProvider());
		values.put(Contract.GpsLog.TIME, loc.getTime());
		return values;
	}

}
