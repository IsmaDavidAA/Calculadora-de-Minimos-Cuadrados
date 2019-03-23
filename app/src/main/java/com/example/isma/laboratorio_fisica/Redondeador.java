package com.example.isma.laboratorio_fisica;

import java.math.BigDecimal;


public class Redondeador{
    private BigDecimal errorRepresentativo,Medicion;
    private String tamañoError,tamañoMedicion;
    private int[] errorSinRedondear;
    private int[] errorRedondeado;
    private int tamañoDigitos;
    private boolean esEntero;
    private Boolean errorEsNegativo = true, valorEsNegativo = true;
    private String valor;
    private String error;
    public Redondeador(String aCon, String valMed){
        double aConvertir = Double.parseDouble(aCon);
        double valorMedicion = Double.parseDouble(valMed);
        sacarValorAbsoluto(aConvertir, valorMedicion);
        this.errorRepresentativo = new BigDecimal("" + aConvertir);
        this.Medicion = new BigDecimal("" + valorMedicion);
        this.tamañoError = this.errorRepresentativo.toPlainString();
        this.tamañoMedicion = this.Medicion.toPlainString();
        this.errorSinRedondear = new int[tamañoError.length() + 3];
        this.errorRedondeado = new int[tamañoError.length()];
        this.tamañoDigitos = 0;
        this.esEntero = true;
        this.error = errorRepresentativo();
        this.valor = redondearMedicion();
    }

    public String getError() {
        return error;
    }

    public String getValor() {
        return valor;
    }

    public Redondeador(){

    }

    public Boolean getValorEsNegativo() {
        return valorEsNegativo;
    }

    private void sacarValorAbsoluto(double aConvertir, double valorMedicion) {
        if(aConvertir < 0)   aConvertir = aConvertir * -1.0;
        else errorEsNegativo  = false;
        if(valorMedicion < 0)   valorMedicion = valorMedicion * -1.0;
        else valorEsNegativo = false;
    }

    public String redondearMedicion(){
        String medicionReal = "";
        int limite = tamañoDigitos;
        boolean pasoPunto = false;
        if(esEntero == true){
            for(int i = 0; i < tamañoMedicion.length(); i ++){
                if('.' == tamañoMedicion.charAt(i)){
                    i = tamañoMedicion.length();
                }else{
                    medicionReal = medicionReal + tamañoMedicion.charAt(i);
                }
            }
        }else{
            for(int i = 0; i < tamañoMedicion.length(); i ++){
                if('.' == tamañoMedicion.charAt(i)){
                    medicionReal = medicionReal + tamañoMedicion.charAt(i);
                    limite = limite - 1;
                    if(limite == 0){
                        i = tamañoMedicion.length();
                    }
                    pasoPunto = true;
                }else{
                    if(pasoPunto == false){
                        medicionReal = medicionReal + tamañoMedicion.charAt(i);
                    }else{
                        medicionReal = medicionReal + tamañoMedicion.charAt(i);
                        limite = limite - 1;
                        if(limite == 0){
                            i = tamañoMedicion.length();
                        }
                    }
                }
                if(i == tamañoMedicion.length() - 1){
                    if(limite > 0){
                        medicionReal = medicionReal + "0";
                    }
                }
            }
        }
        if(this.valorEsNegativo == true) medicionReal = "-" + medicionReal;
        return medicionReal;
    }
    public String errorRepresentativo(){
        redondear();
        String  errorString= "";
        for(int i = errorRedondeado.length - 1;i > 0; i --){
            if(10 == errorRedondeado[i]){
                limpiar(tamañoDigitos);
            }
        }

        for(int i = 0; i < errorRedondeado.length; i ++){
            if(i <= tamañoDigitos){
                if(errorRedondeado[i] != 100 ){
                    errorString = errorString + errorRedondeado[i];
                }else{
                    errorString = errorString + ".";
                    esEntero = false;
                }
            }
        }
        if(this.errorEsNegativo == true) errorString = "-" + errorString;
        return errorString;
    }

    private void redondear(){
        convertir();
        boolean pasoADecimal =  false;
        for (int i = 0; i < errorSinRedondear.length; i++){
            int pos = i, posNext = i + 1, posNextN = i + 2, posNextNN = i  + 3;
            if(errorSinRedondear[posNext] == 100){
                if(errorSinRedondear[pos] == 0){
                    if(errorSinRedondear[posNextN] == 0){
                        errorRedondeado[pos] = 0;
                        errorRedondeado[pos + 1] = 100;
                        errorRedondeado[pos + 2] = 0;
                        i = i + 2;
                        tamañoDigitos = tamañoDigitos + 2;
                        pasoADecimal = true;
                    }else{
                        if(errorSinRedondear[posNextN] == 9){
                            if(errorSinRedondear[posNextNN] >= 5 && errorSinRedondear[posNextNN] != 100){
                                errorRedondeado[pos] = 1;
                            }else{
                                errorRedondeado[pos] = 0;
                                errorRedondeado[posNext] = 100;
                                errorRedondeado[posNextN] = 9;
                                tamañoDigitos = tamañoDigitos + 2;
                            }
                            i = errorSinRedondear.length ;
                        }else{
                            if(errorSinRedondear[posNextN] > 0){
                                errorRedondeado[posNext] = 100;
                                errorRedondeado[posNextN] = aumentarSimple(errorSinRedondear[posNextN], errorSinRedondear[posNextNN]);
                                tamañoDigitos = tamañoDigitos + 2;
                                i = errorSinRedondear.length ;
                            }
                        }
                    }
                }else{
                    if(errorSinRedondear[pos] == 9){
                        if(10 == aumentarSimple(errorSinRedondear[pos], errorSinRedondear[posNextN])){
                            if(pos == 0){
                                errorRedondeado[pos] = 1;
                                errorRedondeado[posNext] = 0;
                                tamañoDigitos = tamañoDigitos + 1;
                            }
                        }else{
                            errorRedondeado[pos] = 9;
                        }
                        i = errorSinRedondear.length ;
                    }else{
                        if(errorSinRedondear[pos] >= 1 && errorSinRedondear[pos] <= 8){
                            errorRedondeado[pos] = aumentarSimple(errorSinRedondear[pos], errorSinRedondear[posNextN]);
                            i = errorSinRedondear.length;
                        }
                    }
                }
            }else{
                if(errorSinRedondear[posNextN] == 100){
                    errorRedondeado[pos] = errorSinRedondear[pos];
                    if(errorSinRedondear[posNextNN] >= 5){
                        errorRedondeado[posNext] = aumentarSimple(errorSinRedondear[posNext], errorSinRedondear[posNextNN]);
                        tamañoDigitos = tamañoDigitos + 1;
                    }else{
                        errorRedondeado[posNext] = errorSinRedondear[posNext];
                        tamañoDigitos = tamañoDigitos + 1;
                    }
                    i = errorSinRedondear.length;
                }else{
                    if(pasoADecimal == true){
                        if(errorSinRedondear[pos] == 9){
                            if(errorSinRedondear[posNext] >= 5){
                                errorRedondeado[pos - 1] = 1;
                                i = errorSinRedondear.length;
                            }else{
                                errorRedondeado[pos] = errorSinRedondear[pos];
                                tamañoDigitos = tamañoDigitos + 1;
                                i = errorSinRedondear.length;
                            }
                        }else{
                            if(errorSinRedondear[pos] != 0){
                                errorRedondeado[pos] = aumentarSimple(errorSinRedondear[pos], errorSinRedondear[posNext]);
                                tamañoDigitos = tamañoDigitos + 1;
                                i = errorSinRedondear.length;
                            }else{
                                tamañoDigitos = tamañoDigitos + 1;
                                errorRedondeado[pos] = 0;
                            }
                        }
                    }else{
                        tamañoDigitos = tamañoDigitos + 1;
                        errorRedondeado[pos] = errorSinRedondear[pos];
                    }
                }
            }
        }
    }

    private void convertir(){
        for(int i = 0; i < tamañoError.length(); i ++){
            if(46 == tamañoError.charAt(i)){
                errorSinRedondear[i] = 100;
            }else{
                errorSinRedondear[i] = tamañoError.charAt(i) - 48;
            }
        }
    }

    private void limpiar(int pos){
        if(10 ==errorRedondeado[pos]){
            this.errorRedondeado[pos] = 0;
            this.errorRedondeado[pos - 1] = this.errorRedondeado[pos - 1] + 1;
            if(10 == this.errorRedondeado[pos - 1]){
                if(pos - 1 == 0){
                    this.errorRedondeado[0] = 1;
                    for(int i = 1; i < errorRedondeado.length; i ++){
                        errorRedondeado[i] = 0;
                    }
                    tamañoDigitos = tamañoDigitos + 1;
                }else{
                    limpiar(pos - 1);
                }
            }
        }
    }

    private int aumentarSimple(int n, int p){
        if(n >= 5){
            if(n == 9){
                if(p >= 5){
                    n = 10;
                }
            }else{
                if(p >= 5){
                    n = n +1;
                }
            }
        }else{
            if(p >= 6){
                n = n + 1;
            }else{
                if(p == 5){
                    if(n%2 != 0){
                        n = n + 1;
                    }
                }
            }
        }
        return n;
    }
}
