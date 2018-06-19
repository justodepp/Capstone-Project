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
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ProjectsAdapter;

import java.util.Objects;

public class ProjectsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ProjectsAdapter.ProjectClickListener{

    //private View mProgressBar;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView rv;
    private ProjectsAdapter mAdapter;

    FragmentProjectListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_project_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding = DataBindingUtil.setContentView(Objects.requireNonNull(getActivity()), R.layout.fragment_project_list);
//        mProgressBar = view.findViewById(R.id.indeterminateBar);
//        mProgressBar.setVisibility(View.GONE);

        rv = view.findViewById(R.id.recyclerview);
        setupRecyclerView();

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        makeCall();
    }

    private void setupRecyclerView() {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
    }

    @Override
    public void onRefresh() {
        makeCall();
        //mSwipeRefreshLayout.setRefreshing(false);
    }

    private void makeCall(){
        Project.getProjects(getContext(), new ResponseInterface() {
            @Override
            public void onResponseLoaded(Projects projects) {
                //mProgressBar.setVisibility(View.GONE);
                mAdapter = new ProjectsAdapter(getActivity(), projects.getProject(),ProjectsFragment.this);
                rv.setAdapter(mAdapter);
            }

            @Override
            public void onResponseFailed() {

            }
        });
    }

    @Override
    public void onClickProjectItem(Project project) {

    }
}
