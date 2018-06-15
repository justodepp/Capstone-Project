
package org.gratitude.data.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Project;

public class ProjectById implements Parcelable {

    @Expose
    private Project project;

    public Project getProject() {
        return project;
    }

    public static class Builder {

        private Project project;

        public ProjectById.Builder withProject(Project project) {
            this.project = project;
            return this;
        }

        public ProjectById build() {
            ProjectById projectById = new ProjectById();
            projectById.project = project;
            return projectById;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.project, flags);
    }

    public ProjectById() {
    }

    protected ProjectById(Parcel in) {
        this.project = in.readParcelable(Project.class.getClassLoader());
    }

    public static final Parcelable.Creator<ProjectById> CREATOR = new Parcelable.Creator<ProjectById>() {
        @Override
        public ProjectById createFromParcel(Parcel source) {
            return new ProjectById(source);
        }

        @Override
        public ProjectById[] newArray(int size) {
            return new ProjectById[size];
        }
    };
}
