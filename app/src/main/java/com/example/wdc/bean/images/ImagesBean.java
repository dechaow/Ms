package com.example.wdc.bean.images;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImagesBean implements Parcelable {

    private String col;
    private String tag;
    private String tag3;
    private String sort;
    private int totalNum;
    private int startIndex;
    private int returnNumber;
    private List<ImagesListBean> imgs;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getReturnNumber() {
        return returnNumber;
    }

    public void setReturnNumber(int returnNumber) {
        this.returnNumber = returnNumber;
    }
    public List<ImagesListBean> getImgs() {
        return imgs;
    }

    public void setImgs(List<ImagesListBean> imgs) {
        this.imgs = imgs;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.col);
        dest.writeString(this.tag);
        dest.writeString(this.tag3);
        dest.writeString(this.sort);
        dest.writeInt(this.totalNum);
        dest.writeInt(this.startIndex);
        dest.writeInt(this.returnNumber);
        dest.writeTypedList(this.imgs);
    }

    public ImagesBean() {
    }

    protected ImagesBean(Parcel in) {
        this.col = in.readString();
        this.tag = in.readString();
        this.tag3 = in.readString();
        this.sort = in.readString();
        this.totalNum = in.readInt();
        this.startIndex = in.readInt();
        this.returnNumber = in.readInt();
        this.imgs = in.createTypedArrayList(ImagesListBean.CREATOR);
    }

    public static final Parcelable.Creator<ImagesBean> CREATOR = new Parcelable.Creator<ImagesBean>() {
        @Override
        public ImagesBean createFromParcel(Parcel source) {
            return new ImagesBean(source);
        }

        @Override
        public ImagesBean[] newArray(int size) {
            return new ImagesBean[size];
        }
    };
}
