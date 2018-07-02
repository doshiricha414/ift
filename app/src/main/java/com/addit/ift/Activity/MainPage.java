package com.addit.ift.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.facebook.AccessToken;
import com.addit.ift.App;
import com.addit.ift.Fragments.PastFragment;
import com.addit.ift.Fragments.RegisterFragment;
import com.addit.ift.Fragments.UpcomingFragment;
import com.addit.ift.R;
import com.facebook.AccessToken;
import com.utils.Utils;

import static com.utils.Utils.setStatusBarColor;

public class MainPage extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private Fragment upcomingFragment = null, registerFragment = null, pastFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Intent intent = getIntent();
        Bundle b1 = intent.getExtras();
        //Toast.makeText(MainPage.this, b1.getString("USER_ID"), Toast.LENGTH_SHORT).show();
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColor(MainPage.this, R.color.transparent);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*CollapsingToolbarLayout collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.toolbar_layout) ;
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        */// Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.palegreen));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent4 = new Intent(MainPage.this, Search_Page.class);
                intent4.putExtra("QUERY", query);
                startActivity(intent4);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logOut) {
            {
                AccessToken.setCurrentAccessToken(null);
                Utils.Logout();

                Intent intent1 = new Intent(MainPage.this, MainActivity.class);
                startActivity(intent1);
            }
        }
        if (id == R.id.update_profile) {
            Intent intent = new Intent(MainPage.this, UpdateProfile.class);
            intent.putExtra("USER_ID", App.Logindetails.USER_ID);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return upcomingFragment == null ? UpcomingFragment.newInstance() : upcomingFragment;
            else if (position == 1)
                return registerFragment == null ? RegisterFragment.newInstance() : registerFragment;
            return pastFragment == null ? PastFragment.newInstance() : pastFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "UPCOMING EVENTS";
                case 1:
                    return "REGISTER";
                case 2:
                    return "PAST EVENTS";
            }
            return null;
        }
    }
}
