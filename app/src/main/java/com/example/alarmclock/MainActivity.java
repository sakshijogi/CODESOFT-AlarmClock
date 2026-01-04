package com.example.alarmclock;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private AlarmDatabase database;
    private AlarmAdapter adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkExactAlarmPermission();

        database = AlarmDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerViewAlarms);
        fabAddAlarm = findViewById(R.id.fabAddAlarm);

        adapter = new AlarmAdapter(this, (alarm, isChecked) -> {
            alarm.isEnabled = isChecked;
            new Thread(() -> database.alarmDao().update(alarm)).start();

            if (isChecked) {
                AlarmScheduler.setAlarm(this, alarm);
            } else {
                AlarmScheduler.cancelAlarm(this, alarm);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        database.alarmDao().getAllAlarms().observe(this, alarms -> {
            adapter.setAlarms(alarms);
        });

        fabAddAlarm.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddAlarmActivity.class));
        });
    }

    private void checkExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null && !alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
            }
        }
    }
}
