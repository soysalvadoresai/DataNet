package com.example.datanet_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.opencv.cultoftheunicorn.marvel.R;

import java.util.HashMap;

public class SubirFoto_Activity extends AppCompatActivity {
    public Button mUploadBtn;
    private ImageView foto;
    private ProgressDialog progressDialog;
    public ImageView avanzar;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    StorageReference storageReference;


    private static final int GALLERY_INTENT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto_);

        inicializarFirebase();
        foto = (ImageView) findViewById(R.id.img_foto);
        progressDialog = new ProgressDialog(this);
        avanzar = (ImageView) findViewById(R.id.subir);
        avanzar.setVisibility(View.INVISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference();

        mUploadBtn = (Button) findViewById(R.id.btn_seleccionar_imagen);

        mUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

    }//Fin del OnCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
            final Uri uri = data.getData();

            foto.setImageURI(uri);
            mUploadBtn.setText("Cambiar foto");
            avanzar.setVisibility(View.VISIBLE);

            avanzar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    progressDialog.setTitle("Subiendo imagen");
                    progressDialog.setMessage("Imagen subida a la nube");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    StorageReference filePath = storageReference.child("fotos").child(uri.getLastPathSegment());

                    filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            final StorageReference ref = storageReference.child("fotos").child(uri.getLastPathSegment());
                            UploadTask uploadTask = ref.putFile(uri);//<-----------------

                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                    if(!task.isSuccessful()){
                                        throw task.getException();
                                    }

                                    //Continua con la tarea de descargar la URL
                                    return ref.getDownloadUrl();
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if(task.isSuccessful()){
                                        Uri downloadUri = task.getResult();
                                        String downloadURL = downloadUri.toString();

                                        Glide.with(SubirFoto_Activity.this)
                                                .load(downloadURL)
                                                .fitCenter()
                                                .centerCrop()
                                                .into(foto);

                                            String uid = user.getUid();

                                        HashMap hashMap = new HashMap();
                                        hashMap.put("imagen",downloadURL);
                                        databaseReference.child("Usuario").child(uid).updateChildren(hashMap);

                                        progressDialog.dismiss();
                                        Toast.makeText(SubirFoto_Activity.this, "Imagen cargada exitosamente", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(SubirFoto_Activity.this, Datos.class );
                                        startActivity(i);
                                    }else{
                                        //Fallos
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    private void inicializarFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
}