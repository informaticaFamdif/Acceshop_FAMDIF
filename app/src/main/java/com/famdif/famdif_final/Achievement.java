package com.famdif.famdif_final;

import org.jsoup.select.Evaluator;

public class Achievement {
    private String titulo;
    private String descripcion;
    private String id;

    public Achievement(){}

    public Achievement(String titulo, String descripcion){
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public Achievement(String id){
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getId() { return id; }
}
