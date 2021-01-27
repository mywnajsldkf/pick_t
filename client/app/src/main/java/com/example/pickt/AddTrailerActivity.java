package com.example.pickt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.pickt.UtilsService.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class AddTrailerActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;

    // 토큰이 key, value 로 저장되어 있는 shardPreference
    SharedPreferenceClass sharedPreferenceClass;
    String token;

    private ImageView imageButton;
    private Button registerButton;

    private EditText nameEditText;
    private EditText licenseEditText;
    private EditText rentalPlaceEditText;
    private EditText capacityEditText;
    private EditText facilitiesEditText;
    private EditText descriptionEditText;

    private RequestQueue requestQueue;

    private String trailerName, license, rentalPlace, capacity, facilities, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trailer);

        sharedPreferenceClass = new SharedPreferenceClass(this);

        nameEditText = (EditText) findViewById(R.id.editName);
        licenseEditText = (EditText) findViewById(R.id.editLicense);
        rentalPlaceEditText = (EditText) findViewById(R.id.editRent);
        capacityEditText = (EditText) findViewById(R.id.editNum);
        facilitiesEditText = (EditText) findViewById(R.id.editFacility);
        descriptionEditText = (EditText) findViewById(R.id.carDescription);

        imageButton = (ImageView) findViewById(R.id.addImageView);
        registerButton = (Button) findViewById(R.id.registerCarBtn);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trailerName = nameEditText.getText().toString();
                license = licenseEditText.getText().toString();
                rentalPlace = rentalPlaceEditText.getText().toString();
                capacity = capacityEditText.getText().toString();
                facilities = facilitiesEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                addTrailer(v);
            }
        });
    }

    // Add Trailer Method
    private void addTrailer(View view){
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("trailerName", trailerName);
        params.put("license", license);
        params.put("rentalPlace", rentalPlace);
        params.put("capacity", capacity);
        params.put("facilities", facilities);
        params.put("description", description);

        String url = "http://101.101.209.224:3001/api/pickt/trailers";
        final String token = sharedPreferenceClass.getValue_string("token");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // success -> true
                    if (response.getBoolean("success")) {
                        String msg = "차량 등록이 완료되었습니다.";
                        Toast.makeText(AddTrailerActivity.this, msg, Toast.LENGTH_SHORT).show();
                        // MainActivity로 넘어가는 것
                        startActivity(new Intent(AddTrailerActivity.this, MainActivity.class));
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
                        Toast.makeText(AddTrailerActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        // 사진 추가
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    imageButton.setImageBitmap(img);
                } catch (Exception e) {

                }

            }else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}