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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCarActivity extends AppCompatActivity {
    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageButton;
    private Button registerButton;

    private EditText editTextName;
    private EditText editTextLicense;
    private EditText editTextRent;
    private EditText editTextCost;
    private EditText editTextNum;
    private EditText editTextFacility;

    String name, license, rent, cost, num, facility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        imageButton = (ImageView) findViewById(R.id.addImageView);
        registerButton = (Button)findViewById(R.id.registerCarBtn);

        editTextName = (EditText)findViewById(R.id.editName);
        editTextLicense = (EditText)findViewById(R.id.editLicense);
        editTextRent = (EditText)findViewById(R.id.editRent);
        editTextCost = (EditText)findViewById(R.id.editCost);
        editTextNum = (EditText)findViewById(R.id.editNum);
        editTextFacility = (EditText)findViewById(R.id.editFacility);

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

        // 차량 등록 버튼 클릭
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editTextName.getText().toString();
                license = editTextLicense.getText().toString();
                rent = editTextRent.getText().toString();
                cost = editTextCost.getText().toString();
                num = editTextNum.getText().toString();
                facility = editTextFacility.getText().toString();

                requestRegister(name, license, rent, cost, num, facility);
            }
        });
    }

    // 요청
    public void requestRegister(String name, String license, String rent, String cost, String num, String facility){
        // 우선 localhost 설정
        String url = "http://localhost:3001/";

        JSONObject carjson = new JSONObject();
        try {
            carjson.put("name", name);
            carjson.put("license", license);
            carjson.put("rent", rent);
            carjson.put("cost", cost);
            carjson.put("num", num);
            carjson.put("facility", facility);
            String resultJson = carjson.toString();

            final RequestQueue requestQueue = Volley.newRequestQueue(AddCarActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, carjson, new Response.Listener<JSONObject>() {

                // 데이터 전달 후 응답을 받을
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.toString());


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

        }catch (Exception e){
            e.printStackTrace();
        }
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