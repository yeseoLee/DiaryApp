package com.example.alomproject3;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookCaseAdapter extends RecyclerView.Adapter<BookCaseAdapter.ItemRowHolder> implements ItemTouchHelperListener {

    private ArrayList<BookCaseItem> dataList;
    private Context mContext;

    public BookCaseAdapter(Context context, ArrayList<BookCaseItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bookcase, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        final String sectionName = dataList.get(i).getHeaderTitle();
        ArrayList singleSectionItems = dataList.get(i).getSingItemList();
        itemRowHolder.itemTitle.setText(sectionName);
        BookAdapter itemListDataAdapter = new BookAdapter(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //remove(itemRowHolder,itemRowHolder.getAdapterPosition());
                AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
                dialog.setTitle("앨범을 영구적으로 삭제하시겠습니까?");
                dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remove(itemRowHolder,itemRowHolder.getAdapterPosition());
                    }
                });
                dialog.setCancelable(false).show();
            }
        });
        itemRowHolder.itemTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent menuIntent = new Intent(mContext, AlbumActivity.class);
                Toast.makeText(mContext, "앨범으로 이동", Toast.LENGTH_SHORT).show();
                String title=itemRowHolder.itemTitle.getText().toString();//title 받아와서 String으로 변환
                menuIntent.putExtra("TAG", title);//title(카테고리 제목)값 넘겨주기
                mContext.startActivity(menuIntent);
            }
        });
    }

    public void remove(ItemRowHolder itemRowHolder,int position){
        try{
            DBHelper helper = new DBHelper(mContext);
            String title=itemRowHolder.itemTitle.getText().toString();//title 받아와서 String으로 변환
            helper.Delete(title);
            dataList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public void filderList(ArrayList<BookCaseItem> filteredList){
        dataList=filteredList;
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        BookCaseItem number = dataList.get(from_position);
        dataList.remove(from_position);
        dataList.add(to_position , number);

        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {

    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected ImageButton delete;

        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
            this.delete= (ImageButton) view.findViewById(R.id.delete);
        }
    }
}

