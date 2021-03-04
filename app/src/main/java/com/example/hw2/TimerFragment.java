package com.example.hw2;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class TimerFragment extends Fragment implements View.OnClickListener {

    private boolean pressed;
    private Button start, lap, reset;
    private TextView time;
    private OnFragmentInteractionListener listener;

    private TimerAsyncTask asyncTask; // will use this in controller to execute timer.
    boolean running; // boolean to check whether timer should be running or not (changed by clicking on start/stop button).
    private Timer timer;


    public TimerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        //Initialize buttons and textview
        start = (Button) view.findViewById(R.id.start);
        lap = (Button) view.findViewById(R.id.lap);
        reset = (Button) view.findViewById(R.id.reset);

        time = (TextView) view.findViewById(R.id.time);

        //set onClickListeners
        start.setOnClickListener(this);
        lap.setOnClickListener(this);
        reset.setOnClickListener(this);

        //New TimerAsyncTask that controls the timer
        asyncTask = new TimerAsyncTask();

        if (savedInstanceState != null) {
            int hr = savedInstanceState.getInt("hour");
            int min = savedInstanceState.getInt("minute");
            int sec = savedInstanceState.getInt("second");
            timer = new Timer(hr, min, sec);
            pressed = savedInstanceState.getBoolean("pressed");
            running = savedInstanceState.getBoolean("running");
            if (pressed) {
                start.setText("STOP");
            }
            else {
                start.setText("START");
            }
            if (running) {
                asyncTask.execute();
            }
        }
        else {
            //Whether start button has been pressed or not
            pressed = false;
            timer = new Timer();
            //boolean saying whether TimerAsyncTask is running or not
            running = false;
        }




        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("pressed", pressed);
        outState.putBoolean("running", running);
        outState.putInt("hour", timer.getHours());
        outState.putInt("minute", timer.getMinutes());
        outState.putInt("second", timer.getSeconds());

    }

    @Override
    public void onDestroy() {
        //checking if asynctask is still runnning
        if(asyncTask!=null && asyncTask.getStatus()== AsyncTask.Status.RUNNING){
            //cancel the task before destroying activity
            asyncTask.cancel(true);
            asyncTask= null;
        }
        super.onDestroy();
    }

    //Called when frag is first attached to activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            this.listener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()+"must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == start.getId()){
            listener.onButtonClicked(0);
        }
        else if (id == lap.getId()) {
            listener.onButtonClicked(1);
        }
        else if (id == reset.getId()) {
            listener.onButtonClicked(2);
        }
    }

    public void startAction() {
        //Start timer
        if (!pressed) {
            pressed = true;
            running = true;
            start.setText("STOP");
            if (asyncTask.getStatus() != AsyncTask.Status.RUNNING) {
                asyncTask = new TimerAsyncTask();
                asyncTask.execute();
            }
        }
        //Stop timer
        else {
            pressed = false;
            running = false;
            start.setText("START");
            if (asyncTask.getStatus() == AsyncTask.Status.RUNNING) {
                asyncTask.cancel(true);
                asyncTask = new TimerAsyncTask();
            }
        }
    }

    public void resetAction() {
        timer.reset();
        time.setText(timer.toString());
        asyncTask.cancel(true);
        running = false;
        start.setText("START");
        pressed = false;
    }

    public String getTime() {
        return timer.toString();
    }

    public interface OnFragmentInteractionListener {
        void onButtonClicked(int id);
    }


    /**
     * AsyncTask class for Timer
     */
    private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            String curr_time = timer.toString();
            time.setText(curr_time); // update UI.
        }

        @Override
        protected Void doInBackground(Integer... times) {
            while (running) { // running boolean must be updated in controller.
                timer.calc(); // calculate time.
                publishProgress(); // publish progress for onProgressUpdate method to be triggered.
                try {
                    if (this.isCancelled()) {
                        break;
                    }
                    Thread.sleep(1000); // sleep for 1 second (1000 milliseconds).
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}