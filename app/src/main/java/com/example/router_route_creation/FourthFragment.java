package com.example.router_route_creation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class FourthFragment extends Fragment {
    private RecyclerView recyclerView;
    CommentDatabaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // the page for comment selection and will bring the user to edit text fragment.
        View view= inflater.inflate(R.layout.fourth_fragment, container, false);
        return view;

    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new CommentDatabaseHelper(getActivity());
        String user = this.getArguments().getString("user");
        Log.e("FourthFragment", "dataGet: " + user);
        ArrayList<Comments> routes = db.getData();
        ArrayList<Comments> customList = new ArrayList<>();
        for (Comments comment : routes) {
            if(comment.getUser().equals(user)){
                customList.add(comment);
            }
        }
        if(customList.size() > 0){
            recyclerView = view.findViewById(R.id.cRecyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
            recyclerView.setLayoutManager(linearLayoutManager);
            CommentAdapter commentAdapter = new CommentAdapter(customList, getActivity());
            recyclerView.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();
        }
        else{

        }
    }
}
