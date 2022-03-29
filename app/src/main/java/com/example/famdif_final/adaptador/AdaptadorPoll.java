package com.example.famdif_final.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.famdif_final.MainActivity;
import com.example.famdif_final.Mensaje;
import com.example.famdif_final.Poll;
import com.example.famdif_final.R;

import java.util.ArrayList;

public class AdaptadorPoll extends BaseAdapter {
    private Context context;
    private ArrayList<Poll> listaPoll;

    public AdaptadorPoll(Context context, ArrayList<Poll> lista){
        this.context=context;
        this.listaPoll=lista;
    }

    @Override
    public int getCount() {
        return listaPoll.size();
    }

    @Override
    public Object getItem(int i) {
        return listaPoll.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_poll, null);
        }

        TextView tituloPoll = (TextView) convertView.findViewById(R.id.idTituloPoll);
        TextView cuantasPreguntas = (TextView) convertView.findViewById(R.id.idCuantasPreguntas);
        TextView vecesRespondida = (TextView) convertView.findViewById(R.id.idVecesRespondida);
        ImageView completada = (ImageView) convertView.findViewById(R.id.idImagenCompletado);

        if(MainActivity.logrosUsuario.contains(listaPoll.get(position).getLogroId())){
            completada.setImageResource(R.drawable.ic_confirmado);
        }else{
            completada.setImageResource(R.drawable.ic_pendiente);
        }



        tituloPoll.setText(listaPoll.get(position).getTitulo());
        cuantasPreguntas.setText(listaPoll.get(position).getPreguntas());
        //vecesRespondida.setText(listaPoll.get(position).getVecesRespondida());
        //listaPoll.get(position).getCompletada() ? completada.setImageResource(R.drawable.ic_confirmado) : completada.setImageResource(R.drawable.ic_pendiente);

        return convertView;
    }



}
