package org.gratitude.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.model.image.Image;
import org.gratitude.data.model.image.Imagelink;
import org.gratitude.data.model.projects.Project;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = ProjectViewModel.class.getSimpleName();

    private LiveData<List<Project>> projects;
    private LiveData<List<Image>> image;
    private LiveData<List<Imagelink>> imageLinks;

    public ProjectViewModel(Application application) {
        super(application);
        GratitudeDatabase database = GratitudeDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        projects = database.projectDao().loadAllProjects();
        image = database.imageDao().loadAllImage();
        imageLinks = database.imageLinkDao().loadAllImageLinks();
    }

    public void reset(){
        onCleared();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }

    public LiveData<List<Image>> getImages() {
        return image;
    }

    public LiveData<List<Imagelink>> getImageLinks() {
        return imageLinks;
    }
}