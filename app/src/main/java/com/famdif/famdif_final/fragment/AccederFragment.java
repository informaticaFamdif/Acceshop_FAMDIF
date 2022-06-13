package com.famdif.famdif_final.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.famdif.famdif_final.Controlador;
import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.MenuType;
import com.famdif.famdif_final.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class AccederFragment extends BaseFragment {
    private EditText email;
    private TextInputLayout email1;
    private TextInputLayout pass1;
    //private  EditText pass;
    private Button btn;
    private Button register;

    SharedPreferences sharedPreferences;

    public static final String fileName = "login";
    public static final String username = "username";
    public static final String password = "password";


    private static final int RC_SIGN_IN=100;
    private GoogleSignInClient googleSignInClient;

    public  AccederFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("loggedin?", null != MainActivity.mAuth.getCurrentUser() ? MainActivity.mAuth.getCurrentUser().getEmail() : "nadie");

        sharedPreferences = getActivity().getSharedPreferences(fileName, Context.MODE_PRIVATE);
/*
        if(sharedPreferences.contains(username)){

            Intent i = new Intent (getActivity().getApplication(), MainActivity.class);
            startActivity(i);
        }
*/
        setMainActivity((MainActivity) getActivity());

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_acceder, container, false);

        register = view.findViewById(R.id.register);

        //email = view.findViewById(R.id.idEmailUsuario);
        email1 = view.findViewById(R.id.accederTextEmail);
        pass1 = view.findViewById(R.id.accederTextPassw);

        btn = view.findViewById(R.id.accederBtnLogin);

        Button btnRecordarPass = view.findViewById(R.id.btnRecordarPass);

        btnRecordarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().setFragment(FragmentName.REMEMBER_PASSWORD);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logInClick(view);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TransitionManager.go(newsScene);
                getMainActivity().setFragment(FragmentName.SIGN_IN);
            }
        });

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageIcon.setImageResource(R.drawable.ic_noticias);
        pageTitle.setText("ACCESO");;
        getMainActivity().changeMenu(MenuType.DISCONNECTED);
        getMainActivity().setOptionMenu(R.id.item_log_in);

        return view;
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }

    private void logInClick(View v) {
        try {
            MainActivity.mAuth.signInWithEmailAndPassword(email1.getEditText().getText().toString(),pass1.getEditText().getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            hideKeybaord(v);
                            MainActivity.db.collection("users").document(email1.getEditText().getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot ds) {
                                    //SharedPreferences.Editor editor = sharedPreferences.edit();
                                    //editor.putString(username, username);
                                    //editor.putString(password, password);
                                    //editor.commit();
                                    if(ds.get("admin").toString().equals("1")) {
                                        getMainActivity().changeMenu(MenuType.ADMIN_LOGGED);
                                        Controlador.getInstance().setAdmin(1);
                                        Controlador.getInstance().setUsuario(ds.get("email").toString());
                                        Controlador.getInstance().setNombreUsuarioActual(ds.get("nombre").toString());
                                        Log.i("USUARIO", Controlador.getInstance().getNombreUsuarioActual());

                                    }else {
                                        getMainActivity().changeMenu(MenuType.USER_LOGGED);
                                        Controlador.getInstance().setUsuario(ds.get("email").toString());
                                        Controlador.getInstance().setNombreUsuarioActual(ds.get("nombre").toString());
                                        Log.i("USUARIO", Controlador.getInstance().getNombreUsuarioActual());
                                    }
                                }
                            });
                            ArrayList<String> arrayListAux = new ArrayList<String>();
                            MainActivity.db.collection("userLogros").document(email1.getEditText().getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(Task<DocumentSnapshot> ds) {
                                    if (ds.isSuccessful()) {
                                        DocumentSnapshot document = ds.getResult();
                                        if (document.exists()) {
                                            for (Object h : document.getData().values()) {
                                                arrayListAux.add(h.toString());
                                            }
                                            MainActivity.logrosUsuario = arrayListAux;
                                        } else {
                                            Log.d("No such document", "");
                                        }
                                    } else {
                                        Log.d("get failed with ", ds.getException().toString());
                                    }

                                }
                            });
                            MainActivity mactiv= (MainActivity) getActivity();
                            Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

                            TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
                            ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);
                            pageIcon.setVisibility(v.GONE);
                            pageTitle.setText("");
                            getMainActivity().clearBackStack();
                            getMainActivity().setFragment(FragmentName.HOME);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getContext(), "Usuario inexistente o contraseña incorrecta. Por favor, vuelve a intentarlo", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount acount = task.getResult(ApiException.class);
                firebaseAutWithGoogleAccount(acount.getIdToken());
            }catch (Exception e){
                Log.i("Error autenticando con google",e.getMessage());
            }
        }
    }


    private void firebaseAutWithGoogleAccount(String idToken){
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            MainActivity.mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Controlador.getInstance().setCurrentUser(FirebaseAuth.getInstance().getCurrentUser());
                            Controlador.getInstance().setUsuario(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            Controlador.getInstance().setNombreUsuarioActual(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

                            MainActivity.db.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if(documentSnapshot.get("admin").toString().contains("1")){
                                                getMainActivity().changeMenu(MenuType.ADMIN_LOGGED);
                                                Controlador.getInstance().setAdmin(1);
                                            }
                                        }
                                    });

                            getMainActivity().getSupportActionBar().setTitle("HOME");
                            getMainActivity().setFragment(FragmentName.HOME);
                            getMainActivity().changeMenu(MenuType.USER_LOGGED);
                        }
                    });

        }catch (Exception e){
            e.getMessage();
        }


    }



}