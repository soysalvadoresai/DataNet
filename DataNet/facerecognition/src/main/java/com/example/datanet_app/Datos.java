    package com.example.datanet_app;


import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.opencv.cultoftheunicorn.marvel.R;

import java.util.HashMap;

public class Datos extends AppCompatActivity {
    ImageView avanzar;
    EditText userNombre;
    EditText userTelefono;
    public boolean band = true;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        userNombre = (EditText) findViewById(R.id.txt_nombre_ss);
        userTelefono = (EditText) findViewById(R.id.txt_telefono_ss);
        inicializarFirebase();

        avanzar = (ImageView) findViewById(R.id.btn_avanzar);
        avanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                band = true;
                band = Validaciones(band);
                if (band = true){
                    UpdateUser();
                    Intent i = new Intent(Datos.this, com.example.datanet_app.MenuPrincipal.class);
                    startActivity(i);
                }else{
                    Toast.makeText(Datos.this, "Debe llenar los espacios obligatorios", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }//Fin del onCreate

    public boolean Validaciones (boolean band){
        String nombre = userNombre.getText().toString();

        if(nombre.equals("")){
            userNombre.setError("Requiered");
            band = false;
        }

        return band;
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void UpdateUser(){
        String uid = user.getUid();
        String nombre = userNombre.getText().toString();
        String telefono = userTelefono.getText().toString();
        String correo = user.getEmail();

        float suma = (float) 0.0;
        int votantes = 0;
        float ratio = (float) 0.0;

        HashMap hashMap= new HashMap();
        hashMap.put("nombre", nombre);
        hashMap.put("telefono", telefono);
        hashMap.put("correo", correo);
        hashMap.put("uid", uid);

        databaseReference.child("Usuario").child(uid).setValue(hashMap);
    }
}