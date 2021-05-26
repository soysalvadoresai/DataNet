package com.example.datanet_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.datanet_app.Models.Usuario;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.opencv.cultoftheunicorn.marvel.R;

import java.util.HashMap;

public class Perfil_Activity extends AppCompatActivity {

    Button mbtn_guardar_cambios, btn_back;
    EditText userNombre, userTelefono;
    Button mRegresar;
    ImageView photo, editarFoto;
    FirebaseAuth mAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    public String uid = user.getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_);

        userNombre = (EditText) findViewById(R.id.txt_nombre_perfil);
        userTelefono = (EditText) findViewById(R.id.txt_telefono_perfil);
        mbtn_guardar_cambios = (Button) findViewById(R.id.btn_guardar_cambios);
        mbtn_guardar_cambios.setEnabled(false);
        photo = (ImageView) findViewById(R.id.myPhoto);
        editarFoto = (ImageView) findViewById(R.id.editPhoto);
        btn_back = (Button) findViewById(R.id.Regresar);
        inicializarFirebase();
        MostrarDatos();

        mbtn_guardar_cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUser();
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Perfil_Activity.this, MenuPrincipal.class);
                startActivity(intent);
            }
        });

        editarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil_Activity.this,EditarFoto_Activity.class);
                startActivity(intent);
            }
        });

        // Ocultar boton guardar
        OcultarBoton();

    }//Fin del onCreate
    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void MostrarDatos (){
    databaseReference.child("Usuario").child(uid).addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            Usuario u =snapshot.getValue(Usuario.class);
            userNombre.setText(u.getNombre());
            userTelefono.setText(u.getTelefono());

            String imagen = u.getImagen();
            Picasso.get().load(imagen).into(photo);
        }
    }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.top_menu_menu,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.btnSignOut:
//                //Detectar al usuario actual
//                mAuth = FirebaseAuth.getInstance();
//
//                //SIGN OUT
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(),Login_Activity.class));
//                break;
//
//            case R.id.btnDeleteAccount:
//                AlertDialog.Builder builder = new AlertDialog.Builder(Perfil_Activity.this);
//                builder.setTitle("Eliminar cuenta permanentemente");
//                builder.setMessage("Una vez que elimine")
//                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                user.delete();
//                                databaseReference.child("Usuario").child(uid).removeValue();
//
//                                Toast.makeText(Perfil_Activity.this, "Account deleted", Toast.LENGTH_SHORT).show();
//
//                                Intent intent = new Intent(Perfil_Activity.this, Login_Activity.class);
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        }).show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

            public void UpdateUser(){
                String uid = user.getUid();
                String nombre = userNombre.getText().toString();
                String telefono = userTelefono.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("nombre",nombre);
                if (!nombre.equals("")){
                    hashMap.put("nombre",nombre);
                }else{
                    databaseReference.child("Usuario").child(uid).child("Nombre").removeValue();
                }

                hashMap.put("telefono",telefono);
                hashMap.put("uid",uid);


                databaseReference.child("Usuario").child(uid).updateChildren(hashMap);

                Toast.makeText(this, "Usuario Actualizado", Toast.LENGTH_SHORT).show();

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }

            private void OcultarBoton(){
                databaseReference.child("Usuario").child(uid).addValueEventListener(new ValueEventListener(){
                    @Override
                public void onDataChange (@NonNull DataSnapshot snapshot){
                        if (snapshot.exists()){
                            Usuario u = snapshot.getValue(Usuario.class);
                            final String newNombre = u.getNombre();
                            userNombre.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                                    if ((charSequence.equals(newNombre))){
                                        mbtn_guardar_cambios.setEnabled(false);
                                    }else{
                                        mbtn_guardar_cambios.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if ((editable.equals(newNombre))){
                                        mbtn_guardar_cambios.setEnabled(false);
                                    }else{
                                        mbtn_guardar_cambios.setEnabled(true);
                                    }

                                }
                            });

                            final String newTelefono = u.getTelefono();
                            userTelefono.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    if((charSequence.equals(newTelefono))){
                                        mbtn_guardar_cambios.setEnabled(false);
                                    }else{
                                        mbtn_guardar_cambios.setEnabled(true);
                                    }
                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    if((editable.equals(newTelefono))){
                                        mbtn_guardar_cambios.setEnabled(false);
                                    }else{
                                        mbtn_guardar_cambios.setEnabled(true);
                                    }
                                }
                            });
                        }

                }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
       });
    }
}
