package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DevolucionesActivity extends AppCompatActivity {

    private FirebaseDatabase db;
    private TextView pDevoluciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devoluciones);

        db = FirebaseDatabase.getInstance();
        pDevoluciones = (TextView)findViewById(R.id.tvDevoluciones);
        mostrarDatos();
    }

    public void btnDevolver(View v){
        startActivity(new Intent(DevolucionesActivity.this, RegistroActivity.class));
    }

    public void mostrarDatos(){
        //listado de una base de datos de una tabla
        DatabaseReference presRef = db.getReference().child("Devoluciones");
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
                pDevoluciones.setText(mensaje);
//                Toast.makeText(PrestamosActivity.this,mensaje, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
