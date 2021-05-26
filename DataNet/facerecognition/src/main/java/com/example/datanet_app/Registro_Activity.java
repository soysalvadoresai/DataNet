package com.example.datanet_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import android.util.Log;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.opencv.cultoftheunicorn.marvel.R;

import java.util.HashMap;




public class Registro_Activity extends AppCompatActivity {

    //VARIABLES DE LOS DATOS QUE VAMOS A REGISTRAR
    public String TAG = " ";
    private FirebaseAuth mAuth;
    public String email, password, nombre;
    public EditText txt_correo;
    public EditText txt_contrasena, txt_confirmar_contrasena;
    public EditText txt_nombre;
    public Button ButtonRegistrer;
    DatabaseReference mDataBase;
    private String name = "" ;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();

        txt_correo = (EditText) findViewById(R.id.editTextEmail);
        txt_contrasena = (EditText) findViewById(R.id.editTextPassword);
        txt_confirmar_contrasena = (EditText) findViewById(R.id.confirmar_contrasena_singUp);
        ButtonRegistrer = (Button) findViewById(R.id.ButtonRegistrer);

        ButtonRegistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Registrarse();
            }
        });



    }// Fin OnCreate



    private void Registrarse() {
        email = txt_correo.getText().toString();
        password = txt_contrasena.getText().toString();
        String confimPassword = txt_confirmar_contrasena.getText().toString();

        if (email.isEmpty()){
            txt_correo.setError("Correo necesario / Email requeried");
            return;
        }
        if (password.isEmpty()){
            txt_contrasena.setError("Contraseña necesaria / Password requeried");
            return;
        }
        if (confimPassword.isEmpty()){
            txt_correo.setError("Required");
            return;
        }
        else{
            Toast.makeText(Registro_Activity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
        }


        if(password.equals(confimPassword)){
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,  new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Registro_Activity.this, "Error al autenticar su cuenta",
                                        Toast.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }else{
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }


    }



    private void updateUI(FirebaseUser currentUser) {
        Intent i = new Intent(this, Confirmo_Registro.class);
        startActivity(i);
    }// Fin updateUI




}
