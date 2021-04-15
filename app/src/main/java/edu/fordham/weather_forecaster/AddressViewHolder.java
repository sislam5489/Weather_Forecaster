package edu.fordham.weather_forecaster;

import android.content.Intent;
import android.location.Address;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressViewHolder extends RecyclerView.ViewHolder{
    TextView tv;
    public AddressViewHolder(@NonNull View itemView) {
        super(itemView);
        tv = itemView.findViewById(R.id.textView);
    }

    public void updateView(Address address){
        tv.setText(address.getAddressLine(0));
        tv.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WeatherActivity.class);
                intent.putExtra("name",address.getAddressLine(0));
                intent.putExtra("latitude",address.getLatitude());
                intent.putExtra("longitude",address.getLongitude());
                view.getContext().startActivity(intent);
            }
        });
    }
}
