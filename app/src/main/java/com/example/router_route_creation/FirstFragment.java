package com.example.router_route_creation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FirstFragment extends Fragment {
    LogInDataBaseHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // the fragment where the user information is stored.
        View view= inflater.inflate(R.layout.first_fragment, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String user = this.getArguments().getString("user");
        String pass;
        Log.e("FirstFragment", "dataGet: " + user);
        TextView userName = view.findViewById(R.id.dAccount);
        TextView passWord = view.findViewById(R.id.fragPass);
        db = new LogInDataBaseHelper(getActivity());
        ArrayList<User> userList = db.getData();
        for(User users : userList){
            if(users.getUser().equals(user)){
                pass = users.getPassword();
                userName.setText(user);
                passWord.setText(pass);
            }
        }
        Button button = (Button) view.findViewById(R.id.changePass);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), changePassword.class);
                intent.putExtra("user", user);
                Log.e("FirstFragment", "dataPassed: " + user);
                startActivity(intent);
            }
        });



    }

}
