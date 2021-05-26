package com.example.datanet_app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datanet_app.Models.Name;
import com.example.datanet_app.adapters.MensajeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.opencv.cultoftheunicorn.marvel.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HistorialActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private DatabaseReference mDatabase;
    private MensajeAdapter mAdapter;
    private ArrayList<Name> mName=new ArrayList<>();
    private FirebaseAuth mAuth;
    private TextView txtVer;
    private Button btnFilter;

    EditText input_minimal,
            input_maximal;
    Button btn_minimal,
            btn_maximal,
            cari;
    Calendar calendars = Calendar.getInstance();
    Calendar calendare = Calendar.getInstance();
    Locale id = new Locale("es", "ID");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMMM-YYYY", id);
    Date date_minimal;
    Date date_maximal;
    Context context;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        context = this;
        txtVer = (TextView) findViewById(R.id.txtMostrar);
        mAuth = FirebaseAuth.getInstance();
        recycler = (RecyclerView) findViewById(R.id.recycler1);
        mDatabase = FirebaseDatabase.getInstance().getReference();
       final String id = mAuth.getCurrentUser().getUid();

        cari = findViewById(R.id.cari);
        input_minimal = findViewById(R.id.input_minimal);
        input_maximal = findViewById(R.id.input_maximal);
        btn_minimal = findViewById(R.id.btn_minimal);
        btn_maximal = findViewById(R.id.btn_maximal);




        btnFilter = (Button) findViewById(R.id.btnFiltro);
        //Para volver a cargar la pagina
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent refresh = new Intent(HistorialActivity.this, HistorialActivity.class);
                startActivity(refresh);
            }
        });



        btn_minimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendars.set(year, month, dayOfMonth,00,00, 01);
                        input_minimal.setText(simpleDateFormat.format(calendars.getTime()));
                        date_minimal = calendars.getTime();

                        String input1 = input_minimal.getText().toString();
                        String input2 = input_maximal.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()){
                            cari.setEnabled(false);
                        }else {
                            cari.setEnabled(true);
                        }
                    }
                }, calendars.get(Calendar.YEAR), calendars.get(Calendar.MONTH), calendars.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        btn_maximal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendare.set(year, month, dayOfMonth, 23,59,59);
                        input_maximal.setText(simpleDateFormat.format(calendare.getTime()));
                        date_maximal = calendare.getTime();

                        String input1 = input_maximal.getText().toString();
                        String input2 = input_minimal.getText().toString();
                        if (input1.isEmpty() && input2.isEmpty()){
                            cari.setEnabled(false);
                        }else {
                            cari.setEnabled(true);
                        }
                    }
                }, calendare.get(Calendar.YEAR), calendare.get(Calendar.MONTH), calendare.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = mDatabase.child("attendence").orderByChild("date").startAt(calendars.getTimeInMillis()).endAt(calendare.getTimeInMillis());
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //showLisener(snapshot);
                        if(snapshot.exists()){

                            mName.clear();
                            for (DataSnapshot ds:snapshot.getChildren()
                            ) {
                                String name = ds.child("names").getValue().toString();
                                String date = ds.child("date").getValue().toString();

                                mName.add(new Name(name,Long.parseLong(date)));
                            }

                            mAdapter = new MensajeAdapter(mName, R.layout.mensaje_view);
                            recycler.setAdapter(mAdapter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        //Aqui nos muestra la informacion de la base de datos
        recycler.setLayoutManager(new LinearLayoutManager(this));

        mDatabase.child("attendence").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    mName.clear();
                    for (DataSnapshot ds:dataSnapshot.getChildren()
                         ) {
                        String name = ds.child("names").getValue().toString();
                        String date = ds.child("date").getValue().toString();

                        mName.add(new Name(name,Long.parseLong(date)));
                    }

                    mAdapter = new MensajeAdapter(mName, R.layout.mensaje_view);
                    recycler.setAdapter(mAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void showLisener(DataSnapshot dataSnapshot) {
        mName.clear();
        for (DataSnapshot ds:dataSnapshot.getChildren()) {
            Name user = ds.getValue(Name.class);
            mName.add(user);
        }
        mAdapter = new MensajeAdapter(mName, R.layout.mensaje_view);
        recycler.setAdapter(mAdapter);
    }

}