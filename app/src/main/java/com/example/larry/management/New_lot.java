package com.example.larry.management;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class New_lot extends AppCompatActivity {
    private EditText lotNameEditText;
    private EditText ownerNameEditText;
    private EditText phoneNumberEditText;
    private EditText capacityEditText;
    private EditText latitudeEditText;
    private EditText longitudeEditText;
    private FirebaseDatabase mDatabase=FirebaseDatabase.getInstance();
    private Parking_Lot_Details parkingLotDetails= new Parking_Lot_Details();

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lot);

        lotNameEditText=(EditText) findViewById(R.id.lotNameEditText);
        ownerNameEditText=(EditText) findViewById(R.id.ownerNameEditText);
        capacityEditText=(EditText) findViewById(R.id.capacityEditText);
        latitudeEditText=(EditText) findViewById(R.id.latitudeEditText);
        longitudeEditText=(EditText) findViewById(R.id.longitudeEditText);
        phoneNumberEditText=(EditText) findViewById(R.id.phoneNumberEditText);
    }

    public void addNewLot(View view){

        if(lotNameEditText.getText().toString().matches("")
                ||ownerNameEditText.getText().toString().matches("")
                ||capacityEditText.getText().toString().matches("")
                /*||latitudeEditText.getText().toString().matches("")
                ||longitudeEditText.getText().toString().matches("")*/
                ) {
            Toast.makeText(getApplicationContext(),"All fields must be filled",Toast.LENGTH_LONG).show();
        }else{

            mDatabaseReference = mDatabase.getReference("Parking Lots");

            String id = mDatabaseReference.push().getKey();

            parkingLotDetails.setLot_id(id);
            
            parkingLotDetails=new Parking_Lot_Details(id,lotNameEditText.getText().toString(),ownerNameEditText.getText().toString(),
                    phoneNumberEditText.getText().toString(),capacityEditText.getText().toString());

            mDatabaseReference.child(id).setValue(parkingLotDetails);

            Double latitude= Double.parseDouble(latitudeEditText.getText().toString());
            Double longitude=Double.parseDouble(longitudeEditText.getText().toString());

            parkingLotDetails.setLotName(lotNameEditText.getText().toString());
            parkingLotDetails.setPhoneNumber(phoneNumberEditText.getText().toString());
            parkingLotDetails.geoLocation(longitude,latitude);



            Toast.makeText(getApplicationContext(),"New Lot saved",Toast.LENGTH_LONG).show();

            lotNameEditText.setText("");
            ownerNameEditText.setText("");
            capacityEditText.setText("");
            latitudeEditText.setText("");
            longitudeEditText.setText("");
            phoneNumberEditText.setText("");



        }



    }


}
