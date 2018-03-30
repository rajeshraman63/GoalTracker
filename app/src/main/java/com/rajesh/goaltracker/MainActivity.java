package com.rajesh.goaltracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter; 
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView goal_list;
    private Firebase mRootRef;
    private ProgressDialog mProgressDialog;
    private ArrayList<Goal> arrayListOfGoals = new ArrayList<>();
    private GoalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRootRef = new Firebase("https://goaltracker-375d7.firebaseio.com/");

        mProgressDialog = new ProgressDialog(MainActivity.this);

        goal_list = (ListView)findViewById(R.id.goal_list);
        //adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        adapter = new GoalAdapter(this, arrayListOfGoals);
        goal_list.setAdapter(adapter);


        mProgressDialog.setMessage("Fetching Data..");
        mProgressDialog.show();

        // fetching the data from Firebase

        mRootRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                /* String value = dataSnapshot.getValue().toString();
                items.add(value);
                adapter.notifyDataSetChanged();*/


                Goal goalItem = new Goal();

                String key = dataSnapshot.getKey();    // for the current child
                String value = dataSnapshot.getValue().toString();  // for the current child

                goalItem.setGoalName(key);

                if(dataSnapshot.hasChildren()){  // if the child has it's own children
                    String inString = dataSnapshot.getKey().toString() + ":\n";

                    Iterator<DataSnapshot> dc = dataSnapshot.getChildren().iterator();

                    while (dc.hasNext()) {
                        DataSnapshot dcc = dc.next();
                        inString += "    " + dcc.getKey() + ":" + dcc.getValue() + "\n";
                        goalItem.setAnyKey(dcc.getKey(),dcc.getValue().toString());
                    }

                    //arrayListOfGoals.add(inString);

                    //arrayListOfGoals.add(new Goal("GN","GD","14/03/18","15/03/18"));

                    arrayListOfGoals.add(goalItem);



                }else{
                    //arrayListOfGoals.add(key + " : " + value);
                    arrayListOfGoals.add(new Goal("GN","GD",
                            "14/03/18","15/03/18"));

                }

                adapter.notifyDataSetChanged();
                mProgressDialog.setMessage("Data Fetched");
                mProgressDialog.dismiss();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            //Toast.makeText(getApplicationContext(),"Add Button Clicked",Toast.LENGTH_SHORT).show();
            Intent newGoalIntent = new Intent(MainActivity.this,NewGoalActivity.class);
            startActivity(newGoalIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onPostResume() {
        adapter = new GoalAdapter(this, arrayListOfGoals);
        goal_list.setAdapter(adapter);
        super.onPostResume();
    }
}
