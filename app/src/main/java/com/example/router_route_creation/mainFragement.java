package com.example.router_route_creation;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

public class mainFragement extends Fragment {
    // display of the main page
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.main_page, container, false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String user = this.getArguments().getString("user");
        Log.e("MainFragemnt", "dataGet:" + user);
        TextView creation = view.findViewById(R.id.Creation);
        TextView profile = view.findViewById(R.id.Profile);
        TextView saved = view.findViewById(R.id.savedRoutes);
        TextView rate = view.findViewById(R.id.Rate);
        TextView comment = view.findViewById(R.id.comment);


        SpannableString s1 = new SpannableString(creation.getText().toString());
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent1 = new Intent(view.getContext(), route_creation.class);
                intent1.putExtra("user", user);
                startActivity(intent1);
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);

            }
        };
        s1.setSpan(clickableSpan1, 0, creation.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        creation.setText(s1);
        creation.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString s2 = new SpannableString(profile.getText().toString());
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent2 = new Intent(view.getContext(),UserProfile.class);
                intent2.putExtra("user", user);
                startActivity(intent2);
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);

            }
        };
        s2.setSpan(clickableSpan2, 0, profile.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        profile.setText(s2);
        profile.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString s3 = new SpannableString(saved.getText().toString());
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent3 = new Intent(view.getContext(), SavedRoutes.class);
                intent3.putExtra("user", user);

                startActivity(intent3);
            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);

            }
        };
        s3.setSpan(clickableSpan3, 0, saved.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        saved.setText(s3);
        saved.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString s4 = new SpannableString(comment.getText().toString());
        ClickableSpan clickableSpan4 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent4 = new Intent(view.getContext(), viewComments.class);
                intent4.putExtra("user", user);
                Log.e("MainFragment", "dataGet:" + user);
                startActivity(intent4);

            }
            @Override
            public void updateDrawState(TextPaint ds){
                super.updateDrawState(ds);
                ds.setColor(Color.BLACK);

            }
        };
        s4.setSpan(clickableSpan4, 0, comment.getText().toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        comment.setText(s4);
        comment.setMovementMethod(LinkMovementMethod.getInstance());;
    }


}
