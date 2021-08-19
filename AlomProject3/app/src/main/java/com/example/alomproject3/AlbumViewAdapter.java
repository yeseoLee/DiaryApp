package com.example.alomproject3;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlbumViewAdapter extends ArrayAdapter<PhotoItem> {
    private static final int LAYOUT_RESOURCE_ID = R.layout.item_album;

    private Context mContext;
    private List<PhotoItem> mItemList;

    public AlbumViewAdapter(Context a_context, List<PhotoItem> a_itemList) {
        super(a_context, LAYOUT_RESOURCE_ID, a_itemList);

        mContext = a_context;
        mItemList = a_itemList;
    }
    @Override
    public int getCount() {
        return mItemList.size(); //배열의 크기를 반환
    }
    @Override
    public long getItemId(int position) {
        PhotoItem photo = mItemList.get(position);
        return photo.getNum();
    }

    @Override
    public View getView(int a_position, View a_convertView, ViewGroup a_parent) {
        AlbumGridItemViewHolder viewHolder;
        if (a_convertView == null) {
            a_convertView = LayoutInflater.from(mContext).inflate(LAYOUT_RESOURCE_ID, a_parent, false);

            viewHolder = new AlbumGridItemViewHolder(a_convertView);
            a_convertView.setTag(viewHolder);
        } else {
            viewHolder = (AlbumGridItemViewHolder) a_convertView.getTag();
        }

        final PhotoItem countryItem = mItemList.get(a_position);

        // Photo 설정
        viewHolder.ivPhoto.setImageURI(Uri.parse(countryItem.getUri()));
        // Date 설정
        viewHolder.tvDate.setText(countryItem.getDate());
        return a_convertView;
    }
}