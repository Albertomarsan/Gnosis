package com.example.gnosis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter <CategoryAdapter.MyViewHolder> {
    ArrayList <Category> categories;
    Context context;
    public CategoryAdapter(ArrayList <Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_category_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyViewHolder h = holder;
        final int pos = position;
        h.title.setText(categories.get(position).getTitle());
        h.description.setText(categories.get(position).getDescription());
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View myView;
        public TextView title;
        public TextView description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myView = itemView;
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
        }
    }

}
