package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import java.util.List;

public class UpcomingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_page);

        RecyclerView recyclerView = findViewById(R.id.upcoming_RecyclerView);
        List<UpcomingTripsData> upcomingTripsData = DataManager.getInstance().getDays();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UpcomingTripsAdapter upcomingTripsAdapter = new UpcomingTripsAdapter(upcomingTripsData, UpcomingPage.this);
        recyclerView.setAdapter(upcomingTripsAdapter);
    }
}