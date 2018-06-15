
package org.gratitude.data.model.themes;

import com.google.gson.annotations.Expose;

public class Theme {

    @Expose
    private String id;
    @Expose
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static class Builder {

        private String id;
        private String name;

        public Theme.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Theme.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Theme build() {
            Theme theme = new Theme();
            theme.id = id;
            theme.name = name;
            return theme;
        }

    }

}
