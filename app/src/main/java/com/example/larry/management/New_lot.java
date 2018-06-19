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
    private EditText emailEditText;
    private EditText longEditText;
    private EditText latEditText;
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
        longEditText =findViewById(R.id.longEditText);
        latEditText =findViewById(R.id.latEditText);
        phoneNumberEditText=findViewById(R.id.phoneNumberEditText);
        emailEditText=findViewById(R.id.emailEditText);
    }

    //This method pushes all details to the database after making sure none of the fields are left blank
    public void addNewLot(View view){

        if(lotNameEditText.getText().toString().matches("")
                ||ownerNameEditText.getText().toString().matches("")
                ||capacityEditText.getText().toString().matches("")
                || longEditText.getText().toString().matches("")
                ||emailEditText.getText().toString().matches("")
                || latEditText.getText().toString().matches("")


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
                    phoneNumberEditText.getText().toString(),emailEditText.getText().toString(),capacityEditText.getText().toString());


            //All details are pushed to the database using the generated id
            mDatabaseReference.child(id).setValue(parkingLotDetails);



            Double longitude= Double.parseDouble(longEditText.getText().toString());
            Double latitude=Double.parseDouble(latEditText.getText().toString());

            parkingLotDetails.setLotName(lotNameEditText.getText().toString());
            parkingLotDetails.setPhoneNumber(phoneNumberEditText.getText().toString());
            parkingLotDetails.geoLocation(latitude,longitude);



            Toast.makeText(getApplicationContext(),"New Lot saved",Toast.LENGTH_LONG).show();



            //clears entries after everything gets saved

            lotNameEditText.setText("");
            ownerNameEditText.setText("");
            emailEditText.setText("");
            capacityEditText.setText("");
            longEditText.setText("");
            latEditText.setText("");
            phoneNumberEditText.setText("");



        }



    }



}
