package com.example.theophilus.agriwaves.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Theophilus on 8/26/2018.
 */

public class Category implements Parcelable{

    private String categoryId;
    private String categoryName;
    private String categoryDesc;
    private String categoryFirstLetter;
    private String categoryBodyLetter;

    public Category(){

    }

    protected Category(Parcel in) {
        this.categoryId     = in.readString();
        this.categoryName   = in.readString();
        this.categoryDesc   = in.readString();
        this.categoryFirstLetter    = in.readString();
        this.categoryBodyLetter     = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDesc() {
        return categoryDesc;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    public String getCategoryFirstLetter() {
        return categoryFirstLetter;
    }

    public void setCategoryFirstLetter(String categoryFirstLetter) {
        this.categoryFirstLetter = categoryFirstLetter;
    }

    public String getCategoryBodyLetter() {
        return categoryBodyLetter;
    }

    public void setCategoryBodyLetter(String categoryBodyLetter) {
        this.categoryBodyLetter = categoryBodyLetter;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.categoryId);
        parcel.writeString(this.categoryName);
        parcel.writeString(this.categoryDesc);
        parcel.writeString(this.categoryFirstLetter);
        parcel.writeString(this.categoryBodyLetter);
    }
}
