package com.exammple.eventmanager1.provider;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey
    @ColumnInfo(name = "categoryId")
    @NonNull
    private String categoryId;
    @ColumnInfo(name = "categoryName")
    private String name;
    @ColumnInfo(name = "eventCount")
    private int eventCount;
    @ColumnInfo(name = "isActive")
    private boolean isActive;

    public Category(@NonNull String categoryId, String name, int eventCount, boolean isActive) {
        this.categoryId = categoryId;
        this.name = name;
        this.eventCount = eventCount;
        this.isActive = isActive;
    }

    @NonNull
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NonNull String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEventCount() {
        return eventCount;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

}
