package com.example.alomproject3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    MainViewPagerAdapter adapter;
    ArrayList<MainViewPagerItem> models;
    Integer[] colors = null;
    ImageButton setting_btn;
    ImageButton menu_btn;
    Button OK_btn;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();

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

        //메뉴버튼
        menu_btn = findViewById(R.id.menuButton);
        menu_btn.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(MainActivity.this, "항목 전체보기 클릭", Toast.LENGTH_SHORT).show();
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

        /*
        OK_btn = findViewById(R.id.add_button);
        OK_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 버튼 클릭시 팝업 메뉴가 나오게 하기

                PopupMenu popupMenu = new PopupMenu(
                        getApplicationContext(),
                        v);
                getMenuInflater().inflate(R.menu.category, popupMenu.getMenu());
                // 이벤트 처리
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.action_menu1) {
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

         */

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
}

/*
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
        Intent intent = new Intent(this, BookCaseActivity.class);
        startActivity(intent);
    }
}
*/
