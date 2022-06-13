package com.famdif.famdif_final;

public class Poll {
    private String titulo;
    private String preguntas;
    private int vecesRespondido;
    private String fragmentName;
    private String logroId;
    private boolean completado;

    public Poll(){}

    public Poll(String preguntas, String titulo, String fragmentName, int vecesRespondido){
        this.preguntas = preguntas;
        this.titulo = titulo;
        this.fragmentName = fragmentName;
        this.completado = false;
        this.vecesRespondido = vecesRespondido;
    }

    public String getPreguntas() {
        return preguntas;
    }

    public String getVecesRespondido() {
        return String.valueOf(vecesRespondido);
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFragmentName() {return fragmentName;}

    public String getLogroId() {return logroId;}

}