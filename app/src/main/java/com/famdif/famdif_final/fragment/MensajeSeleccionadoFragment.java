package com.famdif.famdif_final.fragment;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.famdif.famdif_final.Controlador;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.Mensaje;
import com.famdif.famdif_final.R;

public class MensajeSeleccionadoFragment extends BaseFragment {

    public MensajeSeleccionadoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());
        Mensaje mensajeSeleccionado = Controlador.getInstance().getSelectedMessage();
        View view = inflateFragment(R.layout.fragment_mensaje_seleccionado, inflater, container);



        TextView titulo = view.findViewById(R.id.titleView);
        titulo.setText(mensajeSeleccionado.getTitulo());

        TextView cuerpo = view.findViewById(R.id.bodyView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            cuerpo.setText(Html.fromHtml(mensajeSeleccionado.getCuerpo(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            cuerpo.setText(Html.fromHtml(mensajeSeleccionado.getCuerpo()));
        }

        TextView fecha = view.findViewById(R.id.dateView);
        fecha.setText(mensajeSeleccionado.getFecha());

        return view;

    }

}