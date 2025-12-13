package com.example.notwaste.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.notwaste.data.dao.IngredientDao;
import com.example.notwaste.data.dao.NotificationDao;
import com.example.notwaste.data.dao.NotificationSettingDao;
import com.example.notwaste.data.model.Ingredient;
import com.example.notwaste.data.model.NotificationItem;
import com.example.notwaste.data.model.NotificationSetting;

@Database(entities = {
            Ingredient.class,
            NotificationItem.class,
            NotificationSetting.class,
        },
        version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract IngredientDao ingredientDao();
    public abstract NotificationDao notificationDao();
    public abstract NotificationSettingDao notificationSettingDao();

    // ------------------------------
    // Singleton getInstance() 추가!!
    // ------------------------------
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "notwaste-db"
                    )
                    .fallbackToDestructiveMigration()   // 마이그레이션 자동 처리
                    .allowMainThreadQueries()           // 개발용
                    .build();
        }
        return instance;
    }
}
