package com.example.wdc.bean.news;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wdc on 2016/7/22.
 */
public class NewsListBean implements Parcelable {

    private String date;

    public List<NewsBean> getStories() {
        return stories;
    }

    public void setStories(List<NewsBean> stories) {
        this.stories = stories;
    }

    public List<TopNewsBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopNewsBean> top_stories) {
        this.top_stories = top_stories;
    }

    private List<NewsBean> stories;

    private List<TopNewsBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public NewsListBean() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeTypedList(this.stories);
        dest.writeList(this.top_stories);
    }

    protected NewsListBean(Parcel in) {
        this.date = in.readString();
        this.stories = in.createTypedArrayList(NewsBean.CREATOR);
        this.top_stories = new ArrayList<TopNewsBean>();
        in.readList(this.top_stories, TopNewsBean.class.getClassLoader());
    }

    public static final Creator<NewsListBean> CREATOR = new Creator<NewsListBean>() {
        @Override
        public NewsListBean createFromParcel(Parcel source) {
            return new NewsListBean(source);
        }

        @Override
        public NewsListBean[] newArray(int size) {
            return new NewsListBean[size];
        }
    };
}
