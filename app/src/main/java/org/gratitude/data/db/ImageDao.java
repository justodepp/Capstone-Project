package org.gratitude.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.gratitude.data.model.image.Image;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT * FROM image")
    LiveData<List<Image>> loadAllImage();

    @Insert
    void insertImage(Image image);

    @Query("DELETE FROM image WHERE prjId = :prjId")
    void deleteImage(Long prjId);

    @Query("SELECT * FROM image WHERE prjId = :id")
    LiveData<Image> loadImageById(Long id);
}
