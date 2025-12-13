package com.example.notwaste.ui.notification;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.NotificationSetting;
import com.example.notwaste.util.AlarmScheduler;

public class NotificationSettingActivity extends AppCompatActivity {

    Switch switchNotify;
    Button btnTimePicker;
    RadioGroup radioGroupCycle;
    Button btnSave;

    AppDatabase db;
    int selectedHour;
    int selectedMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        switchNotify = findViewById(R.id.switchNotify);
        btnTimePicker = findViewById(R.id.btnTimePicker);
        radioGroupCycle = findViewById(R.id.radioGroupCycle);
        btnSave = findViewById(R.id.btnSave);

        // db
        db = AppDatabase.getInstance(getApplicationContext());

        btnTimePicker.setOnClickListener(v -> showTimePicker());
        btnSave.setOnClickListener(v -> saveSettings());

        loadSettings();    }

    private void showTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(
                this,
                (view, hourOfDay, minute) -> {
                    selectedHour = hourOfDay;
                    selectedMinute = minute;
                    btnTimePicker.setText(formatTime(hourOfDay, minute));
                },
                selectedHour,
                selectedMinute,
                false   // 12시간제 (오전/오후)
        );
        dialog.show();
    }

    // 시간 포맷 함수
    private String formatTime(int hour, int minute) {
        String ampm = hour < 12 ? "오전" : "오후";
        int displayHour = hour % 12 == 0 ? 12 : hour % 12;
        return ampm + " " + displayHour + ":" + String.format("%02d", minute);
    }



    // 설정값 저장
    private void saveSettings() {
        NotificationSetting setting = new NotificationSetting();
        setting.enabled = switchNotify.isChecked();
        setting.hour = selectedHour;
        setting.minute = selectedMinute;

        int checkedId = radioGroupCycle.getCheckedRadioButtonId();
        if (checkedId == R.id.radioWeekly) setting.cycle = "weekly";
        else if (checkedId == R.id.radioOnce) setting.cycle = "once";
        else setting.cycle = "daily";

        db.notificationSettingDao().save(setting);

        // 알림 스케줄 처리
        if (setting.enabled) {
            AlarmScheduler.schedule(
                    this,
                    setting.hour,
                    setting.minute,
                    setting.cycle
            );
        } else {
            AlarmScheduler.cancel(this);
        }

        finish();
    }


    // 설정값 불러오기
    private void loadSettings() {
        NotificationSetting setting = db.notificationSettingDao().getSetting();

        if (setting == null) return;

        switchNotify.setChecked(setting.enabled);
        selectedHour = setting.hour;
        selectedMinute = setting.minute;

        btnTimePicker.setText(formatTime(setting.hour, setting.minute));

        if ("weekly".equals(setting.cycle)) radioGroupCycle.check(R.id.radioWeekly);
        else if ("once".equals(setting.cycle)) radioGroupCycle.check(R.id.radioOnce);
        else radioGroupCycle.check(R.id.radioDaily);
    }

}
