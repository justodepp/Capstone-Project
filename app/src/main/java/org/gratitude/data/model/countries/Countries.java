
package org.gratitude.data.model.countries;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Countries implements Parcelable {

    @Expose
    private List<Country> country;

    public List<Country> getCountry() {
        return country;
    }

    public static class Builder {

        private List<Country> country;

        public Countries.Builder withCountry(List<Country> country) {
            this.country = country;
            return this;
        }

        public Countries build() {
            Countries countries = new Countries();
            countries.country = country;
            return countries;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.country);
    }

    public Countries() {
    }

    protected Countries(Parcel in) {
        this.country = new ArrayList<Country>();
        in.readList(this.country, Country.class.getClassLoader());
    }

    public static final Parcelable.Creator<Countries> CREATOR = new Parcelable.Creator<Countries>() {
        @Override
        public Countries createFromParcel(Parcel source) {
            return new Countries(source);
        }

        @Override
        public Countries[] newArray(int size) {
            return new Countries[size];
        }
    };
}
