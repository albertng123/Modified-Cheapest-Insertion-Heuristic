package com.example.finding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> arrayMapList = new ArrayList<>();
    private RequestQueue requestQueue;
    private AppCompatAutoCompleteTextView autoTextView;
    private static final String[] COUNTRIES = new String[] {
            "Belgium", "France", "Italy", "Germany", "Spain"
    };

    EditText mOrigin;
    Button tbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mOrigin = findViewById(R.id.mOrigin);
        requestQueue = Volley.newRequestQueue(this);
        getDestinationId();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.activity_list_item,arrayMapList);
        autoTextView = (AppCompatAutoCompleteTextView) findViewById(R.id.mOrigin);
        autoTextView.setThreshold(1);
        autoTextView.setAdapter(adapter);
    }



    private void getDestinationId(){
        System.out.println("GetIDDestinasi");
        String url ="https://maps.googleapis.com/maps/api/place/autocomplete/json?";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url+"&input="+"sunplaza"+"&key="+R.string.Api, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray jsonArray = response.getJSONArray("predictions");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ArrayMapList aMap = new ArrayMapList();
                                System.out.println(" id "+jsonObject.getString("id"));
                                aMap.setDestination(jsonObject.getString("description"));
                                aMap.setIdDestination(jsonObject.getString("id"));
                                arrayMapList.add(aMap);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(request);
    }
}


