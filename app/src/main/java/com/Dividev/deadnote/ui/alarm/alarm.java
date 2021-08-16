package com.Dividev.deadnote.ui.alarm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.deadnote.R;

public class alarm extends Fragment {

    private AlarmViewModel mViewModel;

    public static alarm newInstance() {
        return new alarm();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.alarm_fragment, container, false);
        return root;
    }

}