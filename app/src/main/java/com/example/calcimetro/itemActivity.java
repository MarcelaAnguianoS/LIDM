package com.example.calcimetro;

public class itemActivity  {
    private  String mensaje;
    private String nombre;
    private String hora;
    private String type_mensaje;


    public itemActivity() {
    }

    public itemActivity(String mensaje, String nombre, String type_mensaje,String hora) {
        this.mensaje = mensaje;
        this.nombre = nombre;
        this.hora = hora;
        this.type_mensaje = type_mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getType_mensaje() {
        return type_mensaje;
    }

    public void setType_mensaje(String type_mensaje) {
        this.type_mensaje = type_mensaje;
    }
}