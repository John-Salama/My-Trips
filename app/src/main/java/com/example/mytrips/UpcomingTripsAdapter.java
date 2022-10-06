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

public class UpcomingTripsAdapter extends RecyclerView.Adapter<UpcomingTripsAdapter.ViewHolder>{

    UpcomingTripsData[] mTripsData;
    Context mContext;
    public UpcomingTripsAdapter(UpcomingTripsData[] upcomingTripsData, UpcomingPage newTrip)
    {
        this.mTripsData = upcomingTripsData;
        this.mContext = newTrip;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.upcoming_trips_item_list,parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final UpcomingTripsData upcomingTripsDataList = mTripsData[position];

        holder.startTime_tv.setText(upcomingTripsDataList.getTripStartTime());
        holder.startDate_tv.setText(upcomingTripsDataList.getTripDateTime());
        holder.name_tv.setText(upcomingTripsDataList.getTripName());
        holder.status_tv.setText(upcomingTripsDataList.getTripStatus());
        holder.startLoc_tv.setText(upcomingTripsDataList.getTripStartLoc());
        holder.endLoc_tv.setText(upcomingTripsDataList.getTripEndLoc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,upcomingTripsDataList.getTripName(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView upcoming_img;
        TextView startTime_tv;
        TextView startDate_tv;
        TextView name_tv;
        TextView status_tv;
        TextView startLoc_tv;
        TextView endLoc_tv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            upcoming_img = itemView.findViewById(R.id.upcomig_img);
            startTime_tv = itemView.findViewById(R.id.upcoming_StartTime_tv);
            startDate_tv = itemView.findViewById(R.id.upcoming_StartDate_tv);
            name_tv = itemView.findViewById(R.id.upcoming_Name_tv);
            status_tv = itemView.findViewById(R.id.upcoming_StartLocation_tv);
            startLoc_tv = itemView.findViewById(R.id.upcoming_endLocation_tv);
        }
    }
}
