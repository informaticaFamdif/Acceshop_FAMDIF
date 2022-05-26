package com.example.famdif_final.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.transition.Scene;
import android.transition.TransitionManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.famdif_final.Controlador;
import com.example.famdif_final.FragmentName;
import com.example.famdif_final.Noticia;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.famdif_final.MainActivity;
import com.example.famdif_final.MenuType;
import com.example.famdif_final.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class NewsFragment extends BaseFragment {

    private Scene mainScene;
    private Scene newsScene;

    private TransitionManager transitionManagerForIndexScene;

    private ViewGroup sceneRoot;
    private Semaphore semaforoDescarga = new Semaphore(0, false);

    private String xmlTitulo;
    private ProgressBar spinner;
    private FrameLayout lista_wrapper;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float ratio = ((float)metrics.heightPixels / (float)metrics.widthPixels);

        FirebaseUser user = MainActivity.mAuth.getCurrentUser();

        View view = new View(getContext());
        spinner = (ProgressBar) view.findViewById(R.id.progressBarPoll);
        lista_wrapper = (FrameLayout) view.findViewById(R.id.scene_root_index);
        String child_id = new String();

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.VISIBLE);
        pageIcon.setImageResource(R.drawable.ic_noticias);
        pageTitle.setText("NOTICIAS");

        View childLayout = new View(getContext());

        if(Controlador.getInstance().getEmailUsuarioActual() == ""){

            view = inflateFragment(R.layout.news_scene, inflater, container);
            child_id = "index_nav_menu";
            childLayout = inflater.inflate(R.layout.index_page_buttons, (ViewGroup) view.findViewById(R.id.index_nav_menu));
            Log.d("user", "disconnected + ratio +  learn to code + cope");

            childLayout.setId(R.id.botoneraIndex);

            View botonera = childLayout;

            AppCompatImageButton search = botonera.findViewById(R.id.indexSearchButton);
            AppCompatImageButton menuButton = botonera.findViewById(R.id.indexMenuButton);
            AppCompatImageButton signIn = botonera.findViewById(R.id.indexSignInButton);
            AppCompatImageButton logIn = botonera.findViewById(R.id.indexLogInButton);
            AppCompatImageButton aboutUs = botonera.findViewById(R.id.indexAboutUsButton);

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

            menuButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    getMainActivity().showPopup(view, R.menu.index_menu_popup);
                }
            });


        }else{

            view = inflateFragment(R.layout.news_scene, inflater, container);
            child_id = "home_nav_menu";
            childLayout = inflater.inflate(R.layout.home_page_buttons, (ViewGroup) view.findViewById(R.id.home_nav_menu));
            Log.d("user", "connected + giga chad +  learnt to code + based");

            View botonera = childLayout;

            AppCompatImageButton search = botonera.findViewById(R.id.homeSearchButton);
            AppCompatImageButton menuButton = botonera.findViewById(R.id.homeMenuButton);
            AppCompatImageButton rate = botonera.findViewById(R.id.homeRateShopsButton);
            AppCompatImageButton logout = botonera.findViewById(R.id.homeLogOutButton);
            AppCompatImageButton suggest = botonera.findViewById(R.id.homeSuggestionsButton);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    getMainActivity().setFragment(FragmentName.SEARCH);
                }
            });

            suggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getMainActivity().setFragment(FragmentName.SUGGESTIONS);
                }
            });

            menuButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {

                    getMainActivity().showPopup(view, R.menu.home_menu_popup);
                }
            });
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

        }

        NavigationView menu = view.findViewById(R.id.navigation_index_view);
        LinearLayout layoutNoticias = view.findViewById(R.id.newsLayout);
        ArrayList<Noticia> noticiasAcordeon = new ArrayList<>();
        FrameLayout includeFrame = view.findViewById(R.id.menu_include_complete);
        FrameLayout includeExteriorFrame = view.findViewById(R.id.scene_root_index);




        Log.d("includeFrame", includeFrame.toString());
        Log.d("childLayout", childLayout.toString());

        includeFrame.addView(childLayout);

        getNews(noticiasAcordeon, layoutNoticias);


        if(ratio > 2){

            ConstraintSet constraintSet = new ConstraintSet();

            Log.d("constraints",String.valueOf(constraintSet.getHeight(includeExteriorFrame.getId())));

            includeExteriorFrame.setLayoutParams(new ConstraintLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen._420sdp)));

            View includeExteriorFrameParent = (View) includeExteriorFrame.getParent();

            ConstraintLayout constraintLayout = (ConstraintLayout) includeExteriorFrame.getParent();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(includeExteriorFrame.getId(),ConstraintSet.BOTTOM,includeFrame.getId(),ConstraintSet.TOP);
            constraintSet.connect(includeExteriorFrame.getId(),ConstraintSet.TOP,includeExteriorFrameParent.getId(),ConstraintSet.TOP);
            constraintSet.setVerticalBias(includeExteriorFrame.getId(), (float) 0.73);

            constraintSet.applyTo(constraintLayout);

        }


//        getMainActivity().changeMenu(MenuType.DISCONNECTED);
//        getMainActivity().setOptionMenu(R.id.item_index);

        return view;
    }

    public void getNews(ArrayList<Noticia> noticiasAcordeon, LinearLayout layoutNoticias){
        Thread downloadThread = new Thread() {
            public void run() {
                Document doc = new Document("");

                try {
                    doc = Jsoup.connect("https://famdif.org/?page_id=7#noticias").get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Elements postTitleElements = doc.getElementsByClass("entry-title");
                Elements postExcerptElements = doc.getElementsByClass("entry-excerpt");

                Elements hrefElements = postTitleElements.select("a[href]");
                Elements sinopsisElements = postExcerptElements.select("p");
                ArrayList<String> links = new ArrayList<>();
                ArrayList<String> titles = new ArrayList<>();
                ArrayList<String> synopses = new ArrayList<>();
                for(Element aTag : hrefElements) {
                    String link = aTag.attr("href");
                    links.add(link);
                }
                for(Element pTag : hrefElements) {
                    String title = pTag.text();
                    titles.add(title);
                }
                for(Element pTag : sinopsisElements) {
                    String sinopsis = pTag.text();
                    synopses.add(sinopsis);
                }
                String linkstrings = links.toString();
                String titlestrings = titles.toString();
                String excerptstring = synopses.toString();

                for(int i = 0; i < hrefElements.size(); i++){
                    String newsSinopsis = synopses.get(i).toString();
                    String newsTitle = titles.get(i).toString();
                    String newsLink = links.get(i).toString();

                    Noticia auxNews = new Noticia( newsLink, newsTitle, newsSinopsis);
                    noticiasAcordeon.add(auxNews);
                }
                final TextView[] myTextViews = new TextView[noticiasAcordeon.size()]; // create an empty array;


                Log.d("noticias", noticiasAcordeon.toString());

                semaforoDescarga.release(1);

            }
        };
        downloadThread.start();
        try {
            semaforoDescarga.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setMainActivity((MainActivity) getActivity());

        xmlTitulo = "";

        for (int i = 0; i < noticiasAcordeon.size(); i++) {

            // create a new textview

            final TextView titleTextView = new TextView(getContext());
            final Space spacer = new Space(getContext());
            final TextView sinopsisTextView = new TextView(getContext());
            final TextView linkTextView = new TextView(getContext());

            final String sinopsis = noticiasAcordeon.get(i).getSinopsis() + "\n";
            final String title = noticiasAcordeon.get(i).getTitulo() + "\n";
            final String link = "<a href='" + noticiasAcordeon.get(i).getLink() + "'>Ir a la noticia</a>\n";

            // set some properties of rowTextView or something
            titleTextView.setText(noticiasAcordeon.get(i).getTitulo());
            sinopsisTextView.setText(sinopsis);
            linkTextView.setText(noticiasAcordeon.get(i).getLink());

            // add the textview to the linearlayout

            layoutNoticias.addView(titleTextView);
            layoutNoticias.addView(spacer);
            layoutNoticias.addView(sinopsisTextView);

            String hyperTitle = "<br><a href='" + noticiasAcordeon.get(i).getLink() + "'>" + noticiasAcordeon.get(i).getTitulo() + "</a><br>";

            titleTextView.setClickable(true);
            titleTextView.setMovementMethod(LinkMovementMethod.getInstance());
            titleTextView.setText(Html.fromHtml(hyperTitle, Html.FROM_HTML_MODE_COMPACT));

            titleTextView.setTextSize(24);
            sinopsisTextView.setTextSize(18);
            linkTextView.setTextSize(18);

        }
    }

}