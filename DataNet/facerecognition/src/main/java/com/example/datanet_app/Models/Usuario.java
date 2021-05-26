package com.example.datanet_app.Models;

public class Usuario {
    private String uid;
    private String Nombre;
    private String Telefono;
    private String Imagen;
    private String Correo;
    private String tiempo;



    public String getName() {
        return Nombre;
    }


    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTelefono() {
        return Telefono;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    @Override
    public String toString() {
        return Nombre;
    }
}



