package com.example.notwaste.ui.mypage;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.Ingredient;
import com.example.notwaste.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class WasteIngredientActivity extends AppCompatActivity {

    RecyclerView recyclerWaste;
    WasteIngredientAdapter adapter;
    List<Ingredient> wasteList = new ArrayList<>();

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waste_ingredient);

        db = AppDatabase.getInstance(this);

        recyclerWaste = findViewById(R.id.recyclerWaste);
        recyclerWaste.setLayoutManager(new LinearLayoutManager(this));

        adapter = new WasteIngredientAdapter(wasteList);
        recyclerWaste.setAdapter(adapter);

        ImageView btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        loadWasteIngredients();
    }

    private void loadWasteIngredients() {
        List<Ingredient> all = db.ingredientDao().getAll();
        List<Ingredient> recentWaste = new ArrayList<>();

        for (Ingredient i : all) {
            int dday = DateUtil.calculateDday(i.getExpireDate());

            // 최근 7일 이내 유통기한 지난 식재료
            if (dday < 0 && dday >= -7) {
                recentWaste.add(i);
            }
        }

        adapter.update(recentWaste);
    }
}
