package com.example.artur_laptop.projektv3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.artur_laptop.projektv3.R.layout.usun_kontakt;

/**
 * Created by Artur-laptop on 2017-07-07.
 */

public class Usun_Kontakt extends AppCompatActivity {

    private ZarzadcaBazy zb;
    private ListView lv;
    private ArrayList<String> phrases;
    private String kontakt;
    private int id;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(usun_kontakt);
        id = -1;

        zb = new ZarzadcaBazy(this);

        lv = (ListView) findViewById(R.id.my_list1);
        phrases = zb.getAllContact();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,phrases);
        lv.setAdapter(arrayAdapter);
        initLanguagesListView();

    }

    private void Reinicjalize()
    {
        phrases = zb.getAllContact();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,phrases);
        lv.setAdapter(arrayAdapter);
    }

    private void initLanguagesListView(){
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View v, int pos, long id){
                id = zb.zwroc_ID(phrases.get(pos));
                zb.DeleteByID((int) id);
                Reinicjalize();

                /*
                Intent intent = new Intent(Send.this, WyslijZWyborem.class);
                intent.putExtra(coordy,s);
                intent.putExtra(coodry_tylko,s_tylko);
                intent.putExtra(phone_number,phone);
                startActivity(intent);
                */


            }
        });

    }


}
