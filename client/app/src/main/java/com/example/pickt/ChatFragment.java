package com.example.pickt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ChatRoomClass> chatRooms;
   ChatRoomAdapter adapter;

    public ChatFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataset();
    }
    private void initDataset(){
        chatRooms=new ArrayList<>();
        chatRooms.add(new ChatRoomClass(R.drawable.pooh,"Pooh","주행도 어렵지 않았습니다."));
        chatRooms.add(new ChatRoomClass(R.drawable.genie,"지니","어려움이 있다고요? call me now~"));
    }

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat,container,false);

            final Context context=view.getContext();
            mLayoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView_chatroom);
            recyclerView.setLayoutManager(mLayoutManager);
            adapter=new ChatRoomAdapter(context,chatRooms);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new OnChatRoomClickListener() {
                @Override
                public void onItemClick(ChatRoomAdapter.ViewHolder holder, View view, int position) {
                    ChatRoomClass item=adapter.getItem(position);
                    Intent intent=new Intent(getActivity(),ChattingActivity.class);
                    intent.putExtra("nickname",item.getNickname());
                    startActivity(intent);

                }
            });
       /* recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView_chatroom);

        recyclerView.setHasFixedSize(true);
        ArrayList<ChatRoom> myChatRoomList;
        myChatRoomList=new ArrayList<>();
        adapter=new ChatRoomAdapter(getActivity(),myChatRoomList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myChatRoomList.add(new ChatRoom("pooh","주행도 어렵지 않았습니다."));
        recyclerView.setAdapter(adapter);
        return rootView;*/
       return view;
    }

}