package org.gratitude.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.model.projects.Project;
import org.gratitude.data.model.projects.Projects;
import org.gratitude.databinding.FragmentProjectListBinding;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ProjectsAdapter;

import timber.log.Timber;

public class ProjectsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProjectsAdapter.ProjectClickListener{

    private ProjectsAdapter mAdapter;
    FragmentProjectListBinding mBinding;
    private String typeCode;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            typeCode = bundle.getString(MainActivity.ARGUMENT_TYPE_CODE);
        }

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        handleCall();
    }

    private void handleCall(){
        if(typeCode.equals(getString(R.string.menu_home))){
            callFeatured();
        } else if(typeCode.equals(getString(R.string.menu_prj))){
            callProjects();
        }

    }

    @Override
    public void onRefresh() {
        handleCall();
    }

    private void callFeatured(){
        Project.getFeaturedProjects(getContext(), new ResponseInterface<Projects>() {
            @Override
            public void onResponseLoaded(Projects object) {
                mAdapter = new ProjectsAdapter(getActivity(), object.getProject(),ProjectsFragment.this);
                mBinding.recyclerview.setAdapter(mAdapter);
                mBinding.swipeRefreshLayout.setRefreshing(false);
                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponseFailed() {
                Timber.e("Error retriving data");
            }
        });
    }

    private void callProjects(){
        Project.getProjects(getContext(), new ResponseInterface<Projects>() {
            @Override
            public void onResponseLoaded(Projects object) {
                mAdapter = new ProjectsAdapter(getActivity(), object.getProject(),ProjectsFragment.this);
                mBinding.recyclerview.setAdapter(mAdapter);
                mBinding.swipeRefreshLayout.setRefreshing(false);
                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponseFailed() {
                Timber.e("Error retriving data");
            }
        });
    }

    @Override
    public void onClickProjectItem(Project project) {

    }
}
