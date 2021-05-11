package com.info.bayrakquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;

public class OyunActivity extends AppCompatActivity {

    private ImageView imageViewBayrak;
    private TextView textViewDogru, textViewYanlis, textViewSoru;
    private Button buttonCevap1, buttonCevap2, buttonCevap3, buttonCevap4;

    private ArrayList<Bayraklar> sorularListe;
    private ArrayList<Bayraklar> yanlisSeceneklerListe;
    private Bayraklar dogruSoru;
    private VeriTabani veriTabani;

    private int soruSayac = 0, dogruSayac = 0, yanlisSayac = 0;

    private HashSet<Bayraklar> secenekleriKaristirmaListe = new HashSet<>();
    private ArrayList<Bayraklar> secenekler = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oyun);

        imageViewBayrak = findViewById(R.id.imageViewBayrak);
        textViewDogru = findViewById(R.id.textViewDogru);
        textViewYanlis = findViewById(R.id.textViewYanlis);
        textViewSoru = findViewById(R.id.textViewSoru);
        buttonCevap1 = findViewById(R.id.buttonCevap1);
        buttonCevap2 = findViewById(R.id.buttonCevap2);
        buttonCevap3 = findViewById(R.id.buttonCevap3);
        buttonCevap4 = findViewById(R.id.buttonCevap4);

        veriTabani = new VeriTabani(this);
        sorularListe = new BayraklarDao().rastgele5Getir(veriTabani);
        soruyuYukle();

        buttonCevap1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonCevap1);
                sayacKontrol();
            }
        });

        buttonCevap2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonCevap2);
                sayacKontrol();
            }
        });

        buttonCevap3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonCevap3);
                sayacKontrol();
            }
        });

        buttonCevap4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dogruKontrol(buttonCevap4);
                sayacKontrol();
            }
        });
    }

    public void soruyuYukle(){
        textViewSoru.setText((soruSayac+1)+". SORU");

        dogruSoru = sorularListe.get(soruSayac);
        yanlisSeceneklerListe = new BayraklarDao().rastgele3YanlisSecenekGetir(veriTabani, dogruSoru.getBayrak_id());

        imageViewBayrak.setImageResource(getResources().getIdentifier(dogruSoru.getBayrak_resim(),"drawable",getPackageName()));

        secenekleriKaristirmaListe.clear();
        secenekleriKaristirmaListe.add(dogruSoru);
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(0));
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(1));
        secenekleriKaristirmaListe.add(yanlisSeceneklerListe.get(2));

        secenekler.clear();
        for(Bayraklar b:secenekleriKaristirmaListe){
            secenekler.add(b);
        }

        buttonCevap1.setText(secenekler.get(0).getBayrak_ad());
        buttonCevap2.setText(secenekler.get(1).getBayrak_ad());
        buttonCevap3.setText(secenekler.get(2).getBayrak_ad());
        buttonCevap4.setText(secenekler.get(3).getBayrak_ad());
    }

    public void dogruKontrol(Button button){
        String buttonYazi = button.getText().toString();
        String dogruCevap = dogruSoru.getBayrak_ad();

        if (buttonYazi.equalsIgnoreCase(dogruCevap)){
            dogruSayac++;
        }else {
            yanlisSayac++;
            Snackbar.make(button, "Doğru cevap : " + dogruCevap, Snackbar.LENGTH_LONG).show();
        }

        textViewDogru.setText("Doğru : " + dogruSayac);
        textViewYanlis.setText("Yanlış : " + yanlisSayac);
    }

    public void sayacKontrol(){
        soruSayac++;

        if (soruSayac != 5){
            soruyuYukle();
        }else {

            Intent intent = new Intent(OyunActivity.this, SonucActivity.class);
            intent.putExtra("dogruSayac",dogruSayac);

            startActivity(intent);
            finish();
        }
    }
}