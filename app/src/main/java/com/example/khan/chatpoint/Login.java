package com.example.khan.chatpoint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Login extends AppCompatActivity implements View.OnClickListener {
    TextInputLayout mail,name,password;

    Button btn;
    private FirebaseAuth auth;
    TextView view;
    private android.support.v7.widget.Toolbar toolbar;
    private ProgressDialog dialog;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar=findViewById(R.id.chat_tool);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mail=findViewById(R.id.Chat_email2);
        name=findViewById(R.id.Name);
        view=findViewById(R.id.textMoreText);
        password=findViewById(R.id.chat_password2);
        btn=findViewById(R.id.Button_create_account);
        auth = FirebaseAuth.getInstance();
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String user_name=name.getEditText().getText().toString();
        String user_mail=mail.getEditText().getText().toString();
        String passord2=password.getEditText().getText().toString();
        if (TextUtils.isEmpty(user_mail)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(passord2)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passord2.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(user_mail,passord2).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(Login.this, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    //dialog.hide();
                    Toast.makeText(Login.this, "Authentication Success." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                    FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                    String id=firebaseUser.getUid();
                    reference=FirebaseDatabase.getInstance().getReference("User").child(id);
                    HashMap<String, String> user=new HashMap<>();
                    user.put("name",user_name);
                    user.put("status","Hi chachi i am here");
                    user.put("image","default");
                    user.put("thumb_image","default");
                    reference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){


                                Toast.makeText(Login.this, "Database Success." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Login.this, Main2Activity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(Login.this, "Not Success." + task.getException(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }

            }
        });



    }

}
