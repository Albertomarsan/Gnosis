package com.example.gnosis;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gnosis.models.Comment;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter <CommentAdapter.MyViewHolder> {

    ArrayList<Comment> comments;
    Context context;
    public CommentAdapter(ArrayList<Comment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_comment_adapter, parent, false);
        return new CommentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final CommentAdapter.MyViewHolder h = holder;
        final int pos = position;
        h.content.setText(comments.get(position).getContent());
        h.createdAt.setText(comments.get(position).getCreatedAt());
        h.username.setText(comments.get(position).getUsername());

    }


    @Override
    public int getItemCount() {
        return comments.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View myView;
        public ImageView profilePic;
        public TextView username;
        public TextView createdAt;
        public TextView content;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            myView = itemView;
            this.profilePic = itemView.findViewById(R.id.profilePic_comment);
            this.content = itemView.findViewById(R.id.content_comment);
            this.username = itemView.findViewById(R.id.username_comment);
            this.createdAt = itemView.findViewById(R.id.createdAt_comment);


        }
    }
}
