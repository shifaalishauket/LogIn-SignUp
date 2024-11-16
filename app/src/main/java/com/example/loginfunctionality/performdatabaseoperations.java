package com.example.loginfunctionality;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class performdatabaseoperations extends Fragment {

    AppDatabase database;
    List<Note> noteList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_performdatabaseoperations, container, false);


        database = AppDatabase.getInstance(requireActivity());
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform the database operation on the background thread
                noteList = database.noteDao().getAllNotes();

                // Use a Handler to update the UI after the database operation is complete
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Update the UI or notify the user that the operation is complete
                        RecyclerView recyclerView = view.findViewById(R.id.recycler);
                        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

                        // Initialize and set the adapter for the RecyclerView
                        noteListAdapter adapter = new noteListAdapter(noteList);
                        recyclerView.setAdapter(adapter);
                    }
                });
            }
        })
                .start();


        return view;
    }
}
