package com.example.alomproject3;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;

public class MainViewPagerAdapter extends RecyclerView.Adapter<MainViewPagerAdapter.ViewItemRowHolder> {

    private ArrayList<MainViewPagerItem> itemsList;
    private Context mContext;
    private ClickCallbackListener callbackListener;

    //main activitiy에서 전달 받은 콜백메서드를 set하는 메서드
    public void setCallbackListener(ClickCallbackListener callbackListener){
        this.callbackListener=callbackListener;
    }

    public MainViewPagerAdapter(Context context, ArrayList<MainViewPagerItem> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public ViewItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mainviewpager, null);
        ViewItemRowHolder mh = new ViewItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ViewItemRowHolder holder, int i) {
        MainViewPagerItem mainViewPagerItem = itemsList.get(i);
        holder.Title.setText(mainViewPagerItem.getTitle());
        setCallbackListener(callbackListener);
        if (mainViewPagerItem.getImage()==null){

        }
        else
            holder.Image.setImageURI(Uri.parse(mainViewPagerItem.getImage()));
        holder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=holder.Title.getText().toString();
                if (title.equals("나만의 앨범을 만들어보세요!")){
                    callbackListener.callBack(0);
                }
                else {// 앨범으로 이동
                    Intent menuIntent = new Intent(mContext, AlbumActivity.class);
                    Toast.makeText(mContext, "앨범으로 이동", Toast.LENGTH_SHORT).show();
                    menuIntent.putExtra("TAG", title);//title(카테고리 제목)값 넘겨주기
                    mContext.startActivity(menuIntent);
                }
            }
        });
        holder.bind(callbackListener);
    }
    
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class ViewItemRowHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected TextView Title;
        protected ImageView Image;
        private ClickCallbackListener callbackListener;

        public ViewItemRowHolder(View view) {
            super(view);
            this.Title = view.findViewById(R.id.title);
            this.Image = view.findViewById(R.id.image);
            view.setOnClickListener(this);
            //부모 뷰 그룹에 클릭 리스터 등록

        }
        public void bind(ClickCallbackListener clickCallbackListener){
            this.callbackListener=clickCallbackListener;
            //전달 받은 callbackListener를 해당 뷰 홀더의 멤버로 가짐
        }
        @Override
        public void onClick(View v) {
            int id=getAdapterPosition();
            setCallbackListener(callbackListener);
            if (id==0) {
                callbackListener.callBack(0);
            }
        }
    }
}
