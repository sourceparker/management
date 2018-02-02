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
    private TextView phoneNumber;
    private Button changeCapacityButton;
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
        changeCapacityButton=findViewById(R.id.changeCapacityButton);

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

    public void updateCapacity(View view){

        if(firstClick) {

            capacity.setFocusableInTouchMode(true);
            changeCapacityButton.setText("Save");
            firstClick=false;
        }else if(!firstClick){


            mDatabase.child("Parking Lots").child(clickId).child("capacity").setValue(capacity.getText().toString());
            Log.i("second Click","Second Click");
            changeCapacityButton.setText("update capacity");
            capacity.setFocusableInTouchMode(false);
            firstClick=true;
            Toast.makeText(getApplicationContext(),"Capacity of lot updated",Toast.LENGTH_LONG).show();

        }
    }

    public void removeLot(View view){

        mDatabase.child("Parking Lots").child(clickId).removeValue();

        Intent intent=new Intent(Per_Parking_lot_Details.this,Parking_locations.class);
        startActivity(intent);

    }
}
