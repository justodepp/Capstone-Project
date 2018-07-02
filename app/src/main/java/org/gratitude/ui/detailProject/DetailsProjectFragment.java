package org.gratitude.ui.detailProject;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.model.projects.Project;
import org.gratitude.databinding.FragmentDetailProjectBinding;
import org.gratitude.ui.ProjectsFragment;
import org.gratitude.utils.ImageHandler;

public class DetailsProjectFragment extends Fragment implements View.OnClickListener{

    FragmentDetailProjectBinding mBinding;
    private Project mProject;

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

        mBinding.favoriteButton.setOnClickListener(this);
        mBinding.reportButton.setOnClickListener(this);
        mBinding.shareButton.setOnClickListener(this);

        mBinding.projectTitle.setText(mProject.getTitle());
        ImageHandler.projectImageHandler(getContext(), mBinding.projectImageview, mProject);

        mBinding.projectSummaryTitle.setText("Summary");
        mBinding.projectSummaryText.setText(mProject.getSummary());

        mBinding.projectNeedTitle.setText("Need");
        mBinding.projectNeedText.setText(mProject.getNeed());

        mBinding.projectLongTermImpactTitle.setText("Long term impact");
        mBinding.projectLongTermImpactText.setText(mProject.getLongTermImpact());

        mBinding.projectActivitiesTitle.setText("Activities");
        mBinding.projectActivitiesText.setText(mProject.getActivities());
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

        } else if(view == mBinding.favoriteButton){

        }
    }
}
