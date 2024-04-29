package com.exammple.eventmanager1.recycleradapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exammple.eventmanager1.R;
import com.exammple.eventmanager1.provider.Event;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.CustomViewHolder>{
    ArrayList<Event> db = new ArrayList<>();

    public void setDb(ArrayList<Event> db){
        this.db = db;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.event_card_layout, parent, false);

        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvEventId.setText(db.get(position).getEventId());
        holder.tvCategoryId.setText(db.get(position).getCategoryId());
        holder.tvCategoryName.setText(db.get(position).getName());
        holder.tvTicketsAvailable.setText(String.valueOf(db.get(position).getTicketsAvailable()));
        if(db.get(position).isActive){
            holder.tvIsActive.setText("Active");
        }else holder.tvIsActive.setText("Inactive");

    }

    @Override
    public int getItemCount() {
        if(this.db != null){
            return this.db.size();
        }
        return 0;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder{
        public TextView tvEventId;
        public TextView tvCategoryId;
        public TextView tvCategoryName;
        public TextView tvTicketsAvailable;
        public TextView tvIsActive;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEventId = itemView.findViewById(R.id.tv_Eventid);
            tvCategoryId = itemView.findViewById(R.id.tv_CategoryId);
            tvCategoryName = itemView.findViewById(R.id.tv_CategoryName);
            tvTicketsAvailable = itemView.findViewById(R.id.tv_TicketsAvailible);
            tvIsActive = itemView.findViewById(R.id.tv_IsActive);
        }


    }
}
