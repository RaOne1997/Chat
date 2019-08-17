package com.example.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

public class user_profile extends AppCompatActivity {
    CircleImageView dp;
    ImageView cover;
    TextView number,name,about;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pfofile);
        dp=findViewById(R.id.C_Dp_pic);
        cover=findViewById(R.id.C_cover_pic);
        name=findViewById(R.id.User_name);
        number=findViewById(R.id.Cnumber);
        about=findViewById(R.id.CAbout);



    }
}
