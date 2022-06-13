package com.famdif.famdif_final;

public class Mensaje {
    private String fecha;
    private String titulo;
    private String cuerpo;
    private String sinopsis;

    public Mensaje(){}

    public Mensaje(String fecha, String titulo, String cuerpo){
        this.fecha = fecha;
        this.titulo = titulo;
        this.cuerpo = cuerpo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }


}
