package com.example.pickt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.Volley;
import com.example.pickt.LoginActivity;
import com.example.pickt.R;
import com.example.pickt.UtilsService.SharedPreferenceClass;
import com.example.pickt.UtilsService.UtilService;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class SignUpActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordCheckEditText;
    private EditText nickNameEditText;
    private EditText phoneEditText;
    private Button nextButton;
    ProgressBar progressBar;

    private String name, email, password, passwordCheck, nickName, phone;
    UtilService utilService;
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameEditText = (EditText) findViewById(R.id.editText);
        emailEditText = (EditText) findViewById(R.id.editText2);
        passwordEditText = (EditText) findViewById(R.id.editText3);
        passwordCheckEditText = (EditText) findViewById(R.id.editText4);
        nickNameEditText = (EditText) findViewById(R.id.editText5);
        phoneEditText = (EditText) findViewById(R.id.editText6);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        utilService = new UtilService();
        sharedPreferenceClass = new SharedPreferenceClass(this);


        nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                utilService.hideKeyboard(v, SignUpActivity.this);

                name = nameEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                passwordCheck = passwordCheckEditText.getText().toString();
                nickName = nickNameEditText.getText().toString();
                phone = phoneEditText.getText().toString();

                if(validate(v)) {
                    registerUser(v);
                }

                ///gotoLoginActivity();
            }
        });
    }

    private void registerUser(View view) {
        progressBar.setVisibility(View.VISIBLE);

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", name);
        params.put("email", email);
        params.put("password", password);
        params.put("nickname", nickName);
        params.put("phone", phone);

        String apiKey = "http://10.0.2.2:3001/api/todo/auth/register";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                apiKey, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if(response.getBoolean("success")) {
                        String token = response.getString("token");
                        sharedPreferenceClass.setValue_string("token", token);
                        String msg = "회원가입 성공";
                        Toast.makeText(SignUpActivity.this, msg, Toast.LENGTH_LONG).show();
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    }
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                NetworkResponse response = error.networkResponse;
                if(error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));

                        JSONObject obj = new JSONObject(res);
                        Toast.makeText(SignUpActivity.this, obj.getString("msg"), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    } catch(JSONException | UnsupportedEncodingException je) {
                        je.printStackTrace();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return params;
            }
        };

        // set retry policy
        int socketTime = 3001;
        RetryPolicy policy = new DefaultRetryPolicy(socketTime,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);

        // request add
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    public boolean validate(View view) {
        boolean isValid;

        if(!TextUtils.isEmpty(name)) {
            if(!TextUtils.isEmpty(email)) {
                if(!TextUtils.isEmpty(password)) {
                    if(!TextUtils.isEmpty(passwordCheck)) {
                        if(!TextUtils.isEmpty(nickName)) {
                            if(!TextUtils.isEmpty(phone)) {
                                isValid = true;
                            } else {
                                utilService.showSnackBar(view, "휴대폰 번호를 입력해주세요.");
                                isValid = false;
                            }
                        } else {
                            utilService.showSnackBar(view, "닉네임을 입력해주세요.");
                            isValid = false;
                        }
                    } else {
                        utilService.showSnackBar(view, "비밀번호 확인을 입력해주세요.");
                        isValid = false;
                    }
                } else {
                    utilService.showSnackBar(view, "비밀번호를 입력해주세요.");
                    isValid = false;
                }
            } else {
                utilService.showSnackBar(view, "이메일을 입력해주세요.");
                isValid = false;
            }
        } else {
            utilService.showSnackBar(view, "이름을 입력해주세요.");
            isValid = false;
        }

        return isValid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pickt_pref = getSharedPreferences("user_pickt", MODE_PRIVATE);
        if(pickt_pref.contains("token")) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void gotoLoginActivity() {
        finish();
    }
}

