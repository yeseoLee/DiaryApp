package com.example.alomproject3;

public class PhotoItem {

    public PhotoItem(int Num,String Date, String Uri) {
        num= Num;
        date = Date;
        uri = Uri;
    }
    private int num;
    private String date;
    private String uri;

    public void setNum(int num){this.num = num;}
    public void setDate(String date){this.date = date;}
    public void setUri(String uri){this.uri = uri;}

    public int getNum(){return this.num;}
    public String getDate(){return this.date;}
    public String getUri(){return this.uri;}
}