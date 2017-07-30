package am.dx.varsityspecials.www.varsityspecials;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ScrollingActivity extends AppCompatActivity {

    private String number = "";
   private String name="";
    private  String des ="";
    private String area="";
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        tv = (TextView) findViewById(R.id.tvScroll) ;
        setSupportActionBar(toolbar);
        name = getIntent().getStringExtra("name");
       des = getIntent().getStringExtra("description");
        number = getIntent().getStringExtra("number");
        area = getIntent().getStringExtra("area");
        String time = getIntent().getStringExtra("time");
        String location = getIntent().getStringExtra("location");
        String price =getIntent().getStringExtra("price");
        setTitle(name);
        tv.setText("The time for this special, for "+ name + "\n\t" + time +"\nYou'll find " + name + " at " + location+ ".\n\nThe specials are :\n" + des +"\n\n\nCurrently the specials are submitted by users.\nIt is recommended that you phoone the place before hand and ask if the special is still on.\nAlso to book a table");


        FloatingActionButton nav = (FloatingActionButton) findViewById(R.id.nav);
        nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {



                    if (isLocationPermissionGranted()) {
                        locationg();
                    } else {
                        try {
                            locationg();
                        } catch (Exception ex) {
                            Toast.makeText(ScrollingActivity.this, "Accept the location permission to call", Toast.LENGTH_SHORT).show();
                        }

                    }


                } catch (Exception e) {
                    Toast.makeText(ScrollingActivity.this, "Test " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });



        FloatingActionButton phone = (FloatingActionButton) findViewById(R.id.call);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPermissionGranted()) {
                    call_action();
                } else {
                    try {
                        call_action();
                    } catch (Exception ex) {
                        Toast.makeText(ScrollingActivity.this, "Accept the calling permission to call", Toast.LENGTH_SHORT).show();
                    }

                }


                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

            }


        });
    }

    private boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
               locationg();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }

    }

    private void locationg() {
        Intent intent = new Intent(ScrollingActivity.this, MapsActivity.class);
        intent.putExtra("area", area);
        intent.putExtra("name", name);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }


    public void call_action() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        else {
            startActivity(callIntent);
        }
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
               call_action();
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(ScrollingActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                  // call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



}
