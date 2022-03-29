package com.example.famdif_final.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;

import com.example.famdif_final.FragmentName;
import com.example.famdif_final.MainActivity;
import com.example.famdif_final.R;
import com.google.android.material.button.MaterialButton;

public class HomeFragment extends BaseFragment {
    Boolean pulsado=false;
    private ProgressBar spinner;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);
        pageIcon.setVisibility(view.GONE);
        pageTitle.setText("");

        AppCompatImageButton news = view.findViewById(R.id.newsMainHomeBtn);
        AppCompatImageButton polls = view.findViewById(R.id.homePollsBtn);
        AppCompatImageButton messages = view.findViewById(R.id.homeMessagesButton);
        AppCompatImageButton achievements = view.findViewById(R.id.homeAchievementsBtn);

        View botonera = view.findViewById(R.id.botoneraHome);

        AppCompatImageButton search = view.findViewById(R.id.homeSearchButton);
//        AppCompatImageButton mySuggest = botonera.findViewById(R.id.homeMySuggestionsButton);
        AppCompatImageButton suggest = botonera.findViewById(R.id.homeSuggestionsButton);
        AppCompatImageButton menuButton = botonera.findViewById(R.id.homeMenuButton);
        AppCompatImageButton rate = botonera.findViewById(R.id.homeRateShopsButton);
        AppCompatImageButton logout = botonera.findViewById(R.id.homeLogOutButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.SEARCH);
            }
        });

        menuButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {

                getMainActivity().showPopup(view, R.menu.home_menu_popup);
            }
        });

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.go(newsScene);
                getMainActivity().setFragment(FragmentName.NEWS_FRAGMENT);
            }
        });
        achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.go(newsScene);
                getMainActivity().setFragment(FragmentName.ACHIEVEMENTS_FRAGMENT);
            }
        });

        polls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.go(newsScene);
                getMainActivity().setFragment(FragmentName.POLLS_FRAGMENT);
            }
        });

        messages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.go(newsScene);
                getMainActivity().setFragment(FragmentName.MESSAGES_FRAGMENT);
            }
        });
/*
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.SUGGESTIONS);
            }
        });

        mySuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.MY_SUGGESTIONS);
            }
        });
*/
        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.RATE_SHOP);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().logOut();
            }
        });

        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.SUGGESTIONS);
            }
        });


        return view;
    }

}