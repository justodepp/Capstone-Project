package org.gratitude.main.interfaces;

import org.gratitude.data.model.projects.Projects;

public interface ResponseInterface {
    void onResponseLoaded(Projects projects);
    void onResponseFailed();
}
