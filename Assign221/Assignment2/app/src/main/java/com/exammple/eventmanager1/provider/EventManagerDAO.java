package com.exammple.eventmanager1.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventManagerDAO {
    @Query("select * from categories")
    LiveData<List<Category>> getAllCategories();
    @Query("select * from categories where categoryName=:categoryName")
    List<Category> getCategory(String categoryName);
    @Insert
    void addCategory(Category category);
    @Query("delete from categories where categoryName=:categoryName")
    void deleteCategory(String categoryName);
    @Query("delete from categories")
    void deleteAllCategories();

    @Query("select * from events")
    LiveData<List<Event>> getAllEvents();
    @Query("select * from events where eventName=:eventName")
    List<Event> getEvent(String eventName);
    @Insert
    void addEvent(Event event);
    @Query("delete from events where eventName=:eventName")
    void deleteEvent(String eventName);
    @Query("delete from events")
    void deleteAllEvents();

}
