package com.exammple.eventmanager1.recycleradapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exammple.eventmanager1.R;
import com.exammple.eventmanager1.provider.Category;

import java.util.ArrayList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CustomViewHolder> {
    ArrayList<Category> db = new ArrayList<>();

    public void setDb(ArrayList<Category> db){
        this.db = db;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.category_card_layout, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvCategoryName.setText(db.get(position).getName());
        holder.tvCategoryId.setText(db.get(position).getCategoryId());
        holder.tvCategoryEventCount.setText(String.valueOf(db.get(position).getEventCount()));
        if(db.get(position).isActive()){
            holder.tvIsActive.setText("Active");
        }else {
            holder.tvIsActive.setText("Inactive");
        }
    }

    @Override
    public int getItemCount() {
        if (this.db != null) {
            return this.db.size();
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCategoryId;
        public TextView tvCategoryName;
        public TextView tvCategoryEventCount;
        public TextView tvIsActive;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryId = itemView.findViewById(R.id.tv_CategoryId);
            tvCategoryName = itemView.findViewById(R.id.tv_CategoryName);
            tvCategoryEventCount = itemView.findViewById(R.id.tv_CategoryEventCount);
            tvIsActive = itemView.findViewById(R.id.tv_IsActive);
        }
    }
}
