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

        lotNameEditText= findViewById(R.id.lotNameEditText);
        ownerNameEditText= findViewById(R.id.ownerNameEditText);
        capacityEditText= findViewById(R.id.capacityEditText);
        latitudeEditText=findViewById(R.id.latitudeEditText);
        longitudeEditText=findViewById(R.id.longitudeEditText);
        phoneNumberEditText=findViewById(R.id.phoneNumberEditText);
    }

    //This method pushes all details to the database after making sure none of the fields are left blank
    public void addNewLot(View view){

        if(lotNameEditText.getText().toString().matches("")
                ||ownerNameEditText.getText().toString().matches("")
                ||capacityEditText.getText().toString().matches("")
                ||latitudeEditText.getText().toString().matches("")
                ||longitudeEditText.getText().toString().matches("")


                ) {
            Toast.makeText(getApplicationContext(),"All fields must be filled",Toast.LENGTH_LONG).show();
        }else{

            mDatabaseReference = mDatabase.getReference("Parking Lots");

            //The parking lot id is generated and stored in the variable "id"

            String id = mDatabaseReference.push().getKey();


            //The id is set to the id of the parking lot class

            parkingLotDetails.setLot_id(id);


            //The entries are passed to the constructor of Parking Lot Details
            
            parkingLotDetails=new Parking_Lot_Details(id,lotNameEditText.getText().toString(),ownerNameEditText.getText().toString(),
                    phoneNumberEditText.getText().toString(),capacityEditText.getText().toString());


            //All details are pushed to the database using the generated id
            mDatabaseReference.child(id).setValue(parkingLotDetails);

            Double latitude= Double.parseDouble(latitudeEditText.getText().toString());
            Double longitude=Double.parseDouble(longitudeEditText.getText().toString());

            parkingLotDetails.setLotName(lotNameEditText.getText().toString());
            parkingLotDetails.setPhoneNumber(phoneNumberEditText.getText().toString());
            parkingLotDetails.geoLocation(latitude,longitude);


            //random number of the lot owner gets mapped to the lot_id

            parkingLotDetails.checkNum();



            Toast.makeText(getApplicationContext(),"New Lot saved",Toast.LENGTH_LONG).show();



            //clears entries after everything gets saved

            lotNameEditText.setText("");
            ownerNameEditText.setText("");
            capacityEditText.setText("");
            latitudeEditText.setText("");
            longitudeEditText.setText("");
            phoneNumberEditText.setText("");



        }



    }



}
