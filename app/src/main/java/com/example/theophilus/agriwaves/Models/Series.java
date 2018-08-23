package com.example.theophilus.agriwaves.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Theophilus on 8/15/2018.
 */

public class Series implements Parcelable{
    private String seriesId;
    private String seriesTitle;
    private String seriesDesc;
    private String seriesImgs;
    private String dateCreated;
    private String totalNumber;

    public Series() {

    }

    public Series(Parcel parcel){
        this.seriesId       = parcel.readString();
        this.seriesTitle    = parcel.readString();
        this.seriesDesc     = parcel.readString();
        this.seriesImgs     = parcel.readString();
        this.dateCreated    = parcel.readString();
        this.totalNumber    = parcel.readString();
    }

    //Initiate the parcelable Object Class
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Series createFromParcel(Parcel parcel) {
            return new Series(parcel);
        }

        @Override
        public Series[] newArray(int i) {
            return new Series[i];
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

    public String getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(String totalNumber) {
        this.totalNumber = totalNumber;
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
        parcel.writeString(this.totalNumber);
    }
}
