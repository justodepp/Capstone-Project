
package org.gratitude.data.model.image;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Image implements Parcelable {

    @Expose
    private Long id;
    @Expose
    private List<Imagelink> imagelink;
    @Expose
    private String title;

    public Long getId() {
        return id;
    }

    public List<Imagelink> getImagelink() {
        return imagelink;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {

        private Long id;
        private List<Imagelink> imagelink;
        private String title;

        public Image.Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Image.Builder withImagelink(List<Imagelink> imagelink) {
            this.imagelink = imagelink;
            return this;
        }

        public Image.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Image build() {
            Image image = new Image();
            image.id = id;
            image.imagelink = imagelink;
            image.title = title;
            return image;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeList(this.imagelink);
        dest.writeString(this.title);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.imagelink = new ArrayList<Imagelink>();
        in.readList(this.imagelink, Imagelink.class.getClassLoader());
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
