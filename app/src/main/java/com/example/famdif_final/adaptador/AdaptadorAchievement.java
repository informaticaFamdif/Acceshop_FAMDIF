package com.example.famdif_final.adaptador;

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

import com.example.famdif_final.Achievement;
import com.example.famdif_final.R;
import com.google.android.gms.tasks.OnSuccessListener;
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
        Log.d("desc", listAchievement.get(position).getTitulo());
        descripcionAchievement.setText(listAchievement.get(position).getDescripcion());

        //TODO: Coger la imagen de la BD

        logroImagen.setImageResource(R.drawable.ic_logro_no_conseguido);

        return convertView;
    }

    private void obtenerImagenLogro(Achievement logro) {
        StorageReference mStorageReference;
        mStorageReference = FirebaseStorage.getInstance().getReference("/" + String.valueOf(logro.getId()) + "_A.jpg");

        try {
            File localfile = File.createTempFile("tempfile", "jpg");
            mStorageReference.getFile(localfile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                    // TODO:CREAR ESTE IMAGEVIEW:
                    // imagen.setImageBitmap(bitmap);
                    // foto.update();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
