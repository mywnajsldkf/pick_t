package com.example.pickt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.ViewHolder> {

    Context context;
    ArrayList<ChatRoomClass> items=new ArrayList<>();
    OnChatRoomClickListener listener;

    public ChatRoomAdapter(Context context,ArrayList<ChatRoomClass> room){
        this.context=context;
        this.items=room;
    }
    public void setOnItemClickListener(OnChatRoomClickListener listener){
        this.listener=listener;
    }
    public void setOnItemClick(ViewHolder holder,View view,int position){
        if(listener!=null){
            listener.onItemClick(holder,view,position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_chatroom,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatRoomClass room=items.get(position);
        holder.setItem(room);

        holder.setOnItemClickListener(listener);
    }
    public ChatRoomClass getItem(int position){
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(ChatRoomClass item){
        items.add(item);
        notifyDataSetChanged();
    }
    public void addItems(ArrayList<ChatRoomClass> chatRooms){this.items=chatRooms;}

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nick;
        TextView message;
        ImageView profile;
        OnChatRoomClickListener listener;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nick=(TextView) itemView.findViewById(R.id.textView_nick);
            message=(TextView)itemView.findViewById(R.id.textView_message);
            profile=(ImageView)itemView.findViewById(R.id.imageView_profile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this,view,position);
                    }
                }
            });
        }
        public void setItem(ChatRoomClass item){
            nick.setText(item.getNickname());
            message.setText(item.getRecent_message());
            profile.setImageResource(item.getProfile());
        }
        public void setOnItemClickListener(OnChatRoomClickListener listener){
            this.listener=listener;
        }
    }
}
