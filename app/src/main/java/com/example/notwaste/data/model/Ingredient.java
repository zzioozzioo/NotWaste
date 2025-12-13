package com.example.notwaste.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class Ingredient {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private int count;
    private String location;      // 냉장 / 냉동 / 실온
    private String expireDate;    // yyyy-MM-dd

    public Ingredient(String name, String expireDate, String location, int count) {
        this.name = name;
        this.expireDate = expireDate;
        this.location = location;
        this.count = count;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getCount() { return count; }
    public String getLocation() { return location; }
    public String getExpireDate() { return expireDate; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCount(int count) { this.count = count; }
    public void setLocation(String location) { this.location = location; }
    public void setExpireDate(String expireDate) { this.expireDate = expireDate; }


}
