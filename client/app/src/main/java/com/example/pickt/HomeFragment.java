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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.melnykov.fab.FloatingActionButton;


public class HomeFragment extends Fragment {
    ViewGroup viewGroup;
    FloatingActionButton addCar;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.carRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        MyCarData[] myCarData = new MyCarData[]{
                new MyCarData(R.drawable.trailer1, "제이코 카라반 18피트", "광주 전남대점", "74,820원~"),
                new MyCarData(R.drawable.trailer2, "코치맨 카라반 19피트 에이맥스 나노", "광주 일곡점", "83,210원~"),
                new MyCarData(R.drawable.trailer1, "캠핑 트레일러 카라반", "광주 일곡점", "52,900원~"),
                new MyCarData(R.drawable.trailer1, "블루밴 마이크로 XA", "광주 용봉점", "69,210원~")
        };

        MyCarAdapter myCarAdapter = new MyCarAdapter(myCarData, HomeFragment.this);
        recyclerView.setAdapter(myCarAdapter);

        // 차량 추가
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