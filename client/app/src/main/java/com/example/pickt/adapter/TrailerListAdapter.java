package com.example.pickt.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.pickt.HomeFragment;
import com.example.pickt.R;
import com.example.pickt.interfaces.RecyclerViewClickListener;
import com.example.pickt.model.TrailerModel;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
//import java.util.Base64;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

@RequiresApi(api = Build.VERSION_CODES.O)
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
        final String trailerPhoto = arrayList.get(position).getTrailerPhoto();

        // string화 된 이미지를 base64방식으로 인코딩하여 byte 배열을 만듬
        byte[] encodeByte = Base64.decode(trailerPhoto, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
        holder.trailerImage.setImageBitmap(bitmap);
        //holder.trailerImage.setImageBitmap(bitmap);

        //ByteArrayInputStream inStream = new ByteArrayInputStream(encodeByte);

        //holder.trailerImage.setImageBitmap(bitmap);


        /*
        Glide.with(context).asBitmap().load(bitmap)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        ImageView trailerImage = null;
                        trailerImage.setImageBitmap(resource);
                    }
                });
         */


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

        ImageView trailerImage;

        public TrailerViewHolder(@NonNull View itemView){
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.editTrailerName);
            textViewRent = (TextView) itemView.findViewById(R.id.editRentalPlace);
            textViewCost = (TextView)itemView.findViewById(R.id.editTrailerCost);
            trailer_card = (CardView) itemView.findViewById(R.id.trailerCard);
            trailerBody = (LinearLayout) itemView.findViewById(R.id.trailerBody);
            trailerContent = (LinearLayout) itemView.findViewById(R.id.trailerContent);
            trailerImage = (ImageView) itemView.findViewById(R.id.trailerImage);

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
