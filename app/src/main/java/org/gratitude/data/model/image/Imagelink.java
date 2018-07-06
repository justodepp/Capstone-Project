
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

@Entity(tableName = "image_link",
        foreignKeys = @ForeignKey(
                entity = Project.class,
                parentColumns = "id",
                childColumns = "prjId",
                onDelete = ForeignKey.CASCADE))
public class Imagelink implements Parcelable {

    @Expose
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(index = true)
    private Long prjId;

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

    @Expose
    private String size;
    @Expose
    private String url;

    public int getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    //region Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    //endregion Setter

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

    @Ignore
    public Imagelink(String url) {
        setUrl(url);
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
