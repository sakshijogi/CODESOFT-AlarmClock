package com.example.alarmclock;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Locale;

public class AddAlarmActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private EditText editLabel;
    private RadioGroup radioGroup;
    private Button btnSave;
    private AlarmDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        timePicker = findViewById(R.id.timePicker);
        editLabel = findViewById(R.id.editLabel);
        radioGroup = findViewById(R.id.radioGroup);
        btnSave = findViewById(R.id.btnSave);
        database = AlarmDatabase.getInstance(this);

        timePicker.setIs24HourView(false);

        btnSave.setOnClickListener(v -> {
            int hour = timePicker.getHour();
            int minute = timePicker.getMinute();
            String time = String.format(Locale.getDefault(), "%02d:%02d", hour, minute);

            String label = editLabel.getText().toString().trim();
            boolean isDaily = radioGroup.getCheckedRadioButtonId() == R.id.radioDaily;

            AlarmEntity alarm = new AlarmEntity();
            alarm.time = time;
            alarm.label = label.isEmpty() ? "Alarm" : label;
            alarm.isDaily = isDaily;
            alarm.isEnabled = true;

            new Thread(() -> {
                database.alarmDao().insert(alarm);
                runOnUiThread(() -> {
                    AlarmScheduler.setAlarm(this, alarm); // To be implemented next
                    Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }).start();
        });
    }
}
