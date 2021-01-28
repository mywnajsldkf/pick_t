package com.example.pickt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.pickt.UtilsService.SharedPreferenceClass;
import com.example.pickt.adapter.TrailerListAdapter;
import com.example.pickt.interfaces.RecyclerViewClickListener;
import com.example.pickt.model.TrailerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment implements RecyclerViewClickListener {
    ViewGroup viewGroup;
    SharedPreferenceClass sharedPreferenceClass;
    String token;
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

        sharedPreferenceClass = new SharedPreferenceClass(getContext());
        token = sharedPreferenceClass.getValue_string("token");

        recyclerView = view.findViewById(R.id.trailerRecyclerView);

        trailerName = view.findViewById(R.id.editTrailerName);
        rentalPlace = view.findViewById(R.id.editRentalPlace);

        mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        getTrailers();
        return view;
    }

    // Trailer list로 보여줌
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
                                    jsonObject.getString("license"),
                                    jsonObject.getString("rentalPlace"),
                                    jsonObject.getString("capacity"),
                                    jsonObject.getString("facilities"),
                                    jsonObject.getString("description")
                            );

                            arrayList.add(trailerModel);
                        }
                        trailerListAdapter = new TrailerListAdapter(getActivity(), arrayList, HomeFragment.this);
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

                String body;

                try {
                    body = new String(error.networkResponse.data,"UTF-8");
                    JSONObject errorObject = new JSONObject(body);

                    // 토큰이 유효하지 않다.
                    if (errorObject.getString("msg").equals("Token not valid")){
                        sharedPreferenceClass.clear();
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        Toast.makeText(getActivity(), "Session expired", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity(), errorObject.getString("msg"), Toast.LENGTH_SHORT).show();
                }catch (UnsupportedEncodingException | JSONException e){
                    // exception
                }

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };

        // set retry policy
        int socketTime = 3000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }

    // 관심 차량 등록
    public void onItemDoubleClick(){
        Toast.makeText(getContext(), "관심 차량으로 등록되었습니다.", Toast.LENGTH_SHORT).show();
        mHasDoubleClicked = true;
    }



    public void onItemOneClick(final String id, final int position) {
        mHasDoubleClicked = false;

        Handler tabHandler = new Handler() {
            public void handleMessage(Message m) {
                if (!mHasDoubleClicked) {
                    Intent intentLoadActivity = new Intent(getActivity(), TrailerInfoActivity.class);

                    intentLoadActivity.putExtra("id", id);
                    intentLoadActivity.putExtra("position", position);
                    intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    startActivity(intentLoadActivity);
                }
            }
        };
        Message m = new Message();
        tabHandler.sendMessageDelayed(m, DOUBLE_PRESS_INTERVAL);
    }

    @Override
   public boolean onItemClick(int position) {
        long pressTime = System.currentTimeMillis();
        // 더블 탭 -> 관심 차량 등록
        if (pressTime - lastPressTime <= DOUBLE_PRESS_INTERVAL){
            onItemDoubleClick();
        }
        // 한번 탭 -> 다음 액티비트로 전환
        else {
            onItemOneClick(arrayList.get(position).getId(), position);
        }
        lastPressTime = pressTime;
        return true;
    }
}