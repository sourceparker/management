package com.example.larry.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class Parking_locations extends AppCompatActivity {




    private DatabaseReference mDatabaseReference;
    private ListView parking_locationsListView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.new_lot_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.newlot){
            Intent intent= new Intent(Parking_locations.this,New_lot.class);
            startActivity(intent);
            return true;
        }
        return false;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_locations);
        parking_locationsListView=findViewById(R.id.parking_locationsListView);

        final ArrayList<String>  parking_locationsArrayList= new ArrayList<>();
        final ArrayAdapter  parking_locationsArrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,parking_locationsArrayList);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        mDatabaseReference.child("Parking Lots").orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {


                String lotname= dataSnapshot.child("lotName").getValue(String.class);
                parking_locationsArrayList.add(lotname);
                parking_locationsListView.setAdapter(parking_locationsArrayAdapter);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                parking_locationsArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                parking_locationsArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                parking_locationsArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        parking_locationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                mDatabaseReference.child("Parking Lots").addListenerForSingleValueEvent(new ValueEventListener() {
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
                            Intent intent = new Intent(Parking_locations.this, Per_Parking_lot_Details.class);


                            intent.putExtra("Click ID", keyHolder.get(position));
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
