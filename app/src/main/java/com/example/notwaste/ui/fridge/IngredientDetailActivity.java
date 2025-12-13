package com.example.notwaste.ui.fridge;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.Ingredient;
import com.example.notwaste.ui.add.AddIngredientActivity;

public class IngredientDetailActivity extends AppCompatActivity {

    TextView tvName, tvCount, tvExpire, tvLocation;
    Button btnEdit;
    Button btnDelete;
    ImageView btnBack;

    AppDatabase db;
    Ingredient ingredient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient_detail);

        tvName = findViewById(R.id.tvName);
        tvCount = findViewById(R.id.tvCount);
        tvExpire = findViewById(R.id.tvExpire);
        tvLocation = findViewById(R.id.tvLocation);
        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
        btnBack = findViewById(R.id.btnBack);

        db = AppDatabase.getInstance(this);

        int id = getIntent().getIntExtra("ingredient_id", -1);
        ingredient = db.ingredientDao().findById(id);

        if (ingredient == null) {
            finish();
            return;
        }

        bindData();

        // 식재료 수정
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddIngredientActivity.class);
            intent.putExtra("ingredient_id", ingredient.getId());
            startActivity(intent);
        });
        // 식재료 삭제
        btnDelete.setOnClickListener(v -> showDeleteDialog());

        // 뒤로가기 버튼
        btnBack.setOnClickListener(v -> finish());
    }

    private void bindData() {
        tvName.setText(ingredient.getName());
        tvCount.setText(ingredient.getCount() + "개");
        tvExpire.setText("유통기한: " + ingredient.getExpireDate());
        tvLocation.setText("보관: " + ingredient.getLocation());
    }

    // 식재료 삭제 시 확인 문구
    private void showDeleteDialog() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("식재료 삭제")
                .setMessage("정말 이 식재료를 삭제하시겠습니까?")
                .setPositiveButton("삭제", (dialog, which) -> {
                    db.ingredientDao().delete(ingredient);
                    finish();
                })
                .setNegativeButton("취소", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // DB에서 다시 조회
        ingredient = db.ingredientDao().findById(ingredient.getId());

        if (ingredient == null) {
            finish();
            return;
        }

        bindData(); // 화면 갱신
    }

}
