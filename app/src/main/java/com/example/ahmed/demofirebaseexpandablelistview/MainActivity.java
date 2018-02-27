package com.example.ahmed.demofirebaseexpandablelistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Check";
    // declare the variable needed in activity
    MyExpandableAdapter expandableadapter;
    ExpandableListView expandableListView;
    List<String> headersList;
    HashMap<String, List<String>> itemsMap;

    //Firebase storage references
    private FirebaseDatabase mFirebasedatabase;

    //Task database references:
    private DatabaseReference mTasksDatabaseReference;

    //My Implimentation
    List<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Components
        mFirebasedatabase = FirebaseDatabase.getInstance();
        //For Reading Tasks(Not Using For Now)
        mTasksDatabaseReference = mFirebasedatabase.getReference().child("tasks");

        //Now Saving Tasks(from firebase nodes:task1,task2,task3) to ArrayList and then adding data to HashMap
        itemsMap = new HashMap<String, List<String>>();
        headersList = new ArrayList<>();
        tasks = new ArrayList<>();

        //Getting data from firebase
        getDataFromFirebase();

        //get expandable listview
        expandableListView = (ExpandableListView) findViewById(R.id.expandlist);

        // get expandable list adapter
        expandableadapter = new MyExpandableAdapter(this, headersList, itemsMap);
        //set list adapter to list
        expandableListView.setAdapter(expandableadapter);

        //Refreshing Adapter
        expandableadapter.notifyDataSetChanged();

        //handling the header items click
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MainActivity.this, headersList.get(groupPosition) + "--" + itemsMap.get(headersList.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //get the expand of headersList
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(MainActivity.this, headersList.get(groupPosition), Toast.LENGTH_SHORT).show();
            }
        });
        //get the collapse of headersList
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(MainActivity.this, headersList.get(groupPosition), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Reading Data From firebase
    void getDataFromFirebase(){
        //Firebase Read Listener
        mTasksDatabaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Running Foreach loop
                for(DataSnapshot d : dataSnapshot.getChildren()) {
                    //Getting Value from 1 to 10 in ArrayList(tasks)
                        tasks.add(d.getValue().toString());
                }

                //Putting key & tasks(ArrayList) in HashMap
                itemsMap.put(dataSnapshot.getKey(),tasks);

                headersList.add(dataSnapshot.getKey());

                tasks=new ArrayList<>();

                Log.d(TAG, "onChildAdded: dataSnapshot.getChildren: "+dataSnapshot.getChildren());
                Log.d(TAG, "onChildAdded: KEY"+dataSnapshot.getKey()+" value "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged: "+dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                //task.remove("" + dataSnapshot.getValue());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

       // mTasksDatabaseReference.addChildEventListener(mChildEventListener);
    }

}

