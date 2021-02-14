package com.example.gnosis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.models.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter <PostAdapter.MyViewHolder> {
    ArrayList <Post> posts;
    Context context;
    public PostAdapter(ArrayList<Post> posts, Context context) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post_adapter, parent, false);
        return new PostAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PostAdapter.MyViewHolder h = holder;
        final int pos = position;
        h.title.setText(posts.get(position).getTitulo());
        h.content.setText(posts.get(position).getContenido());
        h.createdAt.setText(posts.get(position).getCreado_el());
        h.category.setText(posts.get(position).getCategoria());
    }


    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View myView;
        public TextView title;
        public TextView content;
        public TextView category;
        public TextView createdAt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myView = itemView;
            this.title = itemView.findViewById(R.id.titlePost);
            this.content = itemView.findViewById(R.id.contentPost);
            this.category = itemView.findViewById(R.id.categoryId);
            this.createdAt = itemView.findViewById(R.id.createdAt);
        }
    }
}
