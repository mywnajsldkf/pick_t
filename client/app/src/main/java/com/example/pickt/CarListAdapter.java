package com.example.pickt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CarListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Car> carsList;

    public CarListAdapter(Context context, int layout, ArrayList<Car> carsList){
        this.context = context;
        this.layout = layout;
        this.carsList = carsList;
    }

    @Override
    public int getCount(){
        return carsList.size();
    }

    @Override
    public Object getItem(int position) {
        return carsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName, txtLicense;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View row = view;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtName = (TextView)row.findViewById(R.id.txtName);
            holder.txtLicense = (TextView)row.findViewById(R.id.txtLicense);
            holder.imageView = (ImageView)row.findViewById(R.id.imgCar);
            row.setTag(holder);
        }
        else {
            holder = (ViewHolder)row.getTag();
        }

        Car car = carsList.get(position);

        holder.txtName.setText(car.getName());
        holder.txtLicense.setText(car.getLicense());

        byte[] carImage = car.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(carImage, 0, carImage.length);
        holder.imageView.setImageBitmap(bitmap);

        return row;
    }


}
