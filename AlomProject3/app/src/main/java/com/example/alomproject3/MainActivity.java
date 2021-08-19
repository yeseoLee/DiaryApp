package com.example.alomproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ArgbEvaluator;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import gun0912.tedbottompicker.TedBottomPicker;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    MainViewPagerAdapter adapter;
    ArrayList<MainViewPagerItem> models;
    Integer[] colors = null;
    ImageButton setting_btn;
    Button OK_btn;
    Spinner spinner;
    //권한 설정
    final static int PERMISSION_REQUEST_CODE = 1000;

    public Context setContext(){
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)findViewById(R.id.view_pager);
        createData();


        setting_btn = findViewById(R.id.setting_button);
        //설정버튼
        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭시 팝업 메뉴가 나오게 하기

                PopupMenu popupMenu = new PopupMenu(
                        getApplicationContext(), // 현재 화면의 제어권자
                        v); // anchor : 팝업을 띄울 기준될 위젯
                getMenuInflater().inflate(R.menu.setting_menu, popupMenu.getMenu());
                // 이벤트 처리
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu1) {//임시로 토스트 메시지로 띄워서 이벤트 실행확인
                            Toast.makeText(MainActivity.this, "메뉴 1 클릭", Toast.LENGTH_SHORT).show();
                        } else if (menuItem.getItemId() == R.id.action_menu2) {
                            Toast.makeText(MainActivity.this, "메뉴 2 클릭", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "메뉴 3 클릭", Toast.LENGTH_SHORT).show();
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }

        });

        //메뉴버튼-> 동적 스피너로 바꿈
        spinner=(Spinner)findViewById(R.id.menuSpinner);
        DBHelper helper = new DBHelper(this);
        Set<String> data=helper.getData();//데이터베이스에서 카테고리 이름 가져오기
        List<String> spinnerArray=new ArrayList<>(data);
        spinnerArray.add(0,"앨범 전체보기");
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(//arrayAdapter에 리스트 값 넣기
                this, android.R.layout.simple_spinner_dropdown_item,spinnerArray);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tag=spinner.getItemAtPosition(position).toString();
                if (tag.equals("앨범 전체보기")){
                    Intent menuIntent = new Intent(MainActivity.this, BookCaseActivity.class);
                    Toast.makeText(MainActivity.this, "앨범 전체보기 클릭", Toast.LENGTH_SHORT).show();
                    startActivity(menuIntent);
                }
                else {
                    Intent menuIntent = new Intent(MainActivity.this, AlbumActivity.class);
                    Toast.makeText(MainActivity.this, tag+"로 이동", Toast.LENGTH_SHORT).show();
                    menuIntent.putExtra("TAG", tag);//title(카테고리 제목)값 넘겨주기
                    startActivity(menuIntent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭시 팝업 메뉴가 나오게 하기

                PopupMenu popupMenu = new PopupMenu(
                        getApplicationContext(), // 현재 화면의 제어권자
                        v);
                getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());
                // 이벤트 처리
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        if (menuItem.getItemId() == R.id.go_bookcase) {
                            Intent menuIntent = new Intent(MainActivity.this, BookCaseActivity.class);
                            Toast.makeText(MainActivity.this, "앨범 전체보기 클릭", Toast.LENGTH_SHORT).show();
                            startActivity(menuIntent);
                        } else if (menuItem.getItemId() == R.id.action_menu1) {
                            Intent menuIntent = new Intent(MainActivity.this, AlbumActivity.class);
                            Toast.makeText(MainActivity.this, "메뉴 1 클릭", Toast.LENGTH_SHORT).show();
                            String t = menuItem.getTitle().toString();//title 받아와서 String으로 변환
                            menuIntent.putExtra("TAG", t);//title(카테고리 제목)값 넘겨주기
                            startActivity(menuIntent);
                        } else if (menuItem.getItemId() == R.id.action_menu2) {
                            Intent menuIntent = new Intent(MainActivity.this, AlbumActivity.class);
                            Toast.makeText(MainActivity.this, "메뉴 2 클릭", Toast.LENGTH_SHORT).show();
                            String t = menuItem.getTitle().toString();
                            System.out.println(t);
                            menuIntent.putExtra("TAG", t);
                            startActivity(menuIntent);
                        } else {
                            Intent menuIntent = new Intent(MainActivity.this, AlbumActivity.class);
                            Toast.makeText(MainActivity.this, "메뉴 3 클릭", Toast.LENGTH_SHORT).show();
                            String t = menuItem.getTitle().toString();
                            menuIntent.putExtra("TAG", t);
                            startActivity(menuIntent);
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
                });
*/

        //OK버튼
        OK_btn = findViewById(R.id.add_button);
        OK_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper helper = new DBHelper(setContext());
                List<String> a=new ArrayList<>(helper.getData());
                CharSequence[] album=new CharSequence[a.size()];
                for(int i=0;i<a.size();i++){
                    album[i]=a.get(i);
                }
                final Set<String> data=helper.getData();
                AlertDialog.Builder dialog=new AlertDialog.Builder(setContext());
                dialog.setTitle("사진을 추가 할 앨범을 선택해주세요!")
                        .setItems(album, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String tag=album[which].toString();
                                //이미지 URI 가져오기
                                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(setContext())
                                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                                            @Override
                                            public void onImageSelected(Uri uri) {
                                                insertPhoto(uri,tag);
                                            }
                                        })
                                        .create();

                                bottomSheetDialogFragment.show(getSupportFragmentManager());
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void createData() {//초기화 후 데이터 다시 넣기 -> 앨범 생성 할 때마다 ?
        DBHelper helper = new DBHelper(this);
        Set<String> data=helper.getData();
        models=new ArrayList<MainViewPagerItem>();//초기화
        models.add(new MainViewPagerItem("나만의 앨범을 만들어보세요!"));
        for(String tag:data){
            MainViewPagerItem viewpager = displayView(tag);
            viewpager.setTitle(tag);
            models.add(viewpager);
        }
        adapter = new MainViewPagerAdapter(this, models);

    } //end of crerateDummyData()
    MainViewPagerItem displayView(String tag){//썸네일 가져오기
        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        MainViewPagerItem preview = new MainViewPagerItem();

        //Cursor에 목록을 담아주기
        //Cursor cursor = database.rawQuery("SELECT * FROM album",null);
        String[] arguments = new String[]{String.valueOf(tag)};
        Cursor cursor = database.rawQuery("SELECT * FROM album WHERE tag = ?",arguments);


        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        if(cursor.moveToNext()){//첫 사진을 썸네일로
            preview.setImage(cursor.getString(3));
        }

        return preview;
    }
    void insertPhoto(Uri uri,String tag){

        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getWritableDatabase();
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        String date = DateFormat.getDateInstance().format(new Date());
        values.put("tag",tag);
        values.put("date", date);
        values.put("uri", uri.toString());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = database.insert("album", null, values);

        // String qry = "INSERT INTO album(date,uri) VALUES('"+tag+"','"+date+"','"+uri+"')";
        // database.execSQL(qry);

    }
}

