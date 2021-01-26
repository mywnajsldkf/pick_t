package com.example.pickt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCarActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;

    private ImageView imageButton;
    private Button registerButton;

    private EditText nameEditText;
    private EditText licenseEditText;
    private EditText rentalPlaceEditText;
    private EditText capacityEditText;
    private EditText facilitiesEditText;
    private EditText descriptionEditText;

    private RequestQueue requestQueue;

    private String name, license, rentalPlace, capacity, facilities, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

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
                name = nameEditText.getText().toString();
                license = licenseEditText.getText().toString();
                rentalPlace = rentalPlaceEditText.getText().toString();
                capacity = capacityEditText.getText().toString();
                facilities = facilitiesEditText.getText().toString();
                description = descriptionEditText.getText().toString();

                addTrailer(v);
            }
        });
    }

    private void addTrailer(View view){
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("license", license);
        params.put("rentalPlace", rentalPlace);
        params.put("capacity", capacity);
        params.put("facilites", facilities);
        params.put("description", description);

        requestQueue = Volley.newRequestQueue(this);
        String url = "http://101.101.209.224:3001/api/pickt/trailers/registers";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // success -> true
                    if (response.getBoolean("success")) {
                        String msg = "차량 등록이 완료되었습니다.";
                        Toast.makeText(AddCarActivity.this, msg, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddCarActivity.this, MainActivity.class));
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
                        Toast.makeText(AddCarActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                    }catch (JSONException | UnsupportedEncodingException je){
                        je.printStackTrace();
                    }
                }
            }
        });

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