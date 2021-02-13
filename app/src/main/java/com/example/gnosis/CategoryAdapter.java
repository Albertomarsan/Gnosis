package com.example.gnosis;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter <CategoryAdapter.MyViewHolder> {

    private OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick (int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }


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
        return new MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final MyViewHolder h = holder;
        final int pos = position;
        h.title.setText(categories.get(position).getNombre());
        h.description.setText(categories.get(position).getDescription());
        h.capaColores.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));

        if(categories.get(position).isSelected())
            h.selectedIcon.setVisibility(View.VISIBLE);
        else
            h.selectedIcon.setVisibility(View.INVISIBLE);
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View myView;
        public TextView title;
        public TextView description;
        public LinearLayout capaColores;
        public ImageView selectedIcon;

        public MyViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            myView = itemView;
            this.title = itemView.findViewById(R.id.title);
            this.description = itemView.findViewById(R.id.description);
            this.capaColores = itemView.findViewById(R.id.capaColores);
            this.selectedIcon = itemView.findViewById(R.id.selectedIcon);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }

}
