package com.myapp.mekvahan.SignupAndLogin.Login;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.myapp.mekvahan.HomePage.AppHomePage;
import com.myapp.mekvahan.R;

import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.sendEmailIntent;
import static com.myapp.mekvahan.CommonFiles.CommonVariablesFunctions.showGoogleForms;

public class FragmentLogin extends Fragment {

    private final String TAG = "FragmentLogin";
    private LoginHomePage activity;

    public FragmentLogin() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (LoginHomePage) getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.e(TAG,"onCreateView");
        View view =  inflater.inflate(R.layout.fragment_login, container, false);

        Button btnLogin          = view.findViewById(R.id.login_btn);
        TextView tv_faq          = view.findViewById(R.id.faq);
        TextView tv_sendFeedback = view.findViewById(R.id.send_feedback);
        TextView careers = view.findViewById(R.id.careers);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), AppHomePage.class));

            }
        });
        tv_sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailIntent(activity);

            }
        });

        careers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGoogleForms(activity);
            }
        });

        return view;
    }



}
