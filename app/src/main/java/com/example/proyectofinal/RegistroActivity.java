package com.example.proyectofinal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private EditText pEtNombre;
    private EditText pEtFechaInicial;
    private EditText pEtFechaFinal;
    private EditText pEtProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        pEtNombre = (EditText) findViewById(R.id.etNombre);
        pEtFechaInicial = (EditText) findViewById(R.id.etFechaInicial);
        pEtFechaFinal = (EditText) findViewById(R.id.etFechaFinal);
        pEtProducto = (EditText) findViewById(R.id.etProducto);
    }

    public void btnPrestar(View v){

        String dnombre = pEtNombre.getText().toString();
        String dFechaIni= pEtFechaInicial.getText().toString();
        String dFechaFin = pEtFechaFinal.getText().toString();
        String dProducto = pEtProducto.getText().toString();
//        Map<String, Object> map = receiptHistory(producto, seccion, nCantidad, nombre, idProd, idUser, currentDate, currentTime);
        if (!dnombre.equals("") && !dFechaIni.equals("") && !dFechaFin.equals("") && !dProducto.equals("")){
            Map<String,Object> map = new HashMap<>();
            map.put("nombre",dnombre);
            map.put("fechaIni",dFechaIni);
            map.put("fechaFin",dFechaFin);
            map.put("producto",dProducto);

            String idPrestamo = mDatabase.push().getKey();
            mDatabase.child("Prestamos").child(idPrestamo).setValue(map);

            startActivity(new Intent(RegistroActivity.this, MainActivity.class));
            Toast.makeText(RegistroActivity.this, "Prestamo adiciconado", Toast.LENGTH_LONG).show();

        }
        else {
            Toast.makeText(RegistroActivity.this, "Completar correctamente",Toast.LENGTH_LONG).show();
        }
    }
    public void btnDevolver(View v){
        String dnombre = pEtNombre.getText().toString();
//        String dFechaIni= pEtFechaInicial.getText().toString();
//        String dFechaFin = pEtFechaFinal.getText().toString();
        String dProducto = pEtProducto.getText().toString();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query applesQuery = ref.child("Prestamos").orderByChild("producto").equalTo(dProducto);

        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                    String id = appleSnapshot.getKey();
                    String nom = appleSnapshot.child("nombre").getValue().toString();
                    String ini = appleSnapshot.child("fechaIni").getValue().toString();
                    String fin = appleSnapshot.child("fechaFin").getValue().toString();
                    String pro = appleSnapshot.child("producto").getValue().toString();

                    envioDevoluciones(id, nom, ini, fin, pro);
//                    Toast.makeText(RegistroActivity.this," dato  "+ nom,Toast.LENGTH_LONG).show();

                    appleSnapshot.getRef().removeValue();
                    startActivity(new Intent(RegistroActivity.this, MainActivity.class));
                    Toast.makeText(RegistroActivity.this, "Emilinado", Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                Log.e(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public void envioDevoluciones(String id, String nom, String ini, String fin, String pro){
//        if (!dnombre.equals("") && !dFechaIni.equals("") && !dFechaFin.equals("") && !dProducto.equals("")){
            Map<String,Object> map = new HashMap<>();
            map.put("nombre",nom);
            map.put("fechaIni",ini);
            map.put("fechaFin",fin);
            map.put("producto",pro);

//            String idPrestamo = mDatabase.push().getKey();
            mDatabase.child("Devoluciones").child(id).setValue(map);

            startActivity(new Intent(RegistroActivity.this, MainActivity.class));
//            Toast.makeText(RegistroActivity.this, "Prestamo adiciconado", Toast.LENGTH_LONG).show();

//        }
//        else {
//            Toast.makeText(RegistroActivity.this, "Completar correctamente",Toast.LENGTH_LONG).show();
//        }
    }
}
