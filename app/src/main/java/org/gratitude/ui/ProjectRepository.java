package org.gratitude.ui;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.db.ImageDao;
import org.gratitude.data.db.ImageLinkDao;
import org.gratitude.data.db.ProjectDao;
import org.gratitude.data.model.image.Image;
import org.gratitude.data.model.image.Imagelink;
import org.gratitude.data.model.projects.Project;

import java.util.List;

public class ProjectRepository {

    private ProjectDao mPrjDao;
    private LiveData<List<Project>> mAllPrj;

    private ImageDao mImgDao;
    private LiveData<List<Image>> mAllImg;

    private ImageLinkDao mImgLinkDao;
    private LiveData<List<Imagelink>> mAllImgLink;

    ProjectRepository(Application application) {
        GratitudeDatabase db = GratitudeDatabase.getInstance(application);
        mPrjDao = db.projectDao();
        mAllPrj = mPrjDao.loadAllProjects();
        mImgDao = db.imageDao();
        mAllImg = mImgDao.loadAllImage();
        mImgLinkDao = db.imageLinkDao();
        mAllImgLink = mImgLinkDao.loadAllImageLinks();
    }

    LiveData<List<Project>> getProjects() {
        return mAllPrj;
    }

    LiveData<List<Image>> getImages() {
        return mAllImg;
    }

    LiveData<List<Imagelink>> getImageLinks() {
        return mAllImgLink;
    }

    public void insert (Project project) {
        new insertAsyncTask(mPrjDao, mImgDao, mImgLinkDao).execute(project);
    }

    public void delete (Project project) {
        new deleteAsyncTask(mPrjDao, mImgDao, mImgLinkDao).execute(project);
    }

    private static class insertAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskPrjDao;
        private ImageDao mAsyncTaskImgDao;
        private ImageLinkDao mAsyncTaskImgLinkDao;

        insertAsyncTask(ProjectDao prjDao, ImageDao imgDao, ImageLinkDao imgLinkDao) {
            mAsyncTaskPrjDao = prjDao;
            mAsyncTaskImgDao = imgDao;
            mAsyncTaskImgLinkDao = imgLinkDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            mAsyncTaskPrjDao.insertProject(projects[0]);
            mAsyncTaskImgDao.insertImage(projects[0].getImage());
            mAsyncTaskImgLinkDao.insertImagelink(projects[0].getImage().getImagelink());
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Project, Void, Void> {

        private ProjectDao mAsyncTaskPrjDao;
        private ImageDao mAsyncTaskImgDao;
        private ImageLinkDao mAsyncTaskImgLinkDao;

        deleteAsyncTask(ProjectDao prjDao, ImageDao imgDao, ImageLinkDao imgLinkDao) {
            mAsyncTaskPrjDao = prjDao;
            mAsyncTaskImgDao = imgDao;
            mAsyncTaskImgLinkDao = imgLinkDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            mAsyncTaskPrjDao.deleteProject(projects[0]);
            mAsyncTaskImgDao.deleteImage(projects[0].getId());
            mAsyncTaskImgLinkDao.deleteImagelink(projects[0].getId());
            return null;
        }
    }
}
