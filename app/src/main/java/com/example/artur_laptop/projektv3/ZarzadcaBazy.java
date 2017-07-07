package com.example.artur_laptop.projektv3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Artur-laptop on 2017-07-06.
 */

public class ZarzadcaBazy extends SQLiteOpenHelper {

    public ZarzadcaBazy(Context context) {

        super(context, "kontakty.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table telefony(" +
                        "nr integer primary key autoincrement," +
                        "nazwa text," +
                        "telefon text);" +
                        "");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void dodajKontakt(String nazwa, String telefon){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("nazwa", nazwa);
        wartosci.put("telefon", telefon);
        db.insertOrThrow("telefony",null, wartosci);
    }

    public Cursor dajWszystkie(){
        String[] kolumny={"nr","nazwa","telefon"};
        SQLiteDatabase db = getReadableDatabase();
        Cursor kursor =db.query("telefony",kolumny,null,null,null,null,null);
        return kursor;
    }

    public ArrayList<String> getValuesFromTable()
    {
        ArrayList<String> nazwy = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor k = db.rawQuery("SELECT * FROM " + "telefony",null);

        k.moveToFirst();

        while(k.moveToNext()){
            nazwy.add(k.getString(k.getColumnIndex("nazwa")));
        }
        return nazwy;
    }

    public ArrayList<String> getAllNames()
    {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAllNames = "SELECT * FROM " + "telefony";
        Cursor c = dajWszystkie();
        if(c.moveToFirst()){
            names.add(c.getString(2));
        }

        while(c.moveToNext())
        {
            names.add(c.getString(2));
        }
        return names;
    }


    public ArrayList<String> getAllContact()
    {
        ArrayList<String> names = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectAllNames = "SELECT * FROM " + "telefony";
        String temp;
        Cursor c = dajWszystkie();
        if(c.moveToFirst()){
            temp = c.getString(2) + " " + c.getString(1);
            names.add(temp);
        }

        while(c.moveToNext())
        {
            temp = c.getString(2) + " " + c.getString(1);
            names.add(temp);
        }
        return names;
    }


    public String zwroc_nr(String nazwa)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String phone = "";
        String name = "";
        Cursor c = dajWszystkie();
        if(c.moveToFirst()){
            name = c.getString(2);
            if(name.equals(nazwa)){
                phone = c.getString(1);
                return phone;
            }
        }

        while(c.moveToNext()){
            name = c.getString(2);
            if(name.equals(nazwa)){
                phone = c.getString(1);
                return phone;
            }
        }
        return "Nie udalo sie";
    }

    public int zwroc_ID(String nazwa)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        int ID;
        String name = "";
        Cursor c = dajWszystkie();
        if(c.moveToFirst()){
            name = c.getString(2) + " " + c.getString(1);
            if(name.equals(nazwa)){
                ID = c.getInt(0);
                return ID;
            }
        }

        while(c.moveToNext()){
            name = c.getString(2) + " " + c.getString(1);
            if(name.equals(nazwa)){
                ID = c.getInt(0);
                return ID;
            }
        }
        return -1;
    }

    public void wyczysc_baze()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = dajWszystkie();
        db.execSQL("delete from "+ "telefony");
    }

    public boolean DeleteByID(int ID_to_delete)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("telefony","nr" + " = ?", new String[] {String.valueOf(ID_to_delete)});
        return true;
    }
}
