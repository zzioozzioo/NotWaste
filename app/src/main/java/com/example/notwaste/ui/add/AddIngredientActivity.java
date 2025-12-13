package com.example.notwaste.ui.add;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import androidx.room.Room;

import com.example.notwaste.R;
import com.example.notwaste.data.database.AppDatabase;
import com.example.notwaste.data.model.Ingredient;

import java.util.Calendar;

public class AddIngredientActivity extends AppCompatActivity {

    EditText editName, editExpire, editQuantity;
    Spinner spinnerLocation;
    Button btnSave, btnCancel;

    String expireDate;

    int ingredientId = -1; // 식재료 수정 위한 변수
    Ingredient editingIngredient;

    AppDatabase db;  // Room DB

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        // Room DB 인스턴스 생성
        db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "notwaste-db"
        ).allowMainThreadQueries().build(); // 개발용

        editName = findViewById(R.id.editName);
        editExpire = findViewById(R.id.editExpire);
        editQuantity = findViewById(R.id.editQuantity);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        // Spinner에 값 넣기
        String[] locations = {"냉장", "냉동", "실온"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(adapter);

        // 날짜 선택 기능
        editExpire.setOnClickListener(v -> showDatePicker());

        // 저장 버튼 클릭
        btnSave.setOnClickListener(v -> saveIngredient());

        // 취소 버튼 클릭
        btnCancel.setOnClickListener(v -> {
            Intent intent = new Intent(this,
                    com.example.notwaste.ui.fridge.FridgeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        // 수정 여부 판단
        ingredientId = getIntent().getIntExtra("ingredient_id", -1);
        if (ingredientId != -1) {
            editingIngredient = db.ingredientDao().findById(ingredientId);
            if (editingIngredient != null) {
                bindEditData();
            }
        }
    }

    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, // 스피너 스타일
                (view, year, month, dayOfMonth) -> {

                    // 항상 같은 형식으로 저장 (0패딩 적용)
                    expireDate = String.format("%04d-%02d-%02d", year, month + 1, dayOfMonth);
                    editExpire.setText(expireDate);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );

        dialog.show();
    }

    // 식재료 저장
    private void saveIngredient() {
        String name = editName.getText().toString();
        String quantityStr = editQuantity.getText().toString();
        String location = spinnerLocation.getSelectedItem().toString();

        if (name.isEmpty() || quantityStr.isEmpty() || expireDate.isEmpty()) {
            Toast.makeText(this, "모든 항목을 입력해 주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int quantity = Integer.parseInt(quantityStr);

        if (quantity <= 0) {
            Toast.makeText(this, "수량은 1 이상이어야 합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (editingIngredient == null) {
            Ingredient ingredient = new Ingredient(name, expireDate, location, quantity);
            db.ingredientDao().insert(ingredient);
        } else {
            editingIngredient.setName(name);
            editingIngredient.setCount(quantity);
            editingIngredient.setLocation(location);
            editingIngredient.setExpireDate(expireDate);

            db.ingredientDao().update(editingIngredient);
        }


        Toast.makeText(this, "등록되었습니다!", Toast.LENGTH_SHORT).show();
        finish(); // 화면 닫기
    }

    // 식재료 수정
    private void bindEditData() {
        editName.setText(editingIngredient.getName());
        editQuantity.setText(String.valueOf(editingIngredient.getCount()));
        editExpire.setText(editingIngredient.getExpireDate());

        expireDate = editingIngredient.getExpireDate();

        String location = editingIngredient.getLocation();
        if (location.equals("냉장")) spinnerLocation.setSelection(0);
        else if (location.equals("냉동")) spinnerLocation.setSelection(1);
        else spinnerLocation.setSelection(2);

        btnSave.setText("수정하기");
    }
}
