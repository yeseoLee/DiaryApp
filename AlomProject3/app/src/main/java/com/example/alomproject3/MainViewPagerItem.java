package com.example.alomproject3;

public class MainViewPagerItem {

    private  String image;
    private String title;

    public MainViewPagerItem(){

    }
    public MainViewPagerItem(String title){
        this.title=title;
    }
    public MainViewPagerItem(String image, String title)
    {
        this.image = image;
        this.title = title;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}