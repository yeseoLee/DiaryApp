package com.example.alomproject3;

import android.content.ContentValues;
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

        //휴지통
        qry = "CREATE TABLE trash(num INTEGER PRIMARY KEY AUTOINCREMENT,tag VARCHAR(40) NOT NULL, date TEXT, uri TEXT NOT NULL)";
        sqLiteDatabase.execSQL(qry);

        //dummy data
        qry = "INSERT INTO album(tag,uri,date) VALUES('오늘의 하늘','file','7/26')";
        sqLiteDatabase.execSQL(qry);
    }
    //album Table 데이터 입력
    public void insert(String t) {
        SQLiteDatabase db = getWritableDatabase();
        String qry="INSERT INTO album(tag,uri,date) VALUES('"+t+"','file','7/26')";

        db.execSQL(qry);
        db.close();
    }
    public void InsertAlbum(long uid){
        SQLiteDatabase db=getReadableDatabase();
        String tag="",url="",date="";
        Cursor cursor=db.rawQuery("SELECT * FROM trash WHERE num="+uid,null);
        if (cursor.moveToNext()){
            tag=cursor.getString(1);
            url=cursor.getString(3);
            date=cursor.getString(2);
        }
        String qry="INSERT INTO album(tag,uri,date) VALUES('"+tag+"','"+url+"','"+date+"')";
        db.execSQL(qry);

        db.execSQL("DELETE FROM trash WHERE num="+uid);
        db.close();
    }

    public void InsertTrash(long uid){
        SQLiteDatabase db=getReadableDatabase();
        String tag="",url="",date="";
        Cursor cursor=db.rawQuery("SELECT * FROM album WHERE num="+uid,null);
        if (cursor.moveToNext()){
            tag=cursor.getString(1);
            url=cursor.getString(3);
            date=cursor.getString(2);
        }
        String qry="INSERT INTO trash(tag,uri,date) VALUES('"+tag+"','"+url+"','"+date+"')";
        db.execSQL(qry);

        db.execSQL("DELETE FROM album WHERE num="+uid);
        db.close();
    }

    // album Table 조회
    public Set<String> getData() { // 읽기가 가능하게 DB 열기
        SQLiteDatabase db = getReadableDatabase();// DB에 있는 데이터를 쉽게 처리하기 위해 Cursor를 사용하여 테이블에 있는 모든 데이터 출력
        Set<String> data=new HashSet<>(); //set으로 할지 list로 할지 여쭤보기
        Cursor cursor = db.rawQuery("SELECT * FROM album", null);
        while (cursor.moveToNext()) {
            data.add(cursor.getString(1));
        }
        return data;
    }

    // album Table 데이터 삭제
    public void Delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM album WHERE tag = '" + name + "'");
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String qry = "DROP TABLE IF EXISTS album";
        sqLiteDatabase.execSQL(qry);
        onCreate(sqLiteDatabase);

    }
}