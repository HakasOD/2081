package com.exammple.eventmanager1.appmanagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.exammple.eventmanager1.provider.Category;
import com.exammple.eventmanager1.provider.Event;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DatabaseManagement {
    private static final Gson gson = new Gson();
    public static ArrayList<Event> getEventDatabaseFromSharedPreferences(Context context){
        String dbRestored = context.getSharedPreferences(KeyStore.EVENT_DB_FILE, MODE_PRIVATE)
                .getString(KeyStore.EVENT_DB_KEY, "[]");
        Type type = new TypeToken<ArrayList<Event>>(){}.getType();
        return gson.fromJson(dbRestored, type);
    }

    public static void saveEventDatabaseToSharedPreferences(Context context, ArrayList<Event> eventDb){
        String dbString = gson.toJson(eventDb);
        SharedPreferences.Editor edit = context.getSharedPreferences
                (KeyStore.EVENT_DB_FILE, MODE_PRIVATE).edit();
        edit.putString(KeyStore.EVENT_DB_KEY, dbString);
        edit.apply();
    }


    public static void clearEventDatabase(Context context){
        ArrayList<Event> db = DatabaseManagement.getEventDatabaseFromSharedPreferences(context);
        db.clear();
        DatabaseManagement.saveEventDatabaseToSharedPreferences(context, db);
    }

    public static void removeLastElementFromEventDatabase(Context context){
        ArrayList<Event> db = getEventDatabaseFromSharedPreferences(context);
        Event lastElement = db.get(db.size() - 1);
        if(!db.isEmpty()) {
            db.remove(lastElement);
        }

        saveEventDatabaseToSharedPreferences(context, db);
    }
    public static void saveCategoryDatabaseToSharedPreferences(Context context, ArrayList<Category> db){
        String dbString = gson.toJson(db);
        SharedPreferences.Editor edit = context.getSharedPreferences
                (KeyStore.EVENT_CATEGORY_DB_FILE, MODE_PRIVATE).edit();
        edit.putString(KeyStore.EVENT_CATEGORY_DB_KEY, dbString);
        edit.apply();
    }

    public static ArrayList<Category> getCategoryDatabaseFromSharedPreferences(Context context){
        String dbRestored = context.getSharedPreferences(KeyStore.EVENT_CATEGORY_DB_FILE, MODE_PRIVATE)
                .getString(KeyStore.EVENT_CATEGORY_DB_KEY, "[]");

        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        return gson.fromJson(dbRestored, type);
    }

    public static void clearCategoriesDatabase(Context context) {
        ArrayList<Category> db =
                getCategoryDatabaseFromSharedPreferences(context);
        db.clear();
        saveCategoryDatabaseToSharedPreferences(context, db);

    }
}
