package com.example.gnosis.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.R;
import com.example.gnosis.models.Post;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter <HomeAdapter.MyViewHolder> {

    private HomeAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick (int pos);
    }

    public void setOnItemClickListener(HomeAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    ArrayList<Post> posts;
    public HomeAdapter(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @NonNull
    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_home_adapter, parent, false);
        return new HomeAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final HomeAdapter.MyViewHolder h = holder;
        final int pos = position;
        h.title.setText(posts.get(position).getTitulo());
        h.createdAt.setText(posts.get(position).getCreado_el());
        h.category.setText(posts.get(position).getCategoria());
        h.username.setText(posts.get(position).getUsername());
        h.category.setText(posts.get(position).getCategoria());
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View myView;
        public TextView title;
        public TextView category;
        public TextView createdAt;
        public TextView username;
        public ImageView profilePic;


        public MyViewHolder(@NonNull View itemView, final HomeAdapter.OnItemClickListener listener) {
            super(itemView);

            myView = itemView;
            this.title = itemView.findViewById(R.id.titlePost_home);
            this.category = itemView.findViewById(R.id.categoryPost_home);
            this.createdAt = itemView.findViewById(R.id.createdAt_home);
            this.username = itemView.findViewById(R.id.username_home);
            this.profilePic = itemView.findViewById(R.id.profilePic_home);

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
