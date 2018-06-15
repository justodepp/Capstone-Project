
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class FeaturedProjects implements Parcelable {

    @Expose
    private Projects projects;

    public Projects getProjects() {
        return projects;
    }

    public static class Builder {

        private Projects projects;

        public FeaturedProjects.Builder withProjects(Projects projects) {
            this.projects = projects;
            return this;
        }

        public FeaturedProjects build() {
            FeaturedProjects featuredProjects = new FeaturedProjects();
            featuredProjects.projects = projects;
            return featuredProjects;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.projects, flags);
    }

    public FeaturedProjects() {
    }

    protected FeaturedProjects(Parcel in) {
        this.projects = in.readParcelable(Projects.class.getClassLoader());
    }

    public static final Parcelable.Creator<FeaturedProjects> CREATOR = new Parcelable.Creator<FeaturedProjects>() {
        @Override
        public FeaturedProjects createFromParcel(Parcel source) {
            return new FeaturedProjects(source);
        }

        @Override
        public FeaturedProjects[] newArray(int size) {
            return new FeaturedProjects[size];
        }
    };
}
