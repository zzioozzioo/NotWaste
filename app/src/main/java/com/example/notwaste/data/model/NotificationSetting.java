package com.example.notwaste.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notification_setting")
public class NotificationSetting {

    @PrimaryKey
    public int id = 1;   // 항상 1개만 유지

    public boolean enabled;
    public int hour;
    public int minute;
    public String cycle; // daily / weekly / once
}
