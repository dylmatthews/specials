package am.dx.varsityspecials.www.varsityspecials;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import direction.DirectionFinder;
import direction.DirectionFinderListener;
import direction.Journey;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {

    private GoogleMap mMap;
    private String destination = "30 Oxfrod drive durban north";
    private String origin = "16 Savell Avenue glenashley";
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    LocationManager locationManager;
    Location location;
    private double latitude;
    private double longitude;
    private String area = "";
    private String name = "";
    private LocationListener listener;

    private Button btnFindPath;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            area = getIntent().getStringExtra("area");
            name = getIntent().getStringExtra("name");
            destination = name + "," + area;
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            btnFindPath = (Button) findViewById(R.id.btnFindPath);


            btnFindPath.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        sendRequest();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });


            listener = new LocationListener() {
                @Override
                public void onLocationChanged(Location locatio) {
                    // Toast.makeText(MapsActivity.this, "Location changed", Toast.LENGTH_SHORT).show();

                    // Called when a new location is found by the network location provider.
                    if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    //  locationManager = (LocationManager)
                    //         getSystemService(Context.LOCATION_SERVICE);
                    //  Criteria criteria = new Criteria();
                    // criteria.setAccuracy(Criteria.ACCURACY_FINE);

                    // location = locationManager.getLastKnownLocation(locationManager
                    //    .NETWORK_PROVIDER);

                    latitude = locatio.getLatitude();
                    longitude = locatio.getLongitude();

                    Log.i("ashitLoca", " " + latitude + "," + longitude);
                    // Toast.makeText(this, latitude + " , " + longitude, Toast.LENGTH_SHORT).show();
                    // sendRequest();

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(i);
                }


            };
        } catch (Exception e) {
            Toast.makeText(this, "Shit " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    private void sendRequest() throws InterruptedException {
        // String origin = etOrigin.getText().toString();
        // String destination = etDestination.getText().toString();
        locationManager.requestLocationUpdates("gps", 100, 0, listener);

        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        // Criteria criteria = new Criteria();
        //criteria.setAccuracy(Criteria.ACCURACY_FINE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        //location = locationManager.getLastKnownLocation(locationManager//.NETWORK_PROVIDER);//.GPS_PROVIDER);
        //        .getBestProvider(criteria, false));
        //latitude = location.getLatitude();
        //longitude = location.getLongitude();
        progressDialog = ProgressDialog.show(getBaseContext(), "Please wait.",
                "Finding Location..!", true);
        while (latitude+""=="0.0,0.0" && longitude+""=="0.0,0.0")
        {
            locationManager.requestLocationUpdates("gps", 100, 0, listener);
        }
        progressDialog.dismiss();
        origin = latitude + "," + longitude;
        Toast.makeText(this, "origin" + origin, Toast.LENGTH_SHORT).show();
        Log.i("logOrigin", origin);


        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
       // Toast.makeText(this, "If route not found, click again. Location not found", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        progressDialog = ProgressDialog.show(getBaseContext(), "Please wait.",
                "Finding Location..!", true);
        //  LatLng hcmus = new LatLng(10.762963, 106.682394);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        locationManager.requestLocationUpdates("gps", 10, 0, listener);

        while (latitude+""=="0.0 , 0.0" && longitude+""=="0.0 , 0.0")
        {
            locationManager.requestLocationUpdates("gps", 100, 0, listener);
        }
        progressDialog.dismiss();
        //location = locationManager.getLastKnownLocation(locationManager//NETWORK_PROVIDER);
        //      .getBestProvider(criteria, false));
        // latitude = location.getLatitude();
        //longitude = location.getLongitude();
        Toast.makeText(this, latitude + " , " + longitude, Toast.LENGTH_SHORT).show();
        LatLng hcmus = new LatLng(latitude, longitude);
        //  LatLng l = new LatLng(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 18));
        originMarkers.add(mMap.addMarker(new MarkerOptions()
                .title("Current location")
                .position(hcmus)));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        // mMap.setMyLocationEnabled(true);
        ///Toast.makeText(this, (CharSequence) mMap.getMyLocation(), Toast.LENGTH_SHORT).show();
        // LatLng loc = new LatLng(mMap.setMyLocation());

    }


    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onPause() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        super.onPause();
        locationManager.removeUpdates(listener);
    }



    @Override
    public void onDirectionFinderSuccess(List<Journey> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Journey journey : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(journey.startLocation, 13));
            //  ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            //((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_flag_black_24dp))
                    .title(journey.startAddress)
                    .position(journey.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_flag_black_24dp))
                    .title(journey.endAddress)
                    .position(journey.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(10);

            for (int i = 0; i < journey.points.size(); i++)
                polylineOptions.add(journey.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

}

