package com.example.pickt;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.melnykov.fab.FloatingActionButton;


public class HomeFragment extends Fragment {
    ViewGroup viewGroup;
    FloatingActionButton addCar;
    Button showList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        addCar = (FloatingActionButton) viewGroup.findViewById(R.id.addCar);
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadActivity = new Intent(getActivity(), AddCarActivity.class);
                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLoadActivity);
            }
        });

        return viewGroup;
    }
}