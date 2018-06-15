
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.themes.Themes;

public class AllThemes {

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
}