package com.example.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mregires,login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mregires=findViewById(R.id.reg_btn);
        login_btn=findViewById(R.id.login_btn);


        mregires.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Reg_int=new Intent(MainActivity.this, regirester.class);
                startActivity(Reg_int);



                    }
                });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,loginAc.class));

            }
        });
    }


}
