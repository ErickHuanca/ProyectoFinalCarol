package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void BtnPrestamos(View v){
        startActivity(new Intent(MainActivity.this, PrestamosActivity.class));
    }

    public void BtnDevoluciones(View v){
        startActivity(new Intent(MainActivity.this, DevolucionesActivity.class));
    }
}
