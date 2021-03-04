package com.example.hw2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ListFragment extends Fragment {

    private TextView list;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        list = (TextView) view.findViewById(R.id.list);
        list.setMovementMethod(ScrollingMovementMethod.getInstance());

        // Inflate the layout for this fragment
        return view;
    }

    public void addTime(String time) {
        list.setText(list.getText() + "\n" + time);
    }

    public void clearList() {
        list.setText("");
    }


}