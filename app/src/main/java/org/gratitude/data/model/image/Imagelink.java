
package org.gratitude.data.model.image;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Imagelink implements Parcelable {

    @Expose
    private String size;
    @Expose
    private String url;

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public static class Builder {

        private String size;
        private String url;

        public Imagelink.Builder withSize(String size) {
            this.size = size;
            return this;
        }

        public Imagelink.Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Imagelink build() {
            Imagelink imagelink = new Imagelink();
            imagelink.size = size;
            imagelink.url = url;
            return imagelink;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.size);
        dest.writeString(this.url);
    }

    public Imagelink() {
    }

    protected Imagelink(Parcel in) {
        this.size = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Imagelink> CREATOR = new Parcelable.Creator<Imagelink>() {
        @Override
        public Imagelink createFromParcel(Parcel source) {
            return new Imagelink(source);
        }

        @Override
        public Imagelink[] newArray(int size) {
            return new Imagelink[size];
        }
    };
}
