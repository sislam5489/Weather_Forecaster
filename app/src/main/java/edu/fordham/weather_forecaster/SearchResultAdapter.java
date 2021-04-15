package edu.fordham.weather_forecaster;

import android.location.Address;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchResultAdapter extends RecyclerView.Adapter<AddressViewHolder>{
    List<Address> data;

    public SearchResultAdapter(List<Address> locations) {
        data = locations;
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item_view,parent,false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = data.get(position);
        //update the view
        holder.updateView(address);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
