package com.computer.team8.DSTR;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;

import com.computer.team8.DSTR.projectui.Preferences;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;


public class GoogleMapsActivity extends FragmentActivity {
    private GoogleMap googleMap;

    EditText addressEditText;
    LatLng addressPos;
    Marker addressMarker;
    Polyline line;

    Context activityContext;

    ArrayList<LatLng> markers = new ArrayList<>();
    ArrayList<Float> xyz = new ArrayList<>();

    float MIN_HEIGHT = 1f;
    float MAX_HEIGHT = 15f;

    // used to keep track centered in game
    float START_LAT;
    float START_LONG;

    public void getTrack(View view) {
        if (xyz.size() > 0) {
            // ensure track is complete aka % 3 == 0
            int diff = xyz.size() % 3;
            if (diff != 0) {
                while (diff > 0) {
                    xyz.remove( xyz.size() );
                    --diff;
                }
            }
            Preferences.addGoogleTrack(xyz);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        activityContext = this;


        addressEditText = (EditText) findViewById(R.id.addressEditText);

        try {
            if(googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }

            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            googleMap.setMyLocationEnabled(true);
            googleMap.setTrafficEnabled(true);
            googleMap.setIndoorEnabled(true);
            googleMap.setBuildingsEnabled(true);
            googleMap.getUiSettings().setZoomControlsEnabled(true);
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                @Override
                public void onMapClick(LatLng point) {

                    Random rand = new Random();
                    float z = rand.nextFloat() *
                              (MAX_HEIGHT - MIN_HEIGHT) + MIN_HEIGHT;

                    MarkerOptions marker = new MarkerOptions().position(
                            new LatLng(point.latitude, point.longitude)).title(String.valueOf(point.latitude)
                                        + ", " + String.valueOf(point.longitude) + ", " + String.valueOf(z));

                    googleMap.addMarker(marker);

                    markers.add(point);

                    if (xyz.size() == 0) {
                        START_LAT = (float)point.latitude;
                        START_LONG = (float)point.longitude;
                    }

                    xyz.add((float) point.latitude - START_LAT);
                    xyz.add(z);
                    xyz.add((float) point.longitude - START_LONG);

                    PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
                    for (int i = 0; i < markers.size(); i++) {
                        LatLng newPoint = markers.get(i);
                        options.add(newPoint);
                    }
                    line = googleMap.addPolyline(options);
                }
            });

            googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                @Override
                public void onMapLongClick(LatLng point) {
                    //Remove all the markers from the map
                    googleMap.clear();
                    markers.clear();
                    xyz.clear();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAddressMarker(View view) {
        String newAddress = addressEditText.getText().toString();

        if (newAddress != null) {
            new PlaceMarker().execute(newAddress);
        } //else System.out.print("Error - Address Null");

    }

    class PlaceMarker extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            String startAddress = params[0];
            startAddress = startAddress.replace(" ", "%20");

            try {
                getLatLong(startAddress);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Random rand = new Random();
            float z = rand.nextFloat() *
                    (MAX_HEIGHT - MIN_HEIGHT) + MIN_HEIGHT;

            addressMarker = googleMap.addMarker(new MarkerOptions().position(addressPos).title(String.valueOf(addressPos.latitude)
                                                  + ", " + String.valueOf(addressPos.longitude) + ", " + z).draggable(true));
            markers.add(addressPos);

            xyz.add((float) addressPos.latitude);
            xyz.add(z);
            xyz.add((float) addressPos.longitude);

        }
    }

    protected void getLatLong(String address) throws UnsupportedEncodingException {
        String query = URLEncoder.encode(address, "utf-8");
        String uri = "http://maps.google.com/maps/api/geocode/json?address="+query+"&sensor=false";

        HttpGet httpGet = new HttpGet(uri);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;

        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();

            int byteData;
            while ((byteData = stream.read()) != -1) {
                stringBuilder.append((char) byteData);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        double lat = 0.0, lng = 0.0;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(stringBuilder.toString());

            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lng");

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                    .getJSONObject("geometry").getJSONObject("location")
                    .getDouble("lat");

            addressPos = new LatLng(lat, lng);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
