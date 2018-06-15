
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class ProjectByOrganization {

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

}
