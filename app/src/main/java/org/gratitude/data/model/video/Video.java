
package org.gratitude.data.model.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Video implements Parcelable {

    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public static class Builder {

        private String url;

        public Video.Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Video build() {
            Video video = new Video();
            video.url = url;
            return video;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
    }

    public Video() {
    }

    protected Video(Parcel in) {
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
