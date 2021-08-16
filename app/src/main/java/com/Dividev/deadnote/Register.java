package com.Dividev.deadnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deadnote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    EditText mnama,memail,mpassword,mtelephone;
    Button mregister_button;
    FirebaseAuth fAuth;
    ProgressBar mload;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mnama = findViewById(R.id.nama);
        memail = findViewById(R.id.email);
        mpassword = findViewById(R.id.password);
        mtelephone = findViewById(R.id.telephone);
        mregister_button = findViewById(R.id.register_button);
        fAuth =FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        mload = findViewById(R.id.load);

        mregister_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail.getText().toString().trim();
                String password = mpassword.getText().toString().trim();
                String nama = mnama.getText().toString().trim();
                String telephone = mtelephone.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    memail.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword.setError("password is required");
                    return;
                }
                if(password.length() < 6){
                    mpassword.setError("password must be >= 6 character");
                    return;
                }
                mload.setVisibility(View.VISIBLE);
                //
                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this,"User created", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("fName",nama);
                            user.put("fpassword",password);
                            user.put("femail",email);
                            user.put("ftelephone",telephone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "onSuccess : user profile is created for" + userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG", "onFailure : " + e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Register.this,"error" + task.getException(), Toast.LENGTH_SHORT).show();
                            mload.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
    public void gotoactivitylogin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}