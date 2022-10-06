package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

public class UpcomingPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_page);

        RecyclerView recyclerView = findViewById(R.id.upcoming_RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        UpcomingTripsData[] upcomingTripsData = new UpcomingTripsData[]{
          new UpcomingTripsData(";","ppp","oo","pp","lll"
          ,"gg",0,"pp","ll")
        };

        UpcomingTripsAdapter upcomingTripsAdapter = new UpcomingTripsAdapter(upcomingTripsData, UpcomingPage.this);
        recyclerView.setAdapter(upcomingTripsAdapter);


    }
}