package com.exammple.eventmanager1.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exammple.eventmanager1.recycleradapters.CategoryRecyclerAdapter;
import com.exammple.eventmanager1.entities.Category;
import com.exammple.eventmanager1.appmanagement.DatabaseManagement;
import com.exammple.eventmanager1.appmanagement.KeyStore;
import com.exammple.eventmanager1.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListCategory extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ArrayList<Category> db;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;



    public FragmentListCategory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListCategory.
     */
    public static FragmentListCategory newInstance(String param1, String param2) {
        FragmentListCategory fragment = new FragmentListCategory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Used to upload a single category to the database
     */
    public void updateCategoryToDatabase(){
        // Get saved fields
        SharedPreferences sharedPreferences = getContext().
                getSharedPreferences(KeyStore.EVENT_CATEGORY_FILE,MODE_PRIVATE);
        String categoryId = sharedPreferences.getString(KeyStore.CATEGORY_ID_KEY, "");
        String name = sharedPreferences.getString(KeyStore.CATEGORY_NAME_KEY, "");
        int eventCount = sharedPreferences.getInt(KeyStore.EVENT_COUNT_KEY, 0);
        boolean isActive = sharedPreferences.getBoolean(KeyStore.IS_ACTIVE_KEY, false);

        // Clear temporary shared preferences to stop refresh
        // button repeatedly uploading same category
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Upload Category to database
        if(!name.isEmpty()) {   // Only if there is data there
            Category category = new Category(categoryId, name, eventCount, isActive);
            db = DatabaseManagement.getCategoryDatabaseFromSharedPreferences(getContext());
            db.add(category);
            categoryRecyclerAdapter.setDb(db);
            categoryRecyclerAdapter.notifyDataSetChanged();

            DatabaseManagement.saveCategoryDatabaseToSharedPreferences(getContext(), db);
        }
    }

    /**
     * Used to update the database so it can be displayed in recyclerview
     */
    public void updateRecyclerView(){
        db = DatabaseManagement.getCategoryDatabaseFromSharedPreferences(getContext());

        // Update UI
        categoryRecyclerAdapter.setDb(db);
        categoryRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db = DatabaseManagement.getCategoryDatabaseFromSharedPreferences(getContext());

        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);

        //Set adapter with recyclerview
        // Recycler view cannot be refreshed if the previous activity was new event category
        Intent intent = getActivity().getIntent();

        recyclerView = view.findViewById(R.id.rvCategory);
        if(intent.getStringExtra("fromActivity") == null)
        {
            categoryRecyclerAdapter = new CategoryRecyclerAdapter();
            categoryRecyclerAdapter.setDb(db);
            recyclerView.setAdapter(categoryRecyclerAdapter);

            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
        } else if (!intent.getStringExtra("fromActivity").equals("NewEventCategory")) {
            categoryRecyclerAdapter = new CategoryRecyclerAdapter();
            categoryRecyclerAdapter.setDb(db);
            recyclerView.setAdapter(categoryRecyclerAdapter);

            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
        }

        return view;
    }
}