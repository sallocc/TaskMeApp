package com.cs250.joanne.myfragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment addTask;
    private Fragment currentTasks;
    private Fragment completedTasks;
    private FragmentTransaction transaction;
    protected CurrentTaskAdapter currentTaskAdapter;
    protected CurrentTaskAdapter completedTaskAdapter;
    protected Toolbar toolbar;
    protected ArrayList<Task> myCurrentTasks;
    protected ArrayList<Task> myCompletedTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.current_tasks);
        setSupportActionBar(toolbar);

        // create ArrayList of items
        myCurrentTasks = new ArrayList<Task>();
        myCompletedTasks = new ArrayList<Task>();
        // make array adapter to bind arraylist to listview with custom item layout
        currentTaskAdapter = new CurrentTaskAdapter(this, R.layout.task_layout, myCurrentTasks);
        completedTaskAdapter = new CurrentTaskAdapter(this, R.layout.task_layout, myCompletedTasks);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        addTask = new AddTasksFrag();
        currentTasks = new TasksListFrag(currentTaskAdapter);
        completedTasks = new TasksListFrag(completedTaskAdapter);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, currentTasks).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Button btn = (Button) findViewById(R.id.addButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setTitle(R.string.task_update);
                transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, addTask);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        } else if (id == R.id.currenttasks_frag) {
            toolbar.setTitle(R.string.current_tasks);
            transaction = getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, currentTasks);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();
        } else if (id == R.id.completedtasks_frag) {
            toolbar.setTitle(R.string.completed_tasks);
            transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.fragment_container, completedTasks);
            transaction.addToBackStack(null);

// Commit the transaction
            transaction.commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
