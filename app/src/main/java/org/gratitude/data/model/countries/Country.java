
package org.gratitude.data.model.countries;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Country implements Parcelable {

    @Expose
    private String iso3166CountryCode;
    @Expose
    private String name;

    public String getIso3166CountryCode() {
        return iso3166CountryCode;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private String iso3166CountryCode;
        private String name;

        public Country.Builder withIso3166CountryCode(String iso3166CountryCode) {
            this.iso3166CountryCode = iso3166CountryCode;
            return this;
        }

        public Country.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Country build() {
            Country country = new Country();
            country.iso3166CountryCode = iso3166CountryCode;
            country.name = name;
            return country;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.iso3166CountryCode);
        dest.writeString(this.name);
    }

    public Country() {
    }

    protected Country(Parcel in) {
        this.iso3166CountryCode = in.readString();
        this.name = in.readString();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
