package com.example.pickt;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pickt.adapter.TrailerListAdapter;
import com.example.pickt.model.TrailerModel;
import com.google.gson.JsonArray;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;


public class HomeFragment extends Fragment {
    ViewGroup viewGroup;
    // FloatingActionButton addCar;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    TrailerListAdapter trailerListAdapter;

    TextView trailerName, rentalPlace;

    public static final long DOUBLE_PRESS_INTERVAL = 250;  // milli-seconds
    public long lastPressTime;

    public boolean mHasDoubleClicked = false;

    ArrayList<TrailerModel> arrayList;

    private Toast toast;

    public HomeFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.trailerRecyclerView);

        trailerName = view.findViewById(R.id.editTrailerName);
        rentalPlace = view.findViewById(R.id.editRentalPlace);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getTrailers();
        return view;

        /*
        MyCarData[] myCarData = new MyCarData[]{
                new MyCarData(R.drawable.trailer1, "제이코 카라반 18피트", "광주 전남대점", "74,820원~"),
                new MyCarData(R.drawable.trailer2, "코치맨 카라반 19피트 에이맥스 나노", "광주 일곡점", "83,210원~"),
                new MyCarData(R.drawable.trailer3, "캠핑 트레일러 카라반", "광주 일곡점", "52,900원~"),
                new MyCarData(R.drawable.trailer4, "블루밴 마이크로 XA", "광주 용봉점", "69,210원~")
        };
         */

        // 프래그먼트에서 커스텀 리스너 객체 생성 및 전달
        //TrailerListAdapter myCarAdapter = new TrailerListAdapter(myCarData, HomeFragment.this);
        //recyclerView.setAdapter(myCarAdapter);


//        trailerListAdapter.setOnItemClickListener(new TrailerListAdapter.OnItemClickListener() {
//            @Override
//           public boolean onItemClick(View v, int position) {
//                long pressTime = System.currentTimeMillis();
                // 더블 탭 -> 다음 차량 이동
//                if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL){

//                    Toast.makeText(getContext(), "관심 차량으로 등록되었습니다", Toast.LENGTH_SHORT).show();
//                    mHasDoubleClicked = true;
//                }
                // 한번 탭 -> 다음 액티비티로 전환
//                else {
//                    mHasDoubleClicked = false;
//                   Handler tabHandler = new Handler() {
//                        public void handleMessage(Message m) {
//                            if (!mHasDoubleClicked) {

                                /*
                                Intent intentLoadActivity = new Intent(getActivity(), DetailCarActivity.class);
                                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intentLoadActivity);
                                 */
//                                Toast.makeText(getContext(), "다음 액티비티로 전환됩니다.", Toast.LENGTH_SHORT).show();
//                            }
//                       }
//                    };
//                    Message m = new Message();
//                    tabHandler.sendMessageDelayed(m, DOUBLE_PRESS_INTERVAL);
//                }
//                lastPressTime = pressTime;
//                return true;
//            }
//        });

        // 차량 추가 페이지 이동
        /*
        addCar = (FloatingActionButton) viewGroup.findViewById(R.id.addCar);
        addCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLoadActivity = new Intent(getActivity(), AddTrailerActivity.class);
                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLoadActivity);
            }
        });
         */
//        return viewGroup;
    }

    public void getTrailers() {
        arrayList = new ArrayList<>();
        String url = "http://101.101.209.224:3001/api/pickt/trailers";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("success")) {
                        JSONArray jsonArray = response.getJSONArray("trailer");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            TrailerModel trailerModel = new TrailerModel(
                                    jsonObject.getString("_id"),
                                    jsonObject.getString("trailerName"),
                                    //jsonObject.getString("license"),
                                    jsonObject.getString("rentalPlace")
                                    //jsonObject.getString("capacity"),
                                    //jsonObject.getString("facilities"),
                                    //jsonObject.getString("description")
                            );

                            arrayList.add(trailerModel);
                        }
                        trailerListAdapter = new TrailerListAdapter(getActivity(), arrayList);
                        recyclerView.setAdapter(trailerListAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error == null || error.networkResponse == null){
                    return;
                }

                //String body;

                /*
                try {

                }catch (){

                }

                 */
                // 토큰 있나 없나 찾는 것
                /*
                body = new String(error.networkResponse.data, "UTF-8");
                JSONObject errorObject = new JSONObject(body);

                if (errorObject.getString("msg").equals("Token not valid")){
                    // 다시 로그인 화면으로 되돌아감
                }
                 */

            }
        });

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    /*
    public void onItemClick(int position){
        Toast.makeText(getActivity(), "Position "+position, Toast.LENGTH_SHORT).show();
    }
     */

}