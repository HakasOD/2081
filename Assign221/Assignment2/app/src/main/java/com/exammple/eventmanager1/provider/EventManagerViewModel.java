package com.exammple.eventmanager1.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventManagerViewModel extends AndroidViewModel {
    private EventManagerRepository repository;
    private LiveData<List<Category>> allCategories;
    public EventManagerViewModel(@NonNull Application application) {
        super(application);

        repository = new EventManagerRepository(application);
        allCategories = repository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories(){
        return repository.getAllCategories();
    }

    public void insert(Category category){repository.insertCategory(category);}

    public void deleteCategory(String name){repository.deleteCategory(name);}

    public void deleteAllCategories(){repository.deleteAllCategories();}

    public LiveData<List<Event>> getAllEvents() {return repository.getAllEvents();}
}
