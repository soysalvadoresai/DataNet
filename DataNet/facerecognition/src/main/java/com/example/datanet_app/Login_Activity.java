package com.example.datanet_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.opencv.cultoftheunicorn.marvel.R;


public class Login_Activity extends AppCompatActivity {
    //Creamos la variable boton

    private EditText emaileditText;
    private EditText passwordeditText;
    Button buttonRegistro;
    Button buttonEntrar;
    FirebaseAuth mAuth;
    Button buttonconfiguracion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        emaileditText = (EditText) findViewById(R.id.emaileditText);
        passwordeditText = (EditText) findViewById(R.id.passwordeditText);
        buttonRegistro = (Button) findViewById(R.id.buttonRegistro);
        buttonEntrar = (Button) findViewById(R.id.buttonEntrar);

        buttonRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(Login_Activity.this, com.example.datanet_app.Registro_Activity.class));
            }
        });

        buttonconfiguracion = (Button) findViewById(R.id. configuracion);

        buttonconfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, com.example.datanet_app.Configuracion_Activity.class));
            }
        });



        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Extraccion de datos y validar
                if(emaileditText.getText().toString().isEmpty()){
                    emaileditText.setError("Falta ingresar el correo");
                    return;

                }
                if (passwordeditText.getText().toString().isEmpty()){
                    passwordeditText.setError("Falta ingresar la contraseña");
                    return;
                }


                mAuth.signInWithEmailAndPassword(emaileditText.getText().toString(), passwordeditText.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                    //Login is succesfull
                        startActivity(new Intent(getApplicationContext(), com.example.datanet_app.Usuarios.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

}