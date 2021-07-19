package com.example.alomproject3;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class Main extends AppCompatActivity {
    ArrayList<BookCaseItem> sectionDataList;
    BookCaseAdapter adapter;
    private EditText Search;
    ArrayList<BookCaseItem> searchDatalist;
    ImageButton home;
    ItemTouchHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookcase);
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
        BookCaseItem todaysky = new BookCaseItem();
        todaysky.setHeaderTitle("오늘의 하늘");
        ArrayList<BookItem> sky = new ArrayList<BookItem>();
        sky.add(new BookItem("1일차"));
        sky.add(new BookItem("2일차"));
        sky.add(new BookItem("3일차"));
        sky.add(new BookItem("4일차"));
        sky.add(new BookItem("5일차"));
        todaysky.setSingItemList(sky);
        sectionDataList.add(todaysky);

        BookCaseItem rosemary = new BookCaseItem();
        rosemary.setHeaderTitle("로즈마리 키우기");
        ArrayList<BookItem> rose = new ArrayList<BookItem>();
        rose.add(new BookItem("1일차"));
        rose.add(new BookItem("2일차"));
        rose.add(new BookItem("3일차"));
        rose.add(new BookItem("4일차"));
        rose.add(new BookItem("5일차"));
        rosemary.setSingItemList(rose);
        sectionDataList.add(rosemary);

        BookCaseItem todaymeal = new BookCaseItem();
        todaymeal.setHeaderTitle("점심메뉴");
        ArrayList<BookItem> meal = new ArrayList<BookItem>();
        meal.add(new BookItem("1일차"));
        meal.add(new BookItem("2일차"));
        meal.add(new BookItem("3일차"));
        meal.add(new BookItem("4일차"));
        meal.add(new BookItem("5일차"));
        todaymeal.setSingItemList(meal);
        sectionDataList.add(todaymeal);

        BookCaseItem mycat = new BookCaseItem();
        mycat.setHeaderTitle("우리 고양이");
        ArrayList<BookItem> cat = new ArrayList<BookItem>();
        cat.add(new BookItem("1일차"));
        cat.add(new BookItem("2일차"));
        cat.add(new BookItem("3일차"));
        cat.add(new BookItem("4일차"));
        cat.add(new BookItem("5일차"));
        mycat.setSingItemList(cat);
        sectionDataList.add(mycat);
    } //end of crerateDummyData()

} //end of MainActivity
