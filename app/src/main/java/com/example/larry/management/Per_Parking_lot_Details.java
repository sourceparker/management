package com.example.larry.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Per_Parking_lot_Details extends AppCompatActivity {

    private TextView lot_id;
    private TextView lotName;
    private TextView capacity;
    private TextView ownerName;
    private TextView phoneNumber;
    private DatabaseReference mDatabase;
    private String clickId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per__parking_lot__details);
        lot_id=findViewById(R.id.lot_idTextView);
        lotName=findViewById(R.id.lotNameTextView);
        capacity=findViewById(R.id.capacityTextView);
        ownerName=findViewById(R.id.ownerTextView);
        phoneNumber=findViewById(R.id.phoneNumberTextView);

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

                        //Retrieve coordinates
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
    public void removeLot(View view){

        mDatabase.child("Parking Lots").child(clickId).removeValue();
        mDatabase.child("Coordinates").child(clickId).removeValue();
        Intent intent=new Intent(Per_Parking_lot_Details.this,Parking_locations.class);
        startActivity(intent);

    }
}
