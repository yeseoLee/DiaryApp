package com.example.alomproject3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends RecyclerView.Adapter<MainViewPagerAdapter.ViewItemRowHolder> {

    private ArrayList<MainViewPagerItem> itemsList;
    private Context mContext;

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
        if (mainViewPagerItem.getImage()==null){

        }
        else
            holder.Image.setImageURI(Uri.parse(mainViewPagerItem.getImage()));
        holder.Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=holder.Title.getText().toString();
                if (title.equals("나만의 앨범을 만들어보세요!")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
                    dialog.setTitle("앨범의 이름을 적어주세요!");
                    final EditText et=new EditText(mContext);
                    dialog.setView(et);
                    dialog.setPositiveButton("입력", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String t=et.getText().toString();
                            DBHelper helper = new DBHelper(mContext);
                            helper.insert(t);
                            dialog.dismiss();
                        }
                    });
                    dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
                else {// 앨범으로 이동
                    Intent menuIntent = new Intent(mContext, AlbumActivity.class);
                    Toast.makeText(mContext, "앨범으로 이동", Toast.LENGTH_SHORT).show();
                    menuIntent.putExtra("TAG", title);//title(카테고리 제목)값 넘겨주기
                    mContext.startActivity(menuIntent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class ViewItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView Title;
        protected ImageView Image;

        public ViewItemRowHolder(View view) {
            super(view);
            this.Title = (TextView) view.findViewById(R.id.title);
            this.Image = (ImageView) view.findViewById(R.id.image);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//나중에 연결하는 형식으로
                    Toast.makeText(v.getContext(), Title.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
