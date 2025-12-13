package com.example.notwaste.ui.fridge;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.Ingredient;
import com.example.notwaste.ui.add.AddIngredientActivity;
import com.example.notwaste.ui.home.HomeActivity;
import com.example.notwaste.ui.mypage.MyPageActivity;
import com.example.notwaste.ui.recipe.RecipeWebViewActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FridgeActivity extends AppCompatActivity {

    Button btnFridge, btnFreeze, btnRoom, btnAddIngredient;
    RecyclerView recyclerIngredients;

    IngredientAdapter adapter;
    List<Ingredient> ingredientList = new ArrayList<>();

    AppDatabase db;

    String currentLocation = null; // 기본 화면에서는 아무 카테고리도 선택 안 함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);

        // View 연결
        btnFridge = findViewById(R.id.btnFridge);
        btnFreeze = findViewById(R.id.btnFreeze);
        btnRoom = findViewById(R.id.btnRoom);
        btnAddIngredient = findViewById(R.id.btnAddIngredient);
        recyclerIngredients = findViewById(R.id.recyclerIngredients);


        // DB
        db = AppDatabase.getInstance(this);

        // RecyclerView
        adapter = new IngredientAdapter(ingredientList, ingredient -> {
            Intent intent = new Intent(this, IngredientDetailActivity.class);
            intent.putExtra("ingredient_id", ingredient.getId());
            startActivity(intent);
        });
        recyclerIngredients.setLayoutManager(new LinearLayoutManager(this));
        recyclerIngredients.setAdapter(adapter);

        // 탭 클릭
        btnFridge.setOnClickListener(v -> selectCategory("냉장"));
        btnFreeze.setOnClickListener(v -> selectCategory("냉동"));
        btnRoom.setOnClickListener(v -> selectCategory("실온"));

        // 식재료 추가
        btnAddIngredient.setOnClickListener(v ->
                startActivity(new Intent(this, AddIngredientActivity.class))
        );

        // 하단 메뉴
        setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadIngredients(); // 추가 후 돌아왔을 때 갱신
        updateTabUI();
    }


    // 위치별 필터링
    private void selectCategory(String location) {
        // 같은 카테고리를 다시 누르면 해제
        if (currentLocation != null && currentLocation.equals(location)) {
            currentLocation = null;
        } else {
            currentLocation = location;
        }        loadIngredients();
        updateTabUI();
    }

    private void loadIngredients() {
        ingredientList.clear();
        if (currentLocation == null) {
            ingredientList.addAll(db.ingredientDao().getAll());
        } else {
            ingredientList.addAll(db.ingredientDao().getByLocation(currentLocation));
        }

        adapter.notifyDataSetChanged();
    }

    // 탭 전환
    private void updateTabUI() {
        int selected = ContextCompat.getColor(this, R.color.dark_gray);
        int unselected = ContextCompat.getColor(this, R.color.light_gray);

        btnFridge.setBackgroundTintList(
                ColorStateList.valueOf(
                        "냉장".equals(currentLocation) ? selected : unselected
                )
        );

        btnFreeze.setBackgroundTintList(
                ColorStateList.valueOf(
                        "냉동".equals(currentLocation) ? selected : unselected
                )
        );

        btnRoom.setBackgroundTintList(
                ColorStateList.valueOf(
                        "실온".equals(currentLocation) ? selected : unselected
                )
        );
    }



    // 하단 메뉴
    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.menu_fridge);

        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(this, HomeActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_recipe) {
                startActivity(new Intent(this, RecipeWebViewActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_mypage) {
                startActivity(new Intent(this, MyPageActivity.class));
                return true;
            } else if (item.getItemId() == R.id.menu_fridge) {
                return true;
            }
            return false;
        });
    }
}
