package com.example.pickt;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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

import com.example.pickt.UtilsService.ApiProvider;

import com.example.pickt.UtilsService.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import android.util.Base64;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddTrailerActivity extends AppCompatActivity {

    ApiProvider apiProvider;

    private final int GET_GALLERY_IMAGE = 200;

    // 토큰이 key, value 로 저장되어 있는 shardPreference
    SharedPreferenceClass sharedPreferenceClass;
    String token;

    private ImageView imageButton;
    private Button registerButton;

    private EditText nameEditText;
    private EditText licenseEditText;
    private EditText rentalPlaceEditText;
    private EditText costEditText;
    private EditText capacityEditText;
    private EditText facilitiesEditText;
    private EditText descriptionEditText;

    private RequestQueue requestQueue;

    private String trailerName, license, rentalPlace, capacity, facilities, description,cost, imagePath;

    //public String finalencodedImage;
    public String revertedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trailer);


        sharedPreferenceClass = new SharedPreferenceClass(this);
        token = sharedPreferenceClass.getValue_string("token");

        nameEditText = (EditText) findViewById(R.id.editName);
        licenseEditText = (EditText) findViewById(R.id.editLicense);
        rentalPlaceEditText = (EditText) findViewById(R.id.editRent);
        costEditText = (EditText)findViewById(R.id.editCost);
        capacityEditText = (EditText) findViewById(R.id.editNum);
        facilitiesEditText = (EditText) findViewById(R.id.editFacility);
        descriptionEditText = (EditText) findViewById(R.id.carDescription);
        imageButton = (ImageView) findViewById(R.id.addImageView);
        registerButton = (Button) findViewById(R.id.registerCarBtn);

        imagePath = revertedImage;

        // apiProvider.addImage(ima);
        System.out.println("과연 결과는 둥두구"+revertedImage);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                trailerName = nameEditText.getText().toString();
                license = licenseEditText.getText().toString();
                rentalPlace = rentalPlaceEditText.getText().toString();
                cost = costEditText.getText().toString();
                capacity = capacityEditText.getText().toString();
                facilities = facilitiesEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                imagePath = getRevertedImage();
                System.out.println("과연 결과는 둥두구"+revertedImage);
                addTrailer(v);
            }
        });

        // 갤러리 선택 시 사진 추가
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

    // Add Trailer Method
    private void addTrailer(View view){
        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("trailerName", trailerName);
        params.put("license", license);
        params.put("rentalPlace", rentalPlace);
        params.put("capacity", capacity);
        params.put("cost", cost);
        params.put("facilities", facilities);
        params.put("description", description);
        params.put("trailerPhoto", imagePath);

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
                        // MainActivity로 넘어가는 것, 기존에 있던 스택 삭제
                        startActivity(new Intent(AddTrailerActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        byte[] image = null;
        byte[] encodedImage = null;
        //String final_result = null;
        // requestCode와 GET_GALLERY_IMAGE가 같을 때
        if (requestCode == GET_GALLERY_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    // 이미지를 bit로 바꾸어주고
                    //InputStream is = getContentResolver().openInputStream(data.getData());
                    // get byte
                    //image = getBytes(is);           // image result -> [B@4dfa00b
                    //System.out.println("image 결과 " + image);

                    // encode
                    //encodedImage = encoder.encode(image);
                    //finalencodedImage = encodedImage.toString();
                    //System.out.println("encodedImage 결과"+encodedImage);
                    //System.out.println("String 결과"+finalencodedImage.getClass().getName());




                    InputStream in = getContentResolver().openInputStream(data.getData());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Bitmap img = BitmapFactory.decodeStream(in);

                    img.compress(Bitmap.CompressFormat.PNG, 70, baos);
                    byte[] bytes = baos.toByteArray();
                    String revertedImage = Base64.encodeToString(bytes, Base64.DEFAULT);

                    setRevertedImage(revertedImage);
                    System.out.println("System 결과" + revertedImage);
                    in.close();
                    imageButton.setImageBitmap(img);
                    // 화면에 보여주고



                    // apiProvider.addImage(revertedImage);
                    /// in.close();/
                    // imageButton.setImageBitmap(img);/과
                }catch (IOException ie){
                    ie.printStackTrace();
                }
            }
        }else if(resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "사진선택 취소", Toast.LENGTH_LONG).show();
        }
    }

    public void setRevertedImage(String revertedImage) {
         this.revertedImage = revertedImage;
    }

    public String getRevertedImage() {
        return revertedImage;
    }

    /*
    public String deliverImage(){

        return revertedImage;
    }
     */

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len=0;
        while ((len = is.read(buffer)) != -1)
            byteBuffer.write(buffer, 0 ,len);
        return byteBuffer.toByteArray();
    }


    /*
    // Uri to byte
    public byte[] getImageContent(Uri uri){
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ((len=inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
            return outputStream.toByteArray();
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

     */
}