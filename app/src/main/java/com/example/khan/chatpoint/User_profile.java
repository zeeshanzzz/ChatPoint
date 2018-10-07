package com.example.khan.chatpoint;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.net.URI;

public class User_profile extends AppCompatActivity {
    private ImageView profileimageView;
    private Button takebutton,gallerybutton,cancelbutton;
    private EditText profileText;
    private TextView profiletextview,textViewNumber,SatusTextview;
    protected static final int SELECT_PICTURE = 1;
    protected static final int Result_code = 2;
    static final int REQUEST_IMAGE_CAPTURE = 3;
    AlertDialog.Builder alertDialog;
    AlertDialog alertDialog1;
    FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        profileimageView=findViewById(R.id.profile_image);
        profiletextview=findViewById(R.id.profile_name);
        textViewNumber=findViewById(R.id.profile_number);
        SatusTextview=findViewById(R.id.profile_Status);
        DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference().child("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Image=dataSnapshot.child("image").getValue().toString();
                Picasso.get().load(Image).into(profileimageView);
                String Number=dataSnapshot.child("phone").getValue().toString();
                textViewNumber.setText(Number);
                String status=dataSnapshot.child("Status").getValue().toString();
                SatusTextview.setText(status);
                String Name=dataSnapshot.child("Name").getValue().toString();
                profiletextview.setText(Name);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        profileimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog=new AlertDialog.Builder(User_profile.this);
         View view1=getLayoutInflater().inflate(R.layout.verifycode,null);
         takebutton=view1.findViewById(R.id.takebutton);
         gallerybutton=view1.findViewById(R.id.choosebutton);
         cancelbutton=view1.findViewById(R.id.cancelbutton);
         takebutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                 if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                     startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                 }

             }
         });
         cancelbutton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 alertDialog1.cancel();
             }
         });

      gallerybutton.setOnClickListener(new View.OnClickListener() {
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




            //    if(profileimageView.set)
                alertDialog.setView(view1);
                alertDialog1=alertDialog.create();
                alertDialog1.show();


            }


        });


    }

      @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SELECT_PICTURE&&resultCode==RESULT_OK){
            Uri image=data.getData();
            CropImage.activity(image).setAspectRatio(1,1)
                    .start(User_profile.this);

        }
          if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
              Uri imageUri = CropImage.getPickImageResultUri(this, data);


              CropImage.ActivityResult result = CropImage.getActivityResult(data);

              if (resultCode == RESULT_OK) {
                  Uri resultUri = result.getUri();
                  try {
                      Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                      firebaseStorage=FirebaseStorage.getInstance();
                      profileimageView.setImageBitmap(bitmap);
                      alertDialog1.cancel();
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
                      .start(User_profile.this);

             // profileimageView.setImageBitmap(imageBitmap);
              alertDialog1.cancel();

          }

    }

}
