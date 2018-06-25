package org.gratitude.ui.detailOrganization;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.gratitude.R;
import org.gratitude.data.model.organization.Organization;
import org.gratitude.databinding.FragmentDetailOrganizationBinding;
import org.gratitude.main.interfaces.ResponseInterface;
import org.gratitude.ui.OrganizationsFragment;

import timber.log.Timber;

public class DetailsOrganizationFragment extends Fragment {

    FragmentDetailOrganizationBinding mBinding;

    private Bundle aboutBundle = new Bundle();
    private Bundle themesBundle = new Bundle();
    private Bundle prjBundle = new Bundle();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_organization, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String bridgeId = "";
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.getString(OrganizationsFragment.ORG_CLICKED) != null)
            bridgeId = bundle.getString(OrganizationsFragment.ORG_CLICKED);

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.org_menu_about:
                        showAbout();
                        break;
                    case R.id.org_menu_theme:
                        showThemes();
                        break;
                    case R.id.org_menu_prj:
                        showPrj();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        callDetailsOrganization(bridgeId);
    }

    private void showPrj() {
    }

    private void showThemes() {
    }

    private void showAbout() {
        Fragment aboutFragment = new AboutFragment();
        if( aboutBundle != null) {
            aboutFragment.setArguments(aboutBundle);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.org_content_frame, aboutFragment)
                    .commit();
        }
    }

    private void callDetailsOrganization(String bridgeId){
        Organization.getOrganizationByBridgeId(getContext(), bridgeId, new ResponseInterface<Organization>() {
            @Override
            public void onResponseLoaded(Organization object) {
                String address = contactBuilder(object);

                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_IMAGE, object.getLogoUrl());
                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_NAME, object.getName());
                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_MISSION, object.getMission());
                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_ADDRESS, address);
                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_URL, object.getUrl());
                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_TOTAL, String.valueOf(object.getTotalProjects()));
                aboutBundle.putString(AboutFragment.DETAIL_ORG_ABOUT_ACTIVE, String.valueOf(object.getActiveProjects()));

                themesBundle.putParcelable("", object.getThemes());
                prjBundle.putString("", String.valueOf(object.getId()));

                mBinding.progressBar.indeterminateBar.setVisibility(View.GONE);
                showAbout();
            }

            @Override
            public void onResponseFailed() {
                Timber.e("Error retriving data");
            }
        });
    }

    private String contactBuilder(Organization org){
        return org.getAddressLine1().concat("\n").concat(org.getAddressLine2()).concat("\n")
                .concat(org.getCity()).concat(",").concat(org.getPostal()).concat("\n")
                .concat(org.getState()).concat("\n").concat(org.getCountry());
    }
}