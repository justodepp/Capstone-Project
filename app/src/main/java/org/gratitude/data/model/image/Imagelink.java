
package org.gratitude.data.model.image;

import com.google.gson.annotations.Expose;

public class Imagelink {

    @Expose
    private String size;
    @Expose
    private String url;

    public String getSize() {
        return size;
    }

    public String getUrl() {
        return url;
    }

    public static class Builder {

        private String size;
        private String url;

        public Imagelink.Builder withSize(String size) {
            this.size = size;
            return this;
        }

        public Imagelink.Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Imagelink build() {
            Imagelink imagelink = new Imagelink();
            imagelink.size = size;
            imagelink.url = url;
            return imagelink;
        }

    }

}
