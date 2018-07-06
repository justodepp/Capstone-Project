package org.gratitude.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import org.gratitude.data.model.image.Image;
import org.gratitude.data.model.image.Imagelink;
import org.gratitude.data.model.projects.Project;

import timber.log.Timber;

@Database(entities = {Project.class, Image.class, Imagelink.class}, version = 1, exportSchema = false)
public abstract class GratitudeDatabase extends RoomDatabase{

    private static final Object LOCK = new Object();
    public static final String DATABASE_NAME = "projectlist";
    private static GratitudeDatabase sInstance;

    public static GratitudeDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Timber.d("Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        GratitudeDatabase.class, GratitudeDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Timber.d("Getting the database instance");
        return sInstance;
    }

    public abstract ProjectDao projectDao();
    public abstract ImageDao imageDao();
    public abstract ImageLinkDao imageLinkDao();
}
