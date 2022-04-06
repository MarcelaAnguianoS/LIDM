package com.example.calcimetro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dia implements Serializable {

    public void setArrayAnalisis(List<Analisis> arrayAnalisis) {
        ArrayAnalisis = arrayAnalisis;
    }

    public List<Analisis> getArrayAnalisis() {

        return ArrayAnalisis;
    }

    public Dia(List<Analisis> arrayAnalisis) {

        ArrayAnalisis = arrayAnalisis;
    }

    List<Analisis> ArrayAnalisis = new ArrayList<>();
}