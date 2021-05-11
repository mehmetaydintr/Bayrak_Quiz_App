package com.info.bayrakquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SonucActivity extends AppCompatActivity {

    private Button buttonTekrarDene;
    private TextView textViewDogruYanlis, textViewSonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        textViewDogruYanlis = findViewById(R.id.textViewDogruYanlis);
        textViewSonuc = findViewById(R.id.textViewSonuc);
        buttonTekrarDene = findViewById(R.id.buttonTekrarDene);

        int dogruSayac = getIntent().getIntExtra("dogruSayac",0);

        textViewDogruYanlis.setText("Doğru : " + dogruSayac + "\nYanlış : " +(5-dogruSayac));
        textViewSonuc.setText("%" + (dogruSayac*100)/5 + "BAŞARI");

        buttonTekrarDene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SonucActivity.this, OyunActivity.class));
                finish();
            }
        });
    }
}