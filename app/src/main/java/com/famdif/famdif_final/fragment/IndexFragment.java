package com.famdif.famdif_final.fragment;

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

import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.MenuType;
import com.famdif.famdif_final.R;
import com.google.android.material.navigation.NavigationView;

import kotlin.reflect.KFunction;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;


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
        View auxView = view;
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

        final FancyShowCaseView tutorialIndex1 = new FancyShowCaseView.Builder(getActivity()).focusAnimationStep(2)
                .title("!Bienvenido a la app de ACCEDEComercios! \n Vamos a empezar un pequeño tutorial, para avanzar solo tienes que tocar la pantalla.")
                .showOnce("tuto1")
                .build();

        final FancyShowCaseView tutorialIndex2  =new FancyShowCaseView.Builder(getActivity()).focusAnimationStep(2)
                .focusOn(messages)
                .title("Este botón te lleva a los mensajes de FAMDIF, aquí iremos subiendo los mensajes exclusivos para los usuarios de esta aplicación.")
                .showOnce("tuto2")
                .build();

        final FancyShowCaseView tutorialIndex3 = new FancyShowCaseView.Builder(getActivity()).focusAnimationStep(3)
                .focusOn(news)
                .title("En este apartado podrás ver las noticias que publicamos, también tienes accesos directos a la web para ver las noticias completas.")
                .showOnce("tuto3")
                .build();

        final FancyShowCaseView tutorialIndex4 = new FancyShowCaseView.Builder(getActivity())
                .focusOn(polls)
                .title("Desde este apartado se pueden copletar las encuestas que hacemos desde FAMDIF a nuestros usuarios, debes estar registrado para completarlas.")
                .showOnce("tuto4")
                .build();

        final FancyShowCaseView tutorialIndex5 = new FancyShowCaseView.Builder(getActivity())
                .focusOn(achievements)
                .title("Este botón te dirige a tu registro de logros, de nuevo debes de estar conectado para poder tener un registro de los mismos. Aunque los consigas todos" +
                        " asegurate de revisarlo de vez en cuando, seguiremos añadiendo más conforme avance la aplicación.")
                .showOnce("tuto5")
                .build();
        final FancyShowCaseView tutorialIndex6 = new FancyShowCaseView.Builder(getActivity())
                .focusOn(signIn)
                .title("Este botón te lleva a la pantalla de registro para que podamos conocerte mejor.")
                .showOnce("tuto6")
                .build();

        final FancyShowCaseView tutorialIndex7 = new FancyShowCaseView.Builder(getActivity())
                .focusOn(logIn)
                .title("Desde este botón podrás entrar a tu cuenta si ya has creado una en nuestra web.")
                .showOnce("tuto7")
                .build();

        final FancyShowCaseView tutorialIndex8 = new FancyShowCaseView.Builder(getActivity())
                .focusOn(search)
                .title("Por último este es el botón de búsqueda de comercios, para continuar el tutorial tócalo.")
                .showOnce("tuto8")
                .build();


        FancyShowCaseQueue cola = new FancyShowCaseQueue()
                .add(tutorialIndex1)
                .add(tutorialIndex2)
                .add(tutorialIndex3)
                .add(tutorialIndex4)
                .add(tutorialIndex5)
                .add(tutorialIndex6)
                .add(tutorialIndex7)
                .add(tutorialIndex8);

        cola.show();

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
                youMustBeLoggedIn(auxView);
            }
        });

        polls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.go(newsScene);
                youMustBeLoggedIn(auxView);
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