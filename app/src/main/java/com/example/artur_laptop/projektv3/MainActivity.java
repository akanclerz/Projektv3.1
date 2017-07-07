package com.example.artur_laptop.projektv3;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements OnClickListener {

    private LocationManager locationManager = null;
    private LocationListener locationListener = null;

    private Button btnGetLocation = null;
    private EditText editLocation = null;
    private ProgressBar pb = null;

    public String s = "";
    private String do_zwrotu="";
    public static final String coordy = "Response";
    public static final String coodry_tylko = "Response_Only";



    private static final String TAG = "Debug";
    private Boolean flag = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button add_contact = (Button) findViewById(R.id.contact);
        Button send = (Button) findViewById(R.id.sendd);
        Button compass = (Button) findViewById(R.id.compass);

        add_contact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,AddContact.class);
                startActivity(intent);

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(do_zwrotu.equals("Nieznana lokacja lub nie uzyto GPS!!!")){
                    Toast.makeText(getApplicationContext(),
                            "Poczekaj aż GPS ustali Twoje współrzędne", Toast.LENGTH_SHORT).show();
                }
                else{
                    Context context;
                    context = getApplicationContext();
                    Intent intent = new Intent(context,Send.class);
                    intent.putExtra(coordy,s);
                    intent.putExtra(coodry_tylko,do_zwrotu);
                    startActivity(intent);
                }


            }
        });

        compass.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,Compass.class);
                startActivity(intent);

            }
        });

        //if you want to lock screen for always Portrait mode
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        editLocation = (EditText) findViewById(R.id.editTextLocation);

        btnGetLocation = (Button) findViewById(R.id.btnLocation);
        btnGetLocation.setOnClickListener(this);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        s = "Użyj przycisku GET LOCATION aby określić swoje położenie";
        do_zwrotu = "Nieznana lokacja lub nie uzyto GPS!!!";
        editLocation.setText(s);
    }

    public void onClick(View v) {
        flag = displayGpsStatus();
        if (flag) {

            Log.v(TAG, "onClick");
            s = "Ustalanie współrzędnych... Aby przyśpieszyć ten proces, nie stój w miejscu :)";

            editLocation.setText(s);

            pb.setVisibility(View.VISIBLE);
            locationListener = new MyLocationListener();

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
            locationManager.requestLocationUpdates(LocationManager
                    .GPS_PROVIDER, 5000, 10, locationListener);

        } else {
            alertbox("Gps Status!!", "Your GPS is: OFF");
        }
    }

    /*----Method to Check GPS is enable or disable ----- */
    private Boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if(gpsStatus) {
            return true;
        }
        else {
            return false;
        }
    }

    /*----------Method to create an AlertBox ------------- */
    protected void alertbox(String title, String mymessage){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Device's GPS is Disable")
                .setCancelable(false).setTitle("** GPS Status **")
                .setPositiveButton("Gps On", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        // finish the current activity
                        // AlertBoxAdvance.this.finish();
                        Intent myIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                        startActivity(myIntent);
                        dialog.cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        // cancel dialog box
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class MyLocationListener implements LocationListener{

        public void onLocationChanged(Location loc) {

            editLocation.setText("");
            pb.setVisibility(View.INVISIBLE);
            String longitude = loc.getLongitude() + "E";
            Log.v(TAG, longitude);

            String latitude =  loc.getLatitude() + "N";
            Log.v(TAG, latitude);

            String cityName = "Lublin";
            Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
            List<Address> addresses;

            try {
                addresses = gcd.getFromLocation(loc.getLatitude(), loc
                        .getLongitude(), 1);
                if (addresses.size() > 0)
                    System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            } catch (IOException e) {
                e.printStackTrace();
            }

            s = "Moja lokalizacja: "+"\n"+longitude + "\n" + latitude + "\n\n Miasto w którym się znajduję: " + cityName;
            do_zwrotu = longitude + " " + latitude;
            editLocation.setText(s);
        }

        public String getCoord()
        {
            return s;
        }



        public void onProviderDisabled(String provider){}

        public void onProviderEnabled(String provider){}

        public void onStatusChanged(String provider, int status, Bundle extras){}
    }
}
