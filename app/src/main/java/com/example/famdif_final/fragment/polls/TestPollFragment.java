package com.example.famdif_final.fragment.polls;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.famdif_final.MainActivity;
import com.example.famdif_final.R;
import com.example.famdif_final.fragment.BaseFragment;

public class TestPollFragment extends BaseFragment {

    public TestPollFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());
        View view = inflateFragment(R.layout.fragment_test_poll, inflater, container);

        return view;

    }

}