package com.example.administrador.ventanas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainPrincipalActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_Registro;
    Button btn_Json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);

        btn_Registro = (Button)findViewById(R.id.btnRegistro);
        btn_Json = (Button)findViewById(R.id.btnJson);

        btn_Registro.setOnClickListener(this);
        btn_Json.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistro:
                //Creamos el intent
                Intent intent1 = new Intent(MainPrincipalActivity.this, MainActivity.class);
                //Iniciamos la nueva Actividad
                startActivity(intent1);
                break;
            case R.id.btnJson:
                //Creamos el intent
                Intent intent2 = new Intent(MainPrincipalActivity.this, ConsumirRest1Activity.class);
                //Iniciamos la nueva Actividad
                startActivity(intent2);
                break;
        }

    }
}
