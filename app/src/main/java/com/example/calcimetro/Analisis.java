package com.example.calcimetro;



import java.util.Date;
import java.io.Serializable;

public class Analisis implements Serializable {

    public Analisis(int nivelGlucosa, String s) {
        this.nivelGlucosa = nivelGlucosa;
    }

    public int getNivel() {
        return nivelGlucosa;
    }

    public String getNota1() {
        return nota1;
    }

    public String getNota2() {
        return nota2;
    }

    public Date getTiempo() {

        return tiempo;
    }


    public Analisis(Date tiempo,  int nivelGlucosa, String nota1, String nota2) {
        this.tiempo = tiempo;
        this.nivelGlucosa = nivelGlucosa;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    Date tiempo;
    int nivelGlucosa;
    String nota1, nota2;


    public int getNivelGlucosa() {

        return nivelGlucosa;
    }

    public void setNivelGlucosa(int nivelGlucosa) {
        this.nivelGlucosa = nivelGlucosa;
    }
}
