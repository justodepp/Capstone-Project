package org.gratitude;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.gratitude.ui.ContentFragment;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements Gratitude.OnFinishedListener{

    private static final int DELAY_MILLIS = 250;

    private View mProgressBar;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Gratitude app = (Gratitude) getApplication();
        app.setInterface(MainActivity.this);

        mProgressBar = findViewById(R.id.indeterminateBar);

        init();
    }

    private void init() {
        setupToolbar();
        setupDrawer();
    }

    //region Fragment
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment;
        Bundle bundle;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.menu_home:
                fragmentClass = ContentFragment.class;
                bundle = getFragmentBundleType(menuItem.getItemId());
                break;
            case R.id.menu_cat:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getItemId());
                break;
            case R.id.menu_org:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getItemId());
                break;
            case R.id.menu_prj:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getItemId());
                break;
            case R.id.menu_about:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getItemId());
                break;
            case R.id.menu_settings:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getItemId());
                break;
            default:
                // Home
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getItemId());
        }

        try {
            assert fragmentClass != null;
            fragment = (Fragment) fragmentClass.newInstance();
            replaceFragmentWithDelay(bundle, fragment);
        } catch (Exception e) {
            Timber.e(e);
        }

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    /**
     * We start the transaction with delay to avoid junk while closing the drawer
     */
    private void replaceFragmentWithDelay(@NonNull final Bundle bundle, final Fragment fragment) {
        fragment.setArguments(bundle);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
            }
        }, DELAY_MILLIS);
    }

    @NonNull
    private Bundle getFragmentBundleType(final int typeCode) {
        final Bundle bundle = new Bundle();
        bundle.putInt(ContentFragment.ARGUMENT_TYPE_CODE, typeCode);
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
    }
    //endregion

    //region Drawer
    private void setupDrawer() {
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = setupDrawerToggle();
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        // Setup drawer view
        setupDrawerContent(mNavigationView);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
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
    public void onFinished() {
        mProgressBar.setVisibility(View.GONE);
    }
    //endregion
}
