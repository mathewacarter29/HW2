package com.example.hw2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements TimerFragment.OnFragmentInteractionListener {

    private TimerFragment timerFrag;
    private ListFragment listFrag;
    private Intent intent;
    private final static String TIMES = "times";
    private String times;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerFrag = (TimerFragment) getSupportFragmentManager().findFragmentById(R.id.timerFrag);
        listFrag = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);

        if (savedInstanceState != null) {
            times = savedInstanceState.getString("times");
            if (listFrag != null && listFrag.isInLayout()) {
                listFrag.addTime(times);
            }
        }
        else {
            times = "";
        }
        intent = new Intent(this, ListActivity.class);
        intent.putExtra(TIMES, times);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("times", times);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onButtonClicked(int id) {
        switch (id) {
            //Start button
            case 0:
                timerFrag.startAction();
                break;

            //Lap button
            case 1:
                times += timerFrag.getTime() + "\n";
                if (listFrag == null || !listFrag.isInLayout()) {
                    intent.putExtra(TIMES, times);
                }
                else {
                    listFrag.clearList();
                    listFrag.addTime(times);
                }
                break;

            //Reset button
            case 2:
                times = "";
                if (listFrag != null && listFrag.isInLayout()) {
                    listFrag.clearList();
                }
                else {
                    intent.putExtra(TIMES, times);
                }
                timerFrag.resetAction();
                break;
        }
    }

    public void switchAction(View view) {
        //Phone is in landscape
        if (listFrag != null && listFrag.isInLayout()) {
            //idk what to put here
        }
        //Phone is in portrait
        else {
            startActivity(intent);
        }
    }
}