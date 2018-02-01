package com.example.larry.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Per_User_Action_Details extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private TextView user_id;
    private TextView name;
    private TextView phoneNumber;
    private TextView email;
    private Button deleteButton;
   private String clickId=" ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_user__action__details);
        user_id=findViewById(R.id.lot_idTextView);
        name=findViewById(R.id.lotNameTextView);
        phoneNumber=findViewById(R.id.ownerTextView);
        email=findViewById(R.id.phoneNumberTextView);
        deleteButton=findViewById(R.id.deleteButton);

        mDatabase= FirebaseDatabase.getInstance().getReference();

        Intent intent=getIntent();
         clickId=intent.getStringExtra("Click ID");
        Log.i("intent",clickId);

        if(clickId!=null) {

            mDatabase.child("isUser").child(clickId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        IsUser isUser = dataSnapshot.getValue(IsUser.class);
                        user_id.setText(clickId);
                        email.setText(isUser.getEmail());
                        phoneNumber.setText(isUser.getPhoneNumber());
                        name.setText(isUser.getFirstName() + " " + isUser.getLastName());
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
    public void removeUser(View view){

        mDatabase.child("isUser").child(clickId).removeValue();
        Intent intent=new Intent(Per_User_Action_Details.this,Registered_users.class);
        startActivity(intent);

    }
}
