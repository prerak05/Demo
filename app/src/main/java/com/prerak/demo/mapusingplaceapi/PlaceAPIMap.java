package com.prerak.demo.mapusingplaceapi;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.prerak.demo.MainActivity;
import com.prerak.demo.R;
import com.prerak.demo.googlemapdirection.DirectionsJSONParser;
import com.prerak.demo.googlemapdirection.MapsActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by emxcel on 18/12/17.
 */

public class PlaceAPIMap extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {
    // variable declaration
    private EditText et_pickup_add, et_destination_add;
    private GoogleMap mMap;
    LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    LatLng sourceLatLang;
    LatLng destinationLatLng;
    double currentLat, currentLng;
    String strSourceAdd,strDestinationAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_api_map_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        init();
        mFusedLocationClient = new FusedLocationProviderClient(this);
        mLocationRequest = new LocationRequest();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(30);
        mLocationRequest.setInterval(1000);
    }

    private void init() {
        et_pickup_add = (EditText) findViewById(R.id.et_pickup_add);
        et_pickup_add.setOnClickListener(this);
        et_destination_add = (EditText) findViewById(R.id.et_destination_add);
        et_destination_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.et_pickup_add) {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(PlaceAPIMap.this);
                startActivityForResult(intent, 100);
            } catch (GooglePlayServicesRepairableException e) {
                // Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // Handle the error.
            }

        } else if (view.getId() == R.id.et_destination_add) {
            try {
                Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(PlaceAPIMap.this);
                startActivityForResult(intent, 101);
            } catch (GooglePlayServicesRepairableException e) {
                // Handle the error.
            } catch (GooglePlayServicesNotAvailableException e) {
                // Handle the error.
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 100 && resultCode == RESULT_OK) {
            // retrive the data by using getPlace() method.
            Place place = PlaceAutocomplete.getPlace(this, data);
            Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
            strSourceAdd = place.getAddress().toString();
            et_pickup_add.setText(strSourceAdd);
            sourceLatLang = place.getLatLng();
            Toast.makeText(this, "sourceLatLang " + sourceLatLang, Toast.LENGTH_SHORT).show();
        } else if (requestCode == 101 && resultCode == RESULT_OK) {
            // retrive the data by using getPlace() method.
            Place place = PlaceAutocomplete.getPlace(this, data);
            destinationLatLng = place.getLatLng();
            Toast.makeText(this, "destinationLatLng" + destinationLatLng, Toast.LENGTH_SHORT).show();
            Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());
            strDestinationAdd = place.getAddress().toString();
            et_destination_add.setText(strDestinationAdd);
        } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
            Status status = PlaceAutocomplete.getStatus(this, data);
            // TODO: Handle the error.
            Log.e("Tag", status.getStatusMessage());
            Toast.makeText(PlaceAPIMap.this, "GPS not working..", Toast.LENGTH_LONG).show();

        } else if (resultCode == RESULT_CANCELED) {
            // The user canceled the operation.
            Toast.makeText(PlaceAPIMap.this, "The user canceled the operation.", Toast.LENGTH_LONG).show();
        }

         if (sourceLatLang != null && destinationLatLng != null){
             drawRout(sourceLatLang,destinationLatLng);
         }
    }

    private void drawRout(LatLng sourceLatLang, LatLng destinationLatLng) {

        String url = getDirectionsUrl(new LatLng(sourceLatLang.latitude,sourceLatLang.longitude), new LatLng(destinationLatLng.latitude,destinationLatLng.longitude));

        DownloadTask downloadTask = new DownloadTask();

        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng latLng, LatLng latLng1) {
        // Origin of route
        String str_origin = "origin=" + latLng.latitude + "," + latLng.longitude;

        // Destination of route
        String str_dest = "destination=" + latLng1.latitude + "," + latLng1.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;

        return url;
    }

    //Todo: Internet connection check
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class DownloadTask  extends AsyncTask<String,Void,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(strings[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(s);
        }
    }
    private String downloadUrl(String string) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(string);

            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception ", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            LatLng position = null;
            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                     position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.BLUE);
            }

            // Drawing polyline in the Google Map for the i-th route
            if (lineOptions != null) {
                mMap.addPolyline(lineOptions);
                if (strSourceAdd.length() != 0 && strDestinationAdd.length() != 0) {
                    et_pickup_add.setText("");
                    et_destination_add.setText("");
                }
//                drawRout(position, position);
            }
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
        mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) ;
                currentLat = location.getLatitude();
                Toast.makeText(PlaceAPIMap.this, "lat" + currentLat, Toast.LENGTH_SHORT).show();
                currentLng = location.getLongitude();
                Toast.makeText(PlaceAPIMap.this, "lng" + currentLng, Toast.LENGTH_SHORT).show();
                LatLng current = new LatLng(currentLat, currentLng);
                mMap.addMarker(new MarkerOptions().position(current).title("Marker in Current"));
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(new LatLng(currentLat,currentLng))
                        .zoom(14f)
                        .build()));

            }
        });
    }
}
