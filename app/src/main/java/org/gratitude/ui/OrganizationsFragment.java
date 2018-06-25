package org.gratitude.ui;

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
import org.gratitude.data.model.organization.Organization;
import org.gratitude.data.model.organization.Organizations;
import org.gratitude.databinding.FragmentOrganizationListBinding;
import org.gratitude.main.MainActivity;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.OrganizationsAdapter;
import org.gratitude.ui.detailOrganization.DetailsOrganizationFragment;
import org.gratitude.utils.EndlessRecyclerViewScrollListener;
import org.gratitude.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.Objects;

import timber.log.Timber;

public class OrganizationsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String ORG_CLICKED = "org_clicked";

    private OrganizationsAdapter mAdapter;
    FragmentOrganizationListBinding mBinding;
    private String typeCode;

    private ArrayList<Organization> mOrgList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;

    private boolean hasNext;
    private long mNextOrganizationId;

    private EndlessRecyclerViewScrollListener endlessScroll;

    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_organization_list, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = this.getArguments();
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

                    callOrganizations(mNextOrganizationId);
                }
            }
        };

        callOrganizations();

        mBinding.recyclerview.addOnScrollListener(endlessScroll);

        ItemClickSupport.addTo(mBinding.recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Fragment fragment = new DetailsOrganizationFragment();

                bundle.putString(ORG_CLICKED, String.valueOf(mAdapter.getItem(position).getBridgeId()));
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

    @Override
    public void onRefresh() {
        callOrganizations();
    }


    private void callOrganizations(){
        resetData();
        callOrganizations(null);
    }

    private void callOrganizations(Long nextOrganizationId){
        Organization.getOrganizations(getContext(), nextOrganizationId, new ResponseInterface<Organizations>() {
            @Override
            public void onResponseLoaded(Organizations object) {
                hasNext = object.getHasNext();
                mNextOrganizationId = object.getNextOrgId();

                mOrgList.clear();
                mOrgList.addAll(object.getOrganization());

                if(mBinding.itemProgressBar.getVisibility() == View.VISIBLE)
                    mBinding.itemProgressBar.setVisibility(View.GONE);

                if(mAdapter == null) {
                    mAdapter = new OrganizationsAdapter(getActivity(), mOrgList);
                    mBinding.recyclerview.setAdapter(mAdapter);
                } else {
                    mAdapter.setOrganizationList(mOrgList);
                    mAdapter.notifyItemRangeChanged(endlessScroll.getPreviousTotal()+1, mOrgList.size());
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
        mOrgList.clear();
        endlessScroll.resetPreviousTotal();
        mAdapter = null;
    }
}
