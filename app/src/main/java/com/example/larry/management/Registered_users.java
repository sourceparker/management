package com.example.larry.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Registered_users extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;



    private ListView registered_usersListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_registered_users);
        registered_usersListView= findViewById(R.id.registered_usersListView);



        final ArrayList<String> registered_usersArrayList= new ArrayList<>();
        final ArrayAdapter registered_usersArrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,registered_usersArrayList);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();



        mDatabaseReference.child("isUser").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String username= dataSnapshot.child("firstName").getValue(String.class)+" "+
                        dataSnapshot.child("lastName").getValue(String.class);

                registered_usersArrayList.add(username);
                Log.i("username",registered_usersArrayList.get(0));
                registered_usersListView.setAdapter(registered_usersArrayAdapter);




            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                registered_usersArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                registered_usersArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                registered_usersArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        registered_usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                if(position!=-1) {

                mDatabaseReference.child("isUser").addListenerForSingleValueEvent(new ValueEventListener() {
                   ArrayList<String> keyHolder= new ArrayList<>();
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int i = 0;
                            for(DataSnapshot d : dataSnapshot.getChildren()) {
                                keyHolder.add(d.getKey());
                                i++;
                            }
                            //Log.i("key:",keyHolder.get(0));
                            //Log.i("key:",keyHolder.get(1));
                            Intent intent = new Intent(Registered_users.this, Per_User_Action_Details.class);


                           intent.putExtra("Click ID", keyHolder.get(position));
                            startActivity(intent);
                        }
                    }//onDataChange

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }//onCancelled
                });


                    }


                }
            });
        };

    }


