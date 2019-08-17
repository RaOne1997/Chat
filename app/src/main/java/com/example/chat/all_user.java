package com.example.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chat.Adapter.Adpteruser;
import com.example.chat.Adapter.UserModul;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class all_user extends AppCompatActivity {
    RecyclerView recyclerView;
    Adpteruser adpteruser;
    List<UserModul>userModulList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        recyclerView=findViewById(R.id.usera);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Select contact");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
           recyclerView.setLayoutManager(new LinearLayoutManager(this));




        userModulList=new ArrayList<>();
            getAlluser();




        }

    private void getAlluser() {
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference muser = FirebaseDatabase.getInstance().getReference().child("users");

        muser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModulList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    UserModul modul=ds.getValue(UserModul.class);

                    if(!modul.getUid().equals(firebaseUser.getUid())){
                        userModulList.add(modul);

                    }
                adpteruser=new Adpteruser(all_user.this,userModulList);

                    recyclerView.setAdapter(adpteruser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);
        MenuItem menuItem=menu.findItem(R.id.searchm);
        SearchView searchView= (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!TextUtils.isEmpty(s.trim())){
                    searchuser(s);

                }else{
                    getAlluser();
                }

                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {

                if(!TextUtils.isEmpty(s.trim())){
                    searchuser(s);

                }else{
                    getAlluser();
                }

                return false;

            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchuser(final String query) {

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference muser = FirebaseDatabase.getInstance().getReference().child("users");

        muser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userModulList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    UserModul modul=ds.getValue(UserModul.class);

                    if(!modul.getUid().equals(firebaseUser.getUid())){
                        if(modul.getName().toLowerCase().contains(query.toLowerCase())){
                            userModulList.add(modul);

                        }

                    }
                    adpteruser=new Adpteruser(all_user.this,userModulList);
                    adpteruser.notifyDataSetChanged();

                    recyclerView.setAdapter(adpteruser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.refrash) {
            Intent chat = new Intent(all_user.this, com.example.chat.user_profile.class);
            startActivity(chat);
        }


        return super.onOptionsItemSelected(item);

    }
}





