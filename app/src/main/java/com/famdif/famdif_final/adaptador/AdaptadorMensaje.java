package com.famdif.famdif_final.adaptador;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

        import com.famdif.famdif_final.Mensaje;
        import com.famdif.famdif_final.R;

        import java.util.ArrayList;

public class AdaptadorMensaje extends BaseAdapter {
    private Context context;
    private ArrayList<Mensaje> listaMensaje;

    public AdaptadorMensaje(Context context, ArrayList<Mensaje> lista){
        this.context=context;
        this.listaMensaje=lista;
    }

    @Override
    public int getCount() {
        return listaMensaje.size();
    }

    @Override
    public Object getItem(int i) {
        return listaMensaje.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_mensaje, null);
        }

        TextView tituloMensaje = (TextView) convertView.findViewById(R.id.idTituloMensaje);
        TextView fechaMensaje = (TextView) convertView.findViewById(R.id.idFechaMensaje);
        TextView cuerpoMensaje = (TextView) convertView.findViewById(R.id.idCuerpoMensaje);

        tituloMensaje.setText(listaMensaje.get(position).getTitulo());
        cuerpoMensaje.setText(listaMensaje.get(position).getSinopsis());
        fechaMensaje.setText(listaMensaje.get(position).getFecha());

        return convertView;
    }



}
