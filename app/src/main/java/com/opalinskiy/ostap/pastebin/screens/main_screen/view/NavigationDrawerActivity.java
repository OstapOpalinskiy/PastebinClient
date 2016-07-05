package com.opalinskiy.ostap.pastebin.screens.main_screen.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.opalinskiy.ostap.pastebin.R;
import com.opalinskiy.ostap.pastebin.global.Constants;
import com.opalinskiy.ostap.pastebin.screens.login_screen.view.LoginFragment;
import com.opalinskiy.ostap.pastebin.screens.main_screen.IMainScreen;
import com.opalinskiy.ostap.pastebin.screens.main_screen.presenter.MainScreenPresenter;
import com.opalinskiy.ostap.pastebin.screens.my_pastes_screen.view.MyPastesFragment;
import com.opalinskiy.ostap.pastebin.screens.new_paste_screen.view.NewPasteFragment;
import com.opalinskiy.ostap.pastebin.screens.profile_screen.view.ProfileFragment;
import com.squareup.picasso.Picasso;

public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainScreen.IView {
    private IMainScreen.IPresenter presenter;
    private ImageView avatar;
    private TextView name;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        init();
        presenter.setData(preferences);
        if (savedInstanceState == null) {
            NewPasteFragment fragment = new NewPasteFragment();
            commitFragment(fragment, Constants.MAIN_SCREEN_FRAGMENT_TAG, false);
        }
    }

    protected void init() {
        preferences = getSharedPreferences(Constants.PREFS_NAME, 0);
        presenter = MainScreenPresenter.getInstance(this, this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            avatar = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.iv_header);
            name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_name);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Bundle args = new Bundle();
        int id = item.getItemId();

        if (id == R.id.nav_new_paste) {
            NewPasteFragment fragment = new NewPasteFragment();
            commitFragment(fragment, Constants.MAIN_SCREEN_FRAGMENT_TAG, true);

        } else if (id == R.id.nav_trending) {
            MyPastesFragment fragment = new MyPastesFragment();
            args.putInt(Constants.MY_OR_TRANDING_KEY, Constants.TRENDING_PASTES);
            fragment.setArguments(args);
            commitFragment(fragment, Constants.MY_PASTES_FRAGMENT_TAG, true);

        } else if (id == R.id.nav_my_pastes) {
            MyPastesFragment fragment = new MyPastesFragment();
            args.putInt(Constants.MY_OR_TRANDING_KEY, Constants.MY_PASTES);
            fragment.setArguments(args);
            commitFragment(fragment, Constants.MY_PASTES_FRAGMENT_TAG, true);

        } else if (id == R.id.nav_profile) {
            ProfileFragment fragment = new ProfileFragment();
            commitFragment(fragment, Constants.PROFILE_FRAGMENT_TAG, false);
        } else if (id == R.id.nav_log_in) {
            LoginFragment fragment = new LoginFragment();
            commitFragment(fragment, Constants.LOGIN_FRAGMENT_TAG, false);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    public void commitFragment(Fragment fragment, String tag, boolean addToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction
                .replace(R.id.container, fragment, tag)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    @Override
    public void setAvatar(String url) {
        Picasso.
                with(this).
                load(url).
                into(avatar);
    }

    @Override
    public void setUserName(String userName) {
        name.setText(userName);
    }

    @Override
    public void setLoginScreen() {
        avatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.guest_white));
        name.setText(R.string.guest);
        commitFragment(new LoginFragment(), Constants.LOGIN_FRAGMENT_TAG, true);
    }

    @Override
    public void setGuest() {
        avatar.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.guest_white));
    }


    @Override
    public void setProfileScreen() {
        presenter.setData(preferences);
        commitFragment(new ProfileFragment(), Constants.PROFILE_FRAGMENT_TAG, true);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(NavigationDrawerActivity.this, msg,
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public IMainScreen.IPresenter getPresenter() {
        return presenter;
    }

}
