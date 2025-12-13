package com.example.notwaste.ui.notification;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.NotificationItem;
import com.example.notwaste.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerNotification;
    NotificationAdapter adapter;

    AppDatabase db;
    List<NotificationItem> notificationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        db = AppDatabase.getInstance(this);

        // RecyclerView
        recyclerNotification = findViewById(R.id.recyclerNotification);
        recyclerNotification.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotificationAdapter(notificationList);
        recyclerNotification.setAdapter(adapter);

        // 설정 버튼
        ImageView btnSetting = findViewById(R.id.btnSetting);

        btnSetting.setOnClickListener(v ->  // 톱니바퀴 클릭 → 알림 설정 화면
            startActivity(new Intent(this, NotificationSettingActivity.class))
        );

        // 뒤로 가기 버튼 누르면 홈 화면
        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );
            startActivity(intent);
            finish();
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotifications();
    }

    private void loadNotifications() {
        notificationList.clear();

        // 최신 알림이 위로 오게 DESC
        notificationList.addAll(
                db.notificationDao().getAllOrderByLatest()
        );

        adapter.notifyDataSetChanged();
    }
}
