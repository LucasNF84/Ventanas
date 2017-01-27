package com.example.administrador.ventanas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, Validator.ValidationListener {

    Boolean validado;

    Validator validator;

    Button btn_Aceptar;
    Button btn_Cancelar;

    @NotEmpty(message = "Debe completar el campo.")
    EditText et_Nombre;

    @NotEmpty(message = "Debe completar el campo.")
    EditText et_Apellido;

    @Email(message= "Debe ingresar un correo valido.")
    EditText et_Email;

    @Password(min = 6, scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,message = "Clave invalida")
    EditText et_Clave;

    @ConfirmPassword(message = "Clave no coincide")
    EditText et_Clave2;

    @Checked(message = "Debe confirmar los términos y condiciones.")
    CheckBox cb_aceptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        validado = false;
        validator = new Validator(this);
        validator.setValidationListener(this);

        btn_Aceptar = (Button)findViewById(R.id.btnAceptar);
        btn_Cancelar = (Button)findViewById(R.id.btnCancelar);
        et_Nombre = (EditText)findViewById(R.id.etNombre);
        et_Apellido = (EditText)findViewById(R.id.etApellido);
        et_Email = (EditText)findViewById(R.id.etEmail);
        et_Clave = (EditText)findViewById(R.id.etClave);
        et_Clave2 = (EditText)findViewById(R.id.etClave2);
        cb_aceptar = (CheckBox)findViewById(R.id.cbAcepta);

        btn_Aceptar.setOnClickListener(this);
        btn_Cancelar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
        case R.id.btnAceptar:

            validator.validate();

            if (validado)
            {
            //Creamos el intent
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);

            //Creamos la informacion para pasar entre actividades
            Bundle b  = new Bundle();

            b.putString("NOMBRE",et_Nombre.getText().toString());
            b.putString("APELLIDO",et_Apellido.getText().toString());
            b.putString("EMAIL",et_Email.getText().toString());
            b.putString("CLAVE",et_Clave.getText().toString());

            //Añadimos la informacion al intent
            intent.putExtras(b);

            //Iniciamos la nueva Actividad
            startActivity(intent);
            }
            break;
        case R.id.btnCancelar:
            Toast.makeText(this,"Canceló el alta de usuario",Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onValidationSucceeded() {
        validado = true;
        Toast.makeText(this, "Datos ingresados correctamente", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }


}





















