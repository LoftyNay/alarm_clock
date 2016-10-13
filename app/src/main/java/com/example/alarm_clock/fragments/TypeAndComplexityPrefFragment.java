package com.example.alarm_clock.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.example.alarm_clock.R;
import com.example.alarm_clock.SPreferences;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by artem on 09.10.16.
 */
public class TypeAndComplexityPrefFragment extends PreferenceFragment {

    private int currentCmpButton, currentTypeButton;
    private List<RadioButton> complexityRadioButtons, typeRadioButtons;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.comp_type_layout_pref, container, false);

        SPreferences.init(getActivity());

        initButtons(view);

        changeType(typeRadioButtons);
        changeComplexity(complexityRadioButtons);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RadioButton cRadioButton = (RadioButton) view.findViewById(SPreferences.getInt(SPreferences.COMPLEXITY_BUTTON_ID, R.id.complexity_radio_2));
        RadioButton tRadioButton = (RadioButton) view.findViewById(SPreferences.getInt(SPreferences.TYPE_BUTTON_ID, R.id.type_radio_2));

        cRadioButton.setChecked(true);
        tRadioButton.setChecked(true);
    }

    private void changeType(final List<RadioButton> buttons) {
        for (RadioButton button : buttons) {
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) otherFalse(compoundButton, buttons);
                    currentTypeButton = compoundButton.getId();
                }
            });
        }
    }

    private void changeComplexity(final List<RadioButton> buttons) {
        for (RadioButton button : buttons) {
            button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) otherFalse(compoundButton, buttons);
                    currentCmpButton = compoundButton.getId();
                }
            });
        }
    }

    private void otherFalse(CompoundButton compoundButton, List<RadioButton> buttons) {
        for (RadioButton button : buttons) {
            if (button != compoundButton) {
                button.setChecked(false);
            }
        }
    }

    private int getImageName(int radioButtonId) {
        int drawable_id = 0;
        switch (radioButtonId) {
            case R.id.complexity_radio_1:
                drawable_id = R.drawable.lite;
                break;
            case R.id.complexity_radio_2:
                drawable_id = R.drawable.medium;
                break;
            case R.id.complexity_radio_3:
                drawable_id = R.drawable.strong;
                break;
            case R.id.type_radio_1:
                drawable_id = R.drawable.plus;
                break;
            case R.id.type_radio_2:
                drawable_id = R.drawable.points;
                break;
            case R.id.type_radio_3:
                drawable_id = R.drawable.points_and_plus;
                break;
        }
        return drawable_id;
    }

    private void initButtons(View view) {
        complexityRadioButtons = new ArrayList<>();
        complexityRadioButtons.add((RadioButton) view.findViewById(R.id.complexity_radio_1));
        complexityRadioButtons.add((RadioButton) view.findViewById(R.id.complexity_radio_2));
        complexityRadioButtons.add((RadioButton) view.findViewById(R.id.complexity_radio_3));

        typeRadioButtons = new ArrayList<>();
        typeRadioButtons.add((RadioButton) view.findViewById(R.id.type_radio_1));
        typeRadioButtons.add((RadioButton) view.findViewById(R.id.type_radio_2));
        typeRadioButtons.add((RadioButton) view.findViewById(R.id.type_radio_3));
    }

    @Override
    public void onPause() {
        super.onPause();
        SPreferences.saveTypeAndComplexity(getImageName(currentCmpButton), getImageName(currentTypeButton),
                currentCmpButton, currentTypeButton);
    }
}
