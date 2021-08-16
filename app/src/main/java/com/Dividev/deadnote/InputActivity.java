package com.Dividev.deadnote;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.deadnote.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InputActivity extends DialogFragment {
    ImageButton button_date;
    Button minput_btn;
    TextView et_date,et_kegiatan,et_status;
    String kegiatan,status,tanggal,key,pilih;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public InputActivity(String kegiatan, String status, String tanggal,String key, String pilih) {
        this.kegiatan = kegiatan;
        this.status = status;
        this.tanggal = tanggal;
        this.key = key;
        this.pilih = pilih;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_input, container, false);

        button_date = (ImageButton)view.findViewById(R.id.btn_date);
        et_kegiatan = (TextView)view.findViewById(R.id.et_kegiatan);
        et_status = (TextView)view.findViewById(R.id.et_status);
        et_date = (TextView)view.findViewById(R.id.et_date);

        et_date.setText(tanggal);
        et_kegiatan.setText(kegiatan);
        et_status.setText(status);

        minput_btn = (Button)view.findViewById(R.id.input_btn);


        button_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int yearNow = calendar.get(Calendar.YEAR);
                int monthNow = calendar.get(Calendar.MONTH);
                int dateNow = calendar.get(Calendar.DAY_OF_MONTH);
                Locale id = new Locale("in", "ID");
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i, i1, i2);
                        et_date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, yearNow, monthNow, dateNow);
                datePickerDialog.setTitle("Pilih Tanggal");
                datePickerDialog.show();
            }
        });

        minput_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _kegiatan = et_kegiatan.getText().toString();
                String _status = et_status.getText().toString();
                String _tanggal = et_date.getText().toString();

                if(TextUtils.isEmpty(_kegiatan)){
                    input((EditText) et_kegiatan,"kegiatan");
                }
                else {

                    if(pilih.equals("Tambah")){
                        database.child("test").push().setValue(new note(_kegiatan,_status,_tanggal)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(),"Data Tersimpan",Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"Data gagal tersimpan",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else if(pilih.equals("Ubah")){
                        database.child("test").child(key).setValue(new note(_kegiatan,_status,_tanggal)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(view.getContext(),"Data berhasil diubah",Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(view.getContext(),"Data gagal diubah",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }

            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
    private void input(EditText txt, String s) {
        txt.setError(s + " tidak boleh kosong ");
        txt.requestFocus();

    }

}
