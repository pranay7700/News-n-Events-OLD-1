package com.vaagdevi.newsnevents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Registration extends AppCompatActivity {

    private DatabaseReference databaseref;

    EditText Email;
    EditText Username;
    EditText Password;
    EditText Confirmpassword;

    Button signup;

    TextView alreadyregistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Email=(EditText)findViewById(R.id.etemail);
        Username=(EditText)findViewById(R.id.etusername);
        Password=(EditText)findViewById(R.id.etpassword);
        Confirmpassword=(EditText)findViewById(R.id.etconfirmpassword);

        signup=(Button)findViewById(R.id.btnsignup);

        alreadyregistered=(TextView)findViewById(R.id.tvalreadyregistered);

        final FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();


        databaseref=FirebaseDatabase.getInstance().getReference("News n Events");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email=Email.getText().toString();
                final String username=Username.getText().toString();
                final String password=Password.getText().toString();
                final String confirmpassword=Confirmpassword.getText().toString();

                if (email.isEmpty()&&username.isEmpty()&&password.isEmpty()&&confirmpassword.isEmpty())
                {
                    Toast.makeText(Registration.this,"Empty Fields",Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty())
                {
                    Email.setError("Email is Required");
                    Email.requestFocus();
                }
                else if (username.isEmpty())
                {
                    Username.setError("Username is Required");
                    Username.requestFocus();
                }
                else if (password.isEmpty())
                {
                    Password.setError("Password is Required");
                    Password.requestFocus();
                }
                else if (confirmpassword.isEmpty())
                {
                    Confirmpassword.setError("Confirm Password is Required");
                    Confirmpassword.requestFocus();
                }
                else if (!(email.isEmpty()&&username.isEmpty()&&password.isEmpty()&&confirmpassword.isEmpty()))
                {

                    mAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if ((task.isSuccessful())) {
                                        // Sign in success, update UI with the signed-in user's information

                                        Regdatabase regdatabase=new Regdatabase(email,username,password,confirmpassword);

                                        FirebaseDatabase.getInstance().getReference(databaseref.getKey()).child(mAuth.getCurrentUser().getUid())
                                                .setValue(regdatabase).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                startActivity(new Intent(Registration.this,MainActivity.class));
                                                Toast.makeText(Registration.this,"Signing Up....",Toast.LENGTH_SHORT).show();
                                                finish();


                                            }
                                        });




                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(Registration.this, "Authentication failed."+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                                    }


                                }
                            });


                }
                else
                {
                    Toast.makeText(Registration.this,"Sign Up Error",Toast.LENGTH_LONG).show();
                }

            }
        });

        alreadyregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Registration.this,MainActivity.class));

            }
        });

    }
}
