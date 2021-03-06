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
import com.firebase.geofire.LocationCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Per_Parking_lot_Details extends AppCompatActivity {

    private TextView lot_id;
    private EditText lotName;
    private EditText capacity;
    private EditText ownerName;
    private EditText longi;
    private EditText lati;
    private EditText phoneNumber;
    private EditText requestsEditText;
    private Button changeDetailsButton;
    private DatabaseReference databaseReference;
    private FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();

    Parking_Lot_Details parking_lot_details=new Parking_Lot_Details();
    private String clickId;
    boolean firstClick=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per__parking_lot__details);

        //reference to widgets
        lot_id=findViewById(R.id.lot_idEditText);
        lotName=findViewById(R.id.lotNameEditText);
        capacity=findViewById(R.id.capacityEditText);
        ownerName=findViewById(R.id.ownerEditText);
        phoneNumber=findViewById(R.id.phoneNumberEditText);
        changeDetailsButton=findViewById(R.id.changeDetailsButton);
        longi=findViewById(R.id.longEditText);
        lati=findViewById(R.id.latEditText);
        requestsEditText=findViewById(R.id.requestsEditText);

        //The id of the parking lot clicked in the listView is saved in the variable clickId
        Intent intent=getIntent();
        clickId=intent.getStringExtra("Click ID");
        Log.i("intent",clickId);

        databaseReference = mDatabase.getReference();

        if(clickId!=null) {

            databaseReference.child("Parking Lots").child(clickId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                      parking_lot_details= dataSnapshot.getValue(Parking_Lot_Details.class);
                        lot_id.setText(clickId);
                        capacity.setText(parking_lot_details.getCapacity());
                        phoneNumber.setText(parking_lot_details.getPhoneNumber());
                        lotName.setText(parking_lot_details.getLotName());
                        ownerName.setText(parking_lot_details.getOwnerName());


                        //Retrieve requests
                        retrieveRequest();

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

    private void retrieveRequest() {
        final String lot_email= parking_lot_details.getEmail().replace(".","");



        databaseReference.child("Requests").child(lot_email).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    //Counts number of requests if that reference exists
                    long requestsMade = dataSnapshot.getChildrenCount();
                    Log.i(dataSnapshot.getKey(), String.valueOf(requestsMade));

                    requestsEditText.setText(Long.toString(requestsMade));




                    }else{
                    requestsEditText.setText("0");


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void retrieveCoordinates() {

        databaseReference = mDatabase.getReference("Coordinates");
        //Log.i("TAG",clickId);
        GeoFire geoFire= new GeoFire(databaseReference);
        geoFire.getLocation(clickId, new LocationCallback() {
            @Override
            public void onLocationResult(String key, GeoLocation location) {
                if (location != null) {
                    Log.i("TAG",String.format("The location for key %s is [%f,%f]", key, location.latitude, location.longitude));

                    longi.setText(Double.toString(location.longitude));
                    lati.setText(Double.toString(location.latitude));

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


            longi.setEnabled(true);
            longi.setFocusableInTouchMode(true);

            lati.setEnabled(true);
            lati.setFocusableInTouchMode(true);



            changeDetailsButton.setText("Save");
            firstClick=false;
        }else if(!firstClick){

            databaseReference = mDatabase.getReference();

            databaseReference.child("Parking Lots").child(parking_lot_details.getLot_id()).child("capacity").setValue(capacity.getText().toString());
            databaseReference.child("Parking Lots").child(parking_lot_details.getLot_id()).child("ownerName").setValue(ownerName.getText().toString());
            databaseReference.child("Parking Lots").child(parking_lot_details.getLot_id()).child("lotName").setValue(lotName.getText().toString());
            databaseReference.child("Parking Lots").child(parking_lot_details.getLot_id()).child("phoneNumber").setValue(phoneNumber.getText().toString());


            updateLocation();


            Log.i("second Click","Second Click");
            changeDetailsButton.setText("update details");

            //capacity.setFocusableInTouchMode(false);

            //rendering the editTexts non-editable
            capacity.setEnabled(false);
            lotName.setEnabled(false);
            ownerName.setEnabled(false);
            phoneNumber.setEnabled(false);
            longi.setEnabled(false);
            lati.setEnabled(false);



            Toast.makeText(getApplicationContext(),"Details updated",Toast.LENGTH_LONG).show();
            firstClick=true;
        }
    }

    private void updateLocation() {
        //Log.i("TAG",clickId);
       // databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Coordinates").child(parking_lot_details.getLot_id()).removeValue();
        databaseReference =mDatabase.getReference("Coordinates");
        GeoFire geoFire= new GeoFire(databaseReference);
        geoFire.setLocation(clickId,new GeoLocation(Double.parseDouble(longi.getText().toString()),
                Double.parseDouble(lati.getText().toString())));
    }

    public void removeLot(View view){

        databaseReference = mDatabase.getReference();
        databaseReference.child("Parking Lots").child(parking_lot_details.getLot_id()).removeValue();
        databaseReference.child("Coordinates").child(parking_lot_details.getLot_id()).removeValue();

        Intent intent=new Intent(Per_Parking_lot_Details.this,Parking_locations.class);
        startActivity(intent);

    }
}
