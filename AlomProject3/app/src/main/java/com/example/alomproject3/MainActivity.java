package com.example.alomproject3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<ViewPagerModelClass> models;
    Integer[] colors = null;
    ImageButton setting_btn;
    ImageButton menu_btn;
    Button OK_btn;
    ArgbEvaluator argbEvaluator =new ArgbEvaluator();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setting_btn = findViewById(R.id.setting_button);
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

        models = new ArrayList<>();
        models.add(new ViewPagerModelClass(R.drawable.sky, "Sky"));
        models.add(new ViewPagerModelClass(R.drawable.sky, "sky2"));

        adapter = new ViewPagerAdapter(models, this);

        viewPager=findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        viewPager.setPadding(130,0,130,0);

        Integer[] colors_temp = {getResources().getColor(R.color.transparent),
                getResources().getColor(R.color.transparent)};
        colors=colors_temp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position<(adapter.getCount()-1)&&position<(colors.length-1))
                {
                    viewPager.setBackgroundColor((Integer)argbEvaluator.evaluate(positionOffset,colors[position],colors[position+1]));
                }else
                {
                    //
                    viewPager.setBackgroundColor(colors[colors.length-1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
        Intent intent = new Intent(this, Main.class);
        startActivity(intent);
    }
}
*/
