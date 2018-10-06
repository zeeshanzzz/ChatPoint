package com.example.khan.chatpoint;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dpizarro.pinview.library.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Create_account extends AppCompatActivity implements View.OnClickListener {
 private CountryCodePicker picker;
 private EditText phonetext ;
 private FloatingActionButton floatingActionButton;
 private static final String TAG = "FirebasePhoneNumAuth";
 private FirebaseUser firebaseUser;
 public PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
 public String mVerificationId;
 private   FirebaseAuth auth;
 public PhoneAuthProvider.ForceResendingToken resendingToken;
 private Button confirmation1,ResendCode;
 public  String code1;
 private   PinView PinView;
    private TextView topText,lowtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Login();
        picker=findViewById(R.id.phoneauthpicker);
        phonetext=findViewById(R.id.phoneauthtext);
        floatingActionButton=findViewById(R.id.floatingActionButton1);
        picker.registerCarrierNumberEditText(phonetext);
        ResendCode=findViewById(R.id.Force_resend1);
        confirmation1=findViewById(R.id.Cc_button);
        floatingActionButton.setOnClickListener(this);

        topText=findViewById(R.id.TextVerifyphone);
        lowtext=findViewById(R.id.textMoreText);
        confirmation1.setOnClickListener(this);

        PinView=findViewById(R.id.pinView1);
        PinView.setPin(6);
        PinView.setMaskPassword(false);
        PinView.setDeleteOnClick(true);

        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(getApplicationContext());

        //  mPinView.setNativePinBox(true);
        //  mPinView.setSplit("**");
        //  mPinView.setColorTitles(Color.rgb(255,255,255));
        // mPinView.setColorTextPinBoxes(Color.rgb(255,255,255));
        //mPinView.setColorSplit(Color.rgb(255,255,255));
        PinView.setOnCompleteListener(new PinView.OnCompleteListener() {
            @Override
            public void onComplete(boolean completed, String pinResults) {
                if(completed)
                {
                    code1=pinResults;
                }
            }
        });


        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(Create_account.this, "Code not Sent", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                mVerificationId=s;
                resendingToken=forceResendingToken;
                Log.d(TAG,"codesent:"+mVerificationId);
            }
        };

    }

    @Override
    public void onClick(View view) {
        if(view==floatingActionButton){
        Phoneauth();
        visibility();


        }
        if(view==confirmation1){
            PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId,code1);
            signInWithPhoneAuthCredential(credential);


        }
        if(view==ResendCode){
            resendVerificationCode(picker.getFullNumberWithPlus(),
                    resendingToken);

        }

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "code verified signIn successful");
                            final FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                            if(firebaseUser!=null){
                                String currentdate=DateFormat.getDateTimeInstance().format(new Date());
                                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("User").child(firebaseUser.getUid());
                            //    reference.setValue(currentdate);
                                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(!dataSnapshot.exists()){
                                            HashMap<String,Object> map=new HashMap<>();
                                            map.put("phone",firebaseUser.getPhoneNumber());
                                            map.put("name",firebaseUser.getPhoneNumber());
                                            map.put("image","Default");
                                            map.put("Thumbnail","default");
                                            reference.updateChildren(map);

                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                           Login();

                        } else {
                            Log.w(TAG, "code verification failed", task.getException());
                            if (task.getException() instanceof
                                    FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }



    public void  Phoneauth(){
        String Nb=picker.getFullNumberWithPlus();
        if (!TextUtils.isEmpty(Nb)) {

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    Nb,  // Phone number to verify
                    10,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);

        }
        else{
            Toast.makeText(this,"Please Enter the valid Number",Toast.LENGTH_LONG).show();

        }





    }

    @SuppressLint("RestrictedApi")
    private void visibility(){
        confirmation1.setVisibility(View.VISIBLE);
       PinView.setVisibility(View.VISIBLE);
        topText.setVisibility(View.GONE);
        lowtext.setVisibility(View.GONE);
        floatingActionButton.setVisibility(View.GONE);
        picker.setVisibility(View.GONE);
        phonetext.setVisibility(View.GONE);
        ResendCode.setVisibility(View.VISIBLE);
    }
    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        confirmation1.setEnabled(true);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    private void Login(){

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null){
            Intent intent=new Intent(Create_account.this,Chat_Activity.class);
            startActivity(intent);
            finish();



        }
    }
    private  void getpermission(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            requestPermissions(new String[]{Manifest.permission.WRITE_CONTACTS,Manifest.permission.READ_CONTACTS},1);
        }
    }
}
