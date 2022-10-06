package com.example.mytrips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpcomingTripsAdapter extends RecyclerView.Adapter<UpcomingTripsAdapter.ViewHolder>{

    List<UpcomingTripsData> mTripsData;
    Context mContext;
    public UpcomingTripsAdapter(List<UpcomingTripsData> upcomingTripsData, UpcomingPage newTrip)
    {
        this.mTripsData = upcomingTripsData;
        this.mContext = newTrip;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.upcoming_trips_item_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.startTime_tv.setText(mTripsData.get(position).getTripStartTime());
        holder.startDate_tv.setText(mTripsData.get(position).getTripDateTime());
        holder.status_tv.setText(mTripsData.get(position).getTripStatus());
        holder.name_tv.setText(mTripsData.get(position).getTripName());
        holder.startLoc_tv.setText(mTripsData.get(position).getTripStartLoc());
        holder.upcoming_Status_tv.setText(mTripsData.get(position).getTripStatus());
    }

    @Override
    public int getItemCount() {
        return mTripsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView startTime_tv;
        TextView startDate_tv;
        TextView name_tv;
        TextView upcoming_Status_tv;
        TextView status_tv;
        TextView startLoc_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            startTime_tv = itemView.findViewById(R.id.upcoming_StartTime_tv);
            startDate_tv = itemView.findViewById(R.id.upcoming_StartDate_tv);
            upcoming_Status_tv = itemView.findViewById(R.id.upcoming_Status_tv);
            name_tv = itemView.findViewById(R.id.upcoming_Name_tv);
            status_tv = itemView.findViewById(R.id.upcoming_StartLocation_tv);
            startLoc_tv = itemView.findViewById(R.id.upcoming_endLocation_tv);
        }
    }
}
