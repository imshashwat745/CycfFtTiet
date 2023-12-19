package com.imshashwat745.cycfit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<LocationModel> lst;
    private BookButtonClickInterface bookButtonClickInterface;
    public LocationAdapter(List<LocationModel> l,BookButtonClickInterface b){
        this.lst=l;
        this.bookButtonClickInterface=b;
    }
    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_rv_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        holder.availableBikes.setText(String.valueOf(lst.get(position).getAvailableBikes())+" bikes available");
        holder.locationName.setText(lst.get(position).getLocationName());
        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookButtonClickInterface.click(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lst.size();
    }
    public interface BookButtonClickInterface{
        void click(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView availableBikes,locationName;
        private Button bookButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            availableBikes=itemView.findViewById(R.id.available_bikes);
            locationName=itemView.findViewById(R.id.location_name);
            bookButton=itemView.findViewById(R.id.bookButton);
        }
    }
}
