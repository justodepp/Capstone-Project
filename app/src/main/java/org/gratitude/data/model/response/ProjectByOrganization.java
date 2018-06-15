
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class ProjectByOrganization implements Parcelable {

    @Expose
    private Projects projects;

    public Projects getProjects() {
        return projects;
    }

    public static class Builder {

        private Projects projects;

        public ProjectByOrganization.Builder withProjects(Projects projects) {
            this.projects = projects;
            return this;
        }

        public ProjectByOrganization build() {
            ProjectByOrganization projectByOrganization = new ProjectByOrganization();
            projectByOrganization.projects = projects;
            return projectByOrganization;
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

    public ProjectByOrganization() {
    }

    protected ProjectByOrganization(Parcel in) {
        this.projects = in.readParcelable(Projects.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProjectByOrganization> CREATOR = new Parcelable.Creator<ProjectByOrganization>() {
        @Override
        public ProjectByOrganization createFromParcel(Parcel source) {
            return new ProjectByOrganization(source);
        }

        @Override
        public ProjectByOrganization[] newArray(int size) {
            return new ProjectByOrganization[size];
        }
    };
}
