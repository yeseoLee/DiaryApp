package com.example.alomproject3;

import java.util.ArrayList;

public class BookCaseItem {

    private String headerTitle;
    private ArrayList<BookItem> bookItem;


    public BookCaseItem() {
    }

    public BookCaseItem(String headerTitle, ArrayList<BookItem> bookItem) {
        this.headerTitle = headerTitle;
        this.bookItem = bookItem;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public ArrayList<BookItem> getSingItemList() {
        return bookItem;
    }

    public void setSingItemList(ArrayList<BookItem> bookItem) {
        this.bookItem = bookItem;
    }

}

