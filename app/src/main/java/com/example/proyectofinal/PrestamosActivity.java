package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PrestamosActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private TextView pPrestamos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prestamos);

        db = FirebaseDatabase.getInstance();
        pPrestamos = (TextView) findViewById(R.id.tvPrestamos);

        mostrarDatos();
    }

    public void btnPrestar(View v){
        startActivity(new Intent(PrestamosActivity.this, RegistroActivity.class));
    }

    public void mostrarDatos(){
        //listado de una base de datos de una tabla
        DatabaseReference presRef = db.getReference().child("Prestamos");
        presRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                List<String> list = new ArrayList<>();
                String mensaje = "NOMBRE    FECHA INI    FECHA FIN   PRODUCTO"+"\n";
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    mensaje = mensaje +
                            ds.child("nombre").getValue().toString()+"   -   "+
                            ds.child("fechaIni").getValue().toString()+"    -    "+
                            ds.child("fechaFin").getValue().toString()+"    -    "+
                            ds.child("producto").getValue().toString()+"\n";
                }
                pPrestamos.setText(mensaje);
//                Toast.makeText(PrestamosActivity.this,mensaje, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
