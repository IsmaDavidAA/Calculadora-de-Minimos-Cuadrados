package com.example.isma.laboratorio_fisica;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__principal);
    }

    public void saltar_Activity_Lineal(View view){
        Intent saltar_Al_Lineal = new Intent(this, Activity_Lineal.class);
        startActivity(saltar_Al_Lineal);
    }

    public void saltar_Activity_Exponencial(View view){
        Intent saltar_Al_Exponencial = new Intent(this, Activity_Exponencial.class);
        startActivity(saltar_Al_Exponencial);
    }

    public void salir(View v) {
        finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
