package com.example.pickt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class CarList extends AppCompatActivity {

    GridView gridView;
    ArrayList<Car> list;
    CarListAdapter adapter = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        gridView = (GridView) findViewById(R.id.gridView);
        list = new ArrayList<>();
        adapter = new CarListAdapter(this, R.layout.car_item, list);
        gridView.setAdapter(adapter);

        // get all data from sqlite
        Cursor cursor = AddCarActivity.sqLiteHelper.getData("SELECT * FROM CARS");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String license = cursor.getString(2);
            byte[] image = cursor.getBlob(3);

            list.add(new Car(name, license, image, id));
        }
    }
}