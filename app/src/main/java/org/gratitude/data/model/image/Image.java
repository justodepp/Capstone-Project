
package org.gratitude.data.model.image;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Image {

    @Expose
    private Long id;
    @Expose
    private List<Imagelink> imagelink;
    @Expose
    private String title;

    public Long getId() {
        return id;
    }

    public List<Imagelink> getImagelink() {
        return imagelink;
    }

    public String getTitle() {
        return title;
    }

    public static class Builder {

        private Long id;
        private List<Imagelink> imagelink;
        private String title;

        public Image.Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Image.Builder withImagelink(List<Imagelink> imagelink) {
            this.imagelink = imagelink;
            return this;
        }

        public Image.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Image build() {
            Image image = new Image();
            image.id = id;
            image.imagelink = imagelink;
            image.title = title;
            return image;
        }

    }

}
