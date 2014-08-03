/**
 * 
 */
package uk.co.craigwarren.gpslogger;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
			Location loc = (Location) intent.getExtras().get(LocationManager.KEY_LOCATION_CHANGED);
			Log.d(TAG, "Location Received:"+loc.getLatitude()+" : "+loc.getLongitude());
		} else {
			Log.w(TAG, "Couldn't find a location in the GPS Update intent");
		}
	}

}
