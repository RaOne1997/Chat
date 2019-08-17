package com.example.chat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class about extends AppCompatActivity {
        TextView aboutE;
        LinearLayout aboutclick;
        ProgressDialog progress;
        FirebaseUser c_user;
        FirebaseDatabase firebaseDatabasep;
        DatabaseReference  databaseReferencep;
        FirebaseAuth mAuth;

   ActionBar mcation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutE=findViewById(R.id.AboutL);
        aboutclick =findViewById(R.id.Aboutlay);

        mAuth = FirebaseAuth.getInstance();
        c_user = mAuth.getCurrentUser();
        firebaseDatabasep = FirebaseDatabase.getInstance();
        databaseReferencep = firebaseDatabasep.getReference("users");

        progress= new ProgressDialog(this);
        mcation = getSupportActionBar();
        mcation.setTitle("About");
        mcation.setDisplayHomeAsUpEnabled(true);
        mcation.setDisplayShowHomeEnabled(true);
        aboutclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About();
            }
        });

         String status_value=getIntent().getStringExtra("Status_value");
        aboutE.setText(status_value);


    }

    private void About() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update");
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10, 10, 10);
        final EditText editText = new EditText(this);
        editText.setHint("Enter");
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    progress.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("about", value);
                    databaseReferencep.child(c_user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    progress.dismiss();
                                    Toast.makeText(about.this, "update.....", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(about.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(about.this, "Please Enter" , Toast.LENGTH_SHORT).show();


                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
    }

