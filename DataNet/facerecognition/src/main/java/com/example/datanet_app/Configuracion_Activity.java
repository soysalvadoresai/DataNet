package com.example.datanet_app;



import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.opencv.cultoftheunicorn.marvel.R;

import java.util.Locale;

public class Configuracion_Activity extends AppCompatActivity {
    Button buttonidioma;
    private Locale locale;
    private Configuration config = new Configuration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion_);

        buttonidioma = (Button) findViewById(R.id.idioma);

        buttonidioma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

    }

    private void showDialog(){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(getResources().getString(R.string.str_button));
        //Se obtienen los idiomas del documento string.xml
        String[] types = getResources().getStringArray(R.array.Languages);
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (which){
                    case  0:
                        locale  = new Locale("en");
                        config.locale = locale;
                        break;
                    case 1:
                        locale = new Locale("es");
                        config.locale = locale;
                        break;
                    case 2:
                        locale = new Locale("pt");
                        config.locale = locale;
                        break;
                }
                getResources().updateConfiguration(config, null);
                Intent refresh = new Intent(Configuracion_Activity.this, Login_Activity.class);
                startActivity(refresh);
                finish();
            }
        });
        b.show();
    }
}