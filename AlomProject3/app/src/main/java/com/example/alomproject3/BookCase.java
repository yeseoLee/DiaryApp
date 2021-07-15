package com.example.alomproject3;

import android.content.Intent;
import android.media.Image;
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
import java.util.List;


public class BookCase extends AppCompatActivity {
    ArrayList<SectionItem> sectionDataList;
    SectionDataAdapter adapter;
    private EditText Search;
    ArrayList<SectionItem> searchDatalist;
    ImageButton home;
    ItemTouchHelper helper;
    Button alarm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookcase);
        sectionDataList = new ArrayList<SectionItem>();
        searchDatalist = new ArrayList<SectionItem>();
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
        adapter = new SectionDataAdapter(this, sectionDataList);
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
        SectionItem todaysky = new SectionItem();
        todaysky.setHeaderTitle("오늘의 하늘");
        ArrayList<SingleItem> sky = new ArrayList<SingleItem>();
        sky.add(new SingleItem("1일차", "설명"));
        sky.add(new SingleItem("2일차", "설명"));
        sky.add(new SingleItem("3일차", "설명"));
        sky.add(new SingleItem("4일차", "설명"));
        sky.add(new SingleItem("5일차", "설명"));
        todaysky.setSingItemList(sky);
        sectionDataList.add(todaysky);

        SectionItem rosemary = new SectionItem();
        rosemary.setHeaderTitle("로즈마리 키우기");
        ArrayList<SingleItem> rose = new ArrayList<SingleItem>();
        rose.add(new SingleItem("1일차", "설명"));
        rose.add(new SingleItem("2일차", "설명"));
        rose.add(new SingleItem("3일차", "설명"));
        rose.add(new SingleItem("4일차", "설명"));
        rose.add(new SingleItem("5일차", "설명"));
        rosemary.setSingItemList(rose);
        sectionDataList.add(rosemary);

        SectionItem todaymeal = new SectionItem();
        todaymeal.setHeaderTitle("점심메뉴");
        ArrayList<SingleItem> meal = new ArrayList<SingleItem>();
        meal.add(new SingleItem("1일차", "설명"));
        meal.add(new SingleItem("2일차", "설명"));
        meal.add(new SingleItem("3일차", "설명"));
        meal.add(new SingleItem("4일차", "설명"));
        meal.add(new SingleItem("5일차", "설명"));
        todaymeal.setSingItemList(meal);
        sectionDataList.add(todaymeal);

        SectionItem mycat = new SectionItem();
        mycat.setHeaderTitle("우리 고양이");
        ArrayList<SingleItem> cat = new ArrayList<SingleItem>();
        cat.add(new SingleItem("1일차", "설명"));
        cat.add(new SingleItem("2일차", "설명"));
        cat.add(new SingleItem("3일차", "설명"));
        cat.add(new SingleItem("4일차", "설명"));
        cat.add(new SingleItem("5일차", "설명"));
        mycat.setSingItemList(cat);
        sectionDataList.add(mycat);
    } //end of crerateDummyData()

} //end of MainActivity
