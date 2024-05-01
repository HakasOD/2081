package com.exammple.eventmanager1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exammple.eventmanager1.provider.EventManagerViewModel;
import com.exammple.eventmanager1.recycleradapters.CategoryRecyclerAdapter;
import com.exammple.eventmanager1.provider.Category;
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
    private EventManagerViewModel eventManagerViewModel;
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
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_list_category, container, false);


        //Set adapter with recyclerview
        recyclerView = view.findViewById(R.id.rvCategory);

        categoryRecyclerAdapter = new CategoryRecyclerAdapter();
        recyclerView.setAdapter(categoryRecyclerAdapter);

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        eventManagerViewModel = new ViewModelProvider(this).get(EventManagerViewModel.class);
        eventManagerViewModel.getAllCategories().observe(getViewLifecycleOwner(), newData -> {
            categoryRecyclerAdapter.setDb((ArrayList<Category>) newData);
            categoryRecyclerAdapter.notifyDataSetChanged();
        });

        return view;
    }


}