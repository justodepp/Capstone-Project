
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class AllProjects implements Parcelable {

    @Expose
    private Projects projects;

    public Projects getProjects() {
        return projects;
    }

    public static class Builder {

        private Projects projects;

        public AllProjects.Builder withProjects(Projects projects) {
            this.projects = projects;
            return this;
        }

        public AllProjects build() {
            AllProjects allProjects = new AllProjects();
            allProjects.projects = projects;
            return allProjects;
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

    public AllProjects() {
    }

    protected AllProjects(Parcel in) {
        this.projects = in.readParcelable(Projects.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllProjects> CREATOR = new Parcelable.Creator<AllProjects>() {
        @Override
        public AllProjects createFromParcel(Parcel source) {
            return new AllProjects(source);
        }

        @Override
        public AllProjects[] newArray(int size) {
            return new AllProjects[size];
        }
    };
}
