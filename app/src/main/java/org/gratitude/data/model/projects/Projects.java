
package org.gratitude.data.model.projects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class Projects implements Parcelable {

    @Expose
    private Boolean hasNext;
    @Expose
    private Long nextProjectId;
    @Expose
    private Long numberFound;
    @Expose
    private List<Project> project;

    public Boolean getHasNext() {
        return hasNext;
    }

    public Long getNextProjectId() {
        return nextProjectId;
    }

    public Long getNumberFound() {
        return numberFound;
    }

    public List<Project> getProject() {
        return project;
    }

    public static class Builder {

        private Boolean hasNext;
        private Long nextProjectId;
        private Long numberFound;
        private List<Project> project;

        public Projects.Builder withHasNext(Boolean hasNext) {
            this.hasNext = hasNext;
            return this;
        }

        public Projects.Builder withNextProjectId(Long nextProjectId) {
            this.nextProjectId = nextProjectId;
            return this;
        }

        public Projects.Builder withNumberFound(Long numberFound) {
            this.numberFound = numberFound;
            return this;
        }

        public Projects.Builder withProject(List<Project> project) {
            this.project = project;
            return this;
        }

        public Projects build() {
            Projects projects = new Projects();
            projects.hasNext = hasNext;
            projects.nextProjectId = nextProjectId;
            projects.numberFound = numberFound;
            projects.project = project;
            return projects;
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.hasNext);
        dest.writeValue(this.nextProjectId);
        dest.writeValue(this.numberFound);
        dest.writeList(this.project);
    }

    public Projects() {
    }

    protected Projects(Parcel in) {
        this.hasNext = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.nextProjectId = (Long) in.readValue(Long.class.getClassLoader());
        this.numberFound = (Long) in.readValue(Long.class.getClassLoader());
        this.project = new ArrayList<Project>();
        in.readList(this.project, Project.class.getClassLoader());
    }

    public static final Parcelable.Creator<Projects> CREATOR = new Parcelable.Creator<Projects>() {
        @Override
        public Projects createFromParcel(Parcel source) {
            return new Projects(source);
        }

        @Override
        public Projects[] newArray(int size) {
            return new Projects[size];
        }
    };
}
