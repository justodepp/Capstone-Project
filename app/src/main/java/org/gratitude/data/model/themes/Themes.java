
package org.gratitude.data.model.themes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Themes implements Parcelable {

    @Expose
    private List<Theme> theme;

    public List<Theme> getTheme() {
        return theme;
    }

    public static class Builder {

        private List<Theme> theme;

        public Themes.Builder withTheme(List<Theme> theme) {
            this.theme = theme;
            return this;
        }

        public Themes build() {
            Themes themes = new Themes();
            themes.theme = theme;
            return themes;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.theme);
    }

    public Themes() {
    }

    protected Themes(Parcel in) {
        this.theme = new ArrayList<Theme>();
        in.readList(this.theme, Theme.class.getClassLoader());
    }

    public static final Parcelable.Creator<Themes> CREATOR = new Parcelable.Creator<Themes>() {
        @Override
        public Themes createFromParcel(Parcel source) {
            return new Themes(source);
        }

        @Override
        public Themes[] newArray(int size) {
            return new Themes[size];
        }
    };
}
