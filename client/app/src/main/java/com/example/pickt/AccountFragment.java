package com.example.pickt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pickt.UtilsService.SharedPreferenceClass;

import org.w3c.dom.Text;

public class AccountFragment extends Fragment {
    MainActivity mainActivity;
    private TextView registerBtn;
    private TextView logoutBtn;
    private TextView favoriteBtn;

    SharedPreferenceClass sharedPreferenceClass;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainActivity = null;
    }

    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        registerBtn = (TextView) view.findViewById(R.id.registerCar);
        favoriteBtn = (TextView) view.findViewById(R.id.favoriteList);
        logoutBtn = (TextView) view.findViewById(R.id.logoutBtn);
        sharedPreferenceClass = new SharedPreferenceClass(mainActivity);

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentLoadActivity = new Intent(getActivity(), AddTrailerActivity.class);
                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLoadActivity);
            }
        });

        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLoadActivity = new Intent(getActivity(),FavoriteActivity.class);
                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLoadActivity);
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceClass.clear();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        return view;
    }

}