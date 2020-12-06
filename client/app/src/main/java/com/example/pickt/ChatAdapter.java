package com.example.pickt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {



    ArrayList<ChattingClass> items= new ArrayList<ChattingClass>();


    public ChatAdapter(ArrayList<ChattingClass> chat){
        this.items=chat;
    }
    @NonNull
    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext()) ;
        View itemView=inflater.inflate(R.layout.recyclerview_chatting,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChattingClass chat=items.get(position);
        holder.setItem(chat);

    }
    public void addItem(ChattingClass item){
        items.add(item);
        notifyDataSetChanged();
    }
    public void addItems(ArrayList<ChattingClass> chat){
        this.items=chat;
    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView message;
        ImageView profile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            message=(TextView) itemView.findViewById(R.id.textView);
            profile=(ImageView)itemView.findViewById(R.id.imageView);
        }
        public void setItem(ChattingClass item){
            message.setText(item.getText());
            profile.setImageResource(R.drawable.pooh);
        }
    }
}
