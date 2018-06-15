
package org.gratitude.data.model.report;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Entry {

    @Expose
    private List<Author> authors;
    @Expose
    private String content;
    @Expose
    private String id;
    @Expose
    private List<Link> links;
    @Expose
    private String published;
    @Expose
    private String title;

    public List<Author> getAuthors() {
        return authors;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getPublished() {
        return published;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {

        private List<Author> authors;
        private String content;
        private String id;
        private List<Link> links;
        private String published;
        private String title;

        public Entry.Builder withAuthors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Entry.Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Entry.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Entry.Builder withLinks(List<Link> links) {
            this.links = links;
            return this;
        }

        public Entry.Builder withPublished(String published) {
            this.published = published;
            return this;
        }

        public Entry.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Entry build() {
            Entry entry = new Entry();
            entry.authors = authors;
            entry.content = content;
            entry.id = id;
            entry.links = links;
            entry.published = published;
            entry.title = title;
            return entry;
        }

    }

}
