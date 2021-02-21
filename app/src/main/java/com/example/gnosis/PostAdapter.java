package com.example.gnosis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gnosis.models.Post;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter <PostAdapter.MyViewHolder> {

    private PostAdapter.OnItemClickListener listener;
    public interface OnItemClickListener{
        void onItemClick (int pos);
        void onDeleteClick (int pos);
        void onEditClick (int pos);
    }

    public void setOnItemClickListener(PostAdapter.OnItemClickListener listener){
        this.listener = listener;
    }



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
        return new PostAdapter.MyViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final PostAdapter.MyViewHolder h = holder;
        final int pos = position;
        h.title.setText(posts.get(position).getTitulo());
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
        public TextView category;
        public TextView createdAt;
        public ImageView remove;
        public ImageView edit;

        public MyViewHolder(@NonNull View itemView, final PostAdapter.OnItemClickListener listener) {
            super(itemView);

            myView = itemView;
            this.title = itemView.findViewById(R.id.titlePost);
            this.category = itemView.findViewById(R.id.categoryId);
            this.createdAt = itemView.findViewById(R.id.createdAt);
            this.remove = itemView.findViewById(R.id.remove_post_icon);
            this.edit = itemView.findViewById(R.id.edit_post_icon);

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

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onDeleteClick(pos);
                        }
                    }
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listener.onEditClick(pos);
                        }
                    }
                }
            });

        }
    }
}
