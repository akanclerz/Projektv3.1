package com.example.artur_laptop.projektv3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.artur_laptop.projektv3.R.layout.wyslijzwyborem;

/**
 * Created by Artur-laptop on 2017-07-07.
 */

public class WyslijZWyborem extends AppCompatActivity{

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    private EditText editLocation;
    private String s = ">>>";
    private String telefon;
    private String wiadomosc;
    EditText txtphoneNo;
    EditText txtMessage;
    private String s_tylko;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(wyslijzwyborem);
        editLocation = (EditText) findViewById(R.id.editInZwyborem);
        Intent intent = getIntent();
        txtMessage = (EditText) findViewById(R.id.editText5);

        s = intent.getStringExtra("Response");
        s_tylko = intent.getStringExtra("Response_Only");
        telefon = intent.getStringExtra("Response_number");
        editLocation.setText(s);

        if(s_tylko.equals("Nieznana lokacja lub nie uzyto GPS!!!")){
            Toast.makeText(getApplicationContext(),
                    "Nie użyto GPS. Wróć do ekranu początkowego i naciśnij GET LOCATION", Toast.LENGTH_LONG).show();
        }



        Button wyslij = (Button) findViewById(R.id.wyslijZ);

        wyslij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage()
    {

        wiadomosc = "Za chwile otrzymasz wspolrzedne wlasciciela tego numeru."+ "\n" + "Wiadomosc dolaczona do wspolrzednych:" + "\n" + txtMessage.getText().toString();

        if (true) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
                Toast.makeText(getApplicationContext(),
                        "cos jest nie tak jak powinno", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else{
            Toast.makeText(getApplicationContext(),
                    "cos jest nie tak jak powinno", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telefon, null, wiadomosc, null, null);
                    smsManager.sendTextMessage(telefon, null, s_tylko, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}
