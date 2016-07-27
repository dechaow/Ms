package com.example.wdc.bean.images;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wdc on 2016/7/26.
 */
public class ImagesOwnerBean implements Parcelable {
        private String userName;
        private String userId;
        private String userSign;
        private String isSelf;
        private String portrait;
        private String isVip;
        private String isLanv;
        private String isJiaju;
        private String isHunjia;
        private String orgName;
        private String resUrl;
        private String cert;
        private String budgetNum;
        private String lanvName;
        private String contactName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserSign() {
            return userSign;
        }

        public void setUserSign(String userSign) {
            this.userSign = userSign;
        }

        public String getIsSelf() {
            return isSelf;
        }

        public void setIsSelf(String isSelf) {
            this.isSelf = isSelf;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public String getIsVip() {
            return isVip;
        }

        public void setIsVip(String isVip) {
            this.isVip = isVip;
        }

        public String getIsLanv() {
            return isLanv;
        }

        public void setIsLanv(String isLanv) {
            this.isLanv = isLanv;
        }

        public String getIsJiaju() {
            return isJiaju;
        }

        public void setIsJiaju(String isJiaju) {
            this.isJiaju = isJiaju;
        }

        public String getIsHunjia() {
            return isHunjia;
        }

        public void setIsHunjia(String isHunjia) {
            this.isHunjia = isHunjia;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getResUrl() {
            return resUrl;
        }

        public void setResUrl(String resUrl) {
            this.resUrl = resUrl;
        }

        public String getCert() {
            return cert;
        }

        public void setCert(String cert) {
            this.cert = cert;
        }

        public String getBudgetNum() {
            return budgetNum;
        }

        public void setBudgetNum(String budgetNum) {
            this.budgetNum = budgetNum;
        }

        public String getLanvName() {
            return lanvName;
        }

        public void setLanvName(String lanvName) {
            this.lanvName = lanvName;
        }

        public String getContactName() {
            return contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.userId);
        dest.writeString(this.userSign);
        dest.writeString(this.isSelf);
        dest.writeString(this.portrait);
        dest.writeString(this.isVip);
        dest.writeString(this.isLanv);
        dest.writeString(this.isJiaju);
        dest.writeString(this.isHunjia);
        dest.writeString(this.orgName);
        dest.writeString(this.resUrl);
        dest.writeString(this.cert);
        dest.writeString(this.budgetNum);
        dest.writeString(this.lanvName);
        dest.writeString(this.contactName);
    }

    public ImagesOwnerBean() {
    }

    protected ImagesOwnerBean(Parcel in) {
        this.userName = in.readString();
        this.userId = in.readString();
        this.userSign = in.readString();
        this.isSelf = in.readString();
        this.portrait = in.readString();
        this.isVip = in.readString();
        this.isLanv = in.readString();
        this.isJiaju = in.readString();
        this.isHunjia = in.readString();
        this.orgName = in.readString();
        this.resUrl = in.readString();
        this.cert = in.readString();
        this.budgetNum = in.readString();
        this.lanvName = in.readString();
        this.contactName = in.readString();
    }

    public static final Parcelable.Creator<ImagesOwnerBean> CREATOR = new Parcelable.Creator<ImagesOwnerBean>() {
        @Override
        public ImagesOwnerBean createFromParcel(Parcel source) {
            return new ImagesOwnerBean(source);
        }

        @Override
        public ImagesOwnerBean[] newArray(int size) {
            return new ImagesOwnerBean[size];
        }
    };
}
