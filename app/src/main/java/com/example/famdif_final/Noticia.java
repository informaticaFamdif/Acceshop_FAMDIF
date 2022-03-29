package com.example.famdif_final;

public class Noticia {
    private String link;
    private String titulo;
    private String sinopsis;

    public Noticia(){}

    public Noticia(String link, String titulo, String sinopsis){
        this.link = link;
        this.titulo = titulo;
        this.sinopsis = sinopsis;
    }

    public String getLink() {
        return link;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public String getTitulo() {
        return titulo;
    }
}
