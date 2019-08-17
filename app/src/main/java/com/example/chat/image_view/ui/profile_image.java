package com.example.chat.image_view.ui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.chat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class profile_image extends AppCompatActivity {

    ImageView imageView;
    FirebaseUser c_user;
    FirebaseDatabase firebaseDatabasep;
    DatabaseReference databaseReferencep;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_image);


        mAuth = FirebaseAuth.getInstance();
        c_user = mAuth.getCurrentUser();
        firebaseDatabasep = FirebaseDatabase.getInstance();
        databaseReferencep = firebaseDatabasep.getReference("users");

        Query query = databaseReferencep.orderByChild("email").equalTo(c_user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String image = "" + ds.child("image").getValue();


                Picasso.get().load(image).into(imageView);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.save_image) {


        }
        return super.onOptionsItemSelected(item);

    }
}


