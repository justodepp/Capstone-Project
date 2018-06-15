
package org.gratitude.data.model.video;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Video {

    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public static class Builder {

        private String url;

        public Video.Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Video build() {
            Video video = new Video();
            video.url = url;
            return video;
        }

    }

}
