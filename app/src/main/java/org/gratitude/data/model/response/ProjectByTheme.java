
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class ProjectByTheme {

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

}
