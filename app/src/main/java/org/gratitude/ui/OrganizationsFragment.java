package org.gratitude.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.model.projects.Project;
import org.gratitude.data.model.projects.Projects;
import org.gratitude.databinding.FragmentProjectListBinding;
import org.gratitude.main.MainActivity;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ProjectsAdapter;
import org.gratitude.utils.EndlessRecyclerViewScrollListener;
import org.gratitude.utils.ItemClickSupport;

import java.util.ArrayList;

import timber.log.Timber;

public class OrganizationsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ProjectsAdapter mAdapter;
    FragmentProjectListBinding mBinding;
    private String typeCode;

    private ArrayList<Project> mProjectList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    private boolean hasNext;
    private long mNextProjectId;

    private EndlessRecyclerViewScrollListener endlessScroll;

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

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerview.setLayoutManager(mLinearLayoutManager);
        mBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        endlessScroll = new EndlessRecyclerViewScrollListener(){
            @Override
            public void onLoadMore() {
                if(hasNext) {
                    mBinding.itemProgressBar.setVisibility(View.VISIBLE);

                    callProjects(mNextProjectId);
                }
            }
        };

        handleCall();

        mBinding.recyclerview.addOnScrollListener(endlessScroll);

        ItemClickSupport.addTo(mBinding.recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // TODO: test click element
                mAdapter.getItem(position);
            }
        });
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
                mAdapter = new ProjectsAdapter(getActivity(), object.getProject());
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
        resetData();
        callProjects(null);
    }

    private void callProjects(Long nexProjectId){
        Project.getProjects(getContext(), nexProjectId, new ResponseInterface<Projects>() {
            @Override
            public void onResponseLoaded(Projects object) {
                hasNext = object.getHasNext();
                mNextProjectId = object.getNextProjectId();

                mProjectList.clear();
                mProjectList.addAll(object.getProject());

                if(mBinding.itemProgressBar.getVisibility() == View.VISIBLE)
                    mBinding.itemProgressBar.setVisibility(View.GONE);

                if(mAdapter == null) {
                    mAdapter = new ProjectsAdapter(getActivity(), mProjectList);
                    mBinding.recyclerview.setAdapter(mAdapter);
                } else {
                    mAdapter.setProjectList(mProjectList);
                    mAdapter.notifyItemRangeChanged(endlessScroll.getPreviousTotal()+1, mProjectList.size());
                }

                mBinding.swipeRefreshLayout.setRefreshing(false);
                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
            }

            @Override
            public void onResponseFailed() {
                Timber.e("Error retriving data");
            }
        });
    }

    private void resetData(){
        mProjectList.clear();
        endlessScroll.resetPreviousTotal();
        mAdapter = null;
    }
}
