
package org.gratitude.data.model.report;

import com.google.gson.annotations.Expose;

public class Author {

    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public static class Builder {

        private String name;

        public Author.Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Author build() {
            Author author = new Author();
            author.name = name;
            return author;
        }

    }

}
