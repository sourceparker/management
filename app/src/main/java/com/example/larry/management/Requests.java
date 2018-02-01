package com.example.larry.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Requests extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private ListView requestsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        requestsListView=findViewById(R.id.requestsListView);


        final ArrayList<String> requestsArrayList= new ArrayList<>();
        final ArrayAdapter requestsArrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,requestsArrayList);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Requests");



        mDatabaseReference.child("reguest_id").child("user_name").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String user_name = dataSnapshot.getValue(String.class);

                requestsArrayList.add(user_name + "'s request");
                Log.i("request id", requestsArrayList.get(0));
                requestsListView.setAdapter(requestsArrayAdapter);


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
            public void onCancelled(DatabaseError databaseError) {

            }
        });





}

}