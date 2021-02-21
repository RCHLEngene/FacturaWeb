package com.example.facturaweb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;

public class pagos extends AppCompatActivity {
    private Spinner spSer;
    private Button btnPagar,btnRegresar;
    private EditText etFactura,etValor,etValorCon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagos);

        spSer=(Spinner)findViewById(R.id.spSer);
        btnPagar=(Button)findViewById(R.id.btnPagar);
        btnRegresar=(Button)findViewById(R.id.btnRegresar);
        etFactura=(EditText)findViewById(R.id.etFactura);
        etValor=(EditText)findViewById(R.id.etValor);
        etValorCon=(EditText)findViewById(R.id.etValorCon);

        String [] servicios={"Ninguno","CENS", "AGUAS KAPITAL", "VEOLIA"};
        //objeto para pasar las opciones al spinner
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,servicios);
        spSer.setAdapter(adapter);
        SQLitedb db=new SQLitedb(getApplicationContext());
        Bundle bl=getIntent().getExtras();
        //funcion onClick del boton pagar factura
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = spSer.getSelectedItem().toString();
                if(etFactura.getText().toString().equals("") || etValor.getText().toString().equals("") || etValorCon.getText().toString().equals("") || op.equals("Ninguno")){
                    Snackbar mySnackbar=Snackbar.make(findViewById(R.id.container), "Todos los campos requeridos!", Snackbar.LENGTH_LONG);
                    mySnackbar.setAction("Ocultar", new View.OnClickListener() { @Override public void onClick(View v) { mySnackbar.dismiss(); } });
                    mySnackbar.show();
                }else{
                    if(etFactura.getText().toString().length()<10){
                        Snackbar mySnackbar=Snackbar.make(findViewById(R.id.container), "El campo de factura es invalido. \ndebe escribir 10 dígitos!", Snackbar.LENGTH_LONG);
                        mySnackbar.setAction("Ocultar", new View.OnClickListener() { @Override public void onClick(View v) { mySnackbar.dismiss(); } });
                        mySnackbar.show();
                    }else {
                        float val1 = Float.parseFloat(etValor.getText().toString());
                        float val2 = Float.parseFloat(etValorCon.getText().toString());
                        if (val1==val2) {
                            if(val1>Integer.parseInt(bl.getString("saldo"))){
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container), "SALDO INSUFICIENTE...!", Snackbar.LENGTH_LONG);
                                mySnackbar.setAction("Ocultar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mySnackbar.dismiss();
                                    }
                                });
                                mySnackbar.show();

                            }else{
                                float saldo=Float.parseFloat(bl.getString("saldo"));
                                db.registrarPagos(bl.getString("user"),saldo, op, etFactura.getText().toString(),val1);
                                spSer.setSelection(0);
                                etFactura.setText("");
                                etValor.setText("");
                                etValorCon.setText("");
                                Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container), "El pago fue realizado\ncon éxito!", Snackbar.LENGTH_LONG);
                                mySnackbar.setAction("Ocultar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mySnackbar.dismiss();
                                    }
                                });
                                mySnackbar.show();
                            }
                        } else {
                            Snackbar mySnackbar = Snackbar.make(findViewById(R.id.container), "Los varoles no coinciden...!", Snackbar.LENGTH_LONG);
                            mySnackbar.setAction("Ocultar", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mySnackbar.dismiss();
                                }
                            });
                            mySnackbar.show();

                        }
                    }
                }
               // db.registrarPagos(,);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(p);
            }
        });
    }
}