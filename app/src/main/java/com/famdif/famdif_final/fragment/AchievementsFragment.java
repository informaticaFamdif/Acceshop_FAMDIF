package com.famdif.famdif_final.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.famdif.famdif_final.Achievement;
import com.famdif.famdif_final.Controlador;
import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.Noticia;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.Poll;
import com.famdif.famdif_final.R;
import com.famdif.famdif_final.adaptador.AdaptadorAchievement;
import com.famdif.famdif_final.adaptador.AdaptadorPoll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AchievementsFragment extends BaseFragment {
    private StorageReference mStorageReference;
    private ArrayList<Achievement> model = new ArrayList<>();
    private AdaptadorAchievement adaptador;
    private ListView lista;
    private ProgressBar spinner;
    private FrameLayout lista_wrapper;

    public AchievementsFragment() {
        // Required empty public constructor
    }

    private void recuperarAchievements() {
        model.clear();
        MainActivity.db.collection("logros")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot d : task.getResult()) {
                            Achievement a = d.toObject(Achievement.class);
                            Log.d("achievement", a.getTitulo());
                            model.add(a);
                            adaptador.notifyDataSetChanged();
                        }
                        spinner.setVisibility(View.GONE);
                        lista_wrapper.setVisibility(View.VISIBLE);
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adaptador = new AdaptadorAchievement(getContext(),model);
        FirebaseUser user = MainActivity.mAuth.getCurrentUser();

        View view = inflateFragment(R.layout.achievements_scene, inflater, container);
        spinner = (ProgressBar) view.findViewById(R.id.progressBarAchievements);
        lista_wrapper = (FrameLayout) view.findViewById(R.id.scene_root_index_achievements);

        recuperarAchievements();
        Log.d("achievement", model.toString());

        lista=view.findViewById(R.id.idListAchievements);
        lista.setAdapter(adaptador);

        String child_id = new String();

        MainActivity mactiv = (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageIcon.setImageResource(R.drawable.ic_logros);
        pageTitle.setText("LOGROS");

        View childLayout = new View(getContext());

        NavigationView menu = view.findViewById(R.id.navigation_index_view);
        FrameLayout includeFrame = view.findViewById(R.id.menu_include_complete);

        Log.d("includeFrame", includeFrame.toString());
        Log.d("childLayout", childLayout.toString());

        if (Controlador.getInstance().getEmailUsuarioActual() == "") {

            child_id = "index_nav_menu";
            childLayout = inflater.inflate(R.layout.index_page_buttons, (ViewGroup) view.findViewById(R.id.index_nav_menu));
            Log.d("user", "disconnected + ratio +  learn to code + cope");

            childLayout.setId(R.id.botoneraIndex);

            View botonera = childLayout;

            includeFrame.addView(childLayout);

            AppCompatImageButton search = botonera.findViewById(R.id.indexSearchButton);
            AppCompatImageButton menuButton = botonera.findViewById(R.id.indexMenuButton);
            AppCompatImageButton signIn = botonera.findViewById(R.id.indexSignInButton);
            AppCompatImageButton logIn = botonera.findViewById(R.id.indexLogInButton);
            AppCompatImageButton aboutUs = botonera.findViewById(R.id.indexAboutUsButton);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.setFragment(FragmentName.SEARCH);
                }
            });

            signIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.setFragment(FragmentName.SIGN_IN);
                }
            });

            logIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.setFragment(FragmentName.LOG_IN);
                }
            });

            aboutUs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.setFragment(FragmentName.ABOUT_US);
                }
            });

            menuButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {
                    Log.d("mactiv", mactiv.toString());
                    mactiv.showPopup(view, R.menu.index_menu_popup);
                }
            });


        } else {

            child_id = "home_nav_menu";
            childLayout = inflater.inflate(R.layout.home_page_buttons, (ViewGroup) view.findViewById(R.id.home_nav_menu));
            Log.d("user", "connected + giga chad +  learnt to code + based");

            View botonera = childLayout;

            AppCompatImageButton search = botonera.findViewById(R.id.homeSearchButton);
            AppCompatImageButton menuButton = botonera.findViewById(R.id.homeMenuButton);
            AppCompatImageButton rate = botonera.findViewById(R.id.homeRateShopsButton);
            AppCompatImageButton logout = botonera.findViewById(R.id.homeLogOutButton);
            AppCompatImageButton suggest = botonera.findViewById(R.id.homeSuggestionsButton);

            includeFrame.addView(childLayout);

            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mactiv.setFragment(FragmentName.SEARCH);
                }
            });

            suggest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.setFragment(FragmentName.SUGGESTIONS);
                }
            });

            menuButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                @Override
                public void onClick(View view) {

                    mactiv.showPopup(view, R.menu.home_menu_popup);
                }
            });
            rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.setFragment(FragmentName.RATE_SHOP);
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mactiv.logOut();
                }
            });

        }

        FrameLayout includeExteriorFrame = view.findViewById(R.id.scene_root_index_achievements);

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
        float ratio = ((float)metrics.heightPixels / (float)metrics.widthPixels);

        if(ratio > 2){

            ConstraintSet constraintSet = new ConstraintSet();

            Log.d("constraints",String.valueOf(constraintSet.getHeight(includeExteriorFrame.getId())));

            includeExteriorFrame.setLayoutParams(new ConstraintLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, getResources().getDimensionPixelSize(R.dimen._420sdp)));

            View includeExteriorFrameParent = (View) includeExteriorFrame.getParent();

            ConstraintLayout constraintLayout = (ConstraintLayout) includeExteriorFrame.getParent();
            constraintSet.clone(constraintLayout);
            constraintSet.connect(includeExteriorFrame.getId(),ConstraintSet.BOTTOM,includeFrame.getId(),ConstraintSet.TOP);
            constraintSet.connect(includeExteriorFrame.getId(),ConstraintSet.TOP,includeExteriorFrameParent.getId(),ConstraintSet.TOP);

            constraintSet.applyTo(constraintLayout);

        }

        return view;
    }
}