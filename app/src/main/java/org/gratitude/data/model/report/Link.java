
package org.gratitude.data.model.report;

import com.google.gson.annotations.Expose;

public class Link {

    @Expose
    private String href;
    @Expose
    private String rel;

    public String getHref() {
        return href;
    }

    public String getRel() {
        return rel;
    }

    public static class Builder {

        private String href;
        private String rel;

        public Link.Builder withHref(String href) {
            this.href = href;
            return this;
        }

        public Link.Builder withRel(String rel) {
            this.rel = rel;
            return this;
        }

        public Link build() {
            Link link = new Link();
            link.href = href;
            link.rel = rel;
            return link;
        }

    }

}
