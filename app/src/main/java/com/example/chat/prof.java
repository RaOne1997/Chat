package com.example.chat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat.image_view.ui.profile_image;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class prof extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseUser c_user;
    boolean writestorageAcceoted;
    StorageReference profile_storage = getInstance().getReference();
    FirebaseDatabase firebaseDatabasep;
    DatabaseReference databaseReferencep;
    private static final int Storage_request_code = 200;
    private static final int Imge_pick_Gallery_code = 300;
    TextView user_name, user_about, user_no, user_email;
    ImageView cover;
    CircleImageView avater;

    String storagepath="User_profile_cover";
    ProgressDialog procd;
    String cameraPermission[];
    String storagePermission[];
    ImageButton dpchange,changec;
    LinearLayout changename,  changeno, changeAbout;
    Uri image_uri;
    String profileorcover;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);


        mAuth = FirebaseAuth.getInstance();
        c_user = mAuth.getCurrentUser();
        firebaseDatabasep = FirebaseDatabase.getInstance();
        databaseReferencep = firebaseDatabasep.getReference("users");

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        user_name = findViewById(R.id.Name_disp);
        user_email = findViewById(R.id.user_email);
        user_about = findViewById(R.id.about_W);
        user_no = findViewById(R.id.number);
        cover = findViewById(R.id.cover_pic);
        avater = findViewById(R.id.Dp_pic);
        procd = new ProgressDialog(this);
        dpchange = findViewById(R.id.Dp_change);
        changename = findViewById(R.id.editdisplay);
        changeno = findViewById(R.id.change_no);
        changeAbout = findViewById(R.id.about_btn);
        changec = findViewById(R.id.cover_change);

        Query query = databaseReferencep.orderByChild("email").equalTo(c_user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String phone = "" + ds.child("phone").getValue();
                    String about = "" + ds.child("about").getValue();
                    String image = "" + ds.child("image").getValue();
                    String coverpic = "" + ds.child("cover").getValue();

                    user_name.setText(name);
                    user_email.setText(email);
                    user_about.setText(about);
                    user_no.setText(phone);
                    try {

                        Picasso.get().load(image).placeholder(R.drawable.images).into(avater);

                    } catch (Exception e) {

                        //default image
                        Picasso.get().load(R.drawable.images).into(avater);


                    }
                    try {


                        Picasso.get().load(coverpic).into(cover);

                    } catch (Exception e) {

                        //default image
                        Picasso.get().load(R.drawable.cover).into(cover);


                    }

                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        dpchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procd.setMessage("Uploding profile");
                if (!checkstoragepermission()) {
                    requestStoragepremission();
                } else {
                    pickFromGallery();
                }
                profileorcover = "image";
            }
        });

        changec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procd.setMessage("Uploding Cover");
                if (!checkstoragepermission()) {
                    requestStoragepremission();
                } else {
                    pickFromGallerycover();
                }
                profileorcover = "cover";
            }
        });
        changeno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procd.setMessage("Uploding Mobile");
                showNameAboutNumberUplode("phone");

            }
        });
        changeAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                procd.setMessage("Uploding About");
                String status_value= user_about.getText().toString();
                Intent ab=new Intent(prof.this,about.class);
                ab.putExtra("Status_value",status_value);
                startActivity(ab);

            }
        });
        changename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procd.setMessage("Uploding Name");
                showNameAboutNumberUplode("name");

            }
        });

        avater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pview= new Intent(prof.this, profile_image.class);
                startActivity(pview);
            }
        });

        return;

    }




    private boolean checkstoragepermission() {

        boolean result = ContextCompat.checkSelfPermission(prof.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void requestStoragepremission() {
        requestPermissions(storagePermission, Storage_request_code);


    }

   /* private boolean checkCamerapermission() {

        boolean result = ContextCompat.checkSelfPermission(prof.this, Manifest.permission.CAMERA)
                == (PackageManager.PERMISSION_GRANTED);

        boolean result1 = ContextCompat.checkSelfPermission(prof.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCamerapremission() {
        requestPermissions(cameraPermission, Camera_request_code);*/





    private void showNameAboutNumberUplode(final String Key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Update" + Key);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10, 10, 10);
        final EditText editText = new EditText(this);
        editText.setHint("Enter" + Key);
        linearLayout.addView(editText);
        builder.setView(linearLayout);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = editText.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    procd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(Key, value);
                    databaseReferencep.child(c_user.getUid()).updateChildren(result)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    procd.dismiss();
                                    Toast.makeText(prof.this, "update.....", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(prof.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    Toast.makeText(prof.this, "Please Enter" + Key, Toast.LENGTH_SHORT).show();


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


  /*  private void showImagePicDialog() {
        String option[] = {"camera", "Gallery", "remove"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("choase Action");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    if (!checkCamerapermission()) {
                        requestCamerapremission();
                    } else {
                       pickFromCamera();
                    }
                } else if (which == 1) {
                    if (!checkstoragepermission()) {
                        requestStoragepremission();
                    } else {
                        pickFromGallery();
                    }

                } else if (which == 2) {
                    Picasso.get().load(R.drawable.images).into(avater);


                }
            }
        });
        builder.create().show();


  /*  private void pickFromCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "temp pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "temp DESCRIPTION");
        image_uri = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        Intent cameraInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraInt.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraInt, Image_pick_camera_code);
    }*/

    private void pickFromGallery() {

       CropImage.activity()
               .setAspectRatio(1,1)

               .setGuidelines(CropImageView.Guidelines.ON)
               .start(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Storage_request_code) {
            if (grantResults.length > 0) {
                writestorageAcceoted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (writestorageAcceoted) {

                    pickFromGallery();
                } else {
                    Toast.makeText(this, "please enable  storage Permission", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
       if (requestCode == Imge_pick_Gallery_code) {

            image_uri = data.getData();

            CropImage.activity(image_uri)
                    .setAspectRatio(4, 2)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                procd.setTitle("Uploding");
                procd.setMessage("plz w8t");
                procd.show();

              Uri Resultur = result.getUri();



                procd.show();
                String filepathandName = storagepath+""+profileorcover+""+c_user.getUid();

                StorageReference storageReference=profile_storage.child(filepathandName);
                storageReference.putFile(Resultur)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                while (!uriTask.isSuccessful());
                                Uri download_uri=uriTask.getResult();
                                if (uriTask.isSuccessful()){
                                    HashMap<String,Object>
                                            result=new HashMap<>();
                                    result.put(profileorcover,download_uri.toString());

                                    databaseReferencep.child(c_user.getUid()).updateChildren(result)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    procd.dismiss();
                                                    Toast.makeText(prof.this,"Image Uploded..........",Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            procd.dismiss();
                                            Toast.makeText(prof.this,"error..........",Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }else {
                                    procd.dismiss();

                                    Toast.makeText(prof.this,"some error Uploded..........",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        procd.dismiss();
                    }
                });






                if (resultCode == Imge_pick_Gallery_code) {

            }


                super.onActivityResult(requestCode, resultCode, data);

            }



        }




    }
    private void pickFromGallerycover() {
         Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery, "selectpic"), Imge_pick_Gallery_code);

    }
}

