package org.gratitude.ui.detailOrganization;

import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.databinding.DetailContentOrgBinding;
import org.gratitude.utils.ImageHandler;

public class AboutFragment extends Fragment {

    public static final String DETAIL_ORG_ABOUT_IMAGE = "detail_image";
    public static final String DETAIL_ORG_ABOUT_NAME = "detail_name";
    public static final String DETAIL_ORG_ABOUT_MISSION = "detail_mission";
    public static final String DETAIL_ORG_ABOUT_ADDRESS = "detail_address";
    public static final String DETAIL_ORG_ABOUT_URL = "detail_url";
    public static final String DETAIL_ORG_ABOUT_TOTAL = "detail_total_prj";
    public static final String DETAIL_ORG_ABOUT_ACTIVE = "detail_active_prj";

    DetailContentOrgBinding mBinding;

    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.detail_content_org, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBundle = this.getArguments();

        ImageHandler.detailOrgImageHandler(getContext(), mBinding.includeHeader.orgImageView, mBundle.getString(DETAIL_ORG_ABOUT_IMAGE));
        mBinding.includeHeader.orgNameText.setText(mBundle.getString(DETAIL_ORG_ABOUT_NAME));
        mBinding.includeHeader.orgMissionText.setText(mBundle.getString(DETAIL_ORG_ABOUT_MISSION));

        String total = String.format(getContext().getString(R.string.org_detail_total_prj_text),
                String.valueOf(mBundle.getString(DETAIL_ORG_ABOUT_TOTAL)));
        mBinding.includeFooter.orgTotalPrjText.setText(Html.fromHtml(total));
        String active = String.format(getContext().getString(R.string.org_detail_active_prj_text),
                String.valueOf(mBundle.getString(DETAIL_ORG_ABOUT_ACTIVE)));
        mBinding.includeFooter.orgActivePrjText.setText(Html.fromHtml(active));

        mBinding.includeBody.aboutFirstimage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_location));
        ImageViewCompat.setImageTintList(mBinding.includeBody.aboutFirstimage, ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPrimary)));

        mBinding.includeBody.aboutFirsttext.setText(mBundle.getString(DETAIL_ORG_ABOUT_ADDRESS));

        mBinding.includeBody.aboutSecondimage.setImageDrawable(getActivity().getDrawable(R.drawable.ic_projects));
        ImageViewCompat.setImageTintList(mBinding.includeBody.aboutSecondimage, ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorPrimary)));

        if (mBundle.getString(DETAIL_ORG_ABOUT_URL) != null && !mBundle.getString(DETAIL_ORG_ABOUT_URL).equals(""))
            mBinding.includeBody.aboutSecondtext.setText(mBundle.getString(DETAIL_ORG_ABOUT_URL));
        else {
            mBinding.includeBody.aboutSecondtext.setText(R.string.org_detail_no_url_provided);
        }
    }
}
