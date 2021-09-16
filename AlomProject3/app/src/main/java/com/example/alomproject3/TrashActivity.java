package com.example.alomproject3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class TrashActivity extends AppCompatActivity {

    GridView gvList;
    ImageButton btnHome;
    TextView textname;


    public Context setContext(){
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trash);
        //xml과 연결
        gvList = findViewById(R.id.trashView);
        btnHome = findViewById(R.id.trashhome);
        textname = findViewById(R.id.trashtitle);

        //제목 정하기
        textname.setText("휴지통");

        //휴지통 전체 보여주기
        displayList();

        //메인화면으로 이동
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("message","메인화면으로 이동");
                startActivity(intent);
            }
        });
        //사진 삭제하기
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();
        gvList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long uid) {
                AlertDialog.Builder builder = new AlertDialog.Builder(setContext());
                builder.setMessage("영구삭제 하시겠습니까?\n원래 앨범으로 복구하시겠습니까?");

                builder.setNegativeButton("영구 삭제", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        database.execSQL("DELETE FROM trash WHERE num="+uid);
                        Toast.makeText(getApplicationContext(), uid+"번째 아이템이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                        displayList();
                        Toast.makeText(getApplicationContext(), "삭제됨", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setPositiveButton("복구", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        helper.InsertAlbum(uid);
                        displayList();
                        Toast.makeText(getApplicationContext(), "앨범으로 복구되었습니다!", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
                //remove
                //database.execSQL("DELETE FROM album WHERE num="+uid);
                //Toast.makeText(getApplicationContext(), uid+"번째 아이템이 삭제되었습니다", Toast.LENGTH_SHORT).show();
                //displayList(tag);
                return false;
            }
        });

    }

    private void displayList() {
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        //Cursor에 휴지통 사진 목록을 담아주기
        Cursor cursor = database.rawQuery("SELECT * FROM trash",null);

        ArrayList<PhotoItem> dbList = new ArrayList<PhotoItem>();

        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            dbList.add(new PhotoItem(cursor.getInt(0),cursor.getString(2),cursor.getString(3)));
        }
        AlbumViewAdapter adapter = new AlbumViewAdapter(this,dbList);
        //리스트뷰의 어댑터 대상을 여태 설계한 adapter로 설정
        gvList.setAdapter(adapter);
    }

}