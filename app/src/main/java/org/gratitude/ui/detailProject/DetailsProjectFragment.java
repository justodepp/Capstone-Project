package org.gratitude.ui.detailProject;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.model.projects.Project;
import org.gratitude.databinding.FragmentDetailProjectBinding;
import org.gratitude.ui.ProjectsFragment;
import org.gratitude.ui.ReportProjectFragment;
import org.gratitude.utils.AppExecutors;
import org.gratitude.utils.ImageHandler;

import java.util.Objects;

public class DetailsProjectFragment extends Fragment implements View.OnClickListener{

    FragmentDetailProjectBinding mBinding;
    private Project mProject;

    // Member variable for the Database
    private GratitudeDatabase mDb;

    private boolean favorite;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_project, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        assert bundle != null;
        mProject = bundle.getParcelable(ProjectsFragment.PRJ_CLICKED);
        setImgPrjId();

        mDb = GratitudeDatabase.getInstance(getContext());

        mBinding.favoriteButton.setOnClickListener(this);
        mBinding.reportButton.setOnClickListener(this);
        mBinding.shareButton.setOnClickListener(this);

        mBinding.projectTitle.setText(mProject.getTitle());
        ImageHandler.projectImageHandler(getContext(), mBinding.projectImageview, mProject);

        mBinding.projectSummaryTitle.setText(R.string.prj_detail_summary);
        mBinding.projectSummaryText.setText(mProject.getSummary());

        mBinding.projectNeedTitle.setText(R.string.prj_detail_need);
        mBinding.projectNeedText.setText(mProject.getNeed());

        mBinding.projectLongTermImpactTitle.setText(R.string.prj_detail_longterm);
        mBinding.projectLongTermImpactText.setText(mProject.getLongTermImpact());

        mBinding.projectActivitiesTitle.setText(R.string.prj_detail_activities);
        mBinding.projectActivitiesText.setText(mProject.getActivities());

        initFav();
    }

    private void setImgPrjId() {
        mProject.getImage().setPrjId(mProject.getId());
        for (int i = 0; i < mProject.getImage().getImagelink().size(); i++) {
            mProject.getImage().getImagelink().get(i).setPrjId(mProject.getId());
        }
    }

    private void initFav() {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mDb.projectDao().loadProjectById(mProject.getId()) != null)
                    favorite = true;

                AppExecutors.getInstance().mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        updateFab(favorite);
                    }
                });
            }
        });
    }

    private void updateFab(boolean favourite) {
        if (favourite)
            mBinding.favoriteButton.setImageDrawable(Objects.requireNonNull(getActivity()).getResources()
                    .getDrawable(R.drawable.ic_favorite_on));
        else
            mBinding.favoriteButton.setImageDrawable(Objects.requireNonNull(getActivity()).getResources()
                    .getDrawable(R.drawable.ic_favorite_off));
    }

    @Override
    public void onClick(View view) {
        if(view == mBinding.shareButton){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mProject.getProjectLink());
            sendIntent.setType("text/plain");
            getContext().startActivity(
                    Intent.createChooser(
                            sendIntent, getContext().getString(R.string.share_to)));
        } else if(view == mBinding.reportButton){
            Fragment fragment = new ReportProjectFragment();

            Bundle bundle = new Bundle();
            bundle.putLong(ReportProjectFragment.REPORT_PRJ_ID, mProject.getId());
            fragment.setArguments(bundle);

            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();

        } else if(view == mBinding.favoriteButton){
            onFavButtonClicked();
        }
    }

    /**
     * onFavButtonClicked is called when the "fav" button is clicked.
     * It retrieves Project fav into the underlying database.
     */
    private void onFavButtonClicked() {
        if(favorite) {
            ProjectsFragment.deleteData(mProject);
            /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    // delete project
                    mDb.projectDao().deleteProject(mProject);
                    mDb.imageDao().deleteImage(mProject.getId());
                    mDb.imageLinkDao().deleteImagelink(mProject.getId());
                }
            });*/

            favorite = false;

            Snackbar.make(mBinding.mainParent, R.string.label_fav_deleted, Snackbar.LENGTH_SHORT).show();
        } else {
            ProjectsFragment.insertData(mProject);
            /*AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    // insert project
                    mDb.projectDao().insertProject(mProject);
                    mDb.imageDao().insertImage(mProject.getImage());
                    mDb.imageLinkDao().insertImagelink(mProject.getImage().getImagelink());
                }
            });*/

            favorite = true;
            Snackbar.make(mBinding.mainParent, R.string.label_fav_added, Snackbar.LENGTH_SHORT).show();
        }

        updateFab(favorite);
    }
}
