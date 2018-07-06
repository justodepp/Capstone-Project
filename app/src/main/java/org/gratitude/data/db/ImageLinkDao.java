package org.gratitude.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import org.gratitude.data.model.image.Imagelink;

import java.util.List;

@Dao
public interface ImageLinkDao {

    @Query("SELECT * FROM image_link")
    LiveData<List<Imagelink>> loadAllImages();

    @Insert
    void insertImagelink(List<Imagelink> imagelinks);

    @Query("DELETE FROM image_link WHERE prjId = :imgId")
    void deleteImagelink(Long imgId);

    @Query("SELECT * FROM image_link WHERE id = :id")
    LiveData<Imagelink> loadImagelinkById(Long id);
}
