package com.example.alarm_clock.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarm_clock.AlarmController;
import com.example.alarm_clock.Helper;
import com.example.alarm_clock.R;
import com.example.alarm_clock.SPreferences;
import com.example.alarm_clock.db.Db;

import java.util.Calendar;

/**
 * Created by artem on 09.10.16.
 */
public class AlarmSetupActivity extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();
    private EditText descriptionEdit;
    private TextView dateSet, timeSet;
    private Context context;
    private Db db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_setup_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = getApplicationContext();

        SPreferences.init(context);
        AlarmController.init(context);

        dateSet = (TextView) findViewById(R.id.date_setup);
        timeSet = (TextView) findViewById(R.id.time_setup);
        Button saveToBase = (Button) findViewById(R.id.save_alarm);
        descriptionEdit = (EditText) findViewById(R.id.description_setup);

        initCurrentDateAndTime();

        click(dateSet);
        click(timeSet);

        saveToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToBaseAndSetAlarm();
            }
        });
    }

    private void saveToBaseAndSetAlarm() {
        String description = descriptionEdit.getText().toString();
        long time = calendar.getTimeInMillis();
        try {
            db = new Db(context);
            db.addAlarm(SPreferences.getInt(SPreferences.TYPE, R.drawable.points),
                    SPreferences.getInt(SPreferences.COMPLEXITY, R.drawable.medium),
                    String.valueOf(time), description, dayName(), "1");

            AlarmController.setAlarm(description, getMaxId() + 1, time);
            Toast.makeText(context, getResources().getString(R.string.notification) + ' ' +
                    Helper.toFormatDateString(context, String.valueOf(time)) + ' ' + getResources().getString(R.string.in) + ' ' +
                    Helper.toFormatTimeString(context, String.valueOf(time)), Toast.LENGTH_LONG).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(context, "Exception: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void click(TextView textView) {
        switch (textView.getId()) {
            case R.id.date_setup:
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setDate();
                    }
                });
                break;
            case R.id.time_setup:
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        setTime();
                    }
                });
                break;
        }
    }

    private int getMaxId() {
        Cursor cursor = db.getMaxId();
        cursor.moveToFirst();
        return cursor.getInt(cursor.getColumnIndex("id"));
    }

    private String dayName() {
        String[] days = context.getResources().getStringArray(R.array.week_days);
        return days[calendar.get(Calendar.DAY_OF_WEEK) - 1];
    }

    private void initCurrentDateAndTime() {
        dateSet.setText(Helper.toFormatDateString(getApplicationContext(), String.valueOf(calendar.getTimeInMillis())));
        timeSet.setText(Helper.toFormatTimeString(getApplicationContext(), String.valueOf(calendar.getTimeInMillis())));
    }

    private void setDate() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                initCurrentDateAndTime();
            }
        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(AlarmSetupActivity.this, onDateSetListener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void setTime() {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY, i);
                calendar.set(Calendar.MINUTE, i1);
                calendar.set(Calendar.MILLISECOND, 0);
                initCurrentDateAndTime();
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(AlarmSetupActivity.this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }
}
