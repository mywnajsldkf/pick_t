package com.example.pickt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordCheckEditText;
    private EditText nickNameEditText;
    private EditText phoneEditText;
    private Button nextButton;

    private String name, email, password, passwordCheck, nickName, phone;

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


        nextButton = (Button) findViewById(R.id.button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameEditText.getText().toString();
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                passwordCheck = passwordCheckEditText.getText().toString();
                nickName = nickNameEditText.getText().toString();
                phone = phoneEditText.getText().toString();

                //gotoLoginActivity();
            }
        });
    }

    private void gotoLoginActivity() {
        finish();
    }
}
