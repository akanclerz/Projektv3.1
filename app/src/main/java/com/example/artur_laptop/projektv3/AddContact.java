package com.example.artur_laptop.projektv3;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.artur_laptop.projektv3.R.layout.addcontact;

/**
 * Created by Artur-laptop on 2017-07-06.
 */

public class AddContact extends AppCompatActivity {

    private ZarzadcaBazy zb;
    private EditText phone;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(addcontact);

        zb = new ZarzadcaBazy(this);
        phone = (EditText) findViewById(R.id.editText);
        name = (EditText) findViewById(R.id.editText2);

        Button add = (Button) findViewById(R.id.add);
        Button remove = (Button) findViewById(R.id.remove);

        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dodaj();
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                usun();
            }
        });

    }


    private void dodaj()
    {
        Cursor k = zb.dajWszystkie();
        boolean exist = false;
        String s1;
        String s2;
        while(k.moveToNext()){
            s1 = k.getString(1).toString();
            s2 = phone.getText().toString();
            if(s1.equals(s2)){
                exist = true;
            }
        }
        if(exist == false) {
            zb.dodajKontakt(phone.getText().toString(), name.getText().toString());
            Toast.makeText(getApplicationContext(),
                    "Dodano numer", Toast.LENGTH_SHORT).show();
            //wyswietl();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "Kontakt o podanym numerze już istnieje", Toast.LENGTH_SHORT).show();
        }
    }

    private void usun()
    {
        zb.wyczysc_baze();
        Toast.makeText(getApplicationContext(),
                "Usunięto WSZYSTKIE kontakty.", Toast.LENGTH_SHORT).show();
    }

}
