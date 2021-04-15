package edu.fordham.weather_forecaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView searchList;
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        geocoder = new Geocoder(this);

        searchList = findViewById(R.id.RecyclerView);
        searchList.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            //when user submits query
            @Override
            public boolean onQueryTextSubmit(String query) {

                //to handle errors
                try {
                    List<Address> locations = geocoder.getFromLocationName(query,5);
                    for(Address a: locations){
                        Log.i("weather.address", a.toString());
                    }
                    SearchResultAdapter adapter = new SearchResultAdapter(locations);
                    searchList.setAdapter(adapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
            //updates real time query
            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
}