package com.example.mytrips;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.AutoTransition;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import java.util.List;

public class UpcomingTripsAdapter extends RecyclerView.Adapter<UpcomingTripsAdapter.ViewHolder> {


    List<UpcomingTripsData> mTripsData;
    Context mContext;
    Intent mIntent;

    public UpcomingTripsAdapter(List<UpcomingTripsData> upcomingTripsData, Context newTrip) {
        this.mTripsData = upcomingTripsData;
        this.mContext = newTrip;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.upcoming_trips_item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.upcoming_Status_tv.setText(mTripsData.get(position).getTripStatus());
        holder.name_tv.setText(mTripsData.get(position).getTripName());
        holder.startTime_tv.setText("start time: ".concat(mTripsData.get(position).getTripStartTime()));
        holder.startDate_tv.setText("start date: ".concat(mTripsData.get(position).getTripDate()));
        holder.startLoc_tv.setText("start location: ".concat(mTripsData.get(position).getTripStartLoc()));
        holder.EndLoc_tv.setText("distnation: ".concat(mTripsData.get(position).getTripEndLoc()));
        if (mTripsData.get(position).getTripType() == 1) {
            holder.startTimeRound_tv.setText("back start time: ".concat(mTripsData.get(position).getTripRoundStartTime()));
            holder.startDateRound_tv.setText("back start date: ".concat(mTripsData.get(position).getTripRoundDate()));
        } else {
            holder.startTimeRound_tv.setVisibility(View.INVISIBLE);
            holder.startDateRound_tv.setVisibility(View.INVISIBLE);
        }

        if (!mTripsData.get(position).getTripStatus().equals("Upcoming"))
            holder.upcomingStartBtn.setVisibility(View.INVISIBLE);
        holder.menuIcon.setOnClickListener(view -> {
            PopupMenu tripMenu = new PopupMenu(mContext, holder.menuIcon);
            tripMenu.inflate(R.menu.trip_item_menu);
            tripMenu.show();
            tripMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.trip_menu_note) {
                    mIntent = new Intent(mContext, AddNotePage.class);
                    mIntent.putExtra("trip_name", mTripsData.get(position).getTripName());
                    mContext.startActivity(mIntent);
                } else if (id == R.id.trip_menu_cancel) {
                    Toast.makeText(mContext, "Trip Cancelled Successfully", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
        });

    }

    @Override
    public int getItemCount() {
        return mTripsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        int visibility;
        TextView startTime_tv;
        TextView startDate_tv;
        TextView name_tv;
        TextView upcoming_Status_tv;
        TextView startLoc_tv;
        TextView EndLoc_tv;
        TextView startTimeRound_tv;
        TextView startDateRound_tv;
        ImageView menuIcon;
        CardView trip;
        Button upcomingStartBtn;
        androidx.constraintlayout.widget.ConstraintLayout data;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_tv = itemView.findViewById(R.id.upcoming_Name_tv);
            upcoming_Status_tv = itemView.findViewById(R.id.upcoming_Status_tv);
            startTime_tv = itemView.findViewById(R.id.upcoming_StartTime_tv);
            startDate_tv = itemView.findViewById(R.id.upcoming_StartDate_tv);
            startLoc_tv = itemView.findViewById(R.id.upcoming_StartLocation_tv);
            EndLoc_tv = itemView.findViewById(R.id.upcoming_endLocation_tv);
            startTimeRound_tv = itemView.findViewById(R.id.upcoming_round_StartTime_tv);
            startDateRound_tv = itemView.findViewById(R.id.upcoming_round_StartDate_tv);
            menuIcon = itemView.findViewById(R.id.popup_img);
            data = itemView.findViewById(R.id.constrain_data);
            trip = itemView.findViewById(R.id.trip_card);
            upcomingStartBtn = itemView.findViewById(R.id.upcoming_Start_btn);

            itemView.setOnClickListener(view -> {
                visibility = data.getVisibility();
                Transition transition = new AutoTransition();
                if (visibility == View.VISIBLE) {
                    transition.setDuration(1);
                    TransitionManager.beginDelayedTransition(trip, transition);
                    visibility = View.GONE;
                } else if (visibility == View.GONE) {
                    transition.setDuration(300);
                    TransitionManager.beginDelayedTransition(trip, transition);
                    visibility = View.VISIBLE;
                }
                data.setVisibility(visibility);
            });
        }

    }
}
