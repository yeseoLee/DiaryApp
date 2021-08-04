package com.example.alomproject3;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumGridItemViewHolder {
    public ImageView ivPhoto;

    public TextView tvDate;

    public AlbumGridItemViewHolder(View a_view) {
        ivPhoto = a_view.findViewById(R.id.item_iv_photo);
        tvDate = a_view.findViewById(R.id.item_tv_date);
    }
}
