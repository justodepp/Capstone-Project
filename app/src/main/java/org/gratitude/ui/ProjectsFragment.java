package org.gratitude.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.model.image.Image;
import org.gratitude.data.model.image.Imagelink;
import org.gratitude.data.model.projects.Project;
import org.gratitude.data.model.projects.Projects;
import org.gratitude.databinding.FragmentProjectListBinding;
import org.gratitude.main.MainActivity;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ProjectsAdapter;
import org.gratitude.ui.detailProject.DetailsProjectFragment;
import org.gratitude.utils.EndlessRecyclerViewScrollListener;
import org.gratitude.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class ProjectsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String PRJ_ORG_ID = "prj_org_id";
    public static final String PRJ_CLICKED = "prj_clicked";

    private ProjectsAdapter mAdapter;
    FragmentProjectListBinding mBinding;
    private String typeCode;

    private ArrayList<Project> mProjectList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    private boolean hasNext;
    private long mNextProjectId;

    private Bundle mBundle;
    private String mTheme = null;
    private String mOrgId = null;

    private EndlessRecyclerViewScrollListener endlessScroll;

    // Member variable for the Database
    private GratitudeDatabase mDb;
    private boolean isFavorite = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDb = GratitudeDatabase.getInstance(getContext());

        mBundle = this.getArguments();
        if(mBundle != null){
            if(mBundle.getString(PRJ_ORG_ID) != null){
                mOrgId = mBundle.getString(PRJ_ORG_ID);
            }
            isFavorite = mBundle.getBoolean(MainActivity.ARGUMENT_FAVORITE, false);
            typeCode = mBundle.getString(MainActivity.ARGUMENT_TYPE_CODE);
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

                    handleEndlessCall(mNextProjectId, mTheme, mOrgId);
                }
            }
        };

        handleCall();

        mBinding.recyclerview.addOnScrollListener(endlessScroll);

        ItemClickSupport.addTo(mBinding.recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Fragment fragment = new DetailsProjectFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable(PRJ_CLICKED, mAdapter.getItem(position));
                fragment.setArguments(bundle);

                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.content_frame, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
    private void setupViewModel2() {
        final List<Image> _Image = new ArrayList<>();
        final List<Project> _Prj= new ArrayList<>();

        final ProjectViewModel viewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        viewModel.getImageLinks().observe(this, new Observer<List<Imagelink>>() {
            @Override
            public void onChanged(@Nullable final List<Imagelink> imagelinks) {
                viewModel.getImageLinks().removeObserver(this);
                viewModel.getImages().observe(ProjectsFragment.this, new Observer<List<Image>>() {
                    @Override
                    public void onChanged(@Nullable final List<Image> images) {
                        viewModel.getImages().removeObserver(this);
                        for (int i = 0; i < images.size(); i++) {
                            List<Imagelink> _Imagelinks = new ArrayList<>();
                            for (int j = 0; j < imagelinks.size(); j++) {
                                if (images.get(i).getPrjId().equals(imagelinks.get(j).getPrjId())){
                                    _Imagelinks.add(imagelinks.get(j));
                                }
                            }
                            images.get(i).setImagelink(_Imagelinks);
                            _Image.add(images.get(i));
                        }

                        viewModel.getProjects().observe(ProjectsFragment.this, new Observer<List<Project>>() {
                            @Override
                            public void onChanged(@Nullable List<Project> projects) {
                                viewModel.getProjects().removeObserver(this);
                                for (int i = 0; i < projects.size(); i++) {
                                    for (int j = 0; j < _Image.size(); j++) {
                                        if(projects.get(i).getId().equals(_Image.get(j).getPrjId())){
                                            projects.get(i).setImage(_Image.get(j));
                                        }
                                    }
                                }

                                _Prj.addAll(projects);
                                populateUI(_Prj);
                            }
                        });
                    }
                });

                mBinding.swipeRefreshLayout.setRefreshing(false);
                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupViewModel() {
        //ProjectViewModelFactory projectViewModelFactory = new ProjectViewModelFactory(mDb, prjId);
        final ProjectViewModel viewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
        viewModel.getProjects().observe(this, new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable final List<Project> projects) {
                Timber.d("Updating list of tasks from LiveData in ViewModel");

                if(projects != null && projects.size() > 0) {
                    for (int i = 0; i < projects.size(); i++) {

                        final Image[] mImage = {new Image()};

                        //Retrieve the current image from ViewModel
                        final int finalI = i;
                        viewModel.getImages().observe(ProjectsFragment.this, new Observer<List<Image>>() {
                            @Override
                            public void onChanged(@Nullable List<Image> images) {
                                viewModel.getImages().removeObserver(this);

                                assert images != null;
                                for (int j = 0; j < images.size(); j++) {
                                    if(images.get(j).getPrjId().equals(projects.get(finalI).getId())) {
                                        mImage[0] = images.get(j);
                                    }
                                }

                                //Retrieve the current order from ViewModel
                                viewModel.getImageLinks().observe(ProjectsFragment.this, new Observer<List<Imagelink>>() {
                                    @Override
                                    public void onChanged(@Nullable List<Imagelink> imagelinks) {
                                        viewModel.getImageLinks().removeObserver(this);

                                        mImage[0].setImagelink(imagelinks);
                                        projects.get(finalI).setImage(mImage[0]);
                                    }
                                });
                            }
                        });
                    }
                    populateUI(projects);
                }

                mBinding.swipeRefreshLayout.setRefreshing(false);
                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
            }
        });
    }

    private void populateUI(List<Project> projects) {
        resetData();
        mProjectList.addAll(projects);
        if (mAdapter == null) {
            mAdapter = new ProjectsAdapter(getActivity(), mProjectList);
            mBinding.recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.setProjectList(mProjectList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void handleCall(){
        if(typeCode != null) {
            //mBinding.swipeRefreshLayout.setEnabled(true);
            if (typeCode.equals(getString(R.string.menu_home))) {
                callFeatured();
            } else if (typeCode.equals(getString(R.string.menu_prj))) {
                callProjects();
            }  else if (typeCode.equals(getString(R.string.menu_fav))) {
                //mBinding.swipeRefreshLayout.setEnabled(false);
                setupViewModel2();
            } else if (typeCode.equals(getString(R.string.menu_cat))) {
                mTheme = mBundle.getString(ThemesFragment.THEME_CLICKED);
                callPrjCat(mTheme);
            }
        } else {
            callPrjByOrg(mOrgId);
        }
    }

    private void handleEndlessCall(Long nextProjectId, String category, String mOrgId){
        if(typeCode != null) {
            if (typeCode.equals(getString(R.string.menu_prj))) {
                callProjects(nextProjectId);
            } else if (typeCode.equals(getString(R.string.menu_cat))) {
                callPrjCat(category, nextProjectId);
            }
        } else if (mOrgId != null) {
            callPrjByOrg(mOrgId, nextProjectId);
        }
    }

    @Override
    public void onRefresh() {
        handleCall();
    }

    private void callPrjByOrg(String orgId) {
        resetData();
        callPrjByOrg(orgId, null);
    }

    private void callPrjByOrg(String orgId, Long nexProjectId) {
        Project.getProjectsByOrgId(getContext(), orgId, nexProjectId, new ResponseInterface<Projects>() {
            @Override
            public void onResponseLoaded(Projects object) {
                if (object.getHasNext() != null) {
                    hasNext = object.getHasNext();
                } else {
                    hasNext = false;
                }
                if (object.getNextProjectId() != null)
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

    private void callPrjCat(String theme){
        resetData();
        callPrjCat(theme, null);
    }

    private void callPrjCat(String theme, Long nexProjectId) {
        Project.getThemeProject(getContext(), theme, nexProjectId, new ResponseInterface<Projects>() {
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
