package com.gic.itc.tourest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gic.itc.tourest.fragment.FragmentDrawer;
import com.gic.itc.tourest.fragment.FragmentMainTab;
import com.gic.itc.tourest.fragment.FragmentProfile;
import com.gic.itc.tourest.fragment.FragmentUserProfile;
import com.gic.itc.tourest.test.TestActivity;


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    //@Bind(R.id.viewPager) ViewPager mViewPager;
    private FragmentDrawer mDrawerFragment;//
    @Bind(R.id.toolbar) Toolbar mToolbar;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        mDrawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mDrawerFragment.setDrawerListener(this);
        displayView(0);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case -2:

            case -1:
                fragment = new FragmentProfile();
                title = "Test";
                break;
            case 0:
                fragment = new FragmentMainTab();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new FragmentUserProfile();
                title = "Find Friends";
                break;
            case 2:
                fragment = new FragmentUserProfile();
                title = "Test";
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.refresh){
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            startActivity(intent);
            return  true;
        }else if (id == R.id.add_post) {
            Intent intent = new Intent(MainActivity.this, TestActivity.class);
            startActivity(intent);
            return true;
        }else if(id == R.id.action_search){
            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
