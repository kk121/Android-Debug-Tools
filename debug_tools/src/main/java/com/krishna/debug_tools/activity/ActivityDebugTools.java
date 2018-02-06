package com.krishna.debug_tools.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.krishna.debug_tools.R;
import com.krishna.debug_tools.data.pojo.DBPojo;
import com.krishna.debug_tools.data.pojo.Table;
import com.krishna.debug_tools.fragment.FragmentDatabaseList;
import com.krishna.debug_tools.fragment.FragmentFilesList;
import com.krishna.debug_tools.fragment.FragmentTablesList;
import com.krishna.debug_tools.utils.DBUtils;

import java.io.File;

public class ActivityDebugTools extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentDatabaseList.Communicator,
        FragmentFilesList.Communicator, FragmentTablesList.Communicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        changeToolbarTitle("Database");
        showAllDatabaseFragment();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_database);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_database) {
            changeToolbarTitle("Database");
            getSupportFragmentManager().popBackStackImmediate();
            showAllDatabaseFragment();
        } else if (id == R.id.nav_shared_pref) {
            getSupportFragmentManager().popBackStackImmediate();
            changeToolbarTitle("SharedPreference");
            showAllSharedPreferencesFragment();
        } else if (id == R.id.nav_files) {
            getSupportFragmentManager().popBackStackImmediate();
            changeToolbarTitle("Files");
            showAllFilesFragment(DBUtils.getFilesPath(ActivityDebugTools.this), false);
        } else if (id == R.id.nav_all) {
            getSupportFragmentManager().popBackStackImmediate();
            changeToolbarTitle("All");
            showOtherFiles();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    private void showAllDatabaseFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FragmentDatabaseList()).commit();
    }

    private void showAllSharedPreferencesFragment() {
        String sharedPrefPath = DBUtils.getSharedPrefsPath(this);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, FragmentFilesList.newInstance(FragmentFilesList.TYPE_SHARED_PREF, sharedPrefPath)).commit();
    }

    private void showAllFilesFragment(String filePath, boolean addToBackStack) {
        if (addToBackStack) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, FragmentFilesList.newInstance(FragmentFilesList.TYPE_FILES, filePath)).addToBackStack(FragmentFilesList.TAG).commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, FragmentFilesList.newInstance(FragmentFilesList.TYPE_FILES, filePath)).commit();
        }
    }

    private void showOtherFiles() {
        String path = DBUtils.getAppPath(this);
        showAllFilesFragment(path, false);
    }

    /*
     Callbacks from fragments
    */
    @Override
    public void showListOfTables(DBPojo dbPojo) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, FragmentTablesList.newInstance(dbPojo)).addToBackStack(FragmentTablesList.TAG).commit();
        changeToolbarTitle("Tables");
    }

    @Override
    public void onFileClick(int fileType, File file) {
        if (fileType == FragmentFilesList.TYPE_FILES) {
            if (file.isDirectory()) {
                showAllFilesFragment(file.getPath(), true);
            } else {
                //handle file click
                openFileInExternalApp(file);
            }
        } else {
            //start shared preference activity
            Intent intent = new Intent(this, ActivitySharedPreference.class);
            intent.putExtra(ActivitySharedPreference.EXTRA_PATH, file.getPath());
            startActivity(intent);
        }
    }

    @Override
    public void onTableSelected(Table table) {
        Intent intent = new Intent(this, ActivityTable.class);
        intent.putExtra(ActivityTable.EXTRA_TABLE, table);
        startActivity(intent);
    }

    private void openFileInExternalApp(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String absoluteFilePath = file.getAbsolutePath();
        Uri fileUri = Uri.parse("content://" + getString(R.string.provider_authority) + "/" + absoluteFilePath);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        String mimeType = DBUtils.getMimeType(file.getPath());
        intent.setDataAndType(fileUri, mimeType);
        startActivity(intent);
    }
}
