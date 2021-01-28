package com.example.pickt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pickt.HomeFragment;
import com.example.pickt.R;
import com.example.pickt.interfaces.RecyclerViewClickListener;
import com.example.pickt.model.TrailerModel;

import java.util.ArrayList;

public class TrailerListAdapter extends RecyclerView.Adapter<TrailerListAdapter.TrailerViewHolder>{
    ArrayList<TrailerModel> arrayList;
    Context context;
    final private RecyclerViewClickListener clickListener;

    private long cardPressTime = 0;
    private Toast toast;

    public TrailerListAdapter(Context context, ArrayList<TrailerModel> arrayList, RecyclerViewClickListener clickListener) {
        this.arrayList = arrayList;
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.trailer_item_list, parent, false);

        final TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);

        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerListAdapter.TrailerViewHolder holder, int position){
        final String trailerName = arrayList.get(position).getTrailerName();
        final String license = arrayList.get(position).getLicense();
        final String rentalPlace = arrayList.get(position).getRentalPlace();
        final String capacity = arrayList.get(position).getCapacity();
        final String facilities = arrayList.get(position).getFacilities();
        final String description = arrayList.get(position).getDescription();
        final String cost = arrayList.get(position).getCost();
        final String id = arrayList.get(position).getId();

        // 보여주는 코드 작성
        holder.textViewName.setText(trailerName);
        holder.textViewRent.setText(rentalPlace);
        holder.textViewCost.setText(cost);
    }

    @Override
    public int getItemCount(){
        return arrayList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder{
        CardView trailer_card;
        TextView textViewName, textViewRent, textViewCost;
        LinearLayout trailerBody, trailerContent;

        // ImageView carImage;

        public TrailerViewHolder(@NonNull View itemView){
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.editTrailerName);
            textViewRent = (TextView) itemView.findViewById(R.id.editRentalPlace);
            textViewCost = (TextView)itemView.findViewById(R.id.editTrailerCost);
            trailer_card = (CardView) itemView.findViewById(R.id.trailerCard);
            trailerBody = (LinearLayout) itemView.findViewById(R.id.trailerBody);
            trailerContent = (LinearLayout) itemView.findViewById(R.id.trailerContent);

            // carImage = itemView.findViewById(R.id.carImage);

            // 아이템 클릭 이벤트 핸들러 메서드에서 객체 메서드 호출
            trailer_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });
        }
    }
}
