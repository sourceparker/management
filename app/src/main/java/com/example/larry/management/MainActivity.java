package com.example.larry.management;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity {

   // private DatabaseReference mDatabase;

    private ListView mainListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainListView = findViewById(R.id.mainListView);


        final ArrayList<String> mainArrayList= new ArrayList<>();
        ArrayAdapter mainArrayAdapter= new ArrayAdapter(this,android.R.layout.simple_list_item_1,mainArrayList);

        mainArrayList.add(0,"Registered Users");
        mainArrayList.add(1,"Parking Locations");
        mainArrayList.add(2,"Requests");

        mainListView.setAdapter(mainArrayAdapter);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0) {
                    Intent intent = new Intent(MainActivity.this, Registered_users.class);
                    startActivity(intent);
                }else if(i==1){
                    Intent intent = new Intent(MainActivity.this, Parking_locations.class);
                    startActivity(intent);
                }else if(i==2){
                    Intent intent = new Intent(MainActivity.this, Requests.class);
                    startActivity(intent);
                }
            }
        });





    }


    //Menu for logout


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.logout){

            UserSession session= new UserSession(getApplicationContext());
            session.setLoggedIn(false);

            startActivity(new Intent(this,SignInActivity.class));
        }


        return super.onOptionsItemSelected(item);
    }
}