package org.gratitude.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.billingclient.api.SkuDetails;
import com.google.firebase.auth.FirebaseAuth;

import org.gratitude.R;
import org.gratitude.databinding.ActivityMainBinding;
import org.gratitude.main.billing.BillingHandler;
import org.gratitude.ui.LoginActivity;
import org.gratitude.ui.OrganizationsFragment;
import org.gratitude.ui.ProjectsFragment;
import org.gratitude.ui.ThemesFragment;
import org.gratitude.utils.GlideApp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements BillingHandler.BillingCallbacks{

    private static final int RC_SIGN_IN = 343;
    public static final String ARGUMENT_TYPE_CODE = "typeCode";
    public static final String ARGUMENT_FAVORITE = "favorite";

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private Menu mNavMenu;

    private boolean fromLogin = false;
    private boolean forceLoad = false;

    private Fragment mPreviousFragment = null;
    private Fragment mFragment;
    private Class mFragmentClass;
    private Bundle mBundle;
    private Bundle outState;

    private TextView mHeaderEmailText;
    private TextView mHeaderNameText;
    private ImageView mHeaderImage;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle mDrawerToggle;

    ActivityMainBinding mBinding;

    private BillingHandler mBilling;
    private static final List<String> SKU = Arrays.asList("donation.regular", "donation.large");
    private boolean mHideSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        outState = savedInstanceState;

        init();

        if(outState == null) {
            showHomeFragment();
        }
        checkSignedIn();
    }

    private void hideLogin(){
        mNavMenu.findItem(R.id.menu_login).setVisible(false);
        mNavMenu.findItem(R.id.menu_logout).setVisible(true);
    }

    private void hideLogout(){
        mNavMenu.findItem(R.id.menu_login).setVisible(true);
        mNavMenu.findItem(R.id.menu_logout).setVisible(false);

        mHeaderNameText.setVisibility(View.INVISIBLE);
        mHeaderEmailText.setVisibility(View.INVISIBLE);
        mHeaderImage.setVisibility(View.INVISIBLE);
    }

    private void checkSignedIn() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            hideLogin();
            // already signed in
            String name, email;
            if(auth.getCurrentUser().getDisplayName() == null
                    || auth.getCurrentUser().getDisplayName().equals("")
                    && (auth.getCurrentUser().getPhoneNumber() != null
                    || !auth.getCurrentUser().getPhoneNumber().equals(""))) {
                name = auth.getCurrentUser().getPhoneNumber();
            } else {
                name = auth.getCurrentUser().getDisplayName();
            }
            if(auth.getCurrentUser().getEmail() == null
                    || auth.getCurrentUser().getEmail().equals("")) {
                email = name;
                name = "";
            } else {
                email = auth.getCurrentUser().getEmail();
            }
            setHeader(name, email, auth.getCurrentUser().getPhotoUrl());
        } else {
            hideLogout();
            if (outState == null)
                // not signed in
                startActivityForResult(new Intent(this, LoginActivity.class), RC_SIGN_IN);
        }
    }

    private void setHeader(String name, String email, Uri img_path) {
        View header = mNavigationView.getHeaderView(0);

        mHeaderNameText = header.findViewById(R.id.nav_header_text_name);
        mHeaderEmailText = header.findViewById(R.id.nav_header_text);
        mHeaderImage = header.findViewById(R.id.profile_image);

        if(name != null || email != null) {
            mHeaderNameText.setText(name);
            mHeaderEmailText.setText(email);
            GlideApp.with(this)
                    .load(img_path)
                    .error(R.mipmap.ic_launcher_foreground)
                    .dontAnimate()
                    .into(mHeaderImage);

            mHeaderNameText.setVisibility(View.VISIBLE);
            mHeaderEmailText.setVisibility(View.VISIBLE);
            mHeaderImage.setVisibility(View.VISIBLE);
        } else {
            mHeaderNameText.setVisibility(View.INVISIBLE);
            mHeaderEmailText.setVisibility(View.INVISIBLE);
            mHeaderImage.setVisibility(View.INVISIBLE);
        }
    }

    private void init() {
        setupToolbar();
        setupDrawer();

        mBilling = new BillingHandler(this, this, SKU);
    }

    private void showHomeFragment() {
        mFragmentClass = ProjectsFragment.class;
        try {
            mFragment = (Fragment) mFragmentClass.newInstance();
            mBundle = getFragmentBundleType(
                    mNavigationView.getMenu().findItem(R.id.menu_home).getTitle().toString());
            replaceFragmentWithTransition(mBundle, mFragment);
            setTitle(getString(R.string.menu_home));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private void showDonation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.donate_title)
                .setMessage(R.string.donate_desc)
                .setNeutralButton(android.R.string.cancel, null);

        List<SkuDetails> skus = new ArrayList<>(getSkus());
        Collections.sort(skus, new Comparator<SkuDetails>() {
            @Override
            public int compare(SkuDetails o1, SkuDetails o2) {
                return Long.compare(o1.getPriceAmountMicros(), o2.getPriceAmountMicros());
            }
        });

        for (int i = 0; i < skus.size() && i < 2; i++) {
            final SkuDetails sku = skus.get(i);

            String price = sku.getPrice();
            DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    buy(sku);
                }
            };

            if (i == 0) {
                // Cheapest IAP
                builder.setNegativeButton(price, listener);
            } else {
                // More expensive IAP
                builder.setPositiveButton(price, listener);
            }
        }
        builder.create().show();
    }

    private void buy(SkuDetails sku) {
        mBilling.buy(sku);
    }

    private Collection<SkuDetails> getSkus() {
        return mBilling.getSkus();
    }

    //region Fragment
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        // clear stack every time click on drawer menu
        getSupportFragmentManager().popBackStackImmediate();

        Class fragmentClass = null;
        switch(menuItem.getItemId()) {
            case R.id.menu_home:
                forceLoad = true;
                fragmentClass = ProjectsFragment.class;
                mFragmentClass = fragmentClass;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_cat:
                fragmentClass = ThemesFragment.class;
                mFragmentClass = fragmentClass;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_org:
                fragmentClass = OrganizationsFragment.class;
                mFragmentClass = fragmentClass;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_prj:
                forceLoad = true;
                fragmentClass = ProjectsFragment.class;
                mFragmentClass = fragmentClass;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_fav:
                fragmentClass = ProjectsFragment.class;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                mBundle.putBoolean(ARGUMENT_FAVORITE, true);
                break;
            case R.id.menu_about:
                fragmentClass = null;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_settings:
                fragmentClass = null;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_support:
                showDonation();
                break;
            case R.id.menu_login:
                fromLogin = forceLoad = true;
                startActivityForResult(new Intent(this, LoginActivity.class), RC_SIGN_IN);
                replaceFragmentWithTransition(mBundle, mFragment);
                break;
            case R.id.menu_logout:
                fragmentClass = mFragmentClass;
                LoginActivity.signOut(this);
                hideLogout();
                break;
            default:
                // Home
                fragmentClass = ProjectsFragment.class;
                mBundle = getFragmentBundleType(menuItem.getTitle().toString());
        }

        try {
            assert fragmentClass != null;
            mFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Timber.e(e);
        }

        mDrawer.closeDrawer(GravityCompat.START);
        if (!menuItem.getTitle().equals(getString(R.string.menu_login))
                && !menuItem.getTitle().equals(getString(R.string.menu_logout))) {
            // Highlight the selected item has been done by NavigationView
            // menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
        }

        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void replaceFragmentWithTransition(final Bundle bundle, final Fragment fragment) {
        if(bundle != null && fragment != null
                && (mPreviousFragment != fragment || forceLoad)) {
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content_frame, fragment)
                    .addToBackStack(null)
                    .commit();

            mPreviousFragment = fragment;
            forceLoad = false;
        }
    }

    @NonNull
    private Bundle getFragmentBundleType(final String typeCode) {
        final Bundle bundle = new Bundle();
        bundle.putString(ARGUMENT_TYPE_CODE, typeCode);
        return bundle;
    }
    //endregion

    //region Toolbar
    private void setupToolbar() {
        // Set a Toolbar to replace the ActionBar.
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        handleBackButton();
    }

    private void handleBackButton() {
        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if(fromLogin && getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                    fromLogin = false;
                } else if(fromLogin && !forceLoad){
                    fromLogin = false;
                }
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    // show back button
                    mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                    mDrawerToggle.setDrawerIndicatorEnabled(false);
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
                    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                } else {
                    // show hamburger
                    mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                    mDrawerToggle.setDrawerIndicatorEnabled(true);
                    mDrawerToggle.syncState();
                    mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDrawer.openDrawer(GravityCompat.START);
                        }
                    });
                }
            }
        });
    }
    //endregion

    //region Drawer
    private void setupDrawer() {
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawer.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                        replaceFragmentWithTransition(mBundle, mFragment);
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        mNavigationView = findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(mNavigationView);

        setHeader(null, null, null);

        mNavMenu = mNavigationView.getMenu();
    }
    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.drawer_open,  R.string.drawer_close);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    //endregion

    //region Override
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE 1: Make sure to override the method with only a single `Bundle` argument
    // Note 2: Make sure you implement the correct `onPostCreate(Bundle savedInstanceState)` method.
    // There are 2 signatures and only `onPostCreate(Bundle state)` shows the hamburger icon.
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else if (getSupportFragmentManager().getBackStackEntryCount() == 1)
            finish();
        else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                if(data == null
                        || !data.getExtras().getString(LoginActivity.SKIPPED).equals(LoginActivity.SKIPPED)) {
                    checkSignedIn();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        mBilling.destroy();
        super.onDestroy();
    }

    @Override
    public void onStateChanged(boolean connected) {
        Timber.w("onStateChanged " + connected);
    }

    @Override
    public void onPurchased(SkuDetails sku, boolean isNew) {
        Timber.w("onPurchased " + sku.getSku() + " " + isNew);
        if (!isNew) {
            if (mHideSnackbar) {
                return;
            } else {
                mHideSnackbar = true;
            }
        }
        Snackbar.make(mBinding.contentMain.contentFrame,
                isNew ? R.string.donate_new : R.string.donate_history,
                Snackbar.LENGTH_LONG).show();
    }
    //endregion
}
