package com.exammple.eventmanager1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventManagerViewModel extends AndroidViewModel {
    private EventManagerRepository repository;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Event>> allEvents;

    public EventManagerViewModel(@NonNull Application application) {
        super(application);

        repository = new EventManagerRepository(application);
        allCategories = repository.getAllCategories();
        allEvents = repository.getAllEvents();
    }

    public LiveData<List<Category>> getAllCategories(){
        return allCategories;
    }

    public void insertCategory(Category category){repository.insertCategory(category);}

    public void deleteCategory(String name){repository.deleteCategory(name);}

    public void deleteAllCategories(){repository.deleteAllCategories();}

    public LiveData<List<Event>> getAllEvents() {return allEvents;}

    public void insertEvent(Event event) {repository.insertEvent(event);}

    public void deleteEvent(String name){repository.deleteEvent(name);}

    public void deleteAllEvents(){repository.deleteAllEvents();}
}
