package com.example.notwaste.receiver;

import static com.example.notwaste.util.DateUtil.calculateDday;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.Ingredient;
import com.example.notwaste.data.model.NotificationItem;

import java.util.List;

public class ExpireAlarmReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "expire_channel";


    @Override
    public void onReceive(Context context, Intent intent) {

        AppDatabase db = AppDatabase.getInstance(context);

        List<Ingredient> ingredients = db.ingredientDao().getAll();

        long now = System.currentTimeMillis();

        for (Ingredient i : ingredients) {
            int dday = calculateDday(i.getExpireDate());

            if (dday <= 1) {
                String msg = "";

                if (dday == 1)
                    msg = i.getName() + "의 유통기한이 1일 남았습니다.";
                else if (dday == 0)
                    msg = i.getName() + "의 유통기한이 오늘까지입니다.";
                else
                    msg = i.getName() + "의 유통기한이 " + Math.abs(dday) + "일 지났습니다.";

                NotificationItem item =
                        new NotificationItem(msg, now, dday);

                db.notificationDao().insert(item);
            }
        }

        showSystemNotification(context);
    }

    private void showSystemNotification(Context context) {

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Android 8.0 이상은 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "유통기한 알림",
                    NotificationManager.IMPORTANCE_HIGH
            );
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification) // 아이콘
                        .setContentTitle("유통기한 임박!")
                        .setContentText("유통기한이 임박한 식재료가 있어요.")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
}
