package com.famdif.famdif_final.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.famdif.famdif_final.adaptador.AdaptadorTienda;
import com.famdif.famdif_final.Controlador;
import com.famdif.famdif_final.FragmentName;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.R;
import com.famdif.famdif_final.Tienda;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Locale;

public class BusquedaTiendaValorarResultFragment extends BaseFragment{
    private View view;
    private ListView lista;
    private AdaptadorTienda adaptador;
    private ArrayList<Tienda> model = new ArrayList<>();
    private String nombreTiendaAux;
    private String direccionAux;


    public BusquedaTiendaValorarResultFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setMainActivity((MainActivity) getActivity());

        adaptador = new AdaptadorTienda(getContext(),model);

        view=inflater.inflate(R.layout.fragment_lista_tiendas_valorar, container, false);

        recuperarTiendas();

        lista=view.findViewById(R.id.idListaTiendaValorar);
        lista.setAdapter(adaptador);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                detallesTienda(i);
            }
        });

        MainActivity mactiv= (MainActivity) getActivity();
        Toolbar toolbar = mactiv.findViewById(R.id.index_toolbar);

        TextView pageTitle = toolbar.findViewById(R.id.toolbar_title);
        ImageView pageIcon = toolbar.findViewById(R.id.toolbar_icon);

        pageIcon.setVisibility(view.GONE);
        pageIcon.setImageResource(R.drawable.ic_noticias);
        pageTitle.setText("VALORAR");


        return view;
    }

    private void recuperarTiendas() {
        model.clear();
        MainActivity.db.collection("ComerciosMurcia")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            if(Controlador.getInstance().getNombreTiendaValorar().length()>0 && Controlador.getInstance().getDireccionTiendaValorar().length()>0){
                                for (QueryDocumentSnapshot d : task.getResult()){
                                    if(d.get("direccion").toString().toUpperCase(Locale.ROOT).contains(Controlador.getInstance().getDireccionTiendaValorar()) && d.get("nombre").toString().contains(Controlador.getInstance().getNombreTiendaValorar().toUpperCase(Locale.ROOT))) {
                                        Tienda t = d.toObject(Tienda.class);
                                        Log.d("1er block", "true");
                                        model.add(t);
                                        adaptador.notifyDataSetChanged();
                                    }
                                }
                            }else if(Controlador.getInstance().getDireccionTiendaValorar().length()>0){
                                for (QueryDocumentSnapshot d : task.getResult()){
                                    if(d.get("direccion").toString().toUpperCase(Locale.ROOT).contains(Controlador.getInstance().getDireccionTiendaValorar().toUpperCase(Locale.ROOT))) {
                                        Tienda t = d.toObject(Tienda.class);
                                        Log.d("2º block", "true");
                                        model.add(t);
                                        adaptador.notifyDataSetChanged();
                                    }
                                }
                            }else{
                                for (QueryDocumentSnapshot d : task.getResult()){
                                    if(d.get("nombre").toString().toUpperCase(Locale.ROOT).contains(Controlador.getInstance().getNombreTiendaValorar().toUpperCase(Locale.ROOT))) {
                                        Tienda t = d.toObject(Tienda.class);
                                        Log.d("3er block", "true");
                                        model.add(t);
                                        adaptador.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    }
                });
    }

    public void detallesTienda(int i){

        Tienda t = new Tienda(model.get(i).getId(),model.get(i).getNombre(),model.get(i).getTipo(),model.get(i).getSubtipo(),
                model.get(i).getDireccion(),model.get(i).getClasificacion(),model.get(i).getLatitud(),model.get(i).getLongitud(),
                model.get(i).getFechaVisita(),model.get(i).getAcceso(),model.get(i).getPuertaAcceso());

        Controlador.getInstance().setSelectedShop(t);
        getMainActivity().setFragment(FragmentName.SEARCH_RESULT_DETAILS);
    }

}

