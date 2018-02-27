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
import java.util.HashSet;

public class Requests extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private ListView requestsListView;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        requestsListView=findViewById(R.id.requestsListView);

        //an arraylist and arrayadapter to populate the requests listView

        final ArrayList<String> requestsArrayList= new ArrayList<>();
        final ArrayAdapter requestsArrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,requestsArrayList);


        mDatabaseReference = FirebaseDatabase.getInstance().getReference();



        mDatabaseReference.child("Requests").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //gets parking lot_id
               key = dataSnapshot.getKey();

               //uses the key obtained to display the name in the listView

               mDatabaseReference.child("Parking Lots").child(key).addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if (dataSnapshot.exists()) {
                           Parking_Lot_Details parking_lot_details=dataSnapshot.getValue(Parking_Lot_Details.class);
                           Log.i("datasnapshot", dataSnapshot.toString());
                          requestsArrayList.add(parking_lot_details.getLotName());
                           requestsListView.setAdapter(requestsArrayAdapter);
                       }


                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

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



//when the name of the lot is clicked,all requests of that lot are loaded from the database
        requestsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                mDatabaseReference.child("Requests").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                   ArrayList<String> keyHolder= new ArrayList<>();
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            int i = 0;
                            for(DataSnapshot d : dataSnapshot.getChildren()) {
                                keyHolder.add(d.getKey());
                                i++;
                            }
                            Log.i("key:",keyHolder.toString());
                            //Log.i("key:",keyHolder.get(1));
                            Intent intent = new Intent(Requests.this, Per_Lot_Requests.class);


                            intent.putExtra("Requests", keyHolder);
                            intent.putExtra("Lot_id", key);
                           startActivity(intent);
                        }
                    }//onDataChange

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }//onCancelled
                });


            }
        });



}

}