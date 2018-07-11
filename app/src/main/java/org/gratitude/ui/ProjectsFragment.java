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
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.db.GratitudeDatabase;
import org.gratitude.data.db.ProjectPojo;
import org.gratitude.data.model.projects.Project;
import org.gratitude.data.model.projects.Projects;
import org.gratitude.databinding.FragmentProjectListBinding;
import org.gratitude.main.MainActivity;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ProjectsAdapter;
import org.gratitude.ui.detailProject.DetailsProjectFragment;
import org.gratitude.utils.AppExecutors;
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

    static ProjectViewModel mViewModel;
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
        mViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);

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

        handleCall();
    }

    private void setupViewModel() {
        /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Here is where you'll implement swipe to delete
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        int position = viewHolder.getAdapterPosition();
                        mDb.projectDao().deleteProject(mAdapter.getItem(position));
                        mDb.imageDao().deleteImage(mAdapter.getItem(position).getId());
                        mDb.imageLinkDao().deleteImagelink(mAdapter.getItem(position).getId());
                    }
                });
            }
        }).attachToRecyclerView(mBinding.recyclerview);

        mViewModel.getProjects().observe(this, new Observer<List<ProjectPojo>>() {
            @Override
            public void onChanged(@Nullable List<ProjectPojo> projectPojos) {
                populateUI(projectPojos);
            }
        });
    }

    private void populateUI(List<ProjectPojo> projectsPojo) {
        mProjectList = new ArrayList<>(Project.getProjectsFromDB(projectsPojo));
        if(mAdapter == null) {
            mAdapter = new ProjectsAdapter(getActivity(), mProjectList);
            mBinding.recyclerview.setAdapter(mAdapter);
        } else {
            mAdapter.setNewProjectList(mProjectList);
            mBinding.recyclerview.setAdapter(mAdapter);
        }

        if (mProjectList == null || mProjectList.size() == 0 ){
            mBinding.textnofavorite.setVisibility(View.VISIBLE);
        }

        mBinding.swipeRefreshLayout.setRefreshing(false);
        mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
    }

    private void handleCall(){
        if(typeCode != null) {
            if (typeCode.equals(getString(R.string.menu_home))) {
                callFeatured();
            } else if (typeCode.equals(getString(R.string.menu_prj))) {
                callProjects();
            }  else if (typeCode.equals(getString(R.string.menu_fav))) {
                setupViewModel();
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

    public static void insertData(Project project) {
        mViewModel.insert(project);
    }

    public static void deleteData(Project project) {
        mViewModel.delete(project);
    }
}
