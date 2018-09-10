package org.gratitude.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.franmontiel.attributionpresenter.AttributionPresenter;
import com.franmontiel.attributionpresenter.entities.Attribution;
import com.franmontiel.attributionpresenter.entities.Library;
import com.franmontiel.attributionpresenter.entities.License;

import org.gratitude.R;
import org.gratitude.databinding.FragmentInfoBinding;

public class InfoFragment extends Fragment implements View.OnClickListener{

    FragmentInfoBinding mBinding;

    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBundle = this.getArguments();

        mBinding.attributionCard.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.attribution_card) {

            AttributionPresenter attributionPresenter = new AttributionPresenter.Builder(getContext())
                    .addAttributions(
                            new Attribution.Builder("AttributionPresenter")
                                    .addCopyrightNotice("Copyright 2017 Francisco José Montiel Navarro")
                                    .addLicense(License.APACHE)
                                    .setWebsite("https://github.com/franmontiel/AttributionPresenter")
                                    .build()
                    )
                    .addAttributions(
                            new Attribution.Builder("CircleImageView")
                                    .addCopyrightNotice("Copyright 2014 - 2018 Henning Dodenhof")
                                    .addLicense(License.APACHE)
                                    .setWebsite("https://github.com/hdodenhof/CircleImageView")
                                    .build()
                    )
                    .addAttributions(
                            new Attribution.Builder("PhotoView")
                                    .addCopyrightNotice("Copyright 2017 Chris Banes")
                                    .addLicense(License.APACHE)
                                    .setWebsite("https://github.com/chrisbanes/PhotoView")
                                    .build()
                    )
                    .addAttributions(
                            Library.GLIDE,
                            Library.OK_HTTP,
                            Library.GSON,
                            Library.RETROFIT)
                    .build();

            attributionPresenter.showDialog("Open Source Libraries");
        }
    }
}
