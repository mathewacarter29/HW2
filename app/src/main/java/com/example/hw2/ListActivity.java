package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ListActivity extends AppCompatActivity {

    private ListFragment listFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish();
            return;
        }

        listFrag = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            listFrag.addTime(bundle.getString("times"));
        }
    }



}