package com.example.isma.laboratorio_fisica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class Activity__Resultados extends AppCompatActivity {
    private TextView ecuacion_base_View,ecuacion_base,valor_a_view,valor_a,valor_b_view,valor_b,mostrar_datos;
    private ArrayList<Double> lista_X;
    private ArrayList<Double> lista_Y;
    private Spinner spinner;
    private Calculador calculador;
    private Redondeador redondeoA;
    private Redondeador redondeoB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity___resultados);
        this.lista_X = new ArrayList<Double>();
        this.lista_Y = new ArrayList<Double>();

        Intent calculadorRecibido = getIntent();
        calculador = (Calculador)calculadorRecibido.getSerializableExtra("calculador");

        this.asignar_nombres_a_Views();
        this.crearSpiner();
        llenar_Campos();
    }
    private void crearSpiner() {
        this.spinner = (Spinner) findViewById(R.id.spinner);
        String [] opciones = {"Mostrar datos de X","Mostrar datos de Y","Mostrar Σx","Mostrar Σy","Mostrar Σxy","Mostrar Σx²","Mostrar Σy²",
                "Numero de datos","error de a", "error de b","valor de A","valor de B"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
        this.spinner.setAdapter(adapter);
    }
    public void saltar_Activity_Principal(View view){
        Intent saltar_A_Principal = new Intent(this, Activity_Principal.class);
        startActivity(saltar_A_Principal);
    }
    public void saltar_Activity_Lineal(View view){
        Intent saltar_Al_Lineal = new Intent(this, Activity_Lineal.class);
        startActivity(saltar_Al_Lineal);
    }

    public void buscar(View view){
        String seleccion = spinner.getSelectedItem().toString();
        if(seleccion.equals("Mostrar datos de X")){
            mostrar_datos.setText(calculador.mostrarDatosX());
        }else if(seleccion.equals("Mostrar datos de Y")){
            mostrar_datos.setText(calculador.mostrarDatosY());
        }else if(seleccion.equals("Mostrar Σx")){
            mostrar_datos.setText(" " + calculador.sumatoria_X());
        }else if(seleccion.equals("Mostrar Σy")){
            mostrar_datos.setText(" " + calculador.sumatoria_Y());
        }else if(seleccion.equals("Mostrar Σxy")){
            mostrar_datos.setText(" " + calculador.sumatoria_XY());
        }else if(seleccion.equals("Mostrar Σx²")){
            mostrar_datos.setText(" "+ calculador.sumatoria_X2());
        }else if(seleccion.equals("Mostrar Σy²")){
            mostrar_datos.setText(" " + calculador.sumatoria_Y2());
        }else if(seleccion.equals("Numero de datos")) {
            mostrar_datos.setText(" " + (calculador.numero_Datos()));
        }else if(seleccion.equals("error de a")) {
            mostrar_datos.setText(" " + (calculador.error_A()));
        }else if(seleccion.equals("error de b")) {
            mostrar_datos.setText(" " + (calculador.error_B()));
        }else if(seleccion.equals("valor de A")) {
            mostrar_datos.setText(" " + (calculador.valor_de_A_verdadera()));
        }else if(seleccion.equals("valor de B")) {
            mostrar_datos.setText(" " + (calculador.valor_de_B_verdadera()));
        }

    }
    public void asignar_nombres_a_Views(){
        ecuacion_base_View = (TextView) findViewById(R.id.ecuacion_base_View);
        ecuacion_base = (TextView) findViewById(R.id.ecuacion_base);
        valor_a_view = (TextView) findViewById(R.id.valor_a_view);
        valor_a = (TextView) findViewById(R.id.valor_a);
        valor_b_view = (TextView) findViewById(R.id.valor_b_view);
        valor_b = (TextView) findViewById(R.id.valor_b);
        mostrar_datos = (TextView) findViewById(R.id.datos_view);
    }
    public void llenar_Campos() {
        String errorA = "" +calculador.error_A();
        String errorB = "" +calculador.error_B();
        String valorA = "" +calculador.valor_de_A_verdadera();
        String valorB = "" +calculador.valor_de_B_verdadera();
        redondeoA = new Redondeador(errorA,valorA);
        redondeoB = new Redondeador(errorB,valorB);
        if(calculador.EsLineal() == true) {
            ecuacion_base_View.setText("Ecuacion Lineal");

            if(redondeoB.getValorEsNegativo() == false) ecuacion_base.setText("Y = " + redondeoA.getValor() + " + " + redondeoB.getValor()+" X");
            else ecuacion_base.setText("Y = " + redondeoA.getValor() + "  " + redondeoB.getValor()+" X");

            valor_a_view.setText("Valor de A");
            valor_b_view.setText("Valor de B");
            valor_a.setText("A = (" + redondeoA.getValor()+" ± "+redondeoA.getError()+")");
            valor_b.setText("B = (" + redondeoB.getValor()+" ± "+redondeoB.getError()+")");
        }else if(calculador.EsLineal() == false){
            ecuacion_base_View.setText("Ecuacion exponencial");
            ecuacion_base.setText("Y = (" + redondeoA.getValor()+ ") * X " + "^ (" + redondeoB.getValor()+")");
            valor_a_view.setText("Valor de a");
            valor_b_view.setText("Valor de b");
            valor_a.setText("A = (" + redondeoA.getValor()+" ± "+redondeoA.getError()+")");
            valor_b.setText("B = (" + redondeoB.getValor()+" ± "+redondeoB.getError()+")");
        }
    }
}
