package org.gratitude.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.gratitude.data.model.projects.Project;

import java.util.List;

@Dao
public interface ProjectDao {

    @Query("SELECT * FROM project")
    LiveData<List<Project>> loadAllProjects();

    @Insert
    void insertProject(Project project);

    @Delete
    void deleteProject(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    LiveData<Project> loadProjectById(int id);
}
