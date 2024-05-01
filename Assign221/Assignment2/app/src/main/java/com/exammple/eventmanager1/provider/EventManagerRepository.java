package com.exammple.eventmanager1.provider;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventManagerRepository {
    private EventManagerDAO eventManagerDAO;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Event>> allEvents;

    EventManagerRepository(Application application) {
        EventManagerDatabase db = EventManagerDatabase.getDatabase(application);
        eventManagerDAO = db.EventManagerDAO();
        allCategories = eventManagerDAO.getAllCategories();
        allEvents = eventManagerDAO.getAllEvents();
    }

    LiveData<List<Category>> getAllCategories(){
        return allCategories;
    }

    void insertCategory(Category category){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.addCategory(category));
    }

    boolean categoryIdExists(String categoryId){
        return eventManagerDAO.getCategoryCount(categoryId) > 0;
    }

    void deleteCategory(String categoryName){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.deleteCategory(categoryName));
    }

    void deleteAllCategories(){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.deleteAllCategories());
    }

    LiveData<List<Event>> getAllEvents(){return allEvents;}

    void insertEvent(Event event){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.addEvent(event));
    }

    void deleteEvent(String eventName){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.deleteEvent(eventName));
    }

    void deleteAllEvents(){
        EventManagerDatabase.databaseWriteExecutor.execute(() -> eventManagerDAO.deleteAllEvents());
    }
}
