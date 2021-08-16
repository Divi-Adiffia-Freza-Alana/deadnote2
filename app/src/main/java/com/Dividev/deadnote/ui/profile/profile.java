package com.Dividev.deadnote.ui.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.deadnote.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class profile extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String userId;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.profile_fragment, container, false);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        TextView txt_nama = (TextView)root.findViewById(R.id.txt_nama);
        TextView txt_email = (TextView)root.findViewById(R.id.txt_email);
        TextView txt_phone = (TextView)root.findViewById(R.id.txt_phone);

        TextView txt_nama_hd = (TextView)root.findViewById(R.id.txt_nama_hd);
        TextView txt_email_hd = (TextView)root.findViewById(R.id.txt_email_hd);

        DocumentReference documentReference = fstore.collection("users").document(userId);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                txt_nama.setText(value.getString("fName"));
                txt_email.setText(value.getString("femail"));
                txt_phone.setText(value.getString("ftelephone"));
                txt_nama_hd.setText(value.getString("fName"));
                txt_email_hd.setText(value.getString("femail"));
            }
        });

         return root;
    }



}