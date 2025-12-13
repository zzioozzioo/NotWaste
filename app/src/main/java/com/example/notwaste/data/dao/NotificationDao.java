package com.example.notwaste.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notwaste.data.model.NotificationItem;

import java.util.List;

@Dao
public interface NotificationDao {

    @Insert
    void insert(NotificationItem item);

    // 알림 화면용 (최근 알림 순 정렬)
    @Query("SELECT * FROM notifications ORDER BY createdAt DESC")
    List<NotificationItem> getAllOrderByLatest();

    @Query("DELETE FROM notifications")
    void clear();
}
