package com.example.alomproject3;

public class BookItem {

    private String name;
    private String uri;

    public BookItem() { }
    public BookItem(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUri() { return uri; }
    public void setUri(String uri) { this.uri = uri; }
}

