
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.themes.Themes;

public class AllThemes implements Parcelable {

    @Expose
    private Themes themes;

    public Themes getThemes() {
        return themes;
    }

    public static class Builder {

        private Themes themes;

        public AllThemes.Builder withThemes(Themes themes) {
            this.themes = themes;
            return this;
        }

        public AllThemes build() {
            AllThemes responseThemes = new AllThemes();
            responseThemes.themes = themes;
            return responseThemes;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.themes, flags);
    }

    public AllThemes() {
    }

    protected AllThemes(Parcel in) {
        this.themes = in.readParcelable(Themes.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllThemes> CREATOR = new Parcelable.Creator<AllThemes>() {
        @Override
        public AllThemes createFromParcel(Parcel source) {
            return new AllThemes(source);
        }

        @Override
        public AllThemes[] newArray(int size) {
            return new AllThemes[size];
        }
    };
}