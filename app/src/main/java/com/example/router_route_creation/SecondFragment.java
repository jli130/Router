package com.example.router_route_creation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
// fragment that used to display the saved routes
public class SecondFragment extends Fragment {
    private RecyclerView recyclerView;
    SavedDatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.second_fragement, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new SavedDatabaseHelper(getActivity());
        String user = this.getArguments().getString("user");
        Log.e("SecondFragment", "dataGet: " + user);
        ArrayList<Routes> routes = db.getData();
        ArrayList<Routes> customList = new ArrayList<>();
        for (Routes route : routes) {
            if(route.getUser().equals(user)){
                customList.add(route);
            }
        }
        if(customList.size() > 0){
            recyclerView = view.findViewById(R.id.pView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            SavedAdapter savedAdapter = new SavedAdapter(customList, getActivity());
            recyclerView.setAdapter(savedAdapter);
            savedAdapter.notifyDataSetChanged();
        }
        else{

        }

    }


}
