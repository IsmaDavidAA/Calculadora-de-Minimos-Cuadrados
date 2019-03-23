package com.example.isma.laboratorio_fisica;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.Inet4Address;
import java.util.ArrayList;

class Calculador implements Serializable {
    private ArrayList<Double> listaDeX;
    private ArrayList<Double> listaDeY;
    private boolean esLineal;
    private double errorA, errorB, valorA, valorB;
    public Calculador(ArrayList<Double> listaX,ArrayList<Double> listaY, boolean esLineal){
        this.listaDeX = listaX;
        this.listaDeY = listaY;
        this.esLineal = esLineal;
        this.errorA = error_A();
        this.errorB = error_B();
        this.valorA = valor_de_A_verdadera();
        this.valorB = valor_de_B_verdadera();
    }
    public  String mostrarDatosX() {
        String datos = "";
        int cont = 1;
        for(int x=0;x<listaDeX.size();x++) {
            datos = datos + "\n" + cont + "-" + listaDeX.get(x);
            cont = cont + 1;
        }
        return datos;
    }
    public String mostrarDatosY() {
        String datos = "";
        int cont = 1;
        for(int x=0;x<listaDeY.size();x++) {
            datos = datos + "\n" + cont + "-" + listaDeY.get(x);
            cont = cont + 1;
        }
        return datos;
    }
    public Integer mostrarNumeroDatosX() {
        int cont = 1;
        for(int x=0;x<listaDeX.size();x++)
            cont = cont + 1;
        return cont;
    }
    public Integer mostrarNumeroDatosY() {
        int cont = 1;
        for(int x=0;x<listaDeY.size();x++)
            cont = cont + 1;
        return cont;
    }

    public void aniadirListaDeX(Double nuevo) {
        if (esLineal == true) {
            this.listaDeX.add(nuevo);
        } else {

            this.listaDeX.add(Math.log(nuevo));
        }
    }
    public void aniadirListaDeY(Double nuevo) {
        if (esLineal == true) {
            this.listaDeY.add(nuevo);
        } else {
            Math.log(nuevo);
            this.listaDeY.add(Math.log(nuevo));
        }
    }
    public double sumatoria_X(){
        Double sumatoria = 0.0;
        for(int x=0;x<this.listaDeX.size();x++) {
            sumatoria = sumatoria + this.listaDeX.get(x);
        }
        return sumatoria;
    }
    public double sumatoria_Y(){
        Double sumatoria = 0.0;
        for(int y=0;y<this.listaDeY.size();y++) {
            sumatoria = sumatoria + this.listaDeY.get(y);
        }
        return sumatoria;
    }
    public double sumatoria_XY(){
        Double sumatoria = 0.0;
        for(int x=0;x<this.listaDeX.size();x++) {
            for (int y = 0; y < this.listaDeY.size(); y++) {
                if(x==y) {
                        sumatoria = sumatoria + (this.listaDeX.get(x) * this.listaDeY.get(y));
                }
            }
        }
        return sumatoria;
    }
    public double sumatoria_X2(){
        Double sumatoria = 0.0;
        for(int x=0;x<this.listaDeX.size();x++) {
            sumatoria = sumatoria + (this.listaDeX.get(x) * this.listaDeX.get(x));
        }
        return sumatoria;
    }
    public double sumatoria_Y2(){
        Double sumatoria = 0.0;
        for(int y=0;y<this.listaDeY.size();y++) {
            sumatoria = sumatoria + (this.listaDeY.get(y) * this.listaDeY.get(y));
        }
        return sumatoria;
    }

    public double sumatoria_discrepancias_cuadrado(){
        return (this.sumatoria_Y2())-(2*this.valor_de_A()*this.sumatoria_Y())-(2*this.valor_de_B_verdadera()*this.sumatoria_XY())+((this.numero_Datos())*this.valor_de_A()*this.valor_de_A())
                +(2*this.valor_de_A()*this.valor_de_B_verdadera()*this.sumatoria_X())+(this.valor_de_B_verdadera()*this.valor_de_B_verdadera()*this.sumatoria_X2());
    }
    public double triangulito(){
        return ((this.numero_Datos())*this.sumatoria_X2())-(this.sumatoria_X()*this.sumatoria_X());
    }
    public double o_cuadradito(){
        return (this.sumatoria_discrepancias_cuadrado())/(this.numero_Datos()-2);
    }
    private double valor_de_A(){
        return ((this.sumatoria_Y()*this.sumatoria_X2())-(this.sumatoria_XY()*this.sumatoria_X()))/(((this.numero_Datos())*this.sumatoria_X2())-(this.sumatoria_X()*this.sumatoria_X()));
    }

    public double valor_de_B_verdadera(){
        return (((this.numero_Datos())*this.sumatoria_XY())-(this.sumatoria_X()*this.sumatoria_Y()))/(((this.numero_Datos())*this.sumatoria_X2())-(this.sumatoria_X()*this.sumatoria_X()));
    }
    public double error_B(){
        return Math.sqrt(((this.numero_Datos())*this.o_cuadradito())/(this.triangulito()));
    }

    public double error_A(){
        Double res = Math.sqrt((this.o_cuadradito()*this.sumatoria_X2())/(this.triangulito()));
        if(this.esLineal == true){
            res = Math.sqrt((this.o_cuadradito()*this.sumatoria_X2())/(this.triangulito()));
        }else if(this.esLineal == false){
            res = Math.sqrt((this.o_cuadradito()*this.sumatoria_X2())/(this.triangulito())) * this.valor_de_A_verdadera();
        }
        return res;
    }
    public double valor_de_A_verdadera(){
        Double res = 0.0;
        if(this.esLineal == true){
            res = this.valor_de_A();
        }else if(this.esLineal == false){
            res = Math.pow(2.718281828,this.valor_de_A());
        }
        return res;
    }
    public Integer numero_Datos(){
        return this.mostrarNumeroDatosX()-1;
    }

    public boolean EsLineal() {
        return esLineal;
    }

    public double getErrorA() {
        return errorA;
    }

    public double getErrorB() {
        return errorB;
    }

    public double getValorA() {
        return valorA;
    }

    public double getValorB() {
        return valorB;
    }
}
