package com.codepath.hackthehood.fragments.slides;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.codepath.hackthehood.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class MapsSlideFragment extends Fragment implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener {

        public MapsSlideFragment(){
        // Required empty public constructor
        }

        private SupportMapFragment mapFragment;
        private GoogleMap map;
        private LocationClient mLocationClient;
    /*
     * Define a request code to send to Google Play services This code is
     * returned in Activity.onActivityResult
     */
        private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps_slide, container, false);

            if (savedInstanceState == null) {
                mLocationClient = new LocationClient(getActivity(), this, this);
                mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map));
                if (mapFragment != null) {
                    map = mapFragment.getMap();
                    if (map != null) {
                        map.setMyLocationEnabled(true);
                        map.getUiSettings().setZoomControlsEnabled(false);
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                        map.getUiSettings().setAllGesturesEnabled(false);
                        LatLng latLng = new LatLng(37.77, -122.404);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
                    } else {
                        Toast.makeText(getActivity(), "Error - Map was null!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
                }
            }
            return rootView;
        }

    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1000;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator = new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 10);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }

    /*
     * Called when the Activity becomes visible.
     */
    @Override
    public void onStart() {
        super.onStart();
        // Connect the client.
        if (isGooglePlayServicesAvailable()) {
            mLocationClient.connect();
        }

    }

    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    public void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

    /*
     * Handle results returned to the FragmentActivity by Google Play services
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Decide what to do based on the original request code
        switch (requestCode) {

            case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        mLocationClient.connect();
                        break;
                }

        }
    }

    private boolean isGooglePlayServicesAvailable() {
        // Check that Google Play services is available
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        // If Google Play services is available
        if (ConnectionResult.SUCCESS == resultCode) {
            // In debug mode, log the status
            Log.d("Location Updates", "Google Play services is available.");
            return true;
        } else {
            // Get the error dialog from Google Play services
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                    CONNECTION_FAILURE_RESOLUTION_REQUEST);

            // If Google Play services can provide an error dialog
            if (errorDialog != null) {
                // Create a new DialogFragment for the error dialog
                ErrorDialogFragment errorFragment = new ErrorDialogFragment();
                errorFragment.setDialog(errorDialog);
                errorFragment.show(getChildFragmentManager(), "Location Updates");
            }

            return false;
        }
    }

    private void addMarker(LatLng latLng) {
        Marker marker = map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_map_pin))
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .position(latLng));
        dropPinEffect(marker);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            map.clear();

            final android.os.Handler handler = new android.os.Handler();

            final Random random = new Random();
            final LatLng latLng = new LatLng(37.753310, -122.419161);
            for (int i = 0; i < 8; i++) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int plusMinus = (random.nextInt(2) == 0 ? -1 : 1);
                        addMarker(new LatLng(latLng.latitude + ((0.01 * random.nextInt(5)) * plusMinus), latLng.longitude + (0.001 * random.nextInt(20)) * plusMinus));
                    }
                }, i * 200);
            }
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onDisconnected() {

    }

    // Define a DialogFragment that displays the error dialog
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        // Default constructor. Sets the dialog field to null
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        // Set the dialog to display
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        // Return a Dialog to the DialogFragment.
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
}
