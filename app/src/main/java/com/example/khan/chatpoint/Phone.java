package com.example.khan.chatpoint;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Phone extends AppCompatActivity implements View.OnClickListener {
  private EditText nb;
    Button Myphn;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private CountryCodePicker codePicker;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        FirebaseApp.initializeApp(this);
        nb=findViewById(R.id.Phone);

        Myphn=findViewById(R.id.Button_verify);
        codePicker=findViewById(R.id.Picker);
        codePicker.registerCarrierNumberEditText(nb);
        Myphn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String number;
        number=codePicker.getFullNumberWithPlus();



         PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e){

            }
        };


    }


    }



