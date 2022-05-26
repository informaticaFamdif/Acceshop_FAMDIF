package com.example.famdif_final;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TipoTienda {
    private ArrayList<String> tiposTienda=new ArrayList<>();

    public TipoTienda(){
        tiposTienda.add("Alimentación");
        tiposTienda.add("Automoción");
        tiposTienda.add("Bancos, seguros y gestoría");
        tiposTienda.add("Educación y auxiliares");
        tiposTienda.add("Estética y cuidado personal");
        tiposTienda.add("Hogar y decoración");
        tiposTienda.add("Inmobiliario");
        tiposTienda.add("Ocio y entretenimiento");
        tiposTienda.add("Restauración");
        tiposTienda.add("Salud");
        tiposTienda.add("Tecnología y telecomunicaciones");
        tiposTienda.add("Textil y complementos");
        tiposTienda.add("Viajes y transportes");
        tiposTienda.add("Arte");
        tiposTienda.add("Residencial Público");
        tiposTienda.add("Otros");

    }

    public List<String> getTiposTienda() {
        return tiposTienda;
    }

}
