package com.example.alomproject3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import gun0912.tedbottompicker.TedBottomPicker;

public class AlbumActivity extends AppCompatActivity {

    GridView gvList;
    Button btnHome;
    Button btnInsert;
    Button btnDeleteAll;
    AlbumViewAdapter adapter;
    TextView textname;

    //권한 설정
    final static int PERMISSION_REQUEST_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        //Activity 시작시 권한 확인
        permissionCheck();

        //Intent tag 처리
        Intent secondIntent = getIntent();
        String tag = secondIntent.getStringExtra("TAG");
        textname=(TextView)findViewById(R.id.textname);
        textname.setText(tag);

        gvList = (GridView)findViewById(R.id.gridView);
        btnHome = (Button)findViewById(R.id.btn_home);
        btnInsert = (Button)findViewById(R.id.btn_insert);
        btnDeleteAll = (Button)findViewById(R.id.btn_delete_all);
        adapter = new AlbumViewAdapter();

        displayList(tag);

        gvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Index : "+ adapter.getItemId(i),Toast.LENGTH_LONG).show();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("message","홈 엑티비티로 이동");
                startActivity(intent);
            }
        });
        btnInsert.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                //이미지 URI 가져오기
                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(AlbumActivity.this)
                        .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                            @Override
                            public void onImageSelected(Uri uri) {
                                insertPhoto(uri,tag);
                            }
                        })
                        .create();

                bottomSheetDialogFragment.show(getSupportFragmentManager());
            }
        });
        btnDeleteAll.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteAlbum("album",tag);
            }
        });

    }
    //권한 체크 함수
    private void permissionCheck() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
            ArrayList<String> arrayPermission = new ArrayList<String>();

            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                arrayPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                arrayPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (arrayPermission.size() > 0) {
                String strArray[] = new String[arrayPermission.size()];
                strArray = arrayPermission.toArray(strArray);
                ActivityCompat.requestPermissions(this, strArray, PERMISSION_REQUEST_CODE);
            } else {
                // Initialize 코드
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE: {
                if (grantResults.length < 1) {
                    Toast.makeText(this, "Failed get permission", Toast.LENGTH_SHORT).show();
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                    return ;
                }

                for (int i=0; i<grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission is denied : " + permissions[i], Toast.LENGTH_SHORT).show();
                        finish();
                        return ;
                    }
                }

                Toast.makeText(this, "Permission is granted", Toast.LENGTH_SHORT).show();
                // Initialize 코드
            }
            break;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void displayList(String tag){
        //Dbhelper의 읽기모드 객체를 가져와 SQLiteDatabase에 담아 사용준비
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        //Cursor에 목록을 담아주기
        //Cursor cursor = database.rawQuery("SELECT * FROM album",null);
        String[] arguments = new String[]{String.valueOf(tag)};
        Cursor cursor = database.rawQuery("SELECT * FROM album WHERE tag = ?",arguments);

        //리스트뷰에 목록 채워주는 도구인 adapter준비
        AlbumViewAdapter adapter = new AlbumViewAdapter();

        //목록의 개수만큼 순회하여 adapter에 있는 list배열에 add
        while(cursor.moveToNext()){
            adapter.addItemToList(cursor.getInt(1),cursor.getString(2),cursor.getString(3));
        }

        //리스트뷰의 어댑터 대상을 여태 설계한 adapter로 설정
        gvList.setAdapter(adapter);

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

        displayList(tag); //리스트뷰 새로고침

    }

    void deleteAlbum(String tablename,String tag){
        DBHelper helper = new DBHelper(this);
        SQLiteDatabase database = helper.getReadableDatabase();

        String qry = "DELETE FROM '"+tablename+"' WHERE tag ="+tag;
        database.execSQL(qry);

        displayList(tag); //리스트뷰 새로고침
    }

}