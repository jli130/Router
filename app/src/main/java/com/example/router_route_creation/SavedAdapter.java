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
// this is a class created for recyclerview. This sets up the data collection and display in the
// recyclerview.
public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.viewHolder>{
    Context context;
    ArrayList<Routes> routesArrayList;
    SavedDatabaseHelper db;
    public SavedAdapter(ArrayList<Routes> list, Context context){
        this.context = context;
        this.routesArrayList = list;

    }

    @NonNull
    @Override
    public SavedAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.saved_routes_item, parent, false);
        return new SavedAdapter.viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        // get the routes type, routes start point, routes destination, and routes time
        final Routes routes = routesArrayList.get(position);
        db = new SavedDatabaseHelper(context);
        holder.routesType.setText(routes.getType());
        holder.routesStart.setText(routes.getStart());
        holder.routesEnd.setText(routes.getEnd());
        holder.routesTime.setText(routes.getTime());
        holder.btnDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.delete(routes.getUser(), routes.getType(), routes.getStart(), routes.getEnd(), routes.getTime());
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
            private Button btnDeleted;
            public viewHolder(@NonNull View itemView){
                super(itemView);
                rootView = itemView;
                btnDeleted = rootView.findViewById(R.id.btnDelete);
                routesType = itemView.findViewById(R.id.routesType);
                routesStart = itemView.findViewById(R.id.routesStart);
                routesEnd = itemView.findViewById(R.id.routesEnd);
                routesTime = itemView.findViewById(R.id.routesTime);

            }
    }
}
