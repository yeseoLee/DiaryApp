package com.example.alomproject3;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class SectionDataAdapter extends RecyclerView.Adapter<SectionDataAdapter.ItemRowHolder> implements ItemTouchHelperListener {

    private ArrayList<SectionItem> dataList;
    private Context mContext;
    Button alarm;

    public SectionDataAdapter(Context context, ArrayList<SectionItem> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        alarm=v.findViewById(R.id.Alarm);
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        final String sectionName = dataList.get(i).getHeaderTitle();
        ArrayList singleSectionItems = dataList.get(i).getSingItemList();
        itemRowHolder.itemTitle.setText(sectionName);
        SingleDataAdapter itemListDataAdapter = new SingleDataAdapter(mContext, singleSectionItems);
        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);
        itemRowHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(itemRowHolder.getAdapterPosition());
            }
        });

    }

    public void remove(int position){
        try{
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

    public void filderList(ArrayList<SectionItem> filteredList){
        dataList=filteredList;
        notifyDataSetChanged();
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        SectionItem number = dataList.get(from_position);
        dataList.remove(from_position);
        dataList.add(to_position , number);

        notifyItemMoved(from_position,to_position);
        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);

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

