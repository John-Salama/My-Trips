package com.example.mytrips;

import static java.lang.String.format;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddTripPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText tripName;
    EditText tripStartLoc;
    EditText tripEndLoc;
    EditText tripDate;
    EditText tripTime;
    Spinner mSpinner;
    EditText tripDateRound;
    EditText tripTimeRound;
    Button btn_addTrip;
    Button btn_addTripRound;

    Calendar calendar;
    TimePickerDialog timePicker;
    int currentHour;
    int currentMinute;
    boolean roundFlag =false; //normal trip
    Date takeoffDate;

    double doubleStartLat;
    double doubleStartLong;
    double doubleEndLat;
    double doubleEndLong;

    UpcomingTripsData mTripData = new UpcomingTripsData();
    UpcomingTripsData mRoundTripData = new UpcomingTripsData();

    Intent mIntent;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_page);
        initializeContent();
        setCalendarPicker(tripDate , roundFlag);
        setTimePicker(tripTime, roundFlag);
        setSpinner();
        addTrip();
        addRoundTrip();
    }
    private void initializeContent() {
        tripName = findViewById(R.id.tripName_editTxt);
        tripStartLoc =  findViewById(R.id.tripStart_editTxt);
        tripEndLoc = findViewById(R.id.tripEnd_editTxt);
        tripDate = findViewById(R.id.tripDate_editTxt);
        tripTime = findViewById(R.id.tripTime_editTxt);
        mSpinner = findViewById(R.id.spinner_tritpType);
        tripDateRound = findViewById(R.id.tripDateRound_editTxt);
        tripTimeRound = findViewById(R.id.tripTimeRound_editTxt);
        btn_addTrip = findViewById(R.id.addTrip_btn);
        btn_addTripRound = findViewById(R.id.addTripRound_btn);

    }

    private void setCalendarPicker(final EditText date1 , Boolean round) {
        calendar =Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR , year);
                calendar.set(Calendar.MONTH , month);
                calendar.set(Calendar.DAY_OF_MONTH , day);
                updateCalendar();
            }
            private void updateCalendar() {
                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                if(!round)
                if (calendar.getTimeInMillis() > (System.currentTimeMillis())) {
                    date1.setText(selectedDate);
                    takeoffDate = calendar.getTime();
                }
                else
                {
                    Toast.makeText(AddTripPage.this, "Date is in the past",Toast.LENGTH_LONG).show();
                }
                else
                {
                    if (calendar.getTimeInMillis() > takeoffDate.getTime()) {
                        date1.setText(selectedDate);
                    }
                    else
                    {
                        Toast.makeText(AddTripPage.this, "Return date must be after trip date",Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
        date1.setOnClickListener(view -> new DatePickerDialog(AddTripPage.this, date
                ,calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    @SuppressLint("DefaultLocale")
    private void setTimePicker(EditText time, Boolean round) {
        time.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddTripPage.this, (timePicker, hours, minutes) -> {
                if (!round) {
                    if (hours > currentHour) {
                        time.setText(format("%02d:%02d", hours, minutes));
                    } else {
                        Toast.makeText(AddTripPage.this, "Time is in the past", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    time.setText(format("%02d:%02d", hours, minutes));
                }
            },currentHour,currentHour,false);
            timePicker.show();
        });
    }
    private void setSpinner()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.tripType_array
                , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
       if(position == 0)
        {
            roundFlag = false;
            tripDateRound.setVisibility((View.INVISIBLE));
            tripTimeRound.setVisibility((View.INVISIBLE));
            btn_addTrip.setVisibility(View.VISIBLE);
            btn_addTripRound.setVisibility(View.INVISIBLE);
        }
       else if(position == 1)
        {

            if(tripTime.getText().toString().isEmpty() ||tripDate.getText().toString().isEmpty())
            {

                mSpinner.setSelection(0);
                Toast.makeText(AddTripPage.this, "Choose takeoff date & time first",Toast.LENGTH_LONG).show();
            }
            else
            {
            roundFlag = true;
            tripDateRound.setVisibility((View.VISIBLE));
            tripTimeRound.setVisibility((View.VISIBLE));
            btn_addTrip.setVisibility(View.INVISIBLE);
            btn_addTripRound.setVisibility(View.VISIBLE);
            setCalendarPicker(tripDateRound, roundFlag);
            setTimePicker(tripTimeRound, roundFlag);
            }
        }
       else
       {
           onNothingSelected(adapterView);
       }
    }
     private void addTrip()
    {
        btn_addTrip.setOnClickListener(view -> {
            getFields();
            String tripStringCounter = "Trip";


            if(checkFields())
            {
                Toast.makeText(AddTripPage.this, "Empty",Toast.LENGTH_LONG).show();
            }
            else
            {
                FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Trips").child(mTripData.tripName).setValue(mTripData);
                if(mTripData.getTripType()==1){
                    FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Trips").child(mRoundTripData.tripName).setValue(mRoundTripData);
                }
                getCoordinates();
                exitActivity();
            }

        });
    }
    private void addRoundTrip()
    {
      btn_addTripRound.setOnClickListener(view -> {
          getFields();
          if(checkFields())
          {
              Toast.makeText(AddTripPage.this, "Empty",Toast.LENGTH_LONG).show();
          }
          else
          {
              FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Trips").child(mTripData.tripName).setValue(mTripData);
              if(mTripData.getTripType()==1){
                  FirebaseDatabase.getInstance().getReference().child(user.getUid()).child("Trips").child(mRoundTripData.tripName).setValue(mRoundTripData);
              }
              getCoordinates();
              exitActivity();
          }
      });
    }
    public void getCoordinates(){
        Geocoder geocoder = new Geocoder(this);
        List<Address> addressList = new ArrayList<>();
        try {
            addressList.add((Address) geocoder.getFromLocationName(tripStartLoc.getText().toString(), 1));
            addressList.add((Address) geocoder.getFromLocationName(tripEndLoc.getText().toString(), 1));

            if (addressList != null){
                doubleStartLat = addressList.get(0).getLatitude();
                doubleStartLong = addressList.get(0).getLongitude();
                doubleEndLat = addressList.get(1).getLatitude();
                doubleEndLong = addressList.get(1).getLongitude();
                // String str = ("Latitude: " + doubleStartLat
                //       + " | " + "Longitude: " + doubleStartLong);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getFields()
    {
        mTripData.setTripName(tripName.getText().toString().trim());
        mTripData.setTripStartLoc(tripStartLoc.getText().toString().trim());
        mTripData.setTripEndLoc(tripEndLoc.getText().toString().trim());
        mTripData.setTripDate(tripDate.getText().toString().trim());
        mTripData.setTripStartTime(tripTime.getText().toString().trim());
        mTripData.setTripType(mSpinner.getSelectedItemPosition());
        mTripData.setTripStatus("Upcoming");
        if (mTripData.getTripType()==1)
        {
            mRoundTripData.setTripName(tripName.getText().toString().trim()+" Round");
            mRoundTripData.setTripStartLoc(tripEndLoc.getText().toString().trim());
            mRoundTripData.setTripEndLoc(tripStartLoc.getText().toString().trim());
            mRoundTripData.setTripStartTime(tripDateRound.getText().toString().trim());
            mRoundTripData.setTripDate(tripTimeRound.getText().toString().trim());
            mRoundTripData.setTripType(mSpinner.getSelectedItemPosition());
            mRoundTripData.setTripStatus("Upcoming");
        }
    }

    private Boolean checkFields()
    {
        getFields();
        return mTripData.check();
    }

    private void exitActivity()
    {
        mIntent = new Intent(AddTripPage.this, MainActivity.class);
        startActivity(mIntent);
    }

    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
