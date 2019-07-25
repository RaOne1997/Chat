package com.example.chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class regirester extends AppCompatActivity {
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSigenClient;
     TextView chage_login_act;
    EditText mEmail,mPassword;
    Button mRegir_btn;
    FirebaseAuth mAuth;
    ProgressDialog prg_dig;
    DatabaseReference mdkatabase;
    SignInButton gmeil_signin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reg_act);




        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Create new account");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        chage_login_act=findViewById(R.id.have_all);
        mEmail=findViewById(R.id.Enail_R);
        mPassword=findViewById(R.id.Password_R);
        mRegir_btn=findViewById(R.id.submit);
        gmeil_signin=findViewById(R.id.gamil_signin);


        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSigenClient= GoogleSignIn.getClient(this,gso);
        mAuth=FirebaseAuth.getInstance();
        prg_dig=new ProgressDialog(regirester.this);
        prg_dig.setMessage("plz w8t");

        mRegir_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String Email=mEmail.getText().toString().trim();
                String Pass=mPassword.getText().toString().trim();
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){

                    mEmail.setError("Invilid ");

                    mEmail.setFocusable(true);
                }else if (mPassword.length()<6){

                    mPassword.setError("password length atlas 6  char");

                    mPassword.setFocusable(true);

                }
                else{
                    Register_user(Email, Pass);
                }




            }
        });

        chage_login_act.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent changlo=new Intent(regirester.this,loginAc.class);
                changlo.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                finish();
            }
        });
        gmeil_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prg_dig.setMessage("Please wait");
                Intent signInTntent =mGoogleSigenClient.getSignInIntent();
                startActivityForResult(signInTntent,RC_SIGN_IN);
            }
        });


    }

    private void Register_user(String Email, String Pass) {


        prg_dig.show();

        mAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {


                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
             /*    if(current_user.isEmailVerified()){


                    }
                    else{
                        current_user.sendEmailVerification();
                    }*/

                    String  email=current_user.getEmail();
                    String uid=current_user.getUid();
                    HashMap<Object,String> user_map=new HashMap<>();
                    user_map.put("email",email);
                    user_map.put("uid",uid);
                    user_map.put("name","");
                    user_map.put("phone","");
                    user_map.put("cover","");
                    user_map.put("image","");
                    user_map.put("about","hey_i_am_using_schat");
                    mdkatabase=FirebaseDatabase.getInstance().getReference().child("users").child(uid);

                    mdkatabase.setValue(user_map);

                    Toast.makeText(regirester.this,"RRRREEE....\n"+ current_user.getEmail(),Toast.LENGTH_SHORT).show();
                    Intent prof=new Intent(regirester.this,DashBoard.class);
                    prof.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(prof);



                    prg_dig.dismiss();


                } else {
                    Toast.makeText(regirester.this, "Enter correct information", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                prg_dig.dismiss();
                Toast.makeText(regirester.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public boolean onSupportNavigateUp () {

        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(data);
            try{
                GoogleSignInAccount account=task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            }catch (ApiException e){
                Toast.makeText(regirester.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user = mAuth.getCurrentUser();
                        if (task.getResult().getAdditionalUserInfo().isNewUser()){

                            String  email=user.getEmail();
                            String uid=user.getUid();
                            HashMap<Object,String> user_map=new HashMap<>();
                            user_map.put("email",email);
                            user_map.put("uid",uid);
                            user_map.put("name","");
                            user_map.put("phone","");
                            user_map.put("cover","");
                            user_map.put("image","");
                            user_map.put("about","hey_i_am_using_schat");
                            mdkatabase=FirebaseDatabase.getInstance().getReference().child("users").child(uid);
                            mdkatabase.setValue(user_map);

                        }



                            Toast.makeText(regirester.this,""+user.getEmail(),Toast.LENGTH_SHORT).show();
                            Intent soogle=new Intent(regirester.this,DashBoard.class);
                            startActivity(soogle);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(regirester.this,"Login Failed",Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(regirester.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

