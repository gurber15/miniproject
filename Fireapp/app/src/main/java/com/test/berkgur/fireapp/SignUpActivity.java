package com.test.berkgur.fireapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextPassword;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextEmail=findViewById(R.id.editTextEmail);
        editTextPassword=findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);



        //instantiate the firebase auth

        mAuth=FirebaseAuth.getInstance();


        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
    }


    private void registerUser(){
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();

        //email should not be empty

        if(email.isEmpty()){
            editTextEmail.setError("Missing Email input");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is missing");
            editTextEmail.requestFocus();
            return;
        }

        //check valid email address so people dont write weird stuff

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Not Valid email");
            editTextEmail.requestFocus();
            return;

        }

        //minimum length of firebase passwords are 6
        if(password.length()<6)
        {
            editTextPassword.setError("Minimum length should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);


                if (task.isSuccessful()){

                    Intent intent = new Intent(SignUpActivity.this,ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);// clear all activities on top of stack
                    //if we dont do this back button in profile page will bring user back to login activity
                    startActivity(intent);
                    //Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_SHORT).show();
                }
                else{
                    //if the exception occured as FireBase Collison Exception (meaning user is already registered) or if there was another error

                    if(task.getException() instanceof FirebaseAuthUserCollisionException)
                    {
                        Toast.makeText(getApplicationContext(),"User already registered ",Toast.LENGTH_SHORT).show();

                    }else
                    {
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });



    }
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textViewLogin:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;




            case R.id.buttonSignUp:
                registerUser();
                break;


        }

    }
}

