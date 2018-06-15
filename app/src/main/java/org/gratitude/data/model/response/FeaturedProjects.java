
package org.gratitude.data.model.response;

import com.google.gson.annotations.Expose;

import org.gratitude.data.model.projects.Projects;

public class FeaturedProjects {

    @Expose
    private Projects projects;

    public Projects getProjects() {
        return projects;
    }

    public static class Builder {

        private Projects projects;

        public FeaturedProjects.Builder withProjects(Projects projects) {
            this.projects = projects;
            return this;
        }

        public FeaturedProjects build() {
            FeaturedProjects featuredProjects = new FeaturedProjects();
            featuredProjects.projects = projects;
            return featuredProjects;
        }

    }

}
