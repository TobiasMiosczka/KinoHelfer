package com.github.tobiasmiosczka.kinohelfer;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class WageCalculatorActivity extends AppCompatActivity {

    private static final String PREFERENCES_NAME = "KinoHelfer";
    private static final String PREFERENCES_HOURLY_WAGE = "hourlyWage";
    private static final String PREFERENCES_PERCENTS = "percents";

    private static final float PRESET_HOURLY_WAGE = 8.84f;
    private static final int PRESET_PERCENTS = 0;

    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            update();
            saveValues();
        }
    };

    private Button      btFrom,
                        btTo;
    private EditText    etHourlyWage,
                        etPercents;
    private TextView    tvHours,
                        tvHoursAfter,
                        tvWage;

    private final Time    from = new Time(13, 0);
    private final Time to = new Time(23, 0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wage_calculator);

        btFrom = (Button)findViewById(R.id.btFrom);
        btTo = (Button)findViewById(R.id.btTo);

        etHourlyWage = (EditText)findViewById(R.id.etHourlyWage);
        etPercents = (EditText)findViewById(R.id.etPercents);

        tvHours = (TextView)findViewById(R.id.tvHours);
        tvHoursAfter = (TextView)findViewById(R.id.tvHoursAfter);
        tvWage = (TextView)findViewById(R.id.tvWage);

        btFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFrom();
            }
        });
        btTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTo();
            }
        });


        etHourlyWage.addTextChangedListener(textWatcher);
        etPercents.addTextChangedListener(textWatcher);

        loadValues();
        update();
    }

    private void changeFrom() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                from.setHours(hourOfDay);
                from.setMinutes(minute);
                update();
            }
        }, from.getHours(), from.getMinutes(), true);
        timePickerDialog.show();
    }

    private void changeTo() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                to.setHours(hourOfDay);
                to.setMinutes(minute);
                update();
            }
        }, to.getHours(), to.getMinutes(), true);
        timePickerDialog.show();
    }

    private float getHourlyWage() {
        try {
            return Float.valueOf(etHourlyWage.getText().toString());
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }

    private int getPercents() {
        try {
            return Integer.valueOf(etPercents.getText().toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void saveValues() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putFloat(PREFERENCES_HOURLY_WAGE, getHourlyWage());
        editor.putInt(PREFERENCES_PERCENTS, getPercents());
        editor.apply();
    }

    private void loadValues() {
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        float hourlyWage = sharedPreferences.getFloat(PREFERENCES_HOURLY_WAGE, PRESET_HOURLY_WAGE);
        etHourlyWage.setText(String.valueOf(hourlyWage));

        int percents = sharedPreferences.getInt(PREFERENCES_PERCENTS, PRESET_PERCENTS);
        etPercents.setText(String.valueOf(percents));
    }

    private void update() {
        btFrom.setText(from.toString());
        btTo.setText(to.toString());

        Time duration = Time.difference(from, to);
        tvHours.setText(duration.toString());

        Time effectiveDuration = WageHelper.getEffectiveDuration(duration);

        tvHoursAfter.setText(effectiveDuration.toString());

        float hourlyWage = getHourlyWage();
        float percents = getPercents();

        float wage = WageHelper.getWage(duration, hourlyWage, percents);

        tvWage.setText(String.valueOf(wage));

    }
}
