package com.example.alarm_clock;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarm_clock.db.Db;

/**
 * Created by artem on 09.10.16.
 */
public class AlarmsAdapter extends SimpleCursorAdapter {

    private Db db;
    private Context context;
    private LayoutInflater layoutInflater;
    private Cursor cursor;

    public AlarmsAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        layoutInflater = LayoutInflater.from(context);
        cursor = c;
        this.context = context;
        db = new Db(context);
        AlarmController.init(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            viewHolder = new ViewHolder();
            viewHolder.complexityImage = (ImageView) convertView.findViewById(R.id.complexity_image);
            viewHolder.typeImage = (ImageView) convertView.findViewById(R.id.type_image);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.description = (TextView) convertView.findViewById(R.id.description);
            viewHolder.weekday = (TextView) convertView.findViewById(R.id.weekday);
            viewHolder.aSwitch = (Switch) convertView.findViewById(R.id.sw);

            viewHolder.aSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean currentValue = viewHolder.aSwitch.isChecked();
                    viewHolder.aSwitch.setChecked(currentValue);
                    cursor.moveToPosition(position);
                    if (currentValue) {
                        AlarmController.setAlarm(cursor.getString(cursor.getColumnIndex(Db.DESCRIPTION)),
                                cursor.getInt(cursor.getColumnIndex(Db.ID)),
                                Long.parseLong(cursor.getString(cursor.getColumnIndex(Db.DATE))));
                    } else {
                        AlarmController.cancelAlarm(cursor.getInt(cursor.getColumnIndex(Db.ID)));
                    }
                    db.updateSwitch(currentValue, cursor.getInt(cursor.getColumnIndex(Db.ID)));
                }
            });
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        cursor.moveToPosition(position);

        viewHolder.complexityImage.setImageResource(cursor.getInt(cursor.getColumnIndex(Db.COMPLEXITY_IMAGE)));
        viewHolder.typeImage.setImageResource(cursor.getInt(cursor.getColumnIndex(Db.TYPE_IMAGE)));
        viewHolder.time.setText(Helper.toFormatTimeString(context, cursor.getString(cursor.getColumnIndex(Db.DATE))));
        viewHolder.date.setText(Helper.toFormatDateString(context, cursor.getString(cursor.getColumnIndex(Db.DATE))));
        viewHolder.description.setText(cursor.getString(cursor.getColumnIndex(Db.DESCRIPTION)));
        viewHolder.weekday.setText(cursor.getString(cursor.getColumnIndex(Db.WEEKDAY)));
        viewHolder.aSwitch.setChecked(Helper.stringToBoolean(cursor.getString(cursor.getColumnIndex(Db.SW))));

        return convertView;
    }

    static class ViewHolder {
        ImageView complexityImage;
        ImageView typeImage;
        TextView time, date, description, weekday;
        Switch aSwitch;
    }
}

