package com.example.facturaweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Timer;
import java.util.TimerTask;

public class Register extends AppCompatActivity {
    //declarar las variables que se utilizaran
    private Spinner spDoc;
    private EditText etNom,etDoc,etPassW,etPassWCon,etSaldoN;
    private Button btnRegistrar,btnSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //instanciar los elementos del activity actual
        spDoc=(Spinner)findViewById(R.id.spDoc);
        String [] tipo_documento={"Ninguno","Cedula de ciudadania","Tarjeta de identidad","Cedula extranjera"};
        //objeto para pasar las opciones al spinner
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,tipo_documento);
        spDoc.setAdapter(adapter);

        etDoc=(EditText)findViewById(R.id.etDoc);
        etNom=(EditText)findViewById(R.id.etNom);
        etPassW=(EditText)findViewById(R.id.etPassW);
        etPassWCon=(EditText)findViewById(R.id.etPassWCon);
        etSaldoN=(EditText)findViewById(R.id.etSaldoN);

        btnRegistrar=(Button)findViewById(R.id.btnRegistrar);
        btnSalir=(Button)findViewById(R.id.btnSalir1);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperar la opcion del Spinner
                String tDoc=spDoc.getSelectedItem().toString();

                if(etNom.getText().toString().equals("") || tDoc.equals("Ninguno") || etDoc.getText().toString().equals("") || etPassW.getText().toString().equals("") || etPassWCon.getText().toString().equals("")){
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container2), "TODOS LOS DATOS SON REQUERIDOS!", Snackbar.LENGTH_LONG);
                    mySnackbar.setAction("Ocultar", new View.OnClickListener() { @Override public void onClick(View v) { mySnackbar.dismiss(); } });
                    mySnackbar.show();
                }else{
                    String p1=etPassW.getText().toString();
                    String p2=etPassWCon.getText().toString();
                    //validar que las contraseñas sean iguales y tengan 8caracteres como mínimo
                    if((p1.equals(p2)) && etPassW.getText().toString().length()>7 || etPassWCon.getText().toString().length()>7) {
                        //instanciar la base de datos
                        SQLitedb admin=new SQLitedb(getApplicationContext());
                        //convertir en decimal el saldo
                        float saldo=Float.parseFloat(etSaldoN.getText().toString());
                        //regstrar la cuenta en la base de datos
                        admin.registrarCuenta(etNom.getText().toString(),tDoc,etDoc.getText().toString(),saldo,etPassW.getText().toString());
                        //mostrar un mensaje exito
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container2), "La cuenta ha sido creada con éxito!", Snackbar.LENGTH_LONG);
                        mySnackbar.setAction("Ocultar", new View.OnClickListener() { @Override public void onClick(View v) { mySnackbar.dismiss(); } });
                        mySnackbar.show();
                        //esperar3 segundos para cerrar esta ventana
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent k= new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(k);
                                finish();
                            }
                        }, 3000);
                    }else{
                        //mostrar un mensaje de error
                        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container2), "Las contraseñas no coinciden o la longitud\n de la(s) contraseñas es invalido!!", Snackbar.LENGTH_LONG);
                        mySnackbar.setAction("Ocultar", new View.OnClickListener() { @Override public void onClick(View v) { mySnackbar.dismiss(); } });
                        mySnackbar.show();
                    }
                }
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}