package com.Dividev.deadnote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.deadnote.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    Button mbtn_nxt_rgs;
    EditText memail_lgn,mpassword_lgn;
    Button mLogin_Btn;
    FirebaseAuth fAuth;
    ProgressBar mload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mbtn_nxt_rgs = findViewById(R.id.btn_nxt_rgs);

        memail_lgn = findViewById(R.id.email_lgn);
        mpassword_lgn = findViewById(R.id.password_lgn);
        mLogin_Btn = findViewById(R.id.LoginBtn);
        fAuth =FirebaseAuth.getInstance();
        mload = findViewById(R.id.load_lgn);

        mLogin_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = memail_lgn.getText().toString().trim();
                String password = mpassword_lgn.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    memail_lgn.setError("email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    mpassword_lgn.setError("password is required");
                    return;
                }
                if(password.length() < 6){
                    mpassword_lgn.setError("password must be >= 6 character");
                    return;
                }
                mload.setVisibility(View.VISIBLE);

                //
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this,"Login Berhasil", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this,"Login Gagal", Toast.LENGTH_SHORT).show();
                            mload.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        mbtn_nxt_rgs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
            }
        });
    }
}