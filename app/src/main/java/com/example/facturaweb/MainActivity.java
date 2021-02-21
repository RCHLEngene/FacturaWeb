package com.example.facturaweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnFactura,btnSalir;
    private TextView etHora,etFecha,etUser,etSaldo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFecha=(TextView)findViewById(R.id.etFecha);
        etHora=(TextView) findViewById(R.id.etHora);
        etUser=(TextView) findViewById(R.id.etUser);
        etSaldo=(TextView) findViewById(R.id.etSaldo);
        btnFactura=(Button)findViewById(R.id.btnFactura);
        btnSalir=(Button)findViewById(R.id.bntSalir);

        Date date = new Date();
        // obtener la hora y salida por pantalla con formato:
        DateFormat hourFormat = new SimpleDateFormat("HH:mm");
        etHora.setText(hourFormat.format(date));
         //obtener la fecha y salida por pantalla con formato:
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        etFecha.setText(dateFormat.format(date));

        //llamar al metodo bundle para recibir las variables del login
        Bundle bl=getIntent().getExtras();

        etUser.setText(bl.getString("nom"));
        SQLitedb admin=new SQLitedb(getApplicationContext());
        SQLiteDatabase db = admin.getWritableDatabase();
        //declarar el metodo cursor, metodo que almacena el resultado de la consulta sql
        Cursor c=db.rawQuery("SELECT saldo FROM users WHERE user='"+bl.getString("user")+"' and pass='"+bl.getString("pass")+"'",null);

        if(c.moveToFirst()) {
            if(Integer.parseInt(c.getString(0))==0){
                etSaldo.setText("0");
            }else{
                etSaldo.setText(c.getString(0));
            }
        }else{
            etSaldo.setText("0");
        }
            btnFactura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //invocar a una nueva ventana llamada recarga
                Intent i=new Intent(getApplicationContext(),pagos.class);
                String user=bl.getString("user");
                //pasar a la nueva ventana el user del usuario
                i.putExtra("user",String.valueOf(user));
                //pasar a la nueva ventana el saldo del usuario
                i.putExtra("saldo",etSaldo.getText().toString());
                //lanzar la nueva ventana
                startActivity(i);
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