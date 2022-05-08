package com.example.needlework;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class firstFragment extends Fragment {

    RecyclerView myRecycleView;

    public firstFragment() {
    }
    public static firstFragment newInstance(String param1, String param2) {
        return new firstFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        myRecycleView = view.findViewById(R.id.recyclerHorizontal);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        myRecycleView.setLayoutManager(manager);
        return view;
    }
}