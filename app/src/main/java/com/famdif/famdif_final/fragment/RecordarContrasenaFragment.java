package com.famdif.famdif_final.fragment;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.R;
import com.famdif.famdif_final.SendMail;
import com.famdif.famdif_final.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RecordarContrasenaFragment extends BaseFragment {

    private View v;

    public RecordarContrasenaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setMainActivity((MainActivity) getActivity());
        final View view = inflater.inflate(R.layout.fragment_recordar_contrasena, container, false);

        TextInputEditText email = view.findViewById(R.id.recordarTextEmailText);
        Button btnRecordarContraseña = view.findViewById(R.id.btnRecordarContraseña);

        btnRecordarContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMainActivity().setFragment(FragmentName.LOG_IN);

                getPass(email.getText().toString());
                Toast.makeText(getContext(), "Podrá encontrar su contraseña en su correo electrónico", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void sendEmail(String email, String pass) {

        String subject = "SU CONTRASEÑA DE ACCEDE COMERCIOS";
        String message = "Su contraseña de accede comercios es:\n\n" + pass + "\n\n" + "Si usted no ha solicitado esta contraseña por favor, cambie su contraseña en la página web.";
        SendMail sm = new SendMail(this.getContext(), email, subject, message);
        sm.execute();

        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(getContext(), "Ha sucedido un error", Toast.LENGTH_SHORT);

    }

    private void getPass(String email) {
        MainActivity.db.collection("users")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Usuario u = document.toObject(Usuario.class);
                                sendEmail(email, u.getPass());
                            } else {
                                Log.d("pass", "no such user");
                            }

                        } else {

                            Log.d("no tenemos pass", ":-(");
                        }
                    }
                });
    }


}