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
public class PreviousRoutesAdapter extends RecyclerView.Adapter<PreviousRoutesAdapter.viewHolder>{
    Context context;
    ArrayList<Routes> routesArrayList;
    PreviousRouteDatabaseHelper db;

    public PreviousRoutesAdapter(ArrayList<Routes> list, Context context){
        this.context = context;
        this.routesArrayList = list;

    }

    @NonNull
    @Override
    public PreviousRoutesAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.previous_route_item, parent, false);
        return new PreviousRoutesAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PreviousRoutesAdapter.viewHolder holder, int position) {
        // get the routes type, routes start point, routes destination, and routes time
        final Routes routes = routesArrayList.get(position);
        db = new PreviousRouteDatabaseHelper(context);
        holder.routesType.setText(routes.getType());
        holder.routesStart.setText(routes.getStart());
        holder.routesEnd.setText(routes.getEnd());
        holder.routesTime.setText(routes.getTime());
        holder.btnSaved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SavedRoutes.class);
                intent.putExtra("user", routes.getUser());
                intent.putExtra("type", routes.getType());
                intent.putExtra("start", routes.getStart());
                intent.putExtra("end", routes.getEnd());
                intent.putExtra("time", routes.getTime());
                context.startActivity(intent);
            }
        });
        holder.btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, editComments.class);
                intent.putExtra("user", routes.getUser());
                intent.putExtra("type", routes.getType());
                intent.putExtra("start", routes.getStart());
                intent.putExtra("end", routes.getEnd());
                intent.putExtra("time", routes.getTime());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return routesArrayList.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        TextView routesType;
        TextView routesStart;
        TextView routesEnd;
        TextView routesTime;
        View rootView;
        Button btnSaved;
        Button btnComment;
        public viewHolder(@NonNull View itemView){
            super(itemView);
            routesType = itemView.findViewById(R.id.routesType);
            routesStart = itemView.findViewById(R.id.routesStart);
            routesEnd = itemView.findViewById(R.id.routesEnd);
            routesTime = itemView.findViewById(R.id.routesTime);
            rootView = itemView;
            btnSaved = rootView.findViewById(R.id.btnSaved);
            btnComment = rootView.findViewById(R.id.btnComment);


        }
    }

}
