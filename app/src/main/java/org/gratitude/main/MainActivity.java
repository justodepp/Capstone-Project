package org.gratitude.main;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import org.gratitude.R;
import org.gratitude.ui.OrganizationsFragment;
import org.gratitude.ui.ProjectsFragment;
import org.gratitude.ui.ThemesFragment;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity {

    public static final String ARGUMENT_TYPE_CODE = "typeCode";

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;

    private Fragment fragment = null;
    private Bundle bundle;

    // Make sure to be using android.support.v7.app.ActionBarDrawerToggle version.
    // The android.support.v4.app.ActionBarDrawerToggle has been deprecated.
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        setupToolbar();
        setupDrawer();
        showHomeFragment();
    }

    private void showHomeFragment() {
        Fragment fragment;
        Class fragmentClass = ProjectsFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
            replaceFragmentWithTransition(getFragmentBundleType(
                    mNavigationView.getMenu().findItem(R.id.menu_home).getTitle().toString()),
                    fragment);
            setTitle(getString(R.string.menu_home));
        } catch (Exception e) {
            Timber.e(e);
        }
    }

    //region Fragment
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
//        Fragment fragment = null;
//        Bundle bundle;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.menu_home:
                fragmentClass = ProjectsFragment.class;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_cat:
                fragmentClass = ThemesFragment.class;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_org:
                fragmentClass = OrganizationsFragment.class;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_prj:
                fragmentClass = ProjectsFragment.class;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_about:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            case R.id.menu_settings:
                fragmentClass = null;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
                break;
            default:
                // Home
                fragmentClass = ProjectsFragment.class;
                bundle = getFragmentBundleType(menuItem.getTitle().toString());
        }

        try {
            assert fragmentClass != null;
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            Timber.e(e);
        }

        mDrawer.closeDrawer(GravityCompat.START);
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void replaceFragmentWithTransition(final Bundle bundle, final Fragment fragment) {
        if( bundle != null && fragment != null) {
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content_frame, fragment)
                    .commitNow();
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
                        replaceFragmentWithTransition(bundle, fragment);
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
        } else {
            super.onBackPressed();
        }
    }
    //endregion
}
