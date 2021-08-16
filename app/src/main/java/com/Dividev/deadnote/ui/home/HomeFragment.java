    package com.Dividev.deadnote.ui.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.Dividev.deadnote.RecycleAdapter;
import com.Dividev.deadnote.InputActivity;
import com.example.deadnote.R;
import com.Dividev.deadnote.note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    FloatingActionButton fab_add;
    RecycleAdapter recycleAdapter;
    DatabaseReference database;
    ArrayList<note> listnote;
    RecyclerView rv_view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        database = FirebaseDatabase.getInstance().getReference();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        fab_add = (FloatingActionButton)root.findViewById(R.id.fab_add);
        rv_view = (RecyclerView) root.findViewById(R.id.rv_view);
        fab_add.setColorFilter(Color.WHITE);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(getContext());
        rv_view.setLayoutManager(mLayout);
        rv_view.setItemAnimator(new DefaultItemAnimator());
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputActivity inputActivity = new InputActivity("","","","","Tambah");
                inputActivity.show(getFragmentManager(),"form");
            }
        });
        showData();
        return root;
    }
    private void  showData(){

        database.child("test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listnote = new ArrayList<>();
                for (DataSnapshot item : snapshot.getChildren()) {
                    note not = item.getValue(note.class);
                    not.setKey(item.getKey());
                    listnote.add(not);
                }
                recycleAdapter = new RecycleAdapter(listnote,getActivity());
                rv_view.setAdapter(recycleAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
}