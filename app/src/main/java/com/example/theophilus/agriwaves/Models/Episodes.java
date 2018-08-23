package com.example.theophilus.agriwaves.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Theophilus on 8/17/2018.
 */

public class Episodes implements Parcelable{
    private String seriesId;
    private String seriesTitle;
    private String seriesDesc;
    private String seriesImgs;
    private String dateCreated;
    private String seriesCatId;
    private String videoId;
    private String eTag;

    public Episodes() {

    }

    public Episodes(Parcel parcel){
        this.seriesId       = parcel.readString();
        this.seriesTitle    = parcel.readString();
        this.seriesDesc     = parcel.readString();
        this.seriesImgs     = parcel.readString();
        this.dateCreated    = parcel.readString();
        this.seriesCatId    = parcel.readString();
        this.videoId        = parcel.readString();
        this.eTag           = parcel.readString();
    }

    public static final Parcelable.Creator CREATOR  = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel parcel) {
            return new Episodes(parcel);
        }

        @Override
        public Episodes[] newArray(int i) {
            return new Episodes[i];
        }
    };

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getSeriesDesc() {
        return seriesDesc;
    }

    public void setSeriesDesc(String seriesDesc) {
        this.seriesDesc = seriesDesc;
    }

    public String getSeriesImgs() {
        return seriesImgs;
    }

    public void setSeriesImgs(String seriesImgs) {
        this.seriesImgs = seriesImgs;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSeriesCatId() {
        return seriesCatId;
    }

    public void setSeriesCatId(String seriesCatId) {
        this.seriesCatId = seriesCatId;
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
        parcel.writeString(this.seriesId);
        parcel.writeString(this.seriesTitle);
        parcel.writeString(this.seriesDesc);
        parcel.writeString(this.seriesImgs);
        parcel.writeString(this.dateCreated);
        parcel.writeString(this.seriesCatId);
        parcel.writeString(this.videoId);
        parcel.writeString(this.eTag);
    }
}
