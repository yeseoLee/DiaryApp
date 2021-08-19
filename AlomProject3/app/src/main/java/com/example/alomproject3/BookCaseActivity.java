package com.example.alomproject3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class BookCaseActivity extends AppCompatActivity {
    ArrayList<BookCaseItem> sectionDataList;
    BookCaseAdapter adapter;
    private EditText Search;
    ArrayList<BookCaseItem> searchDatalist;
    ImageButton home;
    ItemTouchHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookcase);
        sectionDataList = new ArrayList<BookCaseItem>();
        searchDatalist = new ArrayList<BookCaseItem>();
        createDummyData();

        home=(ImageButton) findViewById(R.id.Home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("message","홈 엑티비티로 이동");
                startActivity(intent);
            }
        });

        Search=(EditText)findViewById(R.id.search);

        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        adapter = new BookCaseAdapter(this, sectionDataList);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);

        helper=new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(my_recycler_view);

        Search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = Search.getText().toString();
                search(text);
            }
        });
    } //end of onCreate()

    public void search(String text) {
        searchDatalist.clear();
        if (text.length()==0){
            searchDatalist.addAll(sectionDataList);
        }
        else {
            for(int i=0;i<sectionDataList.size();i++){
                if (sectionDataList.get(i).getHeaderTitle().toLowerCase().contains(text)){
                    searchDatalist.add(sectionDataList.get(i));
                }
            }
        }
        adapter.filderList(searchDatalist);
    }

    public void createDummyData() {
        DBHelper helper = new DBHelper(this);
        Set<String> data=helper.getData();
        for(String tag:data){
            BookCaseItem category = new BookCaseItem();
            category.setHeaderTitle(tag);
            ArrayList<BookItem> book = displayList(tag);
            category.setSingItemList(book);
            sectionDataList.add(category);
        }
    } //end of crerateDummyData()

    ArrayList<BookItem> displayList(String tag){
        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        ArrayList<BookItem> sky = new ArrayList<BookItem>();

        //Cursor에 목록을 담아주기
        //Cursor cursor = database.rawQuery("SELECT * FROM album",null);
        String[] arguments = new String[]{String.valueOf(tag)};
        Cursor cursor = database.rawQuery("SELECT * FROM album WHERE tag = ?",arguments);


        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            BookItem book=new BookItem();
            book.setName(cursor.getString(2));
            book.setUri(cursor.getString(3));
            sky.add(book);
        }

        return sky;

    }

} //end of MainActivity
