package com.example.famdif_final.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.famdif_final.MainActivity;
import com.example.famdif_final.R;

public class AboutUsFragment extends Fragment {
    private View v;
    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        View view = new View(getContext());

        pageIcon.setVisibility(view.VISIBLE);
        pageIcon.setImageResource(R.drawable.ic_menu_aboutus);
        pageTitle.setText("NOSOTROS");;

       v = inflater.inflate(R.layout.fragment_aboutus, container, false);
        return v;
    }
}