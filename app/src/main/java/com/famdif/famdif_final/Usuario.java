package com.famdif.famdif_final;

public class Usuario {
    private String nombre;
    private String email;
    private String admin;
    private String pass;

    public Usuario(String nombre, String email, String admin, String password){
        this.admin=admin;
        this.email=email;
        this.nombre=nombre;
        this.pass=pass;
    }

    public Usuario(){}

    public String getEmail() {
        return email;
    }

    public String getAdmin() {
        return admin;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPass() { return pass; }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPass(String pass) { this.pass = pass; }
}
