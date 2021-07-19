package com.example.alomproject3;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumViewAdapter extends BaseAdapter {

    ArrayList<PhotoItem> list = new ArrayList<PhotoItem>();
    @Override
    public int getCount() {
        return list.size(); //배열의 크기를 반환
    }
    @Override
    public Object getItem(int i) {
        return list.get(i); //배열에 아이템을 현재 위치값을 넣어 가져옴
    }
    @Override
    public long getItemId(int i) { return i; }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_album,viewGroup,false);
        }

        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        TextView tvNum = (TextView)view.findViewById(R.id.item_tv_num);
        TextView tvDate = (TextView)view.findViewById(R.id.item_tv_date);
        ImageView ivPhoto = (ImageView)view.findViewById(R.id.item_iv_photo);

        //현재 포지션에 해당하는 아이템들에 적용하기 위해 list배열에서 객체를 가져온다.
        PhotoItem listdata = list.get(i);

        //가져온 객체안에 있는 데이터들을 각 뷰에 적용한다
        tvNum.setText(Integer.toString(listdata.getNum()));
        tvDate.setText(listdata.getDate());
        ivPhoto.setImageURI(Uri.parse(listdata.getUri())); //String 을 Uri로 변환

        return view;
    }

    //ArrayList로 선언된 list 변수에 목록을 채워주기 위함 다른방시으로 구현해도 됨
    public void addItemToList(int num, String date, String uri){
        PhotoItem listdata = new PhotoItem();

        listdata.setNum(num);
        listdata.setDate(date);
        listdata.setUri(uri);

        //값들의 조립이 완성된 listdata객체 한개를 list배열에 추가
        list.add(listdata);

    }
}
