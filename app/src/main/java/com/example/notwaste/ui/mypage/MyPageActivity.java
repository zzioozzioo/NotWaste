package com.example.notwaste.ui.mypage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.ui.home.HomeActivity;
import com.example.notwaste.ui.fridge.FridgeActivity;
import com.example.notwaste.ui.recipe.RecipeWebViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyPageActivity extends AppCompatActivity {
    LinearLayout btnWaste;

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        db = AppDatabase.getInstance(this);

        btnWaste = findViewById(R.id.btnWaste);
        btnWaste.setOnClickListener(v -> {
            Intent intent = new Intent(this, WasteIngredientActivity.class);
            startActivity(intent);
        });

        setupBottomNavigation();
    }

    // 하단 메뉴
    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.menu_mypage);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_fridge) {
                startActivity(new Intent(this, FridgeActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_recipe) {
                startActivity(new Intent(this, RecipeWebViewActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_mypage) {
                return true;
            }
            return false;
        });
    }
}
