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
import org.gratitude.data.model.report.Entry;
import org.gratitude.data.model.report.Report;
import org.gratitude.databinding.FragmentReportProjectBinding;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ReportProjectAdapter;

import java.util.ArrayList;

import timber.log.Timber;

public class ReportProjectFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    public static final String REPORT_PRJ_ID = "report_prj_id";

    FragmentReportProjectBinding mBinding;
    private Long projectId;

    private ArrayList<Entry> mEntryList = new ArrayList<>();
    private LinearLayoutManager mLinearLayoutManager;
    private ReportProjectAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_report_project, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.getLong(REPORT_PRJ_ID) != 0)
            projectId = bundle.getLong(REPORT_PRJ_ID);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mBinding.recyclerview.setLayoutManager(mLinearLayoutManager);

        callReport(projectId);
    }

    private void callReport(Long projectId) {
        Report.getReport(getContext(), projectId, new ResponseInterface<Report>() {

            @Override
            public void onResponseLoaded(Report object) {
                mEntryList.clear();
                mEntryList.addAll(object.getEntries());

                if(mAdapter == null) {
                    mAdapter = new ReportProjectAdapter(getActivity(), mEntryList);
                    mBinding.recyclerview.setAdapter(mAdapter);
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

    @Override
    public void onRefresh() {
        resetData();
        callReport(projectId);
    }

    private void resetData(){
        mEntryList.clear();
        mAdapter = null;
    }
}
