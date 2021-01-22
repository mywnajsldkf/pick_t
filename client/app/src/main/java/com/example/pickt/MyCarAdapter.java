package com.example.pickt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCarAdapter extends RecyclerView.Adapter<MyCarAdapter.ViewHolder>{

    MyCarData[] myCarData;
    HomeFragment context;

    public MyCarAdapter(MyCarData[] myCarData, HomeFragment fragment){
        this.myCarData = myCarData;
        this.context = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.car_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

         return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MyCarData myCarDataList = myCarData[position];
        holder.carImage.setImageResource(myCarDataList.getCarImage());
        holder.textViewName.setText(myCarDataList.getCarNameText());
        holder.textViewRent.setText(myCarDataList.getCarRentText());
        holder.textViewCost.setText(myCarDataList.getCarCostText());

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Toast.makeText(context, myCarDataList.getCarNameText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return myCarData.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView carImage;
        TextView textViewName;
        TextView textViewRent;
        TextView textViewCost;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            carImage = itemView.findViewById(R.id.carImage);
            textViewName = itemView.findViewById(R.id.carNameText);
            textViewRent = itemView.findViewById(R.id.carRentText);
            textViewCost = itemView.findViewById(R.id.carCostText);
        }
    }
}
