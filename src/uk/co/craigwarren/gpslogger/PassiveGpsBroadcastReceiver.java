/**
 * 
 */
package uk.co.craigwarren.gpslogger;

import uk.co.craigwarren.gpslogger.db.Contract;
import uk.co.craigwarren.gpslogger.db.GpsDatabaseHelper;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

/**
 * @author craig
 *
 */
public class PassiveGpsBroadcastReceiver extends BroadcastReceiver {
	
	private static final String TAG = PassiveGpsBroadcastReceiver.class.getSimpleName();
	private static final String ACTION_PASSIVE_GPS = "uk.co.craigwarren.gpslogger.PASSIVE_GPS";
	
	/* (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		if(Intent.ACTION_BOOT_COMPLETED == intent.getAction()) {
			handleBootIntent(context, intent);
		} else if (ACTION_PASSIVE_GPS == intent.getAction()) {
			handleGpsIntent(context, intent);
		}
	}
	
	private void handleBootIntent(Context context, Intent intent) {
		Log.d(TAG, "Boot Intent received");
		LocationManager locMan = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		Intent passiveLocationIntent = new Intent(ACTION_PASSIVE_GPS);
		PendingIntent passiveLocationPendingIntent = PendingIntent.getBroadcast(context, 0, passiveLocationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		locMan.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, passiveLocationPendingIntent);
	}
	
	private void handleGpsIntent(Context context, Intent intent) {
		Log.d(TAG, "Received Passive Location Intent");
		if(intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)) {
			GpsDatabaseHelper helper = new GpsDatabaseHelper(context);
			SQLiteDatabase db = helper.getWritableDatabase();
			Location loc = (Location) intent.getExtras().get(LocationManager.KEY_LOCATION_CHANGED);
			long id = db.insert(Contract.GpsLog.TABLE_NAME, null, convertToContentValues(loc));
			Log.d(TAG, "Location "+loc.getLatitude()+" : "+loc.getLongitude()+" written to row "+id);
		} else {
			Log.w(TAG, "Couldn't find a location in the GPS Update intent");
		}
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
