package com.famdif.famdif_final.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.R;

public class FirstTimeFragment extends BaseFragment {

    private View v;

    public FirstTimeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setMainActivity((MainActivity) getActivity());
        final View view = inflater.inflate(R.layout.fragment_first_time, container, false);

        Button btnRegistroWelcome = view.findViewById(R.id.botonRegistroWellcome);
        Button btnLoginWelcome = view.findViewById(R.id.botonLoginWellcome);

        btnRegistroWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().setFragment(FragmentName.SIGN_IN);
            }
        });

        btnLoginWelcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().setFragment(FragmentName.LOG_IN);
            }
        });

        return view;
    }
}