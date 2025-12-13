package com.example.notwaste.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;
import androidx.room.Update;

import com.example.notwaste.data.model.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {

    @Insert
    void insert(Ingredient ingredient);

    @Update
    void update(Ingredient ingredient);

    @Delete
    void delete(Ingredient ingredient);

    // 전체 조회 (유통기한 임박 순)
    @Query("SELECT * FROM ingredients ORDER BY expireDate ASC")
    List<Ingredient> getAll();

    // 단건 조회
    @Query("SELECT * FROM ingredients WHERE id = :id LIMIT 1")
    Ingredient findById(int id);

    // 카테고리별 조회 (냉장 / 냉동 / 실온)
    @Query("SELECT * FROM ingredients WHERE location = :location ORDER BY expireDate ASC")
    List<Ingredient> getByLocation(String location);

    @Query("SELECT * FROM ingredients WHERE expireDate < date('now') ORDER BY expireDate DESC")
    List<Ingredient> getExpiredIngredients();


}
