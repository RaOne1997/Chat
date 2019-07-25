package com.example.chat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginAc extends AppCompatActivity {
    FirebaseAuth mAuth;
    ProgressDialog progdill;
    EditText log_email,log_pass;
    Button Log_btn;
    TextView goto_register,forgot_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Login");
      //  actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorShowWhite)));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        log_email=findViewById(R.id.log_Enail_R);
        log_pass=findViewById(R.id.login_Password_R);
        Log_btn=findViewById(R.id.login_Ac);
        progdill=new ProgressDialog(this);
        goto_register=findViewById(R.id.not_have_all);
        forgot_pass=findViewById(R.id.forgot_pass);






        goto_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent charint=new Intent(loginAc.this,regirester.class);
                startActivity(charint);
                charint.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            }
        });

        Log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String email =log_email.getText().toString();
                String login_pass = log_pass.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                    log_email.setError("Invalid ");
                    log_pass.setFocusable(true);

                }else {


                    loginUser(email, login_pass);
                }

            }
        });

        forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showpassrecover();

            }
        });
    }

    private void showpassrecover() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Password Recovery");

        LinearLayout linearLayout=new LinearLayout(this);
        final EditText Emailet=new EditText(this);
        Emailet.setHint("Email");
        Emailet.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);



        Emailet.setMinEms(10);
        linearLayout .addView(Emailet);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email=Emailet.getText().toString().trim();
                beingRecovery(email);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        builder.create().show();



    }

    private void beingRecovery(String email) {
        progdill.setMessage("sending....");
        progdill.show();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progdill.dismiss();
           if(task.isSuccessful()){
               Toast.makeText(loginAc.this,"sending",Toast.LENGTH_SHORT).show();

           }else {
               Toast.makeText(loginAc.this,"Failed",Toast.LENGTH_SHORT).show();

           }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progdill.dismiss();
                Toast.makeText(loginAc.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();



            }
        });
    }

    private void loginUser(String email, String login_pass) {
        progdill.show();
        progdill.setMessage("loggin.....");

        mAuth.signInWithEmailAndPassword(email,login_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progdill.dismiss();
                    FirebaseUser user=mAuth.getCurrentUser();

                    Intent mainint= new Intent(loginAc.this , DashBoard.class);
                    mainint.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainint);

                }else{
                    progdill.dismiss();

                    Toast.makeText(loginAc.this,"Email Or password is wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
