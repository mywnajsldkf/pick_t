package com.example.pickt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

    public static final long DOUBLE_PRESS_INTERVAL = 250;  // milli-seconds
    public long lastPressTime;

    public boolean mHasDoubleClicked = false;

    private Toast toast;

    GestureDetector gd;
    GestureDetector.OnDoubleTapListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup)inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = (RecyclerView) viewGroup.findViewById(R.id.carRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // MyCarAdapter adapter = new MyCarAdapter();

        MyCarData[] myCarData = new MyCarData[]{
                new MyCarData(R.drawable.trailer1, "제이코 카라반 18피트", "광주 전남대점", "74,820원~"),
                new MyCarData(R.drawable.trailer2, "코치맨 카라반 19피트 에이맥스 나노", "광주 일곡점", "83,210원~"),
                new MyCarData(R.drawable.trailer3, "캠핑 트레일러 카라반", "광주 일곡점", "52,900원~"),
                new MyCarData(R.drawable.trailer4, "블루밴 마이크로 XA", "광주 용봉점", "69,210원~")
        };

        // 프래그먼트에서 커스텀 리스너 객체 생성 및 전달
        MyCarAdapter myCarAdapter = new MyCarAdapter(myCarData, HomeFragment.this);
        recyclerView.setAdapter(myCarAdapter);

        myCarAdapter.setOnItemClickListener(new MyCarAdapter.OnItemClickListener() {
            @Override
            public boolean onItemClick(View v, int position) {
                long pressTime = System.currentTimeMillis();
                // 더블 탭 -> 다음 차량 이동
                if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL){

                    Toast.makeText(getContext(), "관심 차량으로 등록되었습니다", Toast.LENGTH_SHORT).show();
                    mHasDoubleClicked = true;
                }
                // 한번 탭 -> 다음 액티비티로 전환
                else {
                    mHasDoubleClicked = false;
                    Handler tabHandler = new Handler() {
                        public void handleMessage(Message m) {
                            if (!mHasDoubleClicked) {

                                Intent intentLoadActivity = new Intent(getActivity(), DetailCarActivity.class);
                                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentLoadActivity);
                                // Toast.makeText(getContext(), "다음 액티비티로 전환됩니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    };
                    Message m = new Message();
                    tabHandler.sendMessageDelayed(m, DOUBLE_PRESS_INTERVAL);
                }
                lastPressTime = pressTime;
                return true;
            }
        });

        // 차량 추가 페이지 이동
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