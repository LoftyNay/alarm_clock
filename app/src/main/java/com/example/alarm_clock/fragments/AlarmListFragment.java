package com.example.alarm_clock.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.alarm_clock.AlarmsAdapter;
import com.example.alarm_clock.R;
import com.example.alarm_clock.activities.AlarmSetupActivity;
import com.example.alarm_clock.db.Db;
import com.melnykov.fab.FloatingActionButton;

/**
 * Created by artem on 08.10.16.
 */
public class AlarmListFragment extends ListFragment implements View.OnClickListener {

    private String[] from = {Db.TYPE_IMAGE, Db.COMPLEXITY_IMAGE, Db.DATE, Db.DATE, Db.DESCRIPTION, Db.WEEKDAY, Db.SW};
    private int[] to = {R.id.type_image, R.id.complexity_image, R.id.time, R.id.date, R.id.description, R.id.weekday, R.id.sw};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFAB(view);
    }

    private void initData() {
        Db db = new Db(getActivity());
        Cursor cursor = db.getAllAlarms();
        AlarmsAdapter alarmsAdapter = new AlarmsAdapter(getActivity(), R.layout.list_row, cursor, from, to, 0);
        setListAdapter(alarmsAdapter);
        db.close();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    private void initFAB(View view) {
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.attachToListView(getListView());
        floatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AlarmSetupActivity.class);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
