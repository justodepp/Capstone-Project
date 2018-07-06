package org.gratitude.ui;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.util.Log;

import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.model.projects.Project;

import java.util.List;

public class ProjectViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = ProjectViewModel.class.getSimpleName();

    private LiveData<List<Project>> projects;

    public ProjectViewModel(Application application) {
        super(application);
        GratitudeDatabase database = GratitudeDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        projects = database.projectDao().loadAllProjects();
    }

    public LiveData<List<Project>> getProjects() {
        return projects;
    }
}