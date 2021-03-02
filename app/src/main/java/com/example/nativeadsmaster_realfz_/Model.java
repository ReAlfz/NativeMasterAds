package com.example.nativeadsmaster_realfz_;

public class Model {
    private String title, url, desk;

    public Model(String titles, String urls, String desk) {
        this.title = titles;
        this.url = urls;
        this.desk = desk;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}
