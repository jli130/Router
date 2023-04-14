package com.example.router_route_creation;

//general imports
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
//getting the date
//imports for time picking functionality
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.TimePicker;
//general functionality imports
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
//Imports for google play location services
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
// imports for API utilization
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;
public class route_creation_prospective extends AppCompatActivity implements
        OnMapReadyCallback,
        OnMarkerDragListener,
        OnMarkerClickListener{

    //list of chip names for selecting transportation type
    int buttonCheck = 0;
    List<String> listChipRef = Arrays.asList("taxi", "bus", "bicycle", "car");
    int chipId = 0;
    FusedLocationProviderClient mFusedLocationClient;

    //initializes google map for use in constructors
    private GoogleMap mMap;

    //string that contains time in UTC format: 24:00:00
    String time = "";
    String end_time_parsed;
    String formatted_address_start;
    String formatted_address_end;
    String instructions = "";

    //string that stores date in YYYY/MM/DD format
    String dateFinal;

    //transport type
    String transportType_Upper = "";
    String transportType = "";

    //string for storing lat/long coords for start dest
    String startDest = "";

    String finalInstructions;
    String finalEndTime;

    //string for final built api call for HERE Api services
    String routeCreated;
    String apiCallCreated;

    //string for storing lat/lon coords for end dest
    String endDest = "";

    //Invokes the chip group for use in selecting a transport type
    //Adds chipClickListener for getting transport type
    public ChipGroup chipGroup;
    public View.OnClickListener chipClickListener;

    //Button for picking location
    Button LocBtPicker;

    //Textview for displaying location lat/lon
    TextView Loctextview;

    //Both used for storing a latitude and longitude for a start destination
    double StartLat = 0;
    double StartLong = 0;

    //Various markers used to place destinations on the Google Maps
    private Marker mSyracuseU;

    private Marker mGoogleHQ;

    private Marker mTimesSquare;

    private Marker mLasVegas;

    private Marker mMiami;

    //Marker that is draggable, used for selecting a dynamic destination
    private Marker mDest;

    //Textview for displaying location when a user selects current/start dest
    TextView ChooseLocTextView;

    //Button for the user to pick a time for their route
    private Button BtnPickTime;

    //Textview for displaying the user selected time
    private TextView selectedTimeTV;

    // Initializing other items
    // from layout file
    TextView latitudeTextView, longitTextView;
    int PERMISSION_ID = 44;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting content view to route creation page
        setContentView(R.layout.route_creation_prospective);
        chipGroup = findViewById(R.id.transportChipGroup);

        //initializing buttons for user clicks
        LocBtPicker = findViewById(R.id.ChooseLocButton);
        Loctextview = findViewById(R.id.latTextView);
        ChooseLocTextView = findViewById(R.id.lonTextView);

        //support fragment for displaying Google Maps Api
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.idRouteDisplayText, mapFragment).commit();
        mapFragment.getMapAsync(this);

        //Initializing textviews for displaying coordinates
        latitudeTextView = findViewById(R.id.latTextView);
        longitTextView = findViewById(R.id.lonTextView);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // method to get the location
        getLastLocation();

        //initializing buttons and textviews for user to click
        View ChooseLocButton = findViewById(R.id.ChooseLocButton);
        View CurrentLocButton = findViewById(R.id.CurrentLocButton);
        View pickTimeBtn = findViewById(R.id.idBtnPickTime);
        selectedTimeTV = findViewById(R.id.idTVSelectedTime);
        View genRouteBtn = findViewById(R.id.idBtnGenerateRoute);
        Button button = (Button) findViewById(R.id.goBack);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(route_creation_prospective.this, LogIn.class);
                startActivity(intent);
            }
        });
        //Method for when the user clicks on the choose start location button, sets the location to the last pinged location
        ChooseLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCheck = 1;
                getLastLocation();
                apiCallAddressCheckStart();

            }
        });
        CurrentLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonCheck = 0;
                //getLastLocation();
                //Loctextview.setText(mDest.getPosition().toString());
                //selectedTimeTV.setText(mDest.getPosition().toString());
                endDest = mDest.getPosition().toString();
                apiCallAddressCheckEnd();
                //selectedTimeTV.setText(endDest);
            }});

        //Button for generating the route using the parameters that the user has provided
        genRouteBtn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking the chip group
                int Chipid = chipGroup.getCheckedChipId();
                Chip chip = chipGroup.findViewById(Chipid);
                if (chip == null){
                    selectedTimeTV.setText("You did not select a transport type!");
                } else if (startDest == "") {
                    selectedTimeTV.setText("You did not select a start destination!");
                } else if (endDest == "") {
                    selectedTimeTV.setText("You did not select an end destination!");
                } else if (time == "") {
                    selectedTimeTV.setText("You did not select a time!");
                } else {
                    transportType_Upper = chip.getText().toString();
                    transportType = chip.getText().toString().toLowerCase(Locale.ROOT);
                    apiCall();
                }

            }
        }));
        pickTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting the
                // instance of our calendar.
                final Calendar c = Calendar.getInstance();
                //below we are constructing a date for use in the routing api call
                // in format YYYY-MM-DD
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                StringBuilder date = new StringBuilder();
                date.append(year);
                date.append("-");
                date.append(month+1);
                date.append("-");
                date.append(day);
                dateFinal = date.toString();
                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                StringBuilder stringbuilder = new StringBuilder();
                // on below line we are initializing our Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(route_creation_prospective.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                // on below line we are setting selected time
                                // in our text view.
                                String minuteText;
                                if (minute < 10){
                                    minuteText = "0" + minute;
                                }
                                else{
                                    minuteText = minute + "";
                                }
                                stringbuilder.append(hourOfDay);
                                stringbuilder.append(":");
                                stringbuilder.append(minuteText);
                                stringbuilder.append(":00");
                                String endText;
                                if (hourOfDay > 12){
                                    endText = "p.m.";
                                    hourOfDay = hourOfDay - 12;
                                }
                                else{
                                    endText = "a.m.";
                                }
                                selectedTimeTV.setText(hourOfDay + ":" + minuteText + " " + endText);
                                time = stringbuilder.toString();
                                //selectedTimeTV.setText(time);
                            }
                        }, hour, minute, false);
                // at last we are calling show to
                // display our time picker dialog.
                timePickerDialog.show();
            }
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng startPoint = new LatLng(37.4219983, -122.084);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 10F));
        mMap.isBuildingsEnabled();
        mMap.getUiSettings().setZoomControlsEnabled(true);
        addMarkersToMap();
        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);

    }
    private void addMarkersToMap() {
        // Uses a colored icon.
        mSyracuseU = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(43.0392, -76.1351))
                .title("Syracuse University")
                .snippet("Home of the Orange!")
                .draggable(false));

        // Uses a custom icon with the info window popping out of the center of the icon.
        mTimesSquare = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(40.7580, -73.9855))
                .title("Times Square")
                .snippet("Center of life in NYC!")
                .draggable(false));

        // Creates a draggable marker. Long press to drag.
        mMiami = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(25.7617, -80.1918))
                .title("Miami")
                .snippet("Nightlife hotspot of the world!")
                .draggable(false));
        mLasVegas = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(36.1716, -115.1391))
                .title("Las Vegas")
                .snippet("Come with money, leave without!")
                .draggable(false));
        mGoogleHQ = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4221, -122.0841))
                .title("Google HQ")
                .snippet("Creators of wonderful technologies!")
                .draggable(false));
        mDest = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4225,-122.0843))
                .title("Location Picker")
                .draggable(true)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }
    private BitmapDescriptor vectorToBitmap(@DrawableRes int id, @ColorInt int color) {
        Drawable vectorDrawable = ResourcesCompat.getDrawable(getResources(), id, null);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        DrawableCompat.setTint(vectorDrawable, color);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            String latitude = String.valueOf(location.getLatitude());
                            String longitude = String.valueOf(location.getLongitude());
                            StartLat = location.getLatitude();
                            StartLong = location.getLongitude();
                            stringBuilder.append("lat/long: (");
                            stringBuilder.append(latitude);
                            stringBuilder.append(",");
                            stringBuilder.append(longitude);
                            stringBuilder.append(")");
                            if (buttonCheck == 0){
                                latitudeTextView.setText(stringBuilder.toString());
                            } else if (buttonCheck == 1) {
                                ChooseLocTextView.setText(stringBuilder.toString());
                                startDest = stringBuilder.toString();
                                selectedTimeTV.setText(stringBuilder.toString());
                            }else{
                                latitudeTextView.setText("Some error has occurred");
                            }



                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitudeTextView.setText("Latitude: " + mLastLocation.getLatitude() + "");
            StartLat = mLastLocation.getLatitude();
            double lat = mLastLocation.getLatitude();
            longitTextView.setText("Longitude: " + mLastLocation.getLongitude() + "");
            double lon = mLastLocation.getLongitude();
            StartLong = mLastLocation.getLongitude();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }
    public void apiCall(){
        //this is for HERE api calls
        String Access_key_ID = "JUBrp8YPTzvUHIpL8Z9D0Q";
        String Access_key_secret = "wrhQfDchnlFciDsdx5RxRBaPazztz_V2FM9TT0OH2sDcECa_CVYD77sQsn9bP6YidG7Yx3nds3UmXIh3emdknA";
        String API_KEY = "BgAbdirQQKjYwuUADax7Ye1eEMaWSwVxaONKJNPjYG4";
        String API_Start = "https://router.hereapi.com/v8/routes?";
        StringBuilder routing = new StringBuilder();
        routing.append(API_Start);
        routing.append("transportMode=");
        //adds the transportation type the user picks
        routing.append(transportType);
        String StartCoords = "";
        String EndCoords = "";
        String[] arrOfCoordStart = startDest.split("[()]");
        String[] arrOfCoordEnd = endDest.split("[()]");
        StartCoords = arrOfCoordStart[1];
        EndCoords = arrOfCoordEnd[1];
        routing.append("&origin=");
        routing.append(StartCoords);
        routing.append("&destination=");
        routing.append(EndCoords);
        routing.append("&return=polyline,actions,instructions&departureTime=");
        routing.append(dateFinal);
        routing.append("T");
        routing.append(time);
        routing.append("&apikey=");
        routing.append(API_KEY);
        routeCreated = routing.toString();
        //routing.append

        //'https://router.hereapi.com/v8/routes?transportMode=car&origin=52.5308,13.3847&destination=52.5323,13.3789&return=summary&departureTime=2019-10-02T17:00:00&apikey={YOUR_API_KEY}'
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest ExampleStringRequest = new JsonObjectRequest(Request.Method.GET, routeCreated, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response){
                TextView textView = (TextView) findViewById(R.id.idTVSelectedTime);
                textView.setText(response.toString());
                try {
                    boolean time_flag = false;
                    String results = response.getJSONArray("routes").getString(0);
                    String[] second = results.split("\"");
                    String end_time_unparsed = "";
                    for(int i = 0; i < second.length; i++){
                        if (second[i].contains("instruction") ){
                            instructions += (second[i+2]);
                            instructions += "\n";
                        } else if (second[i].contains("time")) {
                            if (time_flag == false){
                                time_flag = true;
                            } else if (time_flag == true) {
                                end_time_unparsed = second[i+2];
                                String[] end_time_intermediate = end_time_unparsed.split("T");
                                String[] end_time_parse = end_time_intermediate[1].split("-");
                                end_time_parsed = end_time_parse[0];

                            }
                        }
                    }
                    Intent intent = new Intent(route_creation_prospective.this, routeDisplay_prospective.class);
                    intent.putExtra("type",transportType_Upper);
                    intent.putExtra("start_time", time);
                    intent.putExtra("start_dest", formatted_address_start);
                    intent.putExtra("end_dest", formatted_address_end);
                    intent.putExtra("end_time", end_time_parsed);
                    intent.putExtra("directions", instructions);
                    Log.e("routeCreate", "Datapassed" + instructions);
                    startActivity(intent);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        ExampleRequestQueue.add(ExampleStringRequest);
    }
    public void apiCallAddressCheckEnd(){
        //this is for Google api calls
        //Uses string builder to create the final api call
        String API_Start = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        StringBuilder routing = new StringBuilder();
        routing.append(API_Start);
        //Coordinates will change and be added dynamically
        String StartCoords = "";
        String[] arrOfCoordStart = endDest.split("[()]");
        StartCoords = arrOfCoordStart[1];
        routing.append(StartCoords);
        routing.append("&key=");
        routing.append("AIzaSyDHo1_FlnHVGnLX0yDeXI9Lu0zG1D_npxQ");
        apiCallCreated = routing.toString();
        //routing.append

        //'https://router.hereapi.com/v8/routes?transportMode=car&origin=52.5308,13.3847&destination=52.5323,13.3789&return=summary&departureTime=2019-10-02T17:00:00&apikey={YOUR_API_KEY}'
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest ExampleStringRequest = new JsonObjectRequest(Request.Method.GET, apiCallCreated, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                TextView textView = (TextView) findViewById(R.id.latTextView);
                textView.setText(response.toString());
                try {
                    String results = response.getJSONArray("results").getString(1);
                    String[] formatted_address2 = results.split("formatted_address");
                    String formatted_address3 = formatted_address2[1];
                    String[] formatted_address4 = formatted_address3.split("\"");
                    formatted_address_end = formatted_address4[2];
                    textView.setText(formatted_address_end);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        ExampleRequestQueue.add(ExampleStringRequest);
    }

    public void apiCallAddressCheckStart(){
        //this is for Google api calls
        //Uses string builder to create the final api call
        String API_Start = "https://maps.googleapis.com/maps/api/geocode/json?latlng=";
        StringBuilder routing = new StringBuilder();
        routing.append(API_Start);
        //Coordinates will change and be added dynamically
        String StartCoords = "";
        String[] arrOfCoordStart = startDest.split("[()]");
        StartCoords = arrOfCoordStart[1];
        routing.append(StartCoords);
        routing.append("&key=");
        routing.append("AIzaSyDHo1_FlnHVGnLX0yDeXI9Lu0zG1D_npxQ");
        apiCallCreated = routing.toString();
        //routing.append

        //'https://router.hereapi.com/v8/routes?transportMode=car&origin=52.5308,13.3847&destination=52.5323,13.3789&return=summary&departureTime=2019-10-02T17:00:00&apikey={YOUR_API_KEY}'
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest ExampleStringRequest = new JsonObjectRequest(Request.Method.GET, apiCallCreated, null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                TextView textView = (TextView) findViewById(R.id.lonTextView);
                textView.setText(response.toString());
                try {
                    String results = response.getJSONArray("results").getString(1);
                    String[] formatted_address2 = results.split("formatted_address");
                    String formatted_address3 = formatted_address2[1];
                    String[] formatted_address4 = formatted_address3.split("\"");
                    formatted_address_start = formatted_address4[2];
                    textView.setText(formatted_address_start);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });
        ExampleRequestQueue.add(ExampleStringRequest);
    }


    public SupportMapFragment newInstance (GoogleMapOptions options){
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.idRouteDisplayText, mapFragment)
                .commit();

        return mapFragment;
    }
    @Override
    public void onMarkerDragStart(Marker marker) {
        ChooseLocTextView.setText("    You are setting the start destination!");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        String location = marker.getPosition().toString();
        ChooseLocTextView.setText(location);
        startDest = location;
        apiCallAddressCheckStart();
        //selectedTimeTV.setText(location);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        String location = marker.getPosition().toString();
        //ChooseLocTextView.setText(location);
        startDest = location;
        //selectedTimeTV.setText(location);
    }
    @SuppressLint("SuspiciousIndentation")
    @Override
    public boolean onMarkerClick(Marker marker){
        if(marker.isDraggable() == false){
            ChooseLocTextView.setText(marker.getPosition().toString());
            startDest = marker.getPosition().toString();
            apiCallAddressCheckStart();
            //selectedTimeTV.setText(marker.getPosition().toString());
            return true;
        }else
            return false;
    }
}
