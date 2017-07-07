package com.example.artur_laptop.projektv3;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.artur_laptop.projektv3.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.artur_laptop.projektv3.R.layout.send;

/**
 * Created by Artur-laptop on 2017-07-06.
 */

public class Send extends AppCompatActivity{

    private ZarzadcaBazy zb;
    private TextView tv;
    private ListView ls;

    private String s = "cos nie idzie";
    public static final String coordy = "Response";
    public static final String coodry_tylko = "Response_Only";
    public static final String phone_number = "Response_number";
    private String s_tylko;

    private ListView lv;
    private String[] lang;
    private ArrayList<String> phrases;

    private String phone = ".";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(send);
        phone = ".";
        zb = new ZarzadcaBazy(this);

        lv = (ListView) findViewById(R.id.my_list);
        phrases = zb.getAllNames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,phrases);
        lv.setAdapter(arrayAdapter);

        initLanguagesListView();

        zwroc_wszystko();

        EditText editLocation = null;
        editLocation = (EditText) findViewById(R.id.editT);



        Button dalej = (Button) findViewById(R.id.dalej);
        dalej.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Context context;
                context = getApplicationContext();
                Intent intent = new Intent(context,WyslijBezWyboru.class);
                intent.putExtra(coordy,s);
                intent.putExtra(coodry_tylko,s_tylko);
                startActivity(intent);

            }
        });

        Intent intent = getIntent();
        s = intent.getStringExtra("Response");
        s_tylko = intent.getStringExtra("Response_Only");
       editLocation.setText(s);

    }


    private void wyswietl()
    {
        Cursor k = zb.dajWszystkie();
        while(k.moveToNext()){
            int nr=k.getInt(0);
            String telefon=k.getString(1);
            String nazwa=k.getString(2);
            tv.setText(tv.getText()+"\n"+nazwa+" "+telefon);
        }
    }

    public void zwroc_wszystko()
    {
        ArrayList<String>Listt = new ArrayList<>();
        String nazwa = "";
        Cursor k = zb.dajWszystkie();
        if(k.moveToPosition(0)){
            nazwa=k.getString(2);
            Listt.add(nazwa);
        }
        while(k.moveToNext()){
            nazwa=k.getString(2);
            Listt.add(nazwa);
        }

    }

    private void initLanguagesListView(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
                phone = zb.zwroc_nr(phrases.get(pos));
                Intent intent = new Intent(Send.this, WyslijZWyborem.class);
                intent.putExtra(coordy,s);
                intent.putExtra(coodry_tylko,s_tylko);
                intent.putExtra(phone_number,phone);
                startActivity(intent);
            }
        });

    }




}
