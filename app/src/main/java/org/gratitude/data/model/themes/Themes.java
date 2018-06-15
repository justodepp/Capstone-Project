
package org.gratitude.data.model.themes;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Themes {

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

}
