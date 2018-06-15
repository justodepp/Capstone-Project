
package org.gratitude.data.model.projects;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Projects {

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

}
