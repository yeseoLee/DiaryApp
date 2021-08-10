package com.example.alomproject3;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.content.Context.MODE_PRIVATE;

public class DBHelper extends SQLiteOpenHelper {

    final static String DB_NAME = "data_album.db";  //DB이름
    final static int DB_VERSION = 1; //DB버전



    //생성자
    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //테이블의 구조는 여기서 설계
        String qry = "CREATE TABLE album(num INTEGER PRIMARY KEY AUTOINCREMENT,tag VARCHAR(40) NOT NULL, date TEXT, uri TEXT NOT NULL)";
        sqLiteDatabase.execSQL(qry);

        

        //dummy data
        qry = "INSERT INTO album(tag,uri,date) VALUES('오늘의 하늘','file','7/26')";
        sqLiteDatabase.execSQL(qry);
    }


    // Table 조회
    public Set<String> getData() { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();// DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Set<String> data=new HashSet<>(); //set으로 할지 list로 할지 여쭤보기
        Cursor cursor = db.rawQuery("SELECT * FROM album", null);
        while (cursor.moveToNext()) {
            data.add(cursor.getString(1));
        }
        return data;
    }

    // Table 데이터 삭제
    public void Delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM album WHERE tag = '" + name + "'");
        db.close();
    }



    //버전 업데이트 될때마다 호출 되는데 마지막에 onCreate도 같이 실행되기 때문에 여기서 먼저 DB에 존재하는 테이블들을 지워줘야함.
    //한마디로 초기화역할
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry = "DROP TABLE IF EXISTS album";
        sqLiteDatabase.execSQL(qry);
        onCreate(sqLiteDatabase);

    }
}