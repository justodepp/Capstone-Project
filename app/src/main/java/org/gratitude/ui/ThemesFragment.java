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
import org.gratitude.data.model.themes.Theme;
import org.gratitude.data.model.themes.Themes;
import org.gratitude.databinding.FragmentThemeListBinding;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ThemesAdapter;

import timber.log.Timber;

public class ThemesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ThemesAdapter.ThemeClickListener{

    private ThemesAdapter mAdapter;
    FragmentThemeListBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_theme_list, container, false);
        mBinding.progressBar.indeterminateBar.setVisibility(View.VISIBLE);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        callTheme();
    }

    @Override
    public void onRefresh() {
        callTheme();
    }

    private void callTheme(){
        Theme.getThemes(getContext(), new ResponseInterface<Themes>() {
            @Override
            public void onResponseLoaded(Themes object) {
                mAdapter = new ThemesAdapter(getActivity(), object.getTheme(),ThemesFragment.this);
                mBinding.recyclerview.setAdapter(mAdapter);

                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
                mBinding.swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponseFailed() {
                Timber.e("Error retriving data");
            }
        });
    }

    @Override
    public void onClickThemeItem(Theme theme) {

    }
}
