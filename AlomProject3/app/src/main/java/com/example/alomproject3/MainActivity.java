package com.example.alomproject3;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button btnGoAlbum;
    Button btnGoBookcase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoAlbum = (Button)findViewById(R.id.btn_go_album);
        btnGoBookcase = (Button)findViewById(R.id.btn_go_bookcase);
        btnGoAlbum.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeToAlbumActivity();
            }
        });
        btnGoBookcase.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeToBookcaseActivity();
            }
        });
    }
    void ChangeToAlbumActivity(){
        Intent intent = new Intent(this, AlbumActivity.class);
        startActivity(intent);
    }
    void ChangeToBookcaseActivity(){
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}

