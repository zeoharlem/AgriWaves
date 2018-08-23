package com.example.theophilus.agriwaves.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Theophilus on 8/14/2018.
 */

public class Post implements Parcelable{
    private String postId;
    private String postDesc;
    private String dateCreated;
    private String category;
    private String postTitle;
    private String status;
    private String imageUrl;
    private String videoId;
    private String eTag;

    public Post() {

    }

    public Post(Parcel parcel) {
        this.postId         = parcel.readString();
        this.postDesc       = parcel.readString();
        this.dateCreated    = parcel.readString();
        this.category       = parcel.readString();
        this.postTitle      = parcel.readString();
        this.status         = parcel.readString();
        this.imageUrl       = parcel.readString();
        this.videoId        = parcel.readString();
        this.eTag           = parcel.readString();
    }

    //Initiate the parcelable Object Class
    public static final Creator CREATOR = new Creator() {
        @Override
        public Post createFromParcel(Parcel parcel) {
            return new Post(parcel);
        }

        @Override
        public Post[] newArray(int i) {
            return new Post[i];
        }
    };

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostDesc() {
        return postDesc;
    }

    public void setPostDesc(String postDesc) {
        this.postDesc = postDesc;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String geteTag() {
        return eTag;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.postId);
        parcel.writeString(this.postDesc);
        parcel.writeString(this.dateCreated);
        parcel.writeString(this.category);
        parcel.writeString(this.postTitle);
        parcel.writeString(this.status);
        parcel.writeString(this.imageUrl);
        parcel.writeString(this.videoId);
        parcel.writeString(this.eTag);
    }
}
