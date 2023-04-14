package com.example.router_route_creation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.viewHolder> {
    Context context;
    ArrayList<Comments> commentsArrayList;
    CommentDatabaseHelper db;

    public CommentAdapter(ArrayList<Comments> list, Context context) {
        this.context = context;
        this.commentsArrayList = list;

    }

    @NonNull
    @Override
    public CommentAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_view_item, parent, false);
        return new CommentAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.viewHolder holder, int position) {
        final Comments comments = commentsArrayList.get(position);
        db = new CommentDatabaseHelper(context);
        holder.commentsStart.setText(comments.getStart());
        holder.commentsEnd.setText(comments.getEnd());
        holder.commentsTime.setText(comments.getTime());
        holder.editedComments.setText(comments.getComment());
        holder.btnDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete(comments.getUser(), comments.getType(), comments.getStart(),
                        comments.getEnd(), comments.getTime(), comments.getComment());
                Intent intent = new Intent(context, UserProfile.class);
                intent.putExtra("user", comments.getUser());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentsArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder {
        TextView commentsStart;
        TextView commentsEnd;
        TextView commentsTime;
        View rootView;
        TextView editedComments;
        private Button btnDeleted;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            commentsStart = itemView.findViewById(R.id.commentsStart);
            commentsEnd = itemView.findViewById(R.id.commentsEnd);
            commentsTime = itemView.findViewById(R.id.commentsTime);
            rootView = itemView;
            editedComments = itemView.findViewById(R.id.EditiedComments);
            btnDeleted = rootView.findViewById(R.id.btnViewdelete);

        }
    }
}