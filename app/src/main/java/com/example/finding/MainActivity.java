package com.example.finding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.android.libraries.places.internal.d.b;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    int[][] destination = new int[5][5];
    int value;
    int tValue;
    String allLatLong;
    Button mFinding;
    String mOrginId,mFirstId,mSecondId,mThirdId,mFinalId;
    String[] kota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kota = new String[5];
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.Api));
        }
        {
            //Orgin
            PlacesClient OriginClient = Places.createClient(this);
            final AutocompleteSupportFragment mOrigin = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.mOrigin);

            mOrigin.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
            mOrigin.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    System.out.println("Place: " + place.getName() + ", " + place.getId());
                    mOrginId = place.getId();
                  //  kota[0] = place.getName();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    System.out.println("An error occurred: " + status);
                }
            });
            //FirstDestination
            PlacesClient FirstDestinationClient = Places.createClient(this);
            AutocompleteSupportFragment mFirstDestination = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.mFirstDestination);

            mFirstDestination.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
            mFirstDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    System.out.println("Place: " + place.getName() + ", " + place.getId());
                    mFirstId = place.getId();
                  //  kota[1] = place.getName();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    System.out.println("An error occurred: " + status);
                }
            });
            //SecondDestination
            PlacesClient SecondDestinationClient = Places.createClient(this);
            AutocompleteSupportFragment mSecondDestination = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.mSecondDestination);

            mSecondDestination.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
            mSecondDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    System.out.println("Place: " + place.getName() + ", " + place.getId());
                    mSecondId = place.getId();
                  //  kota[2] = place.getName();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    System.out.println("An error occurred: " + status);
                }
            });


            //ThirdDestination
            PlacesClient ThirdDestinationClient = Places.createClient(this);
            AutocompleteSupportFragment mThirdDestination = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.mThirdestination);

            mThirdDestination.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
            mThirdDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    System.out.println("Place: " + place.getName() + ", " + place.getId());
                    mThirdId = place.getId();
                 //   kota[3] = place.getName();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    System.out.println("An error occurred: " + status);
                }
            });
            //FinalDestination
            PlacesClient FinalDestinationClient = Places.createClient(this);
            AutocompleteSupportFragment mFinalDestination = (AutocompleteSupportFragment)
                    getSupportFragmentManager().findFragmentById(R.id.mFinalDestination);

            mFinalDestination.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
            mFinalDestination.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.
                    System.out.println("Place: " + place.getName() + ", " + place.getId());
                    mFinalId = place.getId();
                //    kota[4] = place.getName();
                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    System.out.println("An error occurred: " + status);
                }
            });
        }
        mFinding = findViewById(R.id.mFInding);
        mFinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sort");
                if (mFirstId == null || mSecondId == null || mThirdId == null || mThirdId == null || mFinalId == null) {
                    Toast.makeText(MainActivity.this, "Ups ada posisi yang kosong", Toast.LENGTH_SHORT).show();
                }
                allLatLong = "place_id:"+mOrginId+"|"+"place_id:"+mFirstId+"|"+"place_id:"+mSecondId+"|"+"place_id:"+mThirdId+"|"+"place_id:"+mFinalId;
                System.out.println(allLatLong);
                GetValue(allLatLong);
                Toast.makeText(MainActivity.this,"test"+value,Toast.LENGTH_SHORT).show();
                /*int[] letak = new int[5];
                letak[0] = 0;
                letak[4] = 4;
                int j1;
                mCIS(letak[0],letak[4],letak,1);
                for(int i=0;i<4;i++){
                    System.out.println("(" + kota[letak[i]] + ", " + kota[letak[i+1]] + ")");
                }*/

            }
        });

    }
    protected void mCIS(int first,int last,int[] letak,int b){
        for(int i =0;i<5;i++){
            for(int j=0;j<5;j++){
                if(i != letak[0]&& i!=letak[1]&& i!=letak[2]&& i!=letak[3]&& i!=letak[4]) {
                    if(j != letak[0]&& j!=letak[1]&& j!=letak[2]&& j!=letak[3]&& j!=letak[4]){
                        tValue = destination[first][i]+destination[j][last]-destination[first][last];
                        if(tValue<value){
                          value=tValue;
                        }
                        else {
                            letak[b] = i;
                        }
                    }
                }
            }
        }b++;
        if(b<4){
            mCIS(letak[b-2],letak[b-1],letak,b);
        }
    }

    protected void GetValue(String allLatLong) {
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?"+
                "&origins="+allLatLong+
                    "&destinations="+allLatLong+
                "&key="+getResources().getString(R.string.Api);
        System.out.println("URL = "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("Data sedang berjalan");
                        JSONObject jValue = null;
                        try {
                                for(int i=0;i<5;i++){
                                    for(int j=0;j<5;j++) {
                                        jValue = new JSONObject(response)
                                                .getJSONArray("rows").getJSONObject(i)
                                                .getJSONArray("elements").getJSONObject(j)
                                                .getJSONObject("distance");
                                        System.out.println("Test " + jValue.getInt("value"));
                                            destination[i][j]=jValue.getInt("value");
                                    }
                            }
                            System.out.println("Nilai = "+ destination[0][1]);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }
}


