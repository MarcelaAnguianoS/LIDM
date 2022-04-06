package com.example.calcimetro;

public class ItemVo {


    private String Calcio;
    private String nota1;
    private String nota2;
    private String hora;

    public ItemVo(){

    }

    public ItemVo(String calcio, String nota1, String nota2, String hora) {
        Calcio = calcio;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.hora = hora;
    }

    public String getCalcio() {
        return Calcio;
    }

    public void setCalcio(String calcio) {
        Calcio = calcio;
    }

    public String getNota1() {
        return nota1;
    }

    public void setNota1(String nota1) {
        this.nota1 = nota1;
    }

    public String getNota2() {
        return nota2;
    }

    public void setNota2(String nota2) {
        this.nota2 = nota2;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
