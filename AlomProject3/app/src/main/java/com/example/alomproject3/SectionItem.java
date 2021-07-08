package com.example.alomproject3;

import java.util.ArrayList;
public class SectionItem {

    private String headerTitle;
    private ArrayList<SingleItem> singItemList;


    public SectionItem() {
    }

    public SectionItem(String headerTitle, ArrayList<SingleItem> singItemList) {
        this.headerTitle = headerTitle;
        this.singItemList = singItemList;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<SingleItem> getSingItemList() {
        return singItemList;
    }

    public void setSingItemList(ArrayList<SingleItem> singItemList) {
        this.singItemList = singItemList;
    }

}

