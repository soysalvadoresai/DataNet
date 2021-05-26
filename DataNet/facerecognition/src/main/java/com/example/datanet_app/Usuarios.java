package com.example.datanet_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.datanet_app.Models.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.opencv.cultoftheunicorn.marvel.R;

public class Usuarios extends AppCompatActivity {

    public Button administrador;
    public Button usuario;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        administrador = (Button) findViewById(R.id.btn_admin);
        usuario = (Button) findViewById(R.id.btn_usu);

        administrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Usuarios.this);
                builder.setTitle(R.string.Administrador);
                builder.setMessage(R.string.Jefes)
                        .setPositiveButton(R.string.opcion1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Usuarios.this, Datos.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();


            }
        });

        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Usuarios.this);
                builder.setTitle(R.string.Usuario);
                builder.setMessage(R.string.Empleado)
                        .setPositiveButton(R.string.opcion2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Usuarios.this, Datos.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });
    }//Fin del Oncreate

    public void onStart() {
        super.onStart();
        //Verificamos si el usuario esta registrado y hacemos un update UI
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Usuario").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Usuario u = snapshot.getValue(Usuario.class);
                    String nombre = u.getNombre();
                    String imagen = u.getImagen();
                    if (nombre != null) {
                        if (imagen != null) {
                            Intent intent = new Intent(Usuarios.this, MenuPrincipal.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        } else {
                            Intent intent = new Intent(Usuarios.this, SubirFoto_Activity.class);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                        }

                    }
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}