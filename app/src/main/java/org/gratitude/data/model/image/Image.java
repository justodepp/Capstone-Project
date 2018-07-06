
package org.gratitude.data.model.image;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Project;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "image",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "prjId",
                onDelete = ForeignKey.CASCADE))
public class Image implements Parcelable {

    @PrimaryKey
    @ColumnInfo(index = true)
    private Long prjId;

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

    @Expose
    private Long id;
    @Expose
    @Ignore
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setImagelink(List<Imagelink> imagelink) {
        this.imagelink = imagelink;
    }

    public void setTitle(String title) {
        this.title = title;
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

    @Ignore
    public Image(Long id, String title, List<Imagelink> imagelink) {
        setId(id);
        setTitle(title);
        setImagelink(imagelink);
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
