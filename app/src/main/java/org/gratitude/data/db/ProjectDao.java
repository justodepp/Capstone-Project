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

    /*@Query("SELECT * FROM project "
            + "INNER JOIN image ON image.prjId = project.id "
            + "INNER JOIN image_link ON image_link.prjId = image.prjId ")*/
    @Query("SELECT * FROM project ")
    LiveData<List<Project>> loadAllProjects();

    @Insert
    void insertProject(Project project);

    @Delete
    void deleteProject(Project project);

    @Query("SELECT * FROM project WHERE id = :id")
    Project loadProjectById(Long id);
}
