package com.famdif.famdif_final.adaptador;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.famdif.famdif_final.Achievement;
import com.famdif.famdif_final.MainActivity;
import com.famdif.famdif_final.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AdaptadorAchievement extends BaseAdapter {
    private Context context;
    private ArrayList<Achievement> listAchievement;

    public AdaptadorAchievement(Context context, ArrayList<Achievement> lista){
        this.context=context;
        this.listAchievement=lista;
    }

    @Override
    public int getCount() {
        return listAchievement.size();
    }

    @Override
    public Object getItem(int i) {
        return listAchievement.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_achievement, null);
        }

        TextView tituloAchievement = (TextView) convertView.findViewById(R.id.titleView);
        TextView descripcionAchievement = (TextView) convertView.findViewById(R.id.descriptionView);
        ImageView logroImagen = (ImageView) convertView.findViewById(R.id.achievementImg);

        tituloAchievement.setText(listAchievement.get(position).getTitulo());
        Log.d("desc", listAchievement.get(position).getId());
        descripcionAchievement.setText(listAchievement.get(position).getDescripcion());

        if (MainActivity.logrosUsuario.contains(listAchievement.get(position).getId())) {
            Log.d("bruh", listAchievement.toString());
            String imageTitle = "ic_medallas_app_comercios_" + listAchievement.get(position).getId();
            logroImagen.setImageResource(context.getResources().getIdentifier(imageTitle,"drawable","com.famdif.famdif_final"));
        }else{
            logroImagen.setImageResource(R.drawable.ic_logro_no_conseguido);
        }
        return convertView;
    }

}
