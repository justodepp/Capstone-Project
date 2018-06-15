
package org.gratitude.data.model.video;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Videos implements Parcelable {

    @Expose
    private List<Video> video;

    public List<Video> getVideo() {
        return video;
    }

    public static class Builder {

        private List<Video> video;

        public Videos.Builder withVideo(List<Video> video) {
            this.video = video;
            return this;
        }

        public Videos build() {
            Videos videos = new Videos();
            videos.video = video;
            return videos;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.video);
    }

    public Videos() {
    }

    protected Videos(Parcel in) {
        this.video = new ArrayList<Video>();
        in.readList(this.video, Video.class.getClassLoader());
    }

    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel source) {
            return new Videos(source);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}
