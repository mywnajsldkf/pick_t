package com.example.pickt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.pickt.UtilsService.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrailerInfoActivity<JSONObjectRequest> extends AppCompatActivity {

    SharedPreferenceClass sharedPreferenceClass;
    String token;

    private TextView registerTextView, rentalPlaceTextView, licenseTextView, capacityTextView, facilityTextView, descriptionTextView;
    private Button reserveBtn;

    private RequestQueue requestQueue;

    private String trailerName, license, rentalPlace, capacity, facilities, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_info);

        Intent intent = getIntent();
        Bundle TrailerData = intent.getExtras();
        String id = TrailerData.getString("id");
        int position = TrailerData.getInt("position");


        sharedPreferenceClass = new SharedPreferenceClass(this);

        registerTextView = (TextView)findViewById(R.id.registerText);
        rentalPlaceTextView = (TextView)findViewById(R.id.rentalPlaceText);
        licenseTextView = (TextView)findViewById(R.id.licenseText);
        capacityTextView = (TextView)findViewById(R.id.capacityText);
        facilityTextView = (TextView)findViewById(R.id.facilityText);
        descriptionTextView = (TextView)findViewById(R.id.descriptionText);

        reserveBtn = (Button)findViewById(R.id.reserveBtn);

        // position값이 잘 넘어오는지 확인
        Toast.makeText(this, Integer.toString(position),Toast.LENGTH_LONG).show();


        // 트레일러의 정보를 확인하는 함수에 id, position 을 함께 보냄
        getTrailerInfo(id, position);
    }

    // getTrailerInfo
    private void getTrailerInfo(final String id, final int position){
        String url = "http://101.101.209.224:3001/api/pickt/trailers/"+id;


        // JSON 형식으로 저장된 것들을 JSON으로 가져온다.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // response 응답이 성공했을 때
                        try {
                            if (response.getBoolean("success")) {

                                //getInfo(id, position);
                                getInfo(id, position);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
        }){
            // token 인증
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };
    }

    //데이터베이스에서 새로운 값을 가져온다.
    private void getInfo(String id, int position){
        //Toast.makeText(this, id,Toast.LENGTH_LONG).show();
        // Toast.makeText(this, position, Toast.LENGTH_LONG).show();
        Toast.makeText(this, Integer.toString(position),Toast.LENGTH_LONG).show();
    }
}