package com.cs250.joanne.myfragments;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;


public class TasksListFrag extends Fragment {

    public static final int MENU_ITEM_EDITVIEW = Menu.FIRST;
    public static final int MENU_ITEM_COPY = Menu.FIRST + 1;
    public static final int MENU_ITEM_DELETE = Menu.FIRST + 2;
    boolean isCompleted;
    private ListView myList;
    private MainActivity myact;
    private TaskAdapter adapter;
    Context cntx;
    private static final String TAG = "MainActivity";

    public TasksListFrag(TaskAdapter adapter, boolean isCompleted) {
        this.adapter = adapter;
        this.isCompleted = isCompleted;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.taskslist_frag, container, false);

        cntx = getActivity().getApplicationContext();

        myact = (MainActivity) getActivity();
        myList = myview.findViewById(R.id.mylist);
        // connect listview to the array adapter in MainActivity

        myList.setAdapter(adapter);
        registerForContextMenu(myList);
        // refresh view
        adapter.notifyDataSetChanged();

        // program a short click on the list item
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Snackbar.make(view, "Selected #" + id, Snackbar.LENGTH_SHORT)
                       // .setAction("Action", null).show();
                Log.d(TAG, "onClick: opening dialog.");
              //  if MyCustomDialog.

                MyCustomDialog dialog = new MyCustomDialog();
                dialog.task = (Task) parent.getItemAtPosition(position);
                dialog.isCompleted = isCompleted;
                dialog.show(myact.getFragmentManager(), "MyCustomDialog");
            }
        });

        return myview;
    }

    // for a long click on a menu item use ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // create menu in code instead of in xml file (xml approach preferred)
        menu.setHeaderTitle("Select Item");

        // Add menu items
        menu.add(0, MENU_ITEM_EDITVIEW, 0, R.string.menu_editview);
        menu.add(0, MENU_ITEM_COPY, 0, R.string.menu_copy);
        menu.add(0, MENU_ITEM_DELETE, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        AdapterView.AdapterContextMenuInfo menuInfo;
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = menuInfo.position; // position in array adapter


        switch (item.getItemId()) {
            case MENU_ITEM_EDITVIEW: {

                Toast.makeText(cntx, "edit request",
                        Toast.LENGTH_SHORT).show();
                myact.transaction = myact.getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                myact.transaction.replace(R.id.fragment_container, myact.addTask);
                myact.transaction.addToBackStack(null);

// Commit the transaction
                myact.transaction.commit();

                //Add reference to Task for edit task
                String completion = myact.toolbar.getTitle().toString().equals("Current Tasks") ? "current": "completed";
                myact.myPrefs.edit().putInt("taskIndex", index).putString("taskCompletion", completion).apply();
                return true;
            }
            case MENU_ITEM_COPY: {
                Toast.makeText(cntx, "copying task", Toast.LENGTH_SHORT).show();
                Task copyTask = myact.toolbar.getTitle().equals("Current Tasks")? myact.myCurrentTasks.get(index): myact.myCompletedTasks.get(index);
                Task newTask = new Task(copyTask.getName() + " (copy)", copyTask.getCategory(), copyTask.getDate());
                myact.myCurrentTasks.add(newTask);
                myact.taskAdapter.notifyDataSetChanged();
                myact.completedTaskAdapter.notifyDataSetChanged();

                int taskNum = myact.myPrefs.getInt("totalTasks", 0);
                myact.myPrefs.edit().putInt("totalTasks", taskNum + 1).apply();

                return true;
            }
            case MENU_ITEM_DELETE: {
                Task removed = myact.toolbar.getTitle().equals("Current Tasks") ? myact.myCurrentTasks.remove(index): myact.myCompletedTasks.remove(index);
                Toast.makeText(cntx, "job " + index + " deleted",
                        Toast.LENGTH_SHORT).show();
                // refresh view
                myact.taskAdapter.notifyDataSetChanged();
                myact.completedTaskAdapter.notifyDataSetChanged();
                return true;
            }
        }
        return false;
    }


    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d ("Other Fragment2", "onStart");
        // Apply any required UI change now that the Fragment is visible.
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d ("Other Fragment", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d ("Other Fragment", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d ("Other Fragment", "onSaveInstanceState");
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        Log.d ("Other Fragment", "onStop");
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        Log.d ("Other Fragment", "onDestroyView");
        // Clean up resources related to the View.
        super.onDestroyView();
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        Log.d ("Other Fragment", "onDestroy");
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        Log.d ("Other Fragment", "onDetach");
        super.onDetach();
    }
}
