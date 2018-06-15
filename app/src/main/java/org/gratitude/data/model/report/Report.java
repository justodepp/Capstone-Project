
package org.gratitude.data.model.report;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Report {

    @Expose
    private Attributes attributes;
    @Expose
    private List<Author> authors;
    @Expose
    private List<Entry> entries;
    @Expose
    private String id;
    @Expose
    private List<Link> links;
    @Expose
    private String title;

    public Attributes getAttributes() {
        return attributes;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getId() {
        return id;
    }

    public List<Link> getLinks() {
        return links;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {

        private Attributes attributes;
        private List<Author> authors;
        private List<Entry> entries;
        private String id;
        private List<Link> links;
        private String title;

        public Report.Builder withAttributes(Attributes attributes) {
            this.attributes = attributes;
            return this;
        }

        public Report.Builder withAuthors(List<Author> authors) {
            this.authors = authors;
            return this;
        }

        public Report.Builder withEntries(List<Entry> entries) {
            this.entries = entries;
            return this;
        }

        public Report.Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Report.Builder withLinks(List<Link> links) {
            this.links = links;
            return this;
        }

        public Report.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Report build() {
            Report report = new Report();
            report.attributes = attributes;
            report.authors = authors;
            report.entries = entries;
            report.id = id;
            report.links = links;
            report.title = title;
            return report;
        }

    }

}
