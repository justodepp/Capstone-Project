
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class ProjectByTheme implements Parcelable {

    @Expose
    private Projects projects;

    public Projects getProjects() {
        return projects;
    }

    public static class Builder {

        private Projects projects;

        public ProjectByTheme.Builder withProjects(Projects projects) {
            this.projects = projects;
            return this;
        }

        public ProjectByTheme build() {
            ProjectByTheme projectByTheme = new ProjectByTheme();
            projectByTheme.projects = projects;
            return projectByTheme;
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

    public ProjectByTheme() {
    }

    protected ProjectByTheme(Parcel in) {
        this.projects = in.readParcelable(Projects.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProjectByTheme> CREATOR = new Parcelable.Creator<ProjectByTheme>() {
        @Override
        public ProjectByTheme createFromParcel(Parcel source) {
            return new ProjectByTheme(source);
        }

        @Override
        public ProjectByTheme[] newArray(int size) {
            return new ProjectByTheme[size];
        }
    };
}
