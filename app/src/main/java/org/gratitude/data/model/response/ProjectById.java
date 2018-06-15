
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Project;

public class ProjectById {

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

}
