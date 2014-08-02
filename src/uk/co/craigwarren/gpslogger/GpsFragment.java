/**
 * 
 */
package uk.co.craigwarren.gpslogger;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.BufferType;

/**
 * @author craig
 *
 */
public class GpsFragment extends Fragment implements LocationListener, OnClickListener{
	
	private static final String TAG = GpsFragment.class.getSimpleName();
	
	private LocationManager mLocMan;
	private TextView mTextView;
	private Button mButton;
	private boolean mListening = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		mTextView = (TextView) rootView.findViewById(R.id.location_text_view);
		mButton = (Button) rootView.findViewById(R.id.gps_button);
		mButton.setOnClickListener(this);
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mLocMan = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
	}

	@Override
	public void onStop() {
		super.onStop();
		mLocMan.removeUpdates(this);
		mListening = false;
		mButton.setText(R.string.locate, BufferType.NORMAL);
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "onLocationChanged: "+location);
		String text = 
				Location.convert(location.getLatitude(), Location.FORMAT_DEGREES)
				+ "+++"
				+ Location.convert(location.getLongitude(), Location.FORMAT_DEGREES);
		mTextView.setText(text);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		if (mListening) {
			stopListening();
		} else {
			startListening();
		}
	}
	
	private void startListening() {
		mLocMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		mListening = true;
		mButton.setText(R.string.stop_locating, BufferType.NORMAL);
	}
	
	private void stopListening() {
		mLocMan.removeUpdates(this);
		mListening = false;
		mButton.setText(R.string.locate, BufferType.NORMAL);
	}

}
