
package org.gratitude.data.model.report;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Attributes implements Parcelable {

    @SerializedName("xml:base")
    private String xmlBase;

    public String getXmlBase() {
        return xmlBase;
    }

    public static class Builder {

        private String xmlBase;

        public Attributes.Builder withXmlBase(String xmlBase) {
            this.xmlBase = xmlBase;
            return this;
        }

        public Attributes build() {
            Attributes attributes = new Attributes();
            attributes.xmlBase = xmlBase;
            return attributes;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.xmlBase);
    }

    public Attributes() {
    }

    protected Attributes(Parcel in) {
        this.xmlBase = in.readString();
    }

    public static final Parcelable.Creator<Attributes> CREATOR = new Parcelable.Creator<Attributes>() {
        @Override
        public Attributes createFromParcel(Parcel source) {
            return new Attributes(source);
        }

        @Override
        public Attributes[] newArray(int size) {
            return new Attributes[size];
        }
    };
}
