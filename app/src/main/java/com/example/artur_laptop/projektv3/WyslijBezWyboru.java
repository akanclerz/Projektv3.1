package com.example.artur_laptop.projektv3;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.artur_laptop.projektv3.R.layout.wyslijbezwyboru;

/**
 * Created by Artur-laptop on 2017-07-06.
 */

public class WyslijBezWyboru extends AppCompatActivity {

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
        setContentView(wyslijbezwyboru);
        editLocation = (EditText) findViewById(R.id.editInBezWyboru);
        Intent intent = getIntent();
        txtphoneNo = (EditText) findViewById(R.id.editText3);
        txtMessage = (EditText) findViewById(R.id.editText4);

        s = intent.getStringExtra("Response");
        s_tylko = intent.getStringExtra("Response_Only");
        if(s_tylko.equals("Nieznana lokacja lub nie uzyto GPS!!!")){
            Toast.makeText(getApplicationContext(),
                    "Nie użyto GPS. Wróć do ekranu początkowego i naciśnij GET LOCATION", Toast.LENGTH_LONG).show();
        }
        editLocation.setText(s);
        Button wyslij = (Button) findViewById(R.id.wyslij);

        wyslij.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage()
    {
            telefon = txtphoneNo.getText().toString();
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
