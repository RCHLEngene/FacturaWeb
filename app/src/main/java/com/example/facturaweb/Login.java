package com.example.facturaweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class Login extends AppCompatActivity {
    //declarar las variables a utilizar
    private EditText etUserLogin,etPass;
    private Button btnLogin,btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //instanciafr los elementos del activity actual
        etUserLogin=(EditText)findViewById(R.id.etUserLogin);
        etPass=(EditText)findViewById(R.id.etPass);

        btnLogin=(Button)findViewById(R.id.btnLogin);
        btnRegister=(Button)findViewById(R.id.btnRegister);

        //llamar a la propiedad OnClick del boton Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //instanciar la conexion a la basededatos
                SQLitedb admin=new SQLitedb(getApplicationContext());
                //llamar al metodo que permite llamar al lector de las tablas en SQLite
                SQLiteDatabase db = admin.getWritableDatabase();
                //declarar el metodo cursor, metodo que almacena el resultado de la consulta sql
                Cursor c=db.rawQuery("SELECT nom,user FROM users WHERE user='"+etUserLogin.getText().toString()+"' and pass='"+etPass.getText().toString()+"'",null);

                if(c.moveToFirst()) {
                   String nom=c.getString(0);
                   String user=c.getString(1);
                    //invocar a una nueva ventana llamada recarga
                    Intent i=new Intent(getApplicationContext(),MainActivity.class);
                    //pasar a la nueva ventana el nombre del usuario
                    i.putExtra("nom",String.valueOf(nom));
                    //pasar a la nueva ventana el user del usuario
                    i.putExtra("user",String.valueOf(user));
                    i.putExtra("pass",etPass.getText().toString());
                    //lanzar la nueva ventana
                    startActivity(i);
                    etPass.setText("");
                    etPass.setText("");
                }else{
                    Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container1), "Credenciales invalidados...!", Snackbar.LENGTH_LONG);
                    mySnackbar.setAction("Ocultar", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mySnackbar.dismiss();
                        }
                    });
                    mySnackbar.show();

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m=new Intent(getApplicationContext(),Register.class);
                startActivity(m);
            }
        });
    }
}