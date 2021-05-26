package com.example.datanet_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import org.opencv.cultoftheunicorn.marvel.R;

import cultoftheunicorn.marvel.MainActivity;

public class MenuPrincipal extends AppCompatActivity {
    Button buttonlogout;
    Button buttonperfil;
    Button buttoncamara;
    Button buttonactividades;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        buttonlogout = (Button) findViewById(R.id.logout);
        buttonperfil = (Button) findViewById(R.id.perfil);
        buttoncamara = (Button) findViewById(R.id.camara);
        buttonactividades = (Button) findViewById(R.id.actividades);




        buttonlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MenuPrincipal.this, Login_Activity.class));
            }
        });

        buttonperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, com.example.datanet_app.Perfil_Activity.class));
            }
        });

        buttoncamara.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, cultoftheunicorn.marvel.MainActivity.class));
            }
        }));
        buttonactividades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuPrincipal.this, HistorialActivity.class));
            }
        });


    }
}