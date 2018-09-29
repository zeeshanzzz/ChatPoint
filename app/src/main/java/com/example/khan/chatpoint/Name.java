package com.example.khan.chatpoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;
import java.util.HashMap;

public class Name extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button button;
    private EditText nameText;
    public ImageView profileimage;
    public Button takebutton1,gallerybutton2,cancelbutton3;
    protected static final int SELECT_PICTURE = 1;
    protected static final int Result_code = 2;
    static final int REQUEST_IMAGE_CAPTURE = 3;
    AlertDialog.Builder alertDialog;
    AlertDialog alertDialog2;
    public String name;
    FirebaseAuth auth;
    DatabaseReference database;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        button=findViewById(R.id.profileNext);
        nameText=findViewById(R.id.profileName);
        profileimage=findViewById(R.id.profilePhoto);
        toolbar=findViewById(R.id.include1);
        getSupportActionBar();
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chat Point");
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                button.setVisibility(View.VISIBLE);


                //  nameText.setText("");



            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameText.getText().toString();
                FirebaseAuth.getInstance().getCurrentUser().getUid();
                reference=FirebaseDatabase.getInstance().getReference().child("User").child( FirebaseAuth.getInstance().getCurrentUser().getUid());
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("Name",name);
                        reference.updateChildren(map);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });





        profileimage.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if(view==profileimage) {
            alertDialog = new AlertDialog.Builder(Name.this);
            View view1 = getLayoutInflater().inflate(R.layout.verifycode, null);
            takebutton1 = view1.findViewById(R.id.takebutton);
            gallerybutton2 = view1.findViewById(R.id.choosebutton);
            cancelbutton3 = view1.findViewById(R.id.cancelbutton);
            takebutton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    }

                }
            });
            cancelbutton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog2.cancel();
                }
            });

            gallerybutton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,
                            "Select Picture"), SELECT_PICTURE);
            /* CropImage.activity()
                      .setGuidelines(CropImageView.Guidelines.ON)
                      .start(User_profile.this);*/
                }

            });
            alertDialog.setView(view1);
            alertDialog2=alertDialog.create();
            alertDialog2.show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_PICTURE&&resultCode==RESULT_OK){
            Uri image=data.getData();
            CropImage.activity(image).setAspectRatio(1,1)
                    .start(Name.this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);


            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    profileimage.setImageBitmap(bitmap);
                    alertDialog2.cancel();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Uri myimage=data.getData();
            Bundle extras = data.getExtras();
            // Bitmap imageBitmap = (Bitmap) extras.get("data");
            CropImage.activity(myimage).setAspectRatio(1,1)
                    .start(Name.this);

            // profileimageView.setImageBitmap(imageBitmap);
            alertDialog2.cancel();

        }

    }

}
