package com.example.power.naviau;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
     ArrayList<String> ID,Name,Description, Type,Latitude,Longitude,location;
     String LocationID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        location = new ArrayList<String>();
        ID=search_loction.ID;
        Name=search_loction.Name;
        Description=search_loction.Description;
        Type=search_loction.Type;
        Latitude=search_loction.Latitude;
        Longitude=search_loction.Longitude;
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
for(int i=0;i<ID.size();i++){
    LatLng sydney = new LatLng(Double.parseDouble(Latitude.get(i)), Double.parseDouble(Longitude.get(i)));
    Marker marker = mMap.addMarker(new MarkerOptions().position(sydney).title(Name.get(i)));
    location.add(marker.getId());
    // Add a marker in Sydney and move the camera

}
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(Latitude.get(0)), Double.parseDouble(Longitude.get(0)))));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(Latitude.get(0)), Double.parseDouble(Longitude.get(0))), 12.0f));
//Here setting onClickListener on mMap object of GoogleMap
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
                //searching marker id in locationDetailses and getting all the information of a particular marker
                for (int i = 0; i<location.size(); i++) {
                    //matching id so, alert dialog can show specific data
                    if (marker.getId().equals(location.get(i))){
                        builder.setTitle(Type.get(i));
                        builder.setMessage(Description.get(i));
                        LocationID=ID.get(i);                    }


                }

                builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton("Add to Favorite", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        location p = new location();
                        p.execute();
                    }
                });

                // Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


                // Add the button

                return false;
            }
        });


    }
    class location extends AsyncTask<Void, Void, String> {
        ProgressDialog Dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Dialog = new ProgressDialog(MapsActivity.this);
            Dialog.setMessage("Processing Your Request Please Wait");
            Dialog.setIndeterminate(false);
            Dialog.setCancelable(false);
            Dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Dialog.dismiss();
            Toast.makeText(MapsActivity.this, s,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(Void... v) {

            HashMap<String, String> post = new HashMap<>();
            post.put("userid", sigin.id);
            post.put("locationid", LocationID);

            ServerPostRequest sr = new ServerPostRequest();
            String page=sr.ServerAddress+"/naviau/Location.php";
            String s = sr.sendPostRequest(page, post);
            return s;



        }
    }


    }



