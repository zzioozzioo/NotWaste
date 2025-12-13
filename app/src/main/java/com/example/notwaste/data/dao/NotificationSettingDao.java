package com.example.notwaste.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.notwaste.data.model.NotificationSetting;

@Dao
public interface NotificationSettingDao {

    @Query("SELECT * FROM notification_setting WHERE id = 1")
    NotificationSetting getSetting();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(NotificationSetting setting);
}
