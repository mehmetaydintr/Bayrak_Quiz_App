package com.info.bayrakquiz.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.info.bayrakquiz.Database.DatabaseCopyHelper;
import com.info.bayrakquiz.R;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button buttonBasla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonBasla = findViewById(R.id.buttonBasla);

        veritabaniKopyala();

        buttonBasla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OyunActivity.class));
            }
        });
    }

    private void veritabaniKopyala(){

        DatabaseCopyHelper helper = new DatabaseCopyHelper(this);
        try {
            helper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        helper.openDataBase();

    }
}