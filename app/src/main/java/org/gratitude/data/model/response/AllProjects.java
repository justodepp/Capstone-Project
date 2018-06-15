
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class AllProjects {

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

}
