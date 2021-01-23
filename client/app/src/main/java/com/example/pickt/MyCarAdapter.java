package com.example.pickt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyCarAdapter extends RecyclerView.Adapter<MyCarAdapter.ViewHolder>{

    // 어탭너 내에서 리스터 인터페이스 정의
    public interface OnItemClickListener {
        boolean onItemClick(View v, int position);
    }

    // 리스너 객체 전달 메서드와 변수 추가
    private OnItemClickListener mListener = null;

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    MyCarData[] myCarData;

    HomeFragment context;

    private long cardPressTime = 0;
    private Toast toast;

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

        /*
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // Toast.makeText(context, myCarDataList.getCarNameText(), Toast.LENGTH_SHORT).show();
            }
        });
         */
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


         ViewHolder(@NonNull View itemView){
             super(itemView);

             carImage = itemView.findViewById(R.id.carImage);
             textViewName = itemView.findViewById(R.id.carNameText);
             textViewRent = itemView.findViewById(R.id.carRentText);
             textViewCost = itemView.findViewById(R.id.carCostText);

             // 아이템 클릭 이벤트 핸들러 메서드에서 객체 메서드 호출
             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int position = getAdapterPosition();
                     if (position != RecyclerView.NO_POSITION){
                         if (mListener != null){
                             mListener.onItemClick(v, position);
                         }
                     }
                }
            });
        }
    }
}
