package com.example.alarm_clock.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.alarm_clock.R;
import com.example.alarm_clock.SPreferences;

/**
 * Created by artem on 08.10.16.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(195, toolbar.getPaddingTop(), toolbar.getPaddingRight(), toolbar.getPaddingBottom());

        SPreferences.init(getApplicationContext());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SPreferences.clear();
    }
}
