package com.example.facturaweb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLitedb extends SQLiteOpenHelper {
    private static final String db="facturas.db";
    private static final String createUsers="CREATE TABLE USERS(NOM TEXT NOT NULL,TP_DOC TEXT NOT NULL,NUM_DOC TEXT NOT NULL UNIQUE,SALDO REAL NOT NULL,USER TEXT NOT NULL,PASS TEXT NOT NULL)";
    private static final String createPagos="CREATE TABLE pagos(SERVICIO TEXT NOT NULL,NUNFAC TEXT NOT NULL,VALOR REAL NOT NULL,USER TEXT NOT NULL)";
    private static final int version_db=1;
    public SQLitedb(@Nullable Context context) {
        super(context, db,null, version_db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(createUsers);
    db.execSQL(createPagos);
    db.execSQL("INSERT INTO USERS VALUES ('Roberto Lagos','Cedula de ciudadania','1067590818',350000,'1067590818','12345678')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(createUsers);
        db.execSQL(createPagos);

    }

    //metodo que permite registrar a los nuevos usuarios
    public void registrarCuenta(String nom,String tp_doc,String num_doc,Float saldo,String pass){
        SQLiteDatabase db=getWritableDatabase();
        if(db !=null){
            db.execSQL("INSERT INTO USERS VALUES ('"+nom+"','"+tp_doc+"','"+num_doc+"','"+saldo+"','"+num_doc+"','"+pass+"')");
            db.close();
        }
    }

    //metodo que permite registrar los pagos de los servicios y actualizar el saldo de los usuarios
    public void registrarPagos(String user,Float saldo,String servicio,String factura, Float val){
        SQLiteDatabase db=getWritableDatabase();
        if(db !=null){
            db.execSQL("INSERT INTO PAGOS VALUES('"+servicio+"','"+factura+"','"+val+"','"+user+"')");//registrar el pago hecho por el usuario
            Float newSaldo=saldo-val;
            db.execSQL("UPDATE USERS SET SALDO='"+newSaldo+"' WHERE USER='"+user+"'");//actualizar el saldo del usuario
            db.close();
        }
    }
}
