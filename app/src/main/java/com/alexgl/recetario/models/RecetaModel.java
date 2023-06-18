package com.alexgl.recetario.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class RecetaModel implements Serializable {

    private String nombre, epoca;
    private ArrayList<IngredienteModel> ingredientes;
    private boolean deSerie;
    private int deSerieNumerico; //la base de datos no tiene como tipo booleano, por lo que devuelve un int 0 o 1.
    private Map<Integer, String> pasos;

    public RecetaModel(String nombre, String epoca, int deSerieNumerico) {
        this.nombre = nombre;
        this.epoca = epoca;
        this.deSerieNumerico = deSerieNumerico;
        //se transforma el int en booleano.
        if(this.deSerieNumerico == 0){
            deSerie = false;
        } else{
            deSerie = true;
        }
        this.ingredientes = new ArrayList<>();
        this.pasos = new TreeMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEpoca() {
        return epoca;
    }

    public void setEpoca(String epoca) {
        this.epoca = epoca;
    }

    public ArrayList<IngredienteModel> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<IngredienteModel> ingredienteModel) {
        this.ingredientes = ingredienteModel;
    }

    public boolean isDeSerie() {
        return deSerie;
    }

    public void setDeSerie(boolean deSerie) {
        this.deSerie = deSerie;
    }

    public int getDeSerieNumerico() {
        return deSerieNumerico;
    }

    public void setDeSerieNumerico(int deSerieNumerico) {
        this.deSerieNumerico = deSerieNumerico;
    }

    public Map<Integer, String> getPasos() {
        return pasos;
    }

    public void setPasos(Map<Integer, String> pasos) {
        this.pasos = pasos;
    }

    public String getListadoIngredientes(){
        String resultado = "";
        for(IngredienteModel ingrediente : ingredientes){
            resultado += "- " + ingrediente.getCantidad() + ingrediente.getUnidadMedida() + " de " + ingrediente.getNombre() + "\n";
        }
        return resultado;
    }

    public int getNumeroPasosTotal(){
        return this.pasos.size();
    }

    public String getPaso(int numeroPaso){
        return pasos.get(numeroPaso);
    }

}
