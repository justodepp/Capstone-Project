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
import org.gratitude.data.model.themes.Theme;
import org.gratitude.data.model.themes.Themes;
import org.gratitude.databinding.FragmentThemeListBinding;
import org.gratitude.main.MainActivity;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.adapter.ThemesAdapter;
import org.gratitude.utils.ItemClickSupport;

import java.util.Objects;

import timber.log.Timber;

public class ThemesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static final String THEME_CLICKED = "category";

    private ThemesAdapter mAdapter;
    FragmentThemeListBinding mBinding;

    private String typeCode;
    private Bundle bundle;

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

        bundle = this.getArguments();

        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        callTheme();

        ItemClickSupport.addTo(mBinding.recyclerview).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Fragment fragment = new ProjectsFragment();

                bundle.putString(THEME_CLICKED, mAdapter.getItem(position).getId());
                fragment.setArguments(bundle);

                Objects.requireNonNull(getActivity()).setTitle(mAdapter.getItem(position).getName());

                Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                        .beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.content_frame, fragment)
                        .commitNow();
            }
        });
    }

    @Override
    public void onRefresh() {
        callTheme();
    }

    private void callTheme(){
        Theme.getThemes(getContext(), new ResponseInterface<Themes>() {
            @Override
            public void onResponseLoaded(Themes object) {
                mAdapter = new ThemesAdapter(getActivity(), object.getTheme());
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
}
