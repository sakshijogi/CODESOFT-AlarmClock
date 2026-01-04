package com.example.alarmclock;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(AlarmEntity alarm);

    @Query("SELECT * FROM alarms")
    LiveData<List<AlarmEntity>> getAllAlarms();

    @Delete
    void delete(AlarmEntity alarm);

    @Update
    void update(AlarmEntity alarm);
}

