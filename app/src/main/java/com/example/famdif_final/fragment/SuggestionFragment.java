package com.example.famdif_final.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.example.famdif_final.Controlador;
import com.example.famdif_final.MainActivity;
import com.example.famdif_final.R;

import java.util.HashMap;
import java.util.Map;

public class SuggestionFragment extends BaseFragment {
    private EditText titulo;
    private EditText cuerpo;
    private Button btnEnviar;

    public SuggestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        titulo = view.findViewById(R.id.textoTitulo);
        cuerpo = view.findViewById(R.id.mensajeCuerpo);

        btnEnviar = view.findViewById(R.id.btnEnviarSugerencia);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarSugerencia(v);
            }
        });

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageIcon.setImageResource(R.drawable.ic_noticias);
        pageTitle.setText("ENVIAR SUGERENCIA");

        return view;

    }

    private void enviarSugerencia(View view) {
        Map<String, Object> suggestion = new HashMap<>();

        if(Controlador.getInstance().getEmailUsuarioActual()=="") {
            suggestion.put("email", MainActivity.mAuth.getCurrentUser().getEmail().toString());
            suggestion.put("autor", MainActivity.mAuth.getCurrentUser().getDisplayName());
            suggestion.put("titulo", titulo.getText().toString());
            suggestion.put("cuerpo", cuerpo.getText().toString());
        }else{
            Log.i("primer caso", "DENTRO DEL PRIMER CASO");
            suggestion.put("email", Controlador.getInstance().getUsuario());
            suggestion.put("autor", Controlador.getInstance().getNombreUsuarioActual());
            suggestion.put("titulo", titulo.getText().toString());
            suggestion.put("cuerpo", cuerpo.getText().toString());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getMainActivity());
        builder.setMessage(R.string.dialog_message_confirmar_sugerencia)
                .setTitle(R.string.dialog_title_confirmar_sugerencia);
        builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.db.collection("suggestions")
                        .document(MainActivity.mAuth.getCurrentUser().getEmail()+suggestion.get("titulo").toString())
                        .set(suggestion);
                if (MainActivity.mAuth.getCurrentUser().getEmail() != null) {
                    Log.d("a", "entra");
                    if (!(MainActivity.logrosUsuario.contains("000004"))) {
                        Log.d("b", "entra");
                        MainActivity.db.collection("userLogros")
                                .document(MainActivity.mAuth.getCurrentUser().getEmail())
                                .update("000004", "000004");
                        MainActivity.logrosUsuario.add("000004");
                    }
                }
                }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
        Toast.makeText(getContext(),"Sugerencia enviada correctamente",Toast.LENGTH_SHORT);
        titulo.setText("");
        cuerpo.setText("");
    }
}