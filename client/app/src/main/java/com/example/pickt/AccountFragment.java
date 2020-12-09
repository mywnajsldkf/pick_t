package com.example.pickt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pickt.UtilsService.SharedPreferenceClass;

public class AccountFragment extends Fragment {
    MainActivity mainActivity;
    private Button logoutButton;
    private Button register_btn;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);

        register_btn = (Button) view.findViewById(R.id.register_btn);
        logoutButton = (Button) view.findViewById(R.id.logoutButton);
        sharedPreferenceClass = new SharedPreferenceClass(mainActivity);

        register_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intentLoadActivity = new Intent(getActivity(), RegisterActivity.class);
                intentLoadActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentLoadActivity);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
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