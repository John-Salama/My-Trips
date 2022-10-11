package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTripPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText tripDate;
    Calendar calendar;
    EditText tripTime;
    TimePickerDialog timePicker;
    int currentHour;
    int currentMinute;

    Spinner mSpinner;
    EditText tripDateRound;
    EditText tripTimeRound;
    Button btn_addTrip;
    Button btn_addTripRound;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_page);
        initializeContent();
        setCalendarPicker(tripDate);
        setTimePicker(tripTime);
        setSpinner();
    }

    private void initializeContent() {
        tripDate = findViewById(R.id.tripDate_editTxt);
        tripTime = findViewById(R.id.tripTime_editTxt);
        mSpinner = findViewById(R.id.spinner_tritpType);
        tripDateRound = findViewById(R.id.tripDateRound_editTxt);
        tripTimeRound = findViewById(R.id.tripTimeRound_editTxt);
        btn_addTrip = findViewById(R.id.addTripRound_btn);
        btn_addTripRound = findViewById(R.id.addTrip_btn2);
    }

    private void setCalendarPicker(final EditText date1) {
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
                date1.setText(selectedDate);
            }
        };
        date1.setOnClickListener(view -> new DatePickerDialog(AddTripPage.this, date
                ,calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void setTimePicker(EditText time) {
        time.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddTripPage.this, (timePicker, hours, minutes) -> time.setText(String.format("%02d:%02d",hours,minutes)),currentHour,currentHour,false);
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
            tripDateRound.setVisibility((View.INVISIBLE));
            tripTimeRound.setVisibility((View.INVISIBLE));
            btn_addTripRound.setVisibility(View.VISIBLE);
            btn_addTrip.setVisibility(View.INVISIBLE);
        }
       else if(position == 1)
        {
            tripDateRound.setVisibility((View.VISIBLE));
            tripTimeRound.setVisibility((View.VISIBLE));
            btn_addTrip.setVisibility(View.VISIBLE);
            btn_addTripRound.setVisibility(View.INVISIBLE);
            setCalendarPicker(tripDateRound);
            setTimePicker(tripTimeRound);
        }
       else
       {
           onNothingSelected(adapterView);
       }
    }



    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
