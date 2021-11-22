package com.example.famdif_final;

public class Votacion {
    private String email;
    private String id;
    private String puntuacion;

    public Votacion(String email, String id, String votacion){
        this.email=email;
        this.id=id;
        this.puntuacion=votacion;
    }

    public Votacion(){}

    public String getPuntuacion() {
        return puntuacion;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }
}
