package com.example.larry.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Per_Parking_lot_Details extends AppCompatActivity {

    private TextView lot_id;
    private TextView lotName;
    private EditText capacity;
    private TextView ownerName;
    private TextView coordinates;
    private TextView phoneNumber;
    private Button changeDetailsButton;
    private DatabaseReference mDatabase;
    private String clickId;
    boolean firstClick=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per__parking_lot__details);
        lot_id=findViewById(R.id.lot_idTextView);
        lotName=findViewById(R.id.lotNameTextView);
        capacity=findViewById(R.id.capacityEditText);
        ownerName=findViewById(R.id.ownerTextView);
        phoneNumber=findViewById(R.id.phoneNumberTextView);
        changeDetailsButton=findViewById(R.id.changeDetailsButton);
        coordinates=findViewById(R.id.coordinatesEditText);

        Intent intent=getIntent();
        clickId=intent.getStringExtra("Click ID");
        Log.i("intent",clickId);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        if(clickId!=null) {

            mDatabase.child("Parking Lots").child(clickId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        Parking_Lot_Details parking_lot_details= dataSnapshot.getValue(Parking_Lot_Details.class);
                        lot_id.setText(clickId);
                        capacity.setText(parking_lot_details.getCapacity());
                        phoneNumber.setText(parking_lot_details.getPhoneNumber());
                        lotName.setText(parking_lot_details.getLotName());
                        ownerName.setText(parking_lot_details.getOwnerName());
      //                coordinates.setText(parking_lot_details.getLocation().toString());

                        //Retrieve coordinates
                        retrieveCoordinates();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }else{

            Log.i("Error","invalid click");
        }


    }

    private void retrieveCoordinates() {

        mDatabase= FirebaseDatabase.getInstance().getReference("Parking Lots").child(clickId);
        //Log.i("TAG",clickId);
        GeoFire geoFire= new GeoFire(mDatabase);
        geoFire.getLocation("coordinates", new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                if (location != null) {
                    Log.i("TAG",String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));
                } else {
                    Log.i("TAG",String.format("There is no location for key %s in GeoFire", key));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.err.println("There was an error getting the GeoFire location: " + databaseError);
            }
        });

    }




    public void updateDetails(View view){

        if(firstClick) {
            //allow edits
            lotName.setEnabled(true);
            lotName.setFocusableInTouchMode(true);

            ownerName.setEnabled(true);
            ownerName.setFocusableInTouchMode(true);

            capacity.setEnabled(true);
            capacity.setFocusableInTouchMode(true);

            phoneNumber.setEnabled(true);
            phoneNumber.setFocusableInTouchMode(true);

            changeDetailsButton.setText("Save");
            firstClick=false;
        }else if(!firstClick){



            mDatabase.child("Parking Lots").child(clickId).child("capacity").setValue(capacity.getText().toString());
            mDatabase.child("Parking Lots").child(clickId).child("ownerName").setValue(ownerName.getText().toString());
            mDatabase.child("Parking Lots").child(clickId).child("lotName").setValue(lotName.getText().toString());
            mDatabase.child("Parking Lots").child(clickId).child("phoneNumber").setValue(phoneNumber.getText().toString());

            Log.i("second Click","Second Click");
            changeDetailsButton.setText("update details");
            //capacity.setFocusableInTouchMode(false);
            capacity.setEnabled(false);

            Toast.makeText(getApplicationContext(),"Capacity of lot updated",Toast.LENGTH_LONG).show();
            firstClick=true;
        }
    }

    public void removeLot(View view){

        mDatabase.child("Parking Lots").child(clickId).removeValue();

        Intent intent=new Intent(Per_Parking_lot_Details.this,Parking_locations.class);
        startActivity(intent);

    }
}
