package com.example.agri_advisor;

import android.os.Parcel;
import android.os.Parcelable;

public class CropModel implements Parcelable {
    private String cropName;
    private String imageUrl;
    private String description;

    public CropModel() {
    }

    public CropModel(String cropName, String imageUrl, String description) {
        this.cropName = cropName;
        this.imageUrl = imageUrl;
        this.description = description;
    }

    protected CropModel(Parcel in) {
        cropName = in.readString();
        description = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<CropModel> CREATOR = new Creator<CropModel>() {
        @Override
        public CropModel createFromParcel(Parcel in) {
            return new CropModel(in);
        }

        @Override
        public CropModel[] newArray(int size) {
            return new CropModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cropName);
        parcel.writeString(description);
        parcel.writeString(imageUrl);
    }

    public String getCropName() {
        return cropName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
