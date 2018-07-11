package org.gratitude.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import org.gratitude.data.db.ProjectPojo;
import org.gratitude.data.db.ProjectRepository;
import org.gratitude.data.model.image.Image;
import org.gratitude.data.model.image.Imagelink;
import org.gratitude.data.model.projects.Project;

import java.util.List;

import timber.log.Timber;

public class ProjectViewModel extends AndroidViewModel {

    private ProjectRepository projectRepository;

    private LiveData<List<ProjectPojo>> projects;
    private LiveData<List<Image>> image;
    private LiveData<List<Imagelink>> imageLinks;

    public ProjectViewModel(Application application) {
        super(application);
        projectRepository = new ProjectRepository(application);
        Timber.d( "Actively retrieving the tasks from the DataBase");
        projects = projectRepository.getProjects();
        image = projectRepository.getImages();
        imageLinks = projectRepository.getImageLinks();
    }

    public LiveData<List<ProjectPojo>> getProjects() {
        return projects;
    }

    public LiveData<List<Image>> getImages() {
        return image;
    }

    public LiveData<List<Imagelink>> getImageLinks() {
        return imageLinks;
    }

    public void insert(Project project) {
        projectRepository.insert(project);
    }

    public void delete(Project project) {
        projectRepository.delete(project);
    }
}