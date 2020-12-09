package com.example.pickt;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class AddUserActivity extends AppCompatActivity {

    private Button setNickName;
    private EditText userNickName;
    private EditText newRoomNumber;
    private Boolean hasConnection = false;
    private Socket RoomSocket;
    {
        try {
            RoomSocket = IO.socket("http://10.0.2.2:3001/room");
        } catch (URISyntaxException e) {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_chat);

        userNickName = findViewById(R.id.userNickName);
        setNickName = findViewById(R.id.setNickName);
        newRoomNumber=findViewById(R.id.newRoomNumber);


        if(savedInstanceState != null){
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }

        if(hasConnection){

        }else {
            RoomSocket.connect();

        }
        RoomSocket.on("existedRoom",onNewRoom);


        userNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    setNickName.setEnabled(true);
                    Log.i(ChatActivity.TAG, "onTextChanged: ABLED");
                } else {
                    Log.i(ChatActivity.TAG, "onTextChanged: DISABLED");
                    setNickName.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        setNickName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject newRoom=new JSONObject();
                try {
                    newRoom.put("roomnumber",newRoomNumber.getText().toString());
                    newRoom.put("user",userNickName.getText().toString());
                    RoomSocket.emit("newRoom",newRoom);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    Emitter.Listener onNewRoom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String exist;
                    try {
                        exist=data.getString("exist");
                        if(exist.equals("false")){

                            Intent intent = new Intent(AddUserActivity.this, ChatActivity.class);
                            intent.putExtra("username", userNickName.getText().toString());
                            intent.putExtra("roomNumber",newRoomNumber.getText().toString());
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"이미 존재하는 방입니다.",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }
}
