package com.exammple.eventmanager1.provider;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventManagerRepository {
    private EventManagerDAO eventManagerDAO;
    private LiveData<List<Category>> allCategories;
    EventManagerRepository(Application application) {
        EventManagerDatabase db = EventManagerDatabase.getDatabase(application);
        eventManagerDAO = db.EventManagerDAO();
        allCategories = eventManagerDAO.getAllCategories();
    }

    LiveData<List<Category>> getAllCategories(){
        return allCategories;
    }

    void insert(Category category){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.addCategory(category));
    }

    void deleteCategory(String categoryName){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.deleteCategory(categoryName));
    }

    void deleteAllCategories(){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.deleteAllCategories());
    }
}
