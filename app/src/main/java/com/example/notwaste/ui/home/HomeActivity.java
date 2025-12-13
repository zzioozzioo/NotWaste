package com.example.notwaste.ui.home;

import static com.example.notwaste.util.DateUtil.calculateDday;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.Ingredient;
import com.example.notwaste.ui.fridge.FridgeActivity;
import com.example.notwaste.ui.fridge.IngredientAdapter;
import com.example.notwaste.ui.fridge.IngredientDetailActivity;
import com.example.notwaste.ui.mypage.MyPageActivity;
import com.example.notwaste.ui.notification.NotificationActivity;
import com.example.notwaste.ui.recipe.RecipeWebViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

//    RecyclerView recyclerExpiring;
    TextView tvExpiringCount;
    LinearLayout expiringList;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 런타임 권한 요청(알림)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS},
                        1001
                );
            }
        }

        tvExpiringCount = findViewById(R.id.tvExpiringCount);
        expiringList = findViewById(R.id.expiringList);

        db = AppDatabase.getInstance(this);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.menu_home); // 현재 화면 선택 고정

        bottomNav.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menu_recipe) {
                startActivity(new Intent(this, RecipeWebViewActivity.class));
                return true;

            } else if (item.getItemId() == R.id.menu_fridge) {
                startActivity(new Intent(this, FridgeActivity.class));
                return true;

            } else if (item.getItemId() == R.id.menu_mypage) {
                startActivity(new Intent(this, MyPageActivity.class));
                return true;

            } else if (item.getItemId() == R.id.menu_home) {
                // 이미 현재 화면 → 아무것도 안 함
                return true;
            }

            return false;
        });

        ImageView btnNotification = findViewById(R.id.btnNotification);

        btnNotification.setOnClickListener(v -> {
            startActivity(new Intent(this, NotificationActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExpiringIngredients();
    }

    // 유통기한이 오늘(D-0) 또는 내일(D-1)까지인 재료만 필터링
    private void loadExpiringIngredients() {

        expiringList.removeAllViews();
        List<Ingredient> all = db.ingredientDao().getAll();

        int count = 0;

        for (Ingredient i : all) {
            int dday = calculateDday(i.getExpireDate());
            if (dday <= 1) {
                count++;

                TextView tv = new TextView(this);
                tv.setText(i.getName() + " (" + i.getCount() + ")");
                tv.setTextSize(16f);
                tv.setPadding(0, 6, 0, 6);
                tv.setTextColor(0xFF444444);

                expiringList.addView(tv);
            }
        }

        if (count == 0) {
            tvExpiringCount.setText("임박한 식재료가 없습니다");
        } else {
            tvExpiringCount.setText("오늘 유통기한 임박한 식재료: " + count + "개");
        }
    }
}
