package com.adedom.library;

public class MyDataBean {

    private String textView;

    public MyDataBean() {
    }

    public MyDataBean(String title) {
        this.textView = title;
    }

    public String getTextView() {
        return textView;
    }

    public void setTextView(String textView) {
        this.textView = textView;
    }
}
