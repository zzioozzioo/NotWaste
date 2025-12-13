package com.example.notwaste.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notifications")
public class NotificationItem {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String message;      // "우유의 유통기한이 오늘까지입니다."
    public long createdAt;      // 알림 생성 시간 (System.currentTimeMillis())
    public int dday;             // D-0, D-1, D-2 ...

    public NotificationItem(String message, long createdAt, int dday) {
        this.message = message;
        this.createdAt = createdAt;
        this.dday = dday;
    }

    public String getMessage() {
        return message;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
