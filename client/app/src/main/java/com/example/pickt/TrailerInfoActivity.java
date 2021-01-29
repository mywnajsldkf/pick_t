package com.example.pickt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pickt.UtilsService.SharedPreferenceClass;
import com.example.pickt.model.TrailerModel;

import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import android.util.Base64;


public class TrailerInfoActivity<JSONObjectRequest> extends AppCompatActivity {

    SharedPreferenceClass sharedPreferenceClass;
    String token;

    private TextView registerTextView, rentalPlaceTextView, licenseTextView, costTextView, capacityTextView, facilityTextView, descriptionTextView;
    private ImageView trailerImageView;
    private Button reserveBtn;

    static String guestId, hostId, trailerPhoto, trailerId, trailerName, rentalPlace, cost;
    private RequestQueue requestQueue;

    ArrayList<TrailerModel> arrayList;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_info);

        Intent intent = getIntent();
        Bundle TrailerData = intent.getExtras();
        String id = TrailerData.getString("id");
        int position = TrailerData.getInt("position");


        sharedPreferenceClass = new SharedPreferenceClass(this);
        token = sharedPreferenceClass.getValue_string("token");

        registerTextView = (TextView)findViewById(R.id.RegisterText);
        rentalPlaceTextView = (TextView)findViewById(R.id.rentalPlaceText);
        licenseTextView = (TextView)findViewById(R.id.licenseText);
        costTextView = (TextView)findViewById(R.id.costText);
        capacityTextView = (TextView)findViewById(R.id.capacityText);
        facilityTextView = (TextView)findViewById(R.id.facilityText);
        descriptionTextView = (TextView)findViewById(R.id.descriptionText);
        trailerImageView = (ImageView)findViewById(R.id.addImageView);

        reserveBtn = (Button)findViewById(R.id.reserveBtn);

        // 트레일러의 정보를 확인하는 함수에 id, position 을 함께 보냄
        getTrailerInfo(id, position);

        reserveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserveTrailer();
            }
        });
    }

    private void reserveTrailer() {
        final HashMap<String, String> body = new HashMap<String, String>();
        body.put("guestId", guestId);
        body.put("hostId", hostId);
        body.put("trailerPhoto", trailerPhoto);
        body.put("trailerId", trailerId);
        body.put("trailerName", trailerName);
        body.put("rentalPlace", rentalPlace);
        body.put("cost", cost);
        String url = "http://101.101.209.224:3001/api/pickt/users/" + guestId + "/reservationLists";

        final String token = sharedPreferenceClass.getValue_string("token");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, new JSONObject(body), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // success -> true
                    if (response.getBoolean("success")) {
                        String msg = "예약 완료";
                        Toast.makeText(TrailerInfoActivity.this, msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(TrailerInfoActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null){
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(TrailerInfoActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", token);
                return headers;
            }
        };
        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);
    }

    // getTrailerInfo
    private void getTrailerInfo(final String id, final int position){
        String url = "http://101.101.209.224:3001/api/pickt/trailers/"+id;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // response 응답이 성공했을 때
                        try {
                            if (response.getBoolean("success")) {
                                JSONObject jsonObject = response.getJSONObject("trailer");
//                                guestId = response.getString("user");
//
//                                //Log.i("check", jsonObject.get("trailerName").toString());
//                                hostId = jsonObject.getString("userId").toString();
//                                trailerPhoto = jsonObject.getString("trailerPhoto").toString();
//                                trailerId = jsonObject.getString("trailerId").toString();
//                                trailerName = jsonObject.getString("trailerName").toString();
//                                rentalPlace = jsonObject.getString("rentalPlace").toString();
//                                cost = jsonObject.getString("cost").toString();


                                registerTextView.setText(jsonObject.get("trailerName").toString());
                                rentalPlaceTextView.setText(jsonObject.get("rentalPlace").toString());
                                licenseTextView.setText(jsonObject.get("license").toString());
                                costTextView.setText(jsonObject.get("cost").toString());
                                capacityTextView.setText(jsonObject.get("capacity").toString());
                                facilityTextView.setText(jsonObject.get("facilities").toString());
                                descriptionTextView.setText(jsonObject.get("description").toString());

                                // trailerTextView.setText(jsonObject.get("trailerPhoto").toString());
                                String trailerTextView = jsonObject.get("trailerPhoto").toString();

                                // byte[] encodeByte = Base64.decode(trailerTextView, Base64.DEFAULT);
                                // Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);

                                byte[] encodeByte = Base64.decode(trailerTextView, Base64.DEFAULT);
                                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
                                trailerImageView.setImageBitmap(bitmap);
                                //BitmapFactory.Options options = new BitmapFactory.Options();
                                //options.inSampleSize = 2;
                                //Bitmap bitmap = BitmapFactory.decodeStream(inStream);
                                //trailerImageView.setImageBitmap(bitmap);

                                /*
                                Glide.with(context).asBitmap().load(bitmap)
                                        .into(new SimpleTarget<Bitmap>() {
                                            @Override
                                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                                ImageView trailerImage = null;
                                                trailerImage.setImageBitmap(resource);
                                            }
                                        });
                                 */
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error == null || error.networkResponse == null) {
                            return;
                        }

                        String body;

                        try {
                            body = new String(error.networkResponse.data, "UTF-8");
                            JSONObject errorObject = new JSONObject(body);

                            // 토큰이 유효하지 않다.
                            if (errorObject.getString("msg").equals("Token not valid")) {
                                sharedPreferenceClass.clear();
                                //startActivity(new Intent(getActivity(), LoginActivity.class));
                                //Toast.makeText(getActivity(), "Session expired", Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(getActivity(), errorObject.getString("msg"), Toast.LENGTH_SHORT).show();
                        } catch (UnsupportedEncodingException | JSONException e) {

                        }
                    }
        }){
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    /*
    //데이터베이스에서 새로운 값을 가져온다.
    private void getInfo(String id, int position){
    }
     */
}