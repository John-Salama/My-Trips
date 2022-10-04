package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;

public class AddTripPage extends AppCompatActivity {

    EditText tripDate;
    Calendar calendar;
    EditText tripTime;
    TimePickerDialog timePicker;
    int currentHour;
    int currentMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip_page);


        initializeContent();
        setCalendarPicker();
        setTimePicker();


    }

    private void initializeContent() {
        tripDate = findViewById(R.id.tripDate_editTxt);
        tripTime = findViewById(R.id.tripTime_editTxt);
    }

    private void setCalendarPicker() {
        calendar =Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR , year);
                calendar.set(Calendar.MONTH , month);
                calendar.set(Calendar.DAY_OF_MONTH , day);
                updateCalendar();
            }//



            private void updateCalendar() {
                String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                tripDate.setText(selectedDate);
            }
        };
        tripDate.setOnClickListener(view -> new DatePickerDialog(AddTripPage.this, date
                ,calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void setTimePicker() {
        tripTime.setOnClickListener(view -> {
            calendar = Calendar.getInstance();
            currentHour = calendar.get(Calendar.HOUR_OF_DAY);
            currentMinute = calendar.get(Calendar.MINUTE);
            timePicker = new TimePickerDialog(AddTripPage.this, (timePicker, hours, minutes) -> tripTime.setText(String.format("%02d:%02d",hours,minutes)),currentHour,currentHour,false);
            timePicker.show();

        });
    }
}