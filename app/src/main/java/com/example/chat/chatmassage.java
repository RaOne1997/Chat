package com.example.chat;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatmassage extends AppCompatActivity {
    FirebaseAuth mAuth;
    Toolbar toolbar;
EditText massage;
RecyclerView chat_mass;
ImageButton send_image, attachment;
    TextView Username,status;
    CircleImageView ivatr;
    DatabaseReference mref;
    String usernae;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatmassage);

        toolbar = findViewById(R.id.toolbarchat);
        setSupportActionBar(toolbar);
        setTitle("");
        Username = findViewById(R.id.textvire);
        status=findViewById(R.id.online_status);
        attachment=findViewById(R.id.attag);
        ivatr = findViewById(R.id.imgc);
       massage=findViewById(R.id.massage);
        send_image=findViewById(R.id.send_mess);

        mAuth=FirebaseAuth.getInstance();



    }
    private void checkuserstatus(){

        FirebaseUser user  =mAuth.getCurrentUser();

        if (user!=null){

            //display user  info



        }else{
            startActivity(new Intent(chatmassage.this,MainActivity.class));
            finish();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();
        if (id == R.id.viewCOnt) {
            Intent prof = new Intent(chatmassage.this, com.example.chat.prof.class);
            startActivity(prof);
        }
        return super.onOptionsItemSelected(item);
    }
}
