package com.example.duoihinhbatchu;

import android.content.Context;

public class Question {
    private String Content;
    private String GiaiNghia;
    private int Ok;
    public Question(){
    }

    public Question(String content, String giaiNghia, int ok) {
        Content = content;
        GiaiNghia = giaiNghia;
        Ok = ok;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getGiaiNghia() {
        return GiaiNghia;
    }

    public void setGiaiNghia(String giaiNghia) {
        GiaiNghia = giaiNghia;
    }

    public int getOk() {
        return Ok;
    }

    public void setOk(int ok) {
        Ok = ok;
    }
}
