package com.example.datanet_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.opencv.cultoftheunicorn.marvel.R;


 public class Confirmo_Registro extends AppCompatActivity {

    Button buttonInicio;
    Button buttonvolverEnviarCorreo;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmo__registro);

        mAuth = FirebaseAuth.getInstance();


        buttonInicio = (Button) findViewById(R. id.Inicio);
        buttonInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Confirmo_Registro.this, com.example.datanet_app.Login_Activity.class));
            }
        });




    }





}