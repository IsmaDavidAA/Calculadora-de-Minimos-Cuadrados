package com.example.isma.laboratorio_fisica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Activity_Exponencial extends AppCompatActivity {
    private EditText textDeXIntroduced;
    private EditText textDeYIntroduced;
    private TextView textDeXView;
    private TextView textDeYView;
    private Calculador calculador;
    private ArrayList<Double> listaDeX;
    private ArrayList<Double> listaDeY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__exponencial);
        textDeXIntroduced = (EditText) findViewById(R.id.editText_X);
        textDeYIntroduced = (EditText) findViewById(R.id.editText2_Y);
        textDeXView = (TextView) findViewById(R.id.outXView_X);
        textDeYView = (TextView) findViewById(R.id.outYView_Y);

        listaDeX = new ArrayList<Double>();
        listaDeY = new ArrayList<Double>();

        calculador = new Calculador(this.listaDeX,this.listaDeY,false);
        textDeXView.setText(this.calculador.mostrarDatosX());
        textDeYView.setText(this.calculador.mostrarDatosY());
    }
    public void saltar_Activity_Principal(View view){
        Intent saltar_Al_Principal = new Intent(this, Activity_Principal.class);
        startActivity(saltar_Al_Principal);
    }

    public void saltar_Activity_Resultados(View view){
        Intent saltar_A_Resultados_Lin = new Intent(this, Activity__Resultados.class);
        saltar_A_Resultados_Lin.putExtra( "calculador", this.calculador);
        startActivity(saltar_A_Resultados_Lin);
    }
    public void llenarX(View view) {
        String nuevo = textDeXIntroduced.getText().toString();
        if(!nuevo.isEmpty()){
            Double nuevoDouble = Double.parseDouble(nuevo);
            this.calculador.aniadirListaDeX(nuevoDouble);
            this.textDeXView.setText(this.calculador.mostrarDatosX());
        }else{
            Toast.makeText(this,"debes introducir un dato", Toast.LENGTH_SHORT).show();
        }
    }
    public void llenarY(View view){
        String nuevo = textDeYIntroduced.getText().toString();
        if(!nuevo.isEmpty()){
            Double nuevoDouble = Double.parseDouble(nuevo);
            if (this.calculador.mostrarNumeroDatosY() < this.calculador.mostrarNumeroDatosX()) {
                this.calculador.aniadirListaDeY(nuevoDouble);
            }
            this.textDeYView.setText(this.calculador.mostrarDatosY());
        }else{
            Toast.makeText(this,"debes introducir un dato", Toast.LENGTH_SHORT).show();
        }
    }
}
