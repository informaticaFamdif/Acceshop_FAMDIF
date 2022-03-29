package com.example.famdif_final.fragment;

import android.animation.LayoutTransition;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.wear.widget.ConfirmationOverlay;

import com.example.famdif_final.FragmentName;
import com.example.famdif_final.MainActivity;
import com.example.famdif_final.MenuType;
import com.example.famdif_final.R;
import com.google.android.material.navigation.NavigationView;


public class IndexFragment extends BaseFragment {

    private Scene mainScene;
    private Scene newsScene;

    private TransitionManager transitionManagerForIndexScene;

    private ViewGroup sceneRoot;



    public IndexFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setMainActivity((MainActivity) getActivity());

        View view = inflateFragment(R.layout.fragment_index, inflater, container);

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageTitle.setText("");

        assert view != null;

        sceneRoot = (ViewGroup) view.findViewById(R.id.scene_root_index);
        mainScene = Scene.getSceneForLayout(sceneRoot, R.layout.main_scene, getActivity());
        newsScene = Scene.getSceneForLayout(sceneRoot, R.layout.news_scene, getActivity());
        transitionManagerForIndexScene = TransitionInflater.from(getActivity()).inflateTransitionManager(R.transition.transition_manager_for_index_scene, sceneRoot);


        AppCompatImageButton news = view.findViewById(R.id.newsMainIndexBtn);
        AppCompatImageButton polls = view.findViewById(R.id.pollsIndexBtn);
        AppCompatImageButton messages = view.findViewById(R.id.messagesIndexBtn);
        AppCompatImageButton achievements = view.findViewById(R.id.achievementsIndexBtn);

        NavigationView menu = view.findViewById(R.id.navigation_index_view);

        View botonera = view.findViewById(R.id.botoneraIndex);

        AppCompatImageButton search = botonera.findViewById(R.id.indexSearchButton);
        AppCompatImageButton menuButton = botonera.findViewById(R.id.indexMenuButton);
        AppCompatImageButton signIn = botonera.findViewById(R.id.indexSignInButton);
        AppCompatImageButton logIn = botonera.findViewById(R.id.indexLogInButton);
        AppCompatImageButton aboutUs = botonera.findViewById(R.id.indexAboutUsButton);

        Log.d("botonera", botonera.findViewById(R.id.indexSearchButton).toString());

        menuButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View view) {
                getMainActivity().showPopup(view, R.menu.index_menu_popup);
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

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.SEARCH);
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.SIGN_IN);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.LOG_IN);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMainActivity().setFragment(FragmentName.ABOUT_US);
            }
        });

        //getMainActivity().getSupportActionBar().setTitle("ACCESSHOP");
        getMainActivity().changeMenu(MenuType.DISCONNECTED);
        getMainActivity().setOptionMenu(R.id.item_index);



        return view;
    }

        /*
        AppCompatImageButton back = view.findViewById(R.id.newsBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.go(mainScene);
                Log.d("peep it", "piper");
            }
        });
        */

}