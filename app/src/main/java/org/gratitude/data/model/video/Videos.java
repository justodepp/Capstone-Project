
package org.gratitude.data.model.video;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Videos {

    @Expose
    private List<Video> video;

    public List<Video> getVideo() {
        return video;
    }

    public static class Builder {

        private List<Video> video;

        public Videos.Builder withVideo(List<Video> video) {
            this.video = video;
            return this;
        }

        public Videos build() {
            Videos videos = new Videos();
            videos.video = video;
            return videos;
        }

    }

}
