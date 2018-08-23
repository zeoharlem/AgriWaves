package com.example.theophilus.agriwaves.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Theophilus on 8/21/2018.
 */

public class Blog implements Parcelable{
    private String postId;
    private String postAuthor;
    private String postDate;
    private String postContent;
    private String postTitle;
    private String postExcerpt;
    private String postStatus;
    private String fullImage;
    private String midImage;
    private String category;

    public Blog() {

    }

    public Blog(Parcel parcel){
        this.postId         = parcel.readString();
        this.postAuthor     = parcel.readString();
        this.postDate       = parcel.readString();
        this.postContent    = parcel.readString();
        this.postTitle      = parcel.readString();
        this.postExcerpt    = parcel.readString();
        this.postStatus     = parcel.readString();
        this.fullImage      = parcel.readString();
        this.midImage       = parcel.readString();
        this.category       = parcel.readString();
    }

    //Initiate the parcelable Object Class
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Blog createFromParcel(Parcel parcel) {
            return new Blog(parcel);
        }

        @Override
        public Blog[] newArray(int i) {
            return new Blog[i];
        }
    };

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(String postAuthor) {
        this.postAuthor = postAuthor;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }

    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }

    public String getFullImage() {
        return fullImage;
    }

    public void setFullImage(String fullImage) {
        this.fullImage = fullImage;
    }

    public String getMidImage() {
        return midImage;
    }

    public void setMidImage(String midImage) {
        this.midImage = midImage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.postId);
        parcel.writeString(this.postAuthor);
        parcel.writeString(this.postDate);
        parcel.writeString(this.postContent);
        parcel.writeString(this.postTitle);
        parcel.writeString(this.postExcerpt);
        parcel.writeString(this.postStatus);
        parcel.writeString(this.fullImage);
        parcel.writeString(this.midImage);
        parcel.writeString(this.category);
    }
}
