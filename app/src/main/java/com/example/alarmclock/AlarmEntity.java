package com.example.alarmclock;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alarms")
public class AlarmEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String time;
    public String label;
    public boolean isDaily;
    public boolean isEnabled;
}

