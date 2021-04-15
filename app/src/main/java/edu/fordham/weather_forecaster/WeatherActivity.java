package edu.fordham.weather_forecaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {

    TextView forecastTextView;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        queue = Volley.newRequestQueue(WeatherActivity.this);

        Intent intent = getIntent();
        String addr = intent.getStringExtra("name");
        double lattitude = intent.getDoubleExtra("latitude",0);
        double longitude = intent.getDoubleExtra("longitude",0);

        TextView addressTextView = findViewById(R.id.addressTextView);
        addressTextView.setText(addr);

        TextView forecastTextView = findViewById(R.id.forecastTextView);

        //make the request to weather.gov
        //param:lat,long

        String url = "https://api.weather.gov/points/" + lattitude + "," + longitude;
        makeRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Hello", response.toString());

                try {
                    //get the url from response
                    JSONObject properties = response.getJSONObject("properties");
                    String forecastUrl = properties.getString("forecast");

                    //make a second request using the url
                    makeRequest(forecastUrl, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject properties = response.getJSONObject("properties");
                                JSONArray periods = properties.getJSONArray("periods");
                                String forecast = "";
                                for(int i = 0; i < periods.length(); i++){
                                    JSONObject object = periods.getJSONObject(i);
                                    String name = object.getString("name");
                                    String shortForecast = object.getString("shortForecast");
                                    forecast += name + "\n" + shortForecast + "\n\n";
                                }

                                //set the text view
                                forecastTextView.setText(forecast);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    void makeRequest(String url, Response.Listener<JSONObject> listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                url,
                null,
                listener,
                null
        ){
            /*
                Returns a list of extra HTTP headers to go along with this request
                Can throw Auth Failure Error as authentication may be requured to provide these values
                user agent required to identify application
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                //User-Agent: (myweatherapp.com, contact@myweatherapp.com
                Map<String, String> headers = new HashMap<>();
                headers.put("User-Agent","Demo weather forecast app");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
    }
}